package me.modmuss50.mods.ee3Compatibility;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.exchange.EnergyValueRegistry;
import com.pahimar.ee3.exchange.OreStack;
import com.pahimar.ee3.exchange.WrappedStack;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class BaseEE3 {

    public static void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BaseEE3());
        addOre("ingotCopper", 128);
        addOre("ingotSilver", 1024);
        addOre("ingotTin", 256);
        addOre("ingotLead", 256);
        addOre("dustSteel", 512);
        addOre("ingotRefinedIron", 512);
        addOre("dustCoal", 32);
        addOre("dustDiamond", 8192);
        addOre("dustSulfur", 32);
        addOre("dustLead", 256);
        addOre("ingotBronze", 256);
        addOre("ingotElectrum", 2052);
        addOre("dustLapis", 864);
        addOre("dustSilver", 1024);
        addOre("dustTin", 256);
    }

    private static void addOre(String name, float value) {
        WrappedStack stack = WrappedStack.wrap(new OreStack(name));
        EnergyValue energyValue = new EnergyValue(value);

        EnergyValueRegistryProxy.addPreAssignedEnergyValue(stack, energyValue);
    }

    private static void addStack(ItemStack itemStack, float value) {
        WrappedStack stack = WrappedStack.wrap(itemStack);
        EnergyValue energyValue = new EnergyValue(value);

        EnergyValueRegistryProxy.addPreAssignedEnergyValue(stack, energyValue);
    }

    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event) {
        //This should be a fix for the things not saving
        EnergyValueRegistry.getInstance().setShouldRegenNextRestart(false);
    }


}
