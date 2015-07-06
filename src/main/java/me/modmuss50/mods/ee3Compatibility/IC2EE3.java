package me.modmuss50.mods.ee3Compatibility;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import com.pahimar.ee3.exchange.OreStack;
import com.pahimar.ee3.exchange.WrappedStack;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import ic2.api.item.IC2Items;
import ic2.api.recipe.*;
import ic2.core.AdvRecipe;
import ic2.core.AdvShapelessRecipe;
import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IC2EE3 {

    public static void addIC2Handlers() {
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.macerator.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.compressor.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.extractor.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerCutting.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerExtruding.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerRolling.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.oreWashing.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.centrifuge.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.blockcutter.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.blastfurance.getRecipes().entrySet()) {
            sendRecipeEntry(entry);
        }

        for (Object recipeObject : CraftingManager.getInstance().getRecipeList()) {
            if (recipeObject instanceof AdvRecipe || recipeObject instanceof AdvShapelessRecipe) {
                IRecipe recipe = (IRecipe) recipeObject;
                if (recipe.getRecipeOutput() != null) {
                    List<Object> recipeInputs = getRecipeInputs(recipe);
                    if (recipeInputs != null && !recipeInputs.isEmpty()) {
                        RecipeRegistryProxy.addRecipe(recipe.getRecipeOutput(), recipeInputs);
                    }
                }
            }
        }
    }

    private static void sendRecipeEntry(Map.Entry<IRecipeInput, RecipeOutput> entry) {
        List<ItemStack> recipeStackOutputs = entry.getValue().items;
        if (recipeStackOutputs.size() == 1) {
            ItemStack recipeOutput = recipeStackOutputs.get(0);
            if (recipeOutput != null) {
                recipeOutput = recipeOutput.copy();
                recipeOutput.setTagCompound(entry.getValue().metadata);
                for (ItemStack recipeInput : entry.getKey().getInputs()) {
                    if (recipeInput != null) {
                        recipeInput = recipeInput.copy();
                        recipeInput.stackSize = entry.getKey().getAmount();

                        RecipeRegistryProxy.addRecipe(recipeOutput, Arrays.asList(recipeInput));
                    }
                }
            }
        }
    }

    private static List<Object> getRecipeInputs(IRecipe recipe) {
        List<Object> recipeInputs = new ArrayList<Object>();
        if (recipe instanceof AdvRecipe) {
            for (Object object : ((AdvRecipe) recipe).input) {
                addInputToList(recipeInputs, object);
            }
        } else if (recipe instanceof AdvShapelessRecipe) {
            for (Object object : ((AdvShapelessRecipe) recipe).input) {
                addInputToList(recipeInputs, object);
            }
        }
        return recipeInputs;
    }

    public static void addInputToList(List<Object> recipeInputs, Object object) {
        if (object instanceof ItemStack) {
            ItemStack itemStack = ((ItemStack) object).copy();
            recipeInputs.add(itemStack);
        } else if (object instanceof String) {
            OreStack stack = new OreStack((String) object);
            recipeInputs.add(stack);
        } else if (object instanceof IRecipeInput) {
            if (object instanceof RecipeInputItemStack)
                recipeInputs.add(((RecipeInputItemStack) object).input);
            else if (object instanceof RecipeInputOreDict)
                recipeInputs.add(new OreStack(((RecipeInputOreDict) object).input));
            else if (object instanceof RecipeInputFluidContainer)
                recipeInputs.add(new FluidStack(((RecipeInputFluidContainer) object).fluid, ((RecipeInputFluidContainer) object).amount));
        }
    }


    public static void postInit(FMLPostInitializationEvent event) {
        addStack(IC2Items.getItem("rubber"), 32);
        addStack(IC2Items.getItem("carbonPlate"), 256);
        addStack(Ic2Items.energyCrystal, 32896 / 9);
        addStack(Ic2Items.chargingEnergyCrystal, 32896 / 9);
        addStack(IC2Items.getItem("refinedIronIngot"), 512);
        addStack(Ic2Items.plateadviron, 512);
        addStack(Ic2Items.reBattery, 608);
        addStack(Ic2Items.chargedReBattery, 608);
        addIC2Handlers();
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

}
