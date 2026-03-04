#!/bin/sh
# KaMPKit OHOS：先编译鸿蒙 libshared.so（若有），再构建并安装
# 使用前：1) 启动模拟器或连接真机  2) 若签名失败，用 DevEco Studio 打开 ohos 目录并 Run

set -e
OHOS_ROOT="$(cd "$(dirname "$0")" && pwd)"
REPO_ROOT="$(cd "$OHOS_ROOT/.." && pwd)"

# [0/3] 自动编译鸿蒙 libshared.so（demo 或 shared 产出后拷贝到 entry）
if [ -x "$OHOS_ROOT/build_libshared.sh" ]; then
  "$OHOS_ROOT/build_libshared.sh" || true
fi

cd "$OHOS_ROOT"

# 优先使用环境变量，否则常见 DevEco 路径
SDK_HOME="${DEVECO_SDK_HOME:-/Applications/Apps/DevEco-Studio.app/Contents/sdk}"
if [ ! -d "$SDK_HOME" ]; then
  SDK_HOME="/Applications/DevEco-Studio.app/Contents/sdk"
fi
export DEVECO_SDK_HOME="$SDK_HOME"
export PATH="$SDK_HOME:$SDK_HOME/../jbr/Contents/Home/bin:$SDK_HOME/../tools/node/bin:$SDK_HOME/../tools/ohpm/bin:$SDK_HOME/../tools/hvigor/bin:$PATH"

echo "[1/3] ohpm install..."
ohpm install --all

echo "[2/3] build HAP..."
node "$SDK_HOME/../tools/hvigor/bin/hvigorw.js" --mode module -p module=entry@default -p product=default -p requiredDeviceType=phone assembleHap --analyze=normal --parallel

HAP_DIR="entry/build/default/outputs/default"
SIGNED_HAP="$HAP_DIR/entry-default-signed.hap"
UNSIGNED_HAP="$HAP_DIR/entry-default-unsigned.hap"

if [ -f "$SIGNED_HAP" ]; then
  HAP="$SIGNED_HAP"
elif [ -f "$UNSIGNED_HAP" ]; then
  HAP="$UNSIGNED_HAP"
  echo "Note: 使用未签名 HAP；若安装失败请在 DevEco Studio 中打开 ohos 目录并 Run（自动签名）。"
else
  echo "Error: 未找到 HAP，请检查构建日志。"
  exit 1
fi

HDC_BIN="$SDK_HOME/default/openharmony/toolchains/hdc"
if [ ! -x "$HDC_BIN" ]; then
  HDC_BIN="hdc"
fi

targets=$("$HDC_BIN" list targets 2>/dev/null || true)
if [ -z "$targets" ]; then
  echo "Error: 未检测到设备。请先启动模拟器或连接真机并开启 USB 调试。"
  exit 2
fi

echo "[3/3] 安装并启动..."
BUNDLE="com.tencent.kuiklyohosapp"
for tid in $targets; do
  echo "  -> 设备 $tid"
  "$HDC_BIN" -t "$tid" install -r "$HAP" 2>/dev/null || "$HDC_BIN" -t "$tid" install "$HAP"
  "$HDC_BIN" -t "$tid" shell aa start -a EntryAbility -b "$BUNDLE"
done
echo "Done. 若启动失败，请用 DevEco Studio 打开 ohos 目录并点击 Run（会使用自动签名）。"
