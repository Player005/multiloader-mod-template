package net.yourpackage.yourmod;

import net.neoforged.bus.EventBus;
import net.neoforged.fml.common.Mod;

@Mod(MyMod.modID)
public class MyModNeoforge {

    public static EventBus modEventBus;

    public MyModNeoforge(EventBus modEventBus) {
        MyModNeoforge.modEventBus = modEventBus;

        MyMod.init();
        // Your neoforge initialisation code here
    }
}
