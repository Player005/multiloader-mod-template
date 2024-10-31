val MOD_VERSION: String by rootProject.extra
val MOD_ID: String by rootProject.extra
val MOD_NAME: String by rootProject.extra

val MINECRAFT_VERSION: String by rootProject.extra
val MINECRAFT_VERSION_RANGE: String by rootProject.extra
val PARCHMENT_VERSION: String by rootProject.extra
val PARCHMENT_MC_VERSION: String by rootProject.extra

val NEOFORGE_VERSION: String by rootProject.extra
val NEOFORGE_VERSION_RANGE: String by rootProject.extra

val FD_NEO_VERSION: String by rootProject.extra


plugins {
    id("net.neoforged.gradle.userdev") version "7.0.165"
}

// put a repositories block here if you need neoforge-only repositories

dependencies {
    implementation("net.neoforged:neoforge:${NEOFORGE_VERSION}")

    implementation(project.project(":common").sourceSets.getByName("main").output)
}

subsystems {
    parchment {
        minecraftVersion = PARCHMENT_MC_VERSION
        mappingsVersion = PARCHMENT_VERSION
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

        val replaceProperties = mapOf(
            "minecraft_version" to MINECRAFT_VERSION,
            "minecraft_version_range" to MINECRAFT_VERSION_RANGE,
            "neo_version" to NEOFORGE_VERSION,
            "neo_version_range" to NEOFORGE_VERSION_RANGE,
            "loader_version_range" to "*",
            "mod_id" to MOD_ID,
            "mod_name" to MOD_NAME,
            "mod_license" to "LGPL 3.0 or later",
            "mod_version" to MOD_VERSION,
            "mod_authors" to "Player005, SayWhatSayMon",
            "mod_description" to ""
        )

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(replaceProperties)
        }
    }
}

runs {
    configureEach {
        modSource(project.sourceSets.main.get())
    }
    create("client") {
        client()
        shouldExportToIDE(true)
    }
}

accessTransformers {
    // TODO
    file("src/main/resources/META-INF/accesstransformer.cfg")
}
