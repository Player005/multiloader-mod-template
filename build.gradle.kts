val MOD_ID by extra { "vegandelight" }
val MOD_VERSION by extra { "1.1.1" }
val MOD_NAME by extra { "Vegan Delight" }

val MINECRAFT_VERSION by extra { "1.21.1" }
val MINECRAFT_VERSION_RANGE by extra { "[1.21,)" }
val PARCHMENT_MC_VERSION by extra { "1.21" }
val PARCHMENT_VERSION by extra { "2024.07.28" }

val NEOFORGE_VERSION by extra { "21.1.68" }
val NEOFORGE_VERSION_RANGE by extra { "[21.1,)" }

val FABRIC_LOADER_VERSION by extra { "0.16.7" }
val FABRIC_API_VERSION by extra { "0.105.0+1.21.1" }

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
            destinationDirectory = rootDir.resolve("build").resolve("libs_$name")

            // add license file to jars
            from(rootDir.resolve("LICENSE.md"))

            // required because apparently some classes are duplicated
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }

    version = MOD_VERSION
    group = "net.player005.vegandelightfabric"

    base {
        archivesName = "vegan-delight-${project.name}-${MINECRAFT_VERSION}"
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.1")
    }
}

tasks.jar {
    enabled = false
}
