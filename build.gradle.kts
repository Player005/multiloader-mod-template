plugins {
    id("java")
    id("idea")
    id("fabric-loom") version "1.8.9" apply false
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "idea")
    apply(plugin = "maven-publish")

    // Add parchment and modrinth maven repositories for convenience
    repositories {
        exclusiveContent {
            forRepository {
                maven {
                    name = "Modrinth"
                    url = uri("https://api.modrinth.com/maven")
                }
            }
            filter {
                includeGroup("maven.modrinth")
            }
        }

        exclusiveContent {
            forRepository {
                maven {
                    name = "Parchment"
                    url = uri("https://maven.parchmentmc.org")
                }
            }
            filter {
                includeGroup("org.parchmentmc.data")
            }
        }
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(21)
        }
        withType<GenerateModuleMetadata>().configureEach {
            enabled = false
        }

        jar {
            // put all built jars in the same dir (build/libs)
            destinationDirectory = rootDir.resolve("build").resolve("libs_${project.name}")

            // add license file to jars
            from(rootDir.resolve("LICENSE.md"))

            // required because apparently some classes are duplicated
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }

    version = properties["mod_version"].toString()
    group = "net.player005.vegandelightfabric"

    base {
        archivesName = "vegan-delight-${project.name}-${rootProject.properties["minecraft_version"]}"
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.1")
    }
}

tasks.jar {
    enabled = false
}
