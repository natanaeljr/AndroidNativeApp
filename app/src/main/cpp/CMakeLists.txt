cmake_minimum_required(VERSION 3.6)
project(NativeApp)

# Export ANativeActivity_onCreate(),
# Refer to: https://github.com/android-ndk/ndk/issues/381.
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -u ANativeActivity_onCreate")

# Build native_app_glue as a static lib
add_library(native_app_glue STATIC
    ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)

# STB for image loading/decoding
find_package(stb 0.8.1 EXACT REQUIRED COMPONENTS stb_image)
# ImGui for graphical user interface
find_package(imgui 1.85 EXACT REQUIRED)
# SPDLog for fast C++ logging library
find_package(spdlog 1.9.2 EXACT REQUIRED)
# GLM for opengl mathematics library
find_package(glm 0.9.9.9 EXACT REQUIRED)

# Native App
add_library(nativeapp SHARED main.cpp)
target_include_directories(nativeapp PRIVATE
    ${ANDROID_NDK}/sources/android/native_app_glue)
target_link_libraries(nativeapp
    android
    native_app_glue
    EGL
    GLESv1_CM
    stb::stb_image
    glm::glm_static
    imgui::imgui
    imgui::imgui_opengl
    imgui::imgui_android
    spdlog::spdlog
    log)

