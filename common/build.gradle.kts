@file:Suppress("UnstableApiUsage")

plugins {
    id("fabric-loom") version ("1.8.9")
}

val MINECRAFT_VERSION: String by rootProject.extra
val PARCHMENT_VERSION: String by rootProject.extra
val PARCHMENT_MC_VERSION: String by rootProject.extra

// you can put a repositories block here if you need dependencies from other sources than modrinth

dependencies {
    minecraft("com.mojang:minecraft:${MINECRAFT_VERSION}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$PARCHMENT_MC_VERSION:$PARCHMENT_VERSION@zip")
    })

    // mixin extras is included by default in both fabric and neoforge (no additional dependency required)
    compileOnly("io.github.llamalad7:mixinextras-common:0.3.5")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.3.5")

    compileOnly("net.fabricmc:sponge-mixin:0.15.3+mixin.0.8.7")
//    compileOnly("net.fabricmc:fabric-loader:${}")

    // add your custom dependencies here
}

loom {
    // if you need to add access wideners, put the path here TODO
    // accessWidenerPath = file("src/main/resources/NAME.accesswidener")

    mixin {
        useLegacyMixinAp = false
    }
}

tasks {
    jar { enabled = false }
    remapJar { enabled = false }
}

//tasks.configureEach {
//    group = null
//}