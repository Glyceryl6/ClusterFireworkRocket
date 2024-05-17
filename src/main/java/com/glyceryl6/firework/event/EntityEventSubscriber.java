package com.glyceryl6.firework.event;

import com.glyceryl6.firework.Main;
import com.glyceryl6.firework.entities.ClusterFireworkRocketEntity;
import com.glyceryl6.firework.items.ClusterFireworkRocketItem;
import com.glyceryl6.firework.registry.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class EntityEventSubscriber {

    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        ItemStack bow = event.getBow();
        Player shooter = event.getEntity();
        if (bow.getItem() instanceof CrossbowItem) {
            List<ItemStack> list = CrossbowItem.getChargedProjectiles(bow);
            float[] pitches = CrossbowItem.getShotPitches(shooter.getRandom());
            float[] angles = new float[] {0.0F, -10.0F, 10.0F};
            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemStack = list.get(i);
                if (!itemStack.isEmpty() && itemStack.is(ModItems.CLUSTER_FIREWORK_ROCKET.get())) {
                    shootProjectile(shooter, bow, itemStack, pitches[i], angles[i]);
                }
            }
        }
    }

    private static void shootProjectile(LivingEntity shooter, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, float projectileAngle) {
        Level level = shooter.level();
        if (!level.isClientSide) {
            ClusterFireworkRocketEntity projectile = new ClusterFireworkRocketEntity(
                    level, ammoStack, shooter, shooter.getEyeY() - 0.15D);
            float angle = projectileAngle * ((float)Math.PI / 180F);
            Vec3 vec31 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(angle, vec31.x, vec31.y, vec31.z);
            Vector3f vector3f = shooter.getViewVector(1.0F).toVector3f().rotate(quaternionf);
            projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.6F, 1.0F);
            ClusterFireworkRocketItem.spawnFireworkRocket(ammoStack, projectile, level);
            crossbowStack.hurtAndBreak(3, shooter, (entity) -> entity.broadcastBreakEvent(shooter.getUsedItemHand()));
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, soundPitch);
        }
    }

}