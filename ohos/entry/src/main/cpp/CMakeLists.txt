cmake_minimum_required(VERSION 3.5)
project(kuikly_entry)

set(CMAKE_CXX_STANDARD 17)
if(DEFINED PACKAGE_FIND_FILE)
    include(${PACKAGE_FIND_FILE})
endif()

add_library(kuikly_entry SHARED napi_init.cpp)
target_include_directories(kuikly_entry PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${CMAKE_CURRENT_SOURCE_DIR}/thirdparty/biz_entry
)

# Link libshared.so when present (Kotlin/Native ohosArm64 output)
set(LIBSHARED_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/arm64-v8a")
if(EXISTS "${LIBSHARED_DIR}/libshared.so")
    target_link_libraries(kuikly_entry PUBLIC "${LIBSHARED_DIR}/libshared.so")
    target_compile_definitions(kuikly_entry PRIVATE KAMPKIT_USE_LIBSHARED=1)
endif()

target_link_libraries(kuikly_entry PUBLIC
    libace_napi.z.so
    libace_ndk.z.so
    hilog_ndk.z.so
)
