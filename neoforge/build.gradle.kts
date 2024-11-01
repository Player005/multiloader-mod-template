import org.slf4j.event.Level

plugins {
    id("net.neoforged.moddev") version "1.0.21"
}

// put a repositories block here for neoforge-only repositories if you need it

dependencies {
    implementation(project.project(":common").sourceSets.getByName("main").output)

    // Add neoforge-only dependencies here.
}

neoForge {
    version = rootProject.properties["neoforge_version"].toString()

    parchment {
        minecraftVersion = rootProject.properties["parchment_version"].toString().split(":").first()
        mappingsVersion = rootProject.properties["parchment_version"].toString().split(":").last()
    }

    runs {
        create("Client") {
            client()
        }
        create("Server") {
            server()
        }

        configureEach {
            // Recommended logging data for userdev environment
            // The markers can be added/remove as needed separated by commas.
            // "SCAN": For mods scan.
            // "REGISTRIES": For firing of registry events.
            // "REGISTRYDUMP": For getting the contents of all registries.
            systemProperty("forge.logging.markers", "REGISTRIES")

            logLevel = Level.DEBUG
        }
    }

    mods {
        create(rootProject.properties["mod_id"].toString()) {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks {
    jar {
        // add common code to jar
        val main = project.project(":common").sourceSets.main.get()
        from(main.output.classesDirs)
        from(main.output.resourcesDir)
    }

    named("compileTestJava").configure {
        enabled = false
    }

    // NeoGradle compiles the game, but we don't want to add our common code to the game's code
    val notNeoTask: (Task) -> Boolean = { !it.name.startsWith("neo") && !it.name.startsWith("compileService") }

    withType<JavaCompile>().matching(notNeoTask).configureEach {
        source(project(":common").sourceSets.main.get().allSource)
    }

    withType<Javadoc>().matching(notNeoTask).configureEach {
        source(project(":common").sourceSets.main.get().allSource)
    }

    withType<ProcessResources>().matching(notNeoTask).configureEach {
        from(project(":common").sourceSets.main.get().resources)

        // make all properties defined in gradle.properties usable in the neoforge.mods.toml
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(rootProject.properties)
        }
    }
}
