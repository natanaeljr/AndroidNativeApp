import com.android.ndkports.AndroidExecutableTestTask
import com.android.ndkports.CMakeCompatibleVersion
import com.android.ndkports.CMakePortTask

val portVersion = "0.9.9.9"

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
        arg("-DBUILD_SHARED_LIBS=OFF")
        arg("-DBUILD_STATIC_LIBS=ON")
        arg("-DGLM_TEST_ENABLE=OFF")
    }
}

tasks.prefabPackage {
    version.set(CMakeCompatibleVersion.parse(portVersion))
    licensePath.set("copying.txt")
    modules {
        create("glm_static") {
            static.set(true)
        }
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["prefab"])
            pom {
                name.set("glm")
                description.set("The ndkports AAR for glm.")
                url.set(
                    "https://android.googlesource.com/platform/tools/ndkports"
                )
                //licenses {
                //    license {
                //        name.set("MIT License")
                //        url.set("https://github.com/ocornut/imgui/blob/master/LICENSE.txt")
                //        distribution.set("repo")
                //    }
                //}
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
