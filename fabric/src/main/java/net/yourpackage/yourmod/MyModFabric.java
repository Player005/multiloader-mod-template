package net.yourpackage.yourmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;

public class MyModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MyMod.init(new FabricPlatform());
        // Your fabric initialisation code here
    }

    public static class FabricPlatform implements Platform {

        @Override
        public CreativeModeTab.Builder creativeTabBuilder() {
            return FabricItemGroup.builder();
        }
    }
}
