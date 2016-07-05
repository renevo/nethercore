package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public final class NetherCoreAchievements {

    private NetherCoreAchievements() {
    }

    public static Achievement netherOreAchievement;
    public static Achievement compressionAchievement;
    public static Achievement netherSporeAchievement;
    public static Achievement netherStoneAchievement;
    public static Achievement netherFurnaceAchievement;

    // xBCrafted achievement
    public static Achievement xbAchievement;

    public static void init() {

        netherSporeAchievement = new Achievement("achievement.netherspore", "netherspore", 0, 12, NetherCoreItems.netherSpore, AchievementList.blazeRod);
        netherSporeAchievement.registerStat();

        netherStoneAchievement = new Achievement("achievement.netherstone", "netherstone", -2, 10, NetherCoreItems.stoneCobble, AchievementList.portal);
        netherStoneAchievement.registerStat();

        netherFurnaceAchievement = new Achievement("achievement.netherfurnace", "netherfurnace", -4, 10, NetherCoreBlocks.blockNetherFurnace, netherStoneAchievement);
        netherFurnaceAchievement.registerStat();

        netherOreAchievement = new Achievement("achievement.netherore", "netherore", -2, 12, NetherCoreItems.netherOreIron, AchievementList.portal);
        netherOreAchievement.registerStat();

        compressionAchievement = new Achievement("achievement.compressednether", "compressednether", -4, 12, NetherCoreBlocks.blockCompressedNetherrack, netherOreAchievement);
        compressionAchievement.registerStat();

        xbAchievement = new Achievement("achievement.xbcrafted", "xbcrafted", -6, 12, Items.nether_star, compressionAchievement);
        xbAchievement.registerStat().setSpecial();

        MinecraftForge.EVENT_BUS.register(new NetherCoreAchievements());
    }

    @SubscribeEvent
    public void onPlayerPickupEntity(EntityItemPickupEvent pickupEvent) {
        Item baseItem = pickupEvent.getItem().getEntityItem().getItem();
        if (baseItem instanceof ItemBlock && ((ItemBlock)baseItem).getBlock() == NetherCoreBlocks.blockNetherOre) {
            pickupEvent.getEntityPlayer().addStat(netherOreAchievement, 1);
        }
    }

    @SubscribeEvent
    public void onCraftItem(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() == NetherCoreItems.compressedNetherrackSingle.getItem()) {
            event.player.addStat(compressionAchievement, 1);
            return;
        }

        if (event.crafting.getItem() == NetherCoreItems.netherSpore) {
            event.player.addStat(netherSporeAchievement, 1);
            return;
        }

        if (event.crafting.getItem() == NetherCoreItems.stoneCobble.getItem()) {
            event.player.addStat(netherStoneAchievement, 1);
            return;
        }

        if (event.crafting.getItem() == NetherCoreItems.netherFurnace.getItem()) {
            event.player.addStat(netherFurnaceAchievement, 1);
            return;
        }
    }

    @SubscribeEvent
    public void onSmeltItem(PlayerEvent.ItemSmeltedEvent event) {
        if (event.smelting.getItem() == Items.nether_star) {
            event.player.addStat(xbAchievement, 1);
            return;
        }
    }
}
