package net.yourpackage.yourmod;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod(MyMod.modID)
public class MyModNeoforge {

    public static IEventBus modEventBus;

    public MyModNeoforge(IEventBus modEventBus) {
        MyModNeoforge.modEventBus = modEventBus;

        MyMod.init(new NeoforgePlatform());
        // Your neoforge initialisation code here
    }

    public static class NeoforgePlatform implements Platform {

        private final Map<Registry<?>, DeferredRegister<?>> registers = new HashMap<>();

        private <T> DeferredRegister<T> getRegister(Registry<T> registry) {
            if (registers.containsKey(registry)) { //noinspection unchecked
                return (DeferredRegister<T>) registers.get(registry);
            }
            var register = DeferredRegister.create(registry, MyMod.modID);
            register.register(modEventBus);
            registers.put(registry, register);
            return register;
        }

        @Override
        public <T> Holder<T> register(Registry<T> registry, ResourceLocation rl, Supplier<T> value) {
            return getRegister(registry).register(rl.getPath(), value);
        }

        @Override
        public CreativeModeTab.Builder creativeTabBuilder() {
            return CreativeModeTab.builder();
        }
    }
}
