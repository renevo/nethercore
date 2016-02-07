package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class NetherCoreAchievements {

    private NetherCoreAchievements() {
    }

    public static Achievement netherOreAchievement;

    public static void init() {

        netherOreAchievement = new Achievement("achievement.netherore", "netherore", -2, 10, NetherCoreBlocks.netherOreIron, AchievementList.portal);
        netherOreAchievement.registerStat();

        MinecraftForge.EVENT_BUS.register(new NetherCoreAchievements());

    }

    @SubscribeEvent
    public void onPlayerPickupEntity(EntityItemPickupEvent pickupEvent) {
        Item baseItem = pickupEvent.item.getEntityItem().getItem();
        if (baseItem instanceof ItemBlock && ((ItemBlock)baseItem).getBlock() == NetherCoreBlocks.blockNetherOre) {
            pickupEvent.entityPlayer.addStat(netherOreAchievement, 1);
        }
    }

}
