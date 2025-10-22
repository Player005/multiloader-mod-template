package net.yourpackage.yourmod

import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import java.util.function.Supplier

object MyMod {
    const val modID: String = "my_mod_id"

    lateinit var platform: Platform

    fun init(platform: Platform) {
        // Your common initialisation code here
        println("Hi from example mod!")

        MyMod.platform = platform

        ModItems.init()
        ModBlocks.init()
        ModCreativeTab.init()
    }

    fun <T> register(registry: Registry<T>, name: String, obj: Supplier<T>): Holder<T> {
        return platform.register(registry, ResourceLocation.fromNamespaceAndPath(modID, name), obj)
    }
}
