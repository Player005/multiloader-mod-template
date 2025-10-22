package net.yourpackage.yourmod

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Holder
import net.minecraft.world.level.block.Block
import net.yourpackage.yourmod.client.ClientPlatform
import net.yourpackage.yourmod.client.MyModClient

class MyModFabricClient : ClientModInitializer {

    override fun onInitializeClient() {
        MyModClient.init(FabricClientPlatform())
    }

    class FabricClientPlatform() : ClientPlatform {
        override fun setRenderLayer(block: Holder<Block>, layer: RenderType) {
            BlockRenderLayerMap.INSTANCE.putBlock(block.value(), layer)
        }
    }
}