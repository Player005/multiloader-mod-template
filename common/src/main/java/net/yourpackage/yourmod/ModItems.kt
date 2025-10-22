package net.yourpackage.yourmod;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.yourpackage.yourmod.MyMod.platform;

public class ModItems {

    public static List<Holder<Item>> ALL_ITEMS = new ArrayList<>();


    // put your custom items here
    public static Holder<Item> EXAMPLE_ITEM = register("my_item", () -> new Item(new Item.Properties()));


    private static Holder<Item> register(String id, Supplier<Item> item) {
        var holder = platform.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MyMod.modID, id), item);
        ALL_ITEMS.add(holder);
        return holder;
    }

    public static void init() { }
}
