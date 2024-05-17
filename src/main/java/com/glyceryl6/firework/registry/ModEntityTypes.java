package com.glyceryl6.firework.registry;

import com.glyceryl6.firework.Main;
import com.glyceryl6.firework.entities.BombletEntity;
import com.glyceryl6.firework.entities.ClusterFireworkRocketEntity;
import net.minecraft.client.renderer.entity.FireworkEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Main.MOD_ID);
    public static final RegistryObject<EntityType<ClusterFireworkRocketEntity>> CLUSTER_FIREWORK_ROCKET = ENTITY_TYPES.register("cluster_firework_rocket",
            () -> EntityType.Builder.<ClusterFireworkRocketEntity>of(ClusterFireworkRocketEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange((4)).updateInterval((10)).build("cluster_firework_rocket"));
    public static final RegistryObject<EntityType<BombletEntity>> BOMBLET = ENTITY_TYPES.register("bomblet",
            () -> EntityType.Builder.<BombletEntity>of(BombletEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange((4)).updateInterval((10)).build("bomblet"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CLUSTER_FIREWORK_ROCKET.get(), FireworkEntityRenderer::new);
        event.registerEntityRenderer(BOMBLET.get(), ThrownItemRenderer::new);
    }

}