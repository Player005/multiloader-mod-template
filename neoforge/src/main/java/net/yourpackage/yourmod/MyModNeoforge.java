package net.yourpackage.yourmod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MyMod.modID)
public class MyModNeoforge {

    public static IEventBus modEventBus;

    @SuppressWarnings("removal")
    public MyModNeoforge() {
        MyModNeoforge.modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MyMod.init();
        // Your neoforge initialisation code here
    }
}
