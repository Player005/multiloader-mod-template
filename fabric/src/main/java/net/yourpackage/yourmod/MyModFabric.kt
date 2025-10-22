package net.yourpackage.yourmod

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.world.item.CreativeModeTab

class MyModFabric : ModInitializer {
    override fun onInitialize() {
        MyMod.init(FabricPlatform())
        // Your fabric initialisation code here
    }

    class FabricPlatform : Platform {
        override fun creativeTabBuilder(): CreativeModeTab.Builder {
            return FabricItemGroup.builder()
        }
    }
}
