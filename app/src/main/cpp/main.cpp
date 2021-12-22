#include <android_native_app_glue.h>
#include <stb/stb_image.h>
#include <imgui/imgui.h>
#include <spdlog/sinks/android_sink.h>
#include <glm/vec3.hpp>

void android_main(struct android_app* state) {
    stbi_image_free(NULL);
    ImGui::ShowDemoWindow();
    std::string tag = "spdlog-android";
    auto android_logger = spdlog::android_logger_mt("android", tag);
    android_logger->critical("Use \"adb shell logcat\" to view this message.");
    glm::vec3();
    // Loop waiting for stuff to do.
    while (true) {
        // Read all pending events.
        int ident;
        int events;
        struct android_poll_source* source;
        // We will block forever waiting for events.
        while ((ident = ALooper_pollAll(-1, nullptr, &events, (void **)&source)) >= 0) {
        }
    }
}
