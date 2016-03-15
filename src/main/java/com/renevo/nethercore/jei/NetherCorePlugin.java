package com.renevo.nethercore.jei;

import com.renevo.nethercore.client.gui.inventory.GuiNetherFurnace;
import com.renevo.nethercore.inventory.ContainerNetherFurnace;
import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;

@JEIPlugin
public class NetherCorePlugin implements IModPlugin {
    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) { }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) { }

    @Override
    @Deprecated
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) { }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) { }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeClickArea(GuiNetherFurnace.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.SMELTING);

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerNetherFurnace.class, VanillaRecipeCategoryUid.SMELTING, 0, 1, 2, 35);
    }
}
