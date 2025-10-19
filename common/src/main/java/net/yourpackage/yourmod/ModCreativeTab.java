package net.yourpackage.yourmod;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import static net.yourpackage.yourmod.MyMod.platform;

public class ModCreativeTab {

    public static final Holder<CreativeModeTab> EXAMPLE_CREATIVE_TAB = platform.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        ResourceLocation.fromNamespaceAndPath(MyMod.modID, "my_creative_tab"),
        () -> platform.creativeTabBuilder()
            .icon(() -> new ItemStack(Blocks.GRASS_BLOCK))
            .title(Component.literal("My custom Creative Tab!"))
            .displayItems(((itemDisplayParameters, output) -> {
                for (var item : ModItems.ALL_ITEMS) output.accept(item.value());
            })).build()
    );

    public static void init() { }
}
