#include <android_native_app_glue.h>
#include <stb/stb_image.h>
#include <imgui/imgui.h>

void android_main(struct android_app* state) {
    stbi_image_free(NULL);
    ImGui::ShowDemoWindow();
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
