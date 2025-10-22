package net.yourpackage.yourmod

import net.minecraft.client.Minecraft
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import org.jetbrains.annotations.Contract
import java.util.function.Supplier

interface Platform {
    fun <T> register(registry: Registry<T>, rl: ResourceLocation, value: Supplier<T>): Holder<T> {
        return Registry.registerForHolder<T>(registry, rl, value.get())
    }

    @Contract(value = " -> new", pure = true)
    fun creativeTabBuilder(): CreativeModeTab.Builder

    val isClient: Boolean
        get() {
            try {
                Minecraft.getInstance()
                return true
            } catch (_: Exception) {
                return false
            }
        }
}
