package net.yourpackage.yourmod

import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

@Mod(MyMod.modID)
class MyModNeoforge(val modEventBus: IEventBus) {

    init {
        MyMod.init(NeoforgePlatform())
        // Your neoforge initialisation code here
    }

    inner class NeoforgePlatform : Platform {
        private val registers: MutableMap<Registry<*>, DeferredRegister<*>> = HashMap()

        private fun <T> getRegister(registry: Registry<T>): DeferredRegister<T> {
            if (registers.containsKey(registry)) {
                @Suppress("UNCHECKED_CAST")
                return registers[registry] as DeferredRegister<T>
            }
            return DeferredRegister.create<T>(registry, MyMod.modID)
                .also { it.register(modEventBus); registers[registry] = it }
        }

        override fun <T> register(registry: Registry<T>, rl: ResourceLocation, value: Supplier<T>): Holder<T> {
            return getRegister(registry).register<T>(rl.path, value)
        }

        override fun creativeTabBuilder(): CreativeModeTab.Builder {
            return CreativeModeTab.builder()
        }
    }
}
