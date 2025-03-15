plugins {
    id("net.neoforged.moddev.legacyforge") version "2.0.78"
}

// put a repositories block here for neoforge-only repositories if you need it

dependencies {
    implementation(project.project(":common").sourceSets.getByName("main").output)

    // Add neoforge-only dependencies here.
}

legacyForge {
    version = rootProject.properties["neoforge_version"].toString()

    parchment {
        minecraftVersion = rootProject.properties["parchment_version"].toString().split(":").first()
        mappingsVersion = rootProject.properties["parchment_version"].toString().split(":").last()
    }

    runs {
        create("Client") {
            client()
            disableIdeRun()
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

    // add common code & javadoc to built jars (except for NeoGradle jars)
    withType<JavaCompile>().matching(notNeoTask).configureEach {
        source(project(":common").sourceSets.main.get().allSource)
    }
    withType<Javadoc>().matching(notNeoTask).configureEach {
        source(project(":common").sourceSets.main.get().allSource)
    }

    withType<ProcessResources>().matching(notNeoTask).configureEach {
        // include common resources
        from(project(":common").sourceSets.main.get().resources)

        // make all properties defined in gradle.properties usable in the mods.toml file
        filesMatching("META-INF/mods.toml") {
            expand(rootProject.properties)
        }
    }

    // put all artifacts in the right directory
    withType<Jar> {
        destinationDirectory = rootDir.resolve("build").resolve("libs_forge")
    }
}
