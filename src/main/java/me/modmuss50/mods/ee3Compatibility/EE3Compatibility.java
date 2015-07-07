package me.modmuss50.mods.ee3Compatibility;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.modmuss50.mods.ee3Compatibility.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

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
        MinecraftForge.EVENT_BUS.register(new EE3Compatibility());
    }


    @SubscribeEvent
    public  void rightClickEvent(PlayerInteractEvent event){
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if(event.entityPlayer.getCurrentEquippedItem() != null){
                if(event.entityPlayer.getCurrentEquippedItem().getItem() == com.pahimar.ee3.init.ModItems.stonePhilosophers){
                    if(event.entityPlayer.isSneaking()){
                        if(block == Blocks.cobblestone){
                            event.world.setBlock(event.x, event.y, event.z, Blocks.grass);
                        }
                        if(block == Blocks.grass){
                            event.world.setBlock(event.x, event.y, event.z, Blocks.sand);
                        }
                    } else {
                        if(block == Blocks.cobblestone){
                            event.world.setBlock(event.x, event.y, event.z, Blocks.stone);
                        }
                        if(block == Blocks.stone){
                            event.world.setBlock(event.x, event.y, event.z, Blocks.cobblestone);
                        }
                    }
                }
            }
        }

    }

}
