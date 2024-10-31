@file:Suppress("UnstableApiUsage")

import net.fabricmc.loom.task.RemapJarTask


plugins {
    id("fabric-loom") version "1.8.9"
}

// add a repositories block here for fabric-only dependencies if you need it

dependencies {
    minecraft("com.mojang:minecraft:${rootProject.properties["minecraft_version"]}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${rootProject.properties["parchment_version"]}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${rootProject.properties["fabric_loader_version"]}")
    // This line can be removed if you don't need fabric api
    modImplementation("net.fabricmc.fabric-api:fabric-api:${rootProject.properties["fabric_api_version"]}")

    implementation(project.project(":common").sourceSets.getByName("main").output)

    // Add fabric-only dependencies here.
}

loom {
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
        }
    }

    // TODO: AWs
}

tasks {
    withType<JavaCompile> {
        source(project(":common").sourceSets.main.get().allSource)
    }

    withType<Jar> {
        destinationDirectory = rootDir.resolve("build").resolve("libs_fabric")
    }
    withType<RemapJarTask> {
        destinationDirectory = rootDir.resolve("build").resolve("libs_fabric")
    }

    javadoc { source(project(":common").sourceSets.main.get().allJava) }

    processResources {
        from(project(":common").sourceSets.main.get().resources)

        // make all properties defined in gradle.properties usable in the neoforge.mods.toml
        filesMatching("fabric.mod.json") {
            expand(rootProject.properties)
        }
    }

    named("compileTestJava").configure {
        enabled = false
    }

    named("test").configure {
        enabled = false
    }
}

