package com.glyceryl6.firework.entities;

import com.glyceryl6.firework.registry.ModEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ClusterFireworkRocketEntity extends FireworkRocketEntity {

    public int bombletCount;
    public int enhancementLevel;
    public String bombletType = "Empty";

    public ClusterFireworkRocketEntity(EntityType<? extends ClusterFireworkRocketEntity> type, Level level) {
        super(type, level);
    }

    public ClusterFireworkRocketEntity(Level level, @Nullable Entity shooter, double x, double y, double z, ItemStack stack) {
        super(level, shooter, x, y, z, stack);
    }

    public ClusterFireworkRocketEntity(Level level, ItemStack stack, LivingEntity shooter) {
        super(level, stack, shooter);
    }

    public ClusterFireworkRocketEntity(Level level, ItemStack stack, double x, double y, double z) {
        super(level, stack, x, y, z, Boolean.TRUE);
    }

    public ClusterFireworkRocketEntity(Level level, ItemStack stack, Entity shooter, double y) {
        super(level, stack, shooter, shooter.getX(), y, shooter.getZ(), Boolean.TRUE);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntityTypes.CLUSTER_FIREWORK_ROCKET.get();
    }

    @Override
    public void explode() {
        Level level = this.level();
        if (this.bombletCount > 0 && !level.isClientSide) {
            for (int i = 0; i < this.bombletCount; i++) {
                if (this.bombletType.equals("Arrow")) {
                    Arrow arrow = new Arrow(level, this.getRandomX(2.0F), this.getRandomY(), this.getRandomZ(2.0F));
                    if (this.getItem().getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
                        arrow.setSecondsOnFire(100);
                    }

                    arrow.setBaseDamage(arrow.getBaseDamage() + this.enhancementLevel * 0.5F);
                    arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    arrow.setOwner(this);
                    level.addFreshEntity(arrow);
                } else {
                    BombletEntity bomblet = new BombletEntity(this.getRandomX(2.0F), this.getRandomY(), this.getRandomZ(2.0F), level);
                    bomblet.setItem(Items.TNT.getDefaultInstance());
                    bomblet.setBombletType(this.bombletType);
                    bomblet.enhancementLevel = this.enhancementLevel;
                    level.addFreshEntity(bomblet);
                }
            }

            level.explode(this, this.getX(), this.getY() + 0.40F, this.getZ(), 4.0F, Level.ExplosionInteraction.NONE);
            level.explode(this, this.getX(), this.getY() + 0.40F, this.getZ(), 4.0F, Level.ExplosionInteraction.NONE);
        }

        super.explode();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("BombletCount", this.bombletCount);
        compound.putInt("EnhancementLevel", this.enhancementLevel);
        compound.putString("BombletType", this.bombletType);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.bombletCount = compound.getInt("BombletCount");
        this.enhancementLevel = compound.getInt("EnhancementLevel");
        this.bombletType = compound.getString("BombletType");
    }

}