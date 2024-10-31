plugins {
    id("net.neoforged.gradle.userdev") version "7.0.165"
}

// put a repositories block here for neoforge-only repositories if you need it

dependencies {
    implementation("net.neoforged:neoforge:${rootProject.properties["neoforge_version"]}")

    implementation(project.project(":common").sourceSets.getByName("main").output)

    // Add neoforge-only dependencies here.
}

subsystems {
    parchment {
        minecraftVersion = rootProject.properties["parchment_version"].toString().split(":").first()
        mappingsVersion = rootProject.properties["parchment_version"].toString().split(":").last()
    }
}

tasks {
    jar {
        // add common code to jar
        val main = project.project(":common").sourceSets.getByName("main")
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

runs {
    configureEach {
        modSource(project.sourceSets.main.get())
    }
    named("client") {
        client()
        shouldExportToIDE(true)
    }
}

accessTransformers {
    // TODO
//    file("src/main/resources/META-INF/accesstransformer.cfg")
}
