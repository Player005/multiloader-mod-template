package net.yourpackage.yourmod.client

import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Holder
import net.minecraft.world.level.block.Block

object MyModClient {

    fun init(platform: ClientPlatform) {
        // This method is called once on the client when the game starts.
        // You can extend the ClientPlatform interface as necessary.
        // Use it to do client-side things like registering block render types, example:
        // platform.setRenderLayer(ModBlocks.MY_OTHER_BLOCK, RenderType.cutout())
    }
}


interface ClientPlatform {

    fun setRenderLayer(block: Holder<Block>, layer: RenderType)
}
