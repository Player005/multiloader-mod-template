package net.yourpackage.yourmod;

public class MyMod {
    public static final String modID = "my_mod_id";

    public static Platform platform;

    static void init(Platform platform) {
        // Your common initialisation code here
        System.out.println("Hi from example mod!");

        MyMod.platform = platform;

        ModBlocks.init();
        ModItems.init();
        ModCreativeTab.init();
    }
}
