package net.yourpackage.yourmod

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks

@Suppress("unused")
object ModCreativeTab {

    val EXAMPLE_CREATIVE_TAB: Holder<CreativeModeTab> = MyMod.platform.register<CreativeModeTab>(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        ResourceLocation.fromNamespaceAndPath(MyMod.modID, "my_creative_tab")
    ) {
        MyMod.platform.creativeTabBuilder()
            .icon { ItemStack(Blocks.GRASS_BLOCK) }
            .title(Component.literal("My custom Creative Tab!"))
            .displayItems { _: CreativeModeTab.ItemDisplayParameters?, output: CreativeModeTab.Output? ->
                for (item in ALL_ITEMS) output!!.accept(item.value())
            }.build()
    }

    val ALL_ITEMS: MutableList<Holder<Item>> = ArrayList()

    fun init() {}
}
