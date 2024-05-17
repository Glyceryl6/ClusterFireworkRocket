package com.glyceryl6.firework.event;

import com.glyceryl6.firework.Main;
import com.glyceryl6.firework.items.ClusterFireworkRocketItem;
import com.glyceryl6.firework.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemEventSubscriber {

    @SubscribeEvent
    public static void registerDispenserBehavior(FMLCommonSetupEvent event) {
        DispenserBlock.registerBehavior(ModItems.CLUSTER_FIREWORK_ROCKET.get(), new ClusterFireworkRocketItem.Shoot());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerItemProperties(FMLClientSetupEvent event) {
        ItemProperties.register(Items.CROSSBOW, new ResourceLocation("firework"), (stack, level, entity, seed) ->
                CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, ModItems.CLUSTER_FIREWORK_ROCKET.get()) ? 1.0F : 0.0F);
    }

}