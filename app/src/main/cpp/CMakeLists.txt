cmake_minimum_required(VERSION 3.6)
project(NativeApp)

# Export ANativeActivity_onCreate(),
# Refer to: https://github.com/android-ndk/ndk/issues/381.
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -u ANativeActivity_onCreate")

# Build native_app_glue as a static lib
add_library(native_app_glue STATIC
    ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)

# Native App
add_library(nativeapp SHARED main.cpp)
target_include_directories(nativeapp PRIVATE
    ${ANDROID_NDK}/sources/android/native_app_glue)
target_link_libraries(nativeapp
    android
    native_app_glue
    EGL
    GLESv1_CM
    log)

