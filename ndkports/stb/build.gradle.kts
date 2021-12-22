import com.android.ndkports.AndroidExecutableTestTask
import com.android.ndkports.CMakeCompatibleVersion
import com.android.ndkports.CMakePortTask

val portVersion = "0.8.1"

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
        arg("-DSTB_IMAGE=ON")
    }
}

tasks.prefabPackage {
    version.set(CMakeCompatibleVersion.parse(portVersion))

    modules {
        create("stb_image") {
            static.set(true)
        }
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["prefab"])
            pom {
                name.set("stb")
                description.set("The ndkports AAR for stb.")
                url.set(
                    "https://android.googlesource.com/platform/tools/ndkports"
                )
                //licenses {
                //    license {
                //        name.set("BSD-3-Clause License")
                //        url.set("https://github.com/google/googletest/blob/master/LICENSE")
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
