import com.android.ndkports.AndroidExecutableTestTask
import com.android.ndkports.CMakeCompatibleVersion
import com.android.ndkports.CMakePortTask

val portVersion = "1.9.2"

group = "com.android.ndk.thirdparty"
version = "$portVersion${rootProject.extra.get("snapshotSuffix")}"

plugins {
    id("maven-publish")
    id("com.android.ndkports.NdkPorts")
}

ndkPorts {
    ndkPath.set(File(project.findProperty("ndkPath") as String))
    source.set(project.file("src.tar.gz"))
    minSdkVersion.set(16)
}

val buildTask = tasks.register<CMakePortTask>("buildPort") {
    cmake {
        arg("-DSPDLOG_INSTALL=ON")
        arg("-DSPDLOG_BUILD_SHARED=OFF")
        arg("-DSPDLOG_BUILD_TESTS=OFF")
        arg("-DSPDLOG_BUILD_EXAMPLE=OFF")
        arg("-DSPDLOG_FMT_EXTERNAL=OFF")
        arg("-DSPDLOG_NO_EXCEPTIONS=ON")
    }
}

tasks.prefabPackage {
    version.set(CMakeCompatibleVersion.parse(portVersion))

    modules {
        create("spdlog") {
            static.set(true)
        }
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["prefab"])
            pom {
                name.set("spdlog")
                description.set("The ndkports AAR for spdlog.")
                url.set(
                    "https://android.googlesource.com/platform/tools/ndkports"
                )
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/ocornut/imgui/blob/master/LICENSE.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        name.set("The Android Open Source Project")
                    }
                }
                scm {
                    url.set("https://android.googlesource.com/platform/tools/ndkports")
                    connection.set("scm:git:https://android.googlesource.com/platform/tools/ndkports")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("${rootProject.buildDir}/repository")
        }
    }
}
