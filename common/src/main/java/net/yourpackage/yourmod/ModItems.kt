package net.yourpackage.yourmod

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import java.util.function.Supplier
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
object ModItems {

    // Register your items here by simply calling the register() method.
    // Example:
    val EXAMPLE_ITEM = register("my_item") { Item(Item.Properties()) }

    // Alternatively, if you use registerDelegated() you can omit the item id,
    // which will instead be inferred from the variable name:
    val MY_OTHER_ITEM: Holder<Item> by registerDelegated { Item(Item.Properties()) }

    // You can even omit the lambda, if you just want a simple item
    // without any special functionality:
    val MY_OTHER_OTHER_ITEM: Holder<Item> by registerDelegated()


    private fun register(id: String, addToCreativeTab: Boolean = true, item: Supplier<Item>): Holder<Item> {
        val holder = MyMod.register(BuiltInRegistries.ITEM, id, item)
        if (addToCreativeTab) ModCreativeTab.ALL_ITEMS.add(holder)
        return holder
    }

    private fun registerDelegated(addToTab: Boolean = true, itemSupplier: () -> Item = { Item(Item.Properties()) }) =
        PropertyDelegateProvider { _: Any, property ->
            val item = register(property.name.lowercase(), addToTab, itemSupplier)
            ReadOnlyProperty { _: Any, _ -> item }
        }

    fun init() {}
}
