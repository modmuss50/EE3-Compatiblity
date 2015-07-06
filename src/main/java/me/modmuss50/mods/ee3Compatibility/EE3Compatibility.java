package me.modmuss50.mods.ee3Compatibility;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.modmuss50.mods.ee3Compatibility.lib.ModInfo;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class EE3Compatibility {
    @Mod.EventHandler
    public static void preinit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public static void postinit(FMLPostInitializationEvent event) {
        BaseEE3.postInit(event);
        if (Loader.isModLoaded("IC2")) {
            IC2EE3.postInit(event);
        }
    }

}
