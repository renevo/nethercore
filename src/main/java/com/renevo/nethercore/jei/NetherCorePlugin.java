package com.renevo.nethercore.jei;

import com.renevo.nethercore.client.gui.inventory.GuiNetherFurnace;
import com.renevo.nethercore.inventory.ContainerNetherFurnace;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;

@JEIPlugin
public class NetherCorePlugin extends BlankModPlugin {
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) { }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeClickArea(GuiNetherFurnace.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.SMELTING);

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerNetherFurnace.class, VanillaRecipeCategoryUid.SMELTING, 0, 1, 2, 35);
    }
}
