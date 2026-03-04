#!/bin/sh
# 编译鸿蒙 libshared.so（Kotlin/Native OHOS 产物）
# 使用 settings.2.0.ohos.gradle.kts 与 Kotlin 2.0.21-KBA；成功则拷贝到 entry/libs 与 thirdparty
# 运行鸿蒙应用前可由 runOhosApp.sh 自动调用

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

# 环境变量（与 2.0_ohos_test_publish.sh 一致）
export KUIKLY_AGP_VERSION="${KUIKLY_AGP_VERSION:-7.4.2}"
export KUIKLY_KOTLIN_VERSION="${KUIKLY_KOTLIN_VERSION:-2.0.21-KBA-004}"

BUILD_TASK=""
SO_SOURCE=""
HDR_SOURCE=""

if [ -d "demo" ]; then
  BUILD_TASK=":demo:linkSharedReleaseSharedOhosArm64"
  SO_SOURCE="demo/build/bin/ohosArm64/sharedReleaseShared/libshared.so"
  HDR_SOURCE="demo/build/bin/ohosArm64/sharedReleaseShared/libshared_api.h"
elif [ -d "shared" ]; then
  # 本仓库无 :demo 时尝试 :shared（需 shared 已配置 ohosArm64 且用 settings.2.0.ohos 构建）
  BUILD_TASK=":shared:linkReleaseSharedLibOhosArm64"
  SO_SOURCE="shared/build/bin/ohosArm64/releaseShared/libshared.so"
  HDR_SOURCE="shared/build/bin/ohosArm64/releaseShared/libshared_api.h"
fi

if [ -z "$BUILD_TASK" ]; then
  echo "[build_libshared] 跳过: 未找到 demo 或 shared 目录"
  exit 0
fi

echo "[build_libshared] 编译 OHOS libshared: $BUILD_TASK"
if ! ./gradlew -c settings.2.0.ohos.gradle.kts "$BUILD_TASK" --no-daemon -q 2>/dev/null; then
  echo "[build_libshared] 编译未成功或任务不可用，跳过（将使用 entry 内置 NAPI stub）"
  exit 0
fi

# 再次检查产物路径（不同模块产出路径可能不同）
if [ -n "$SO_SOURCE" ] && [ ! -f "$SO_SOURCE" ]; then
  SO_SOURCE=""
fi
if [ -n "$HDR_SOURCE" ] && [ ! -f "$HDR_SOURCE" ]; then
  HDR_SOURCE=""
fi

if [ -f "$SO_SOURCE" ]; then
  cp "$SO_SOURCE" "$OHOS_ENTRY_LIBS/libshared.so"
  echo "[build_libshared] 已拷贝 libshared.so -> $OHOS_ENTRY_LIBS"
fi
if [ -f "$HDR_SOURCE" ]; then
  cp "$HDR_SOURCE" "$OHOS_ENTRY_API/libshared_api.h"
  echo "[build_libshared] 已拷贝 libshared_api.h -> $OHOS_ENTRY_API"
fi
echo "[build_libshared] 完成"
