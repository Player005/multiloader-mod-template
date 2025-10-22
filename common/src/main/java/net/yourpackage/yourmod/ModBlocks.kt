package net.yourpackage.yourmod;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static net.yourpackage.yourmod.MyMod.platform;

public class ModBlocks {

    // put your custom blocks here
    public static Holder<Block> EXAMPLE_BLOCK = registerWithItem(
        "my_block",
        () -> new Block(BlockBehaviour.Properties.of())
    );


    private static Holder<Block> register(String id, Supplier<Block> bl) {
        return platform.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MyMod.modID, id), bl);
    }

    private static Holder<Block> registerWithItem(String id, Supplier<Block> supplier) {
        var rl = ResourceLocation.fromNamespaceAndPath(MyMod.modID, id);
        var block = register(id, supplier);
        ModItems.ALL_ITEMS.add(
            platform.register(BuiltInRegistries.ITEM, rl, () -> new BlockItem(block.value(), new Item.Properties()))
        );
        return block;
    }

    public static void init() { }
}
