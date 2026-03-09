#!/bin/sh
# 编译鸿蒙 libshared.so（Kotlin/Native OHOS 产物）
#
# 构建变体（默认 debug，支持 DevEco 打断点）：
#   ./ohos/build_libshared.sh                          # debug（含调试符号）
#   LIBSHARED_VARIANT=release ./ohos/build_libshared.sh  # release（优化，去符号）
#
# 优先级：
#   1. KuiklyUI/demo（根目录下的 KuiklyUI 子目录或符号链接，含 settings.2.0.ohos.gradle.kts）
#   2. demo/（根目录下的本地 demo 模块）
#   3. shared/（需已在 build.2.0.ohos.gradle.kts 中配置 ohosArm64 目标）
#
# 运行鸿蒙应用前可由 runOhosApp.sh 自动调用。

set -e
REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$REPO_ROOT"

# Java 17
export JAVA_HOME="${JAVA_HOME:-$(/usr/libexec/java_home -v 17 2>/dev/null)}"
if [ -z "$JAVA_HOME" ]; then
  echo "[build_libshared] 跳过: 需要 Java 17 (JAVA_HOME)"
  exit 0
fi

OHOS_ENTRY_LIBS="ohos/entry/libs/arm64-v8a"
OHOS_ENTRY_API="ohos/entry/src/main/cpp/thirdparty/biz_entry"
mkdir -p "$OHOS_ENTRY_LIBS"
mkdir -p "$OHOS_ENTRY_API"

# 构建变体：debug（默认，含调试符号，支持 DevEco 打断点）或 release
LIBSHARED_VARIANT="${LIBSHARED_VARIANT:-debug}"
export KUIKLY_AGP_VERSION="${KUIKLY_AGP_VERSION:-7.4.2}"
export KUIKLY_KOTLIN_VERSION="${KUIKLY_KOTLIN_VERSION:-2.0.21-KBA-010}"

# 根据变体确定 Gradle 任务名与产物路径中的大小写前缀
# debug → Debug / debugShared；release → Release / releaseShared
if [ "$LIBSHARED_VARIANT" = "release" ]; then
  VARIANT_CAP="Release"
  VARIANT_DIR="sharedReleaseShared"
  VARIANT_DIR_SHARED="releaseShared"
  echo "[build_libshared] 构建 release 版 libshared.so（优化版，已剥离调试符号）"
else
  VARIANT_CAP="Debug"
  VARIANT_DIR="sharedDebugShared"
  VARIANT_DIR_SHARED="debugShared"
  echo "[build_libshared] 构建 debug 版 libshared.so（含调试符号，可在 DevEco 打断点）"
fi

BUILD_DIR=""
BUILD_TASK=""
SO_SOURCE=""
HDR_SOURCE=""
SETTINGS_FILE="settings.2.0.ohos.gradle.kts"

# 1. KaMPKit 自身 shared 模块（settings.kampkit.ohos.gradle.kts + build.2.0.ohos.gradle.kts）
#    生成包含 KaMPKit 业务逻辑（状态管理）的 libshared.so
if [ -f "settings.kampkit.ohos.gradle.kts" ] && [ -d "shared" ]; then
  SETTINGS_FILE="settings.kampkit.ohos.gradle.kts"
  BUILD_TASK=":shared:link${VARIANT_CAP}SharedOhosArm64"
  SO_SOURCE="shared/build/bin/ohosArm64/${VARIANT_DIR_SHARED}/libshared.so"
  HDR_SOURCE="shared/build/bin/ohosArm64/${VARIANT_DIR_SHARED}/libshared_api.h"
# 2. KuiklyUI 子目录（符号链接），从其目录执行（生成 KuiklyUI 框架的 libshared.so）
elif [ -d "KuiklyUI/demo" ] && [ -f "KuiklyUI/settings.2.0.ohos.gradle.kts" ]; then
  BUILD_DIR="KuiklyUI"
  BUILD_TASK=":demo:linkShared${VARIANT_CAP}SharedOhosArm64"
  SO_SOURCE="KuiklyUI/demo/build/bin/ohosArm64/${VARIANT_DIR}/libshared.so"
  HDR_SOURCE="KuiklyUI/demo/build/bin/ohosArm64/${VARIANT_DIR}/libshared_api.h"
# 3. 本地 demo 目录
elif [ -d "demo" ]; then
  SETTINGS_FILE="settings.2.0.ohos.gradle.kts"
  BUILD_TASK=":demo:linkShared${VARIANT_CAP}SharedOhosArm64"
  SO_SOURCE="demo/build/bin/ohosArm64/${VARIANT_DIR}/libshared.so"
  HDR_SOURCE="demo/build/bin/ohosArm64/${VARIANT_DIR}/libshared_api.h"
fi

if [ -z "$BUILD_TASK" ]; then
  echo "[build_libshared] 跳过: 未找到可用的编译模块（KuiklyUI/demo、demo 或 shared）"
  exit 0
fi

echo "[build_libshared] 执行任务: $BUILD_TASK"

BUILD_OK=0
if [ -n "$BUILD_DIR" ]; then
  # 从子目录（KuiklyUI）执行，使用其自身的 settings
  if (cd "$BUILD_DIR" && \
      KUIKLY_AGP_VERSION="$KUIKLY_AGP_VERSION" \
      KUIKLY_KOTLIN_VERSION="$KUIKLY_KOTLIN_VERSION" \
      ./gradlew -c settings.2.0.ohos.gradle.kts "$BUILD_TASK" --no-daemon -q 2>/dev/null); then
    BUILD_OK=1
  fi
else
  if KUIKLY_AGP_VERSION="$KUIKLY_AGP_VERSION" \
     KUIKLY_KOTLIN_VERSION="$KUIKLY_KOTLIN_VERSION" \
     ./gradlew -c "$SETTINGS_FILE" "$BUILD_TASK" --no-daemon -q 2>/dev/null; then
    BUILD_OK=1
  fi
fi

if [ "$BUILD_OK" != "1" ]; then
  echo "[build_libshared] 编译未成功或任务不可用，跳过（将使用 entry 内置 NAPI stub）"
  exit 0
fi

# 检查产物是否存在
if [ -n "$SO_SOURCE" ] && [ ! -f "$SO_SOURCE" ]; then
  SO_SOURCE=""
fi
if [ -n "$HDR_SOURCE" ] && [ ! -f "$HDR_SOURCE" ]; then
  HDR_SOURCE=""
fi

if [ -f "$SO_SOURCE" ]; then
  cp "$SO_SOURCE" "$OHOS_ENTRY_LIBS/libshared.so"
  echo "[build_libshared] 已拷贝 libshared.so ($LIBSHARED_VARIANT) -> $OHOS_ENTRY_LIBS"
fi
if [ -f "$HDR_SOURCE" ]; then
  cp "$HDR_SOURCE" "$OHOS_ENTRY_API/libshared_api.h"
  echo "[build_libshared] 已拷贝 libshared_api.h -> $OHOS_ENTRY_API"
  # 删除旧的根目录头文件（若存在会优先于 thirdparty/biz_entry/ 被 CMake 找到，导致 ABI 不匹配）
  OLD_HDR="ohos/entry/src/main/cpp/libshared_api.h"
  if [ -f "$OLD_HDR" ]; then
    rm "$OLD_HDR"
    echo "[build_libshared] 已删除过期头文件 $OLD_HDR"
  fi
fi
echo "[build_libshared] 完成 ($LIBSHARED_VARIANT)"
