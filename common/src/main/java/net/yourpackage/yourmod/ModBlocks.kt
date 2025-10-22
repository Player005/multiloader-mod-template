package net.yourpackage.yourmod

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import java.util.function.Supplier
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
object ModBlocks {

    // Put your custom blocks here. registerWithItem() will also register a block item for your block.
    // Example:
    val MY_BLOCK = registerWithItem("my_block") { Block(BlockBehaviour.Properties.of()) }

    // Alternatively, if you use registerDelegatedWithItem(), you can omit the block id,
    // which will instead be inferred from the variable name:
    val MY_OTHER_BLOCK by registerDelegatedWithItem { Block(BlockBehaviour.Properties.of()) }

    // You can even omit the lambda, if you just want a simple block
    // with default properties:
    val MY_OTHER_OTHER_BLOCK by registerDelegatedWithItem()

    // If you just use registerDelegated(), there won't be a block item,
    // meaning the block can be placed in the world but not dropped as an item or put in any inventory
    val MY_MORE_OTHER_BLOCK by registerDelegated()


    private fun registerWithItem(id: String, addItemToTab: Boolean = true, supplier: Supplier<Block>): Holder<Block> {
        val rl = ResourceLocation.fromNamespaceAndPath(MyMod.modID, id)
        val block = MyMod.register(BuiltInRegistries.BLOCK, id, supplier)
        if (addItemToTab) ModCreativeTab.ALL_ITEMS.add(
            MyMod.platform.register(BuiltInRegistries.ITEM, rl) { BlockItem(block.value()!!, Item.Properties()) }
        )
        return block
    }

    private fun registerDelegated(
        blockSupplier: () -> Block = { Block(BlockBehaviour.Properties.of()) }
    ) = PropertyDelegateProvider { _: Any, property ->
        val item = MyMod.register(BuiltInRegistries.BLOCK, property.name.lowercase(), blockSupplier)
        ReadOnlyProperty { _: Any, _ -> item }
    }

    private fun registerDelegatedWithItem(
        addToTab: Boolean = true,
        blockSupplier: () -> Block = { Block(BlockBehaviour.Properties.of()) }
    ) = PropertyDelegateProvider { _: Any, property ->
        val block = registerWithItem(property.name.lowercase(), addToTab, blockSupplier)
        ReadOnlyProperty { _: Any, _ -> block }
    }

    fun init() {}
}
