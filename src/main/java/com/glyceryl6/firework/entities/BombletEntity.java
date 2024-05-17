package com.glyceryl6.firework.entities;

import com.glyceryl6.firework.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BombletEntity extends ThrowableItemProjectile {

    private static final EntityDataAccessor<String> BOMBLET_TYPE = SynchedEntityData.defineId(BombletEntity.class, EntityDataSerializers.STRING);
    public int enhancementLevel;

    public BombletEntity(EntityType<? extends BombletEntity> type, Level level) {
        super(type, level);
    }

    public BombletEntity(double x, double y, double z, Level level) {
        super(ModEntityTypes.BOMBLET.get(), x, y, z, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOMBLET_TYPE, "Empty");
    }

    @Override
    protected Item getDefaultItem() {
        return Items.TNT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.getBombletType().equals("Exploding")) {
                this.makeTrail(ParticleTypes.LARGE_SMOKE);
            }

            if (this.getBombletType().equals("Burning")) {
                for (int i = 0; i < 3; i++) {
                    this.makeTrail(ParticleTypes.FLAME);
                }
            }
        }
    }

    private void makeTrail(ParticleOptions particle) {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.level().addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    public String getBombletType() {
        return this.entityData.get(BOMBLET_TYPE);
    }

    public void setBombletType(String bombletType) {
        this.entityData.set(BOMBLET_TYPE, bombletType);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("BombletType", this.getBombletType());
        compound.putInt("EnhancementLevel", this.enhancementLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBombletType(compound.getString("BombletType"));
        this.enhancementLevel = compound.getInt("EnhancementLevel");
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Level level = this.level();
        if (!level.isClientSide) {
            if (this.getBombletType().equals("Exploding")) {
                float radius = 1.5F + this.enhancementLevel * 0.15F;
                level.explode(this, this.getX(), this.getY(), this.getZ(), radius, Level.ExplosionInteraction.TNT);
            }

            if (this.getBombletType().equals("Burning")) {
                if (result instanceof BlockHitResult hitResult) {
                    BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
                    if (level.isEmptyBlock(pos)) {
                        level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
                    }
                } else if (result instanceof EntityHitResult hitResult) {
                    Entity entity = hitResult.getEntity();
                    entity.setSecondsOnFire(5 + this.enhancementLevel);
                    entity.hurt(this.damageSources().inFire(), 5.0F);
                    if (this.getOwner() instanceof LivingEntity livingEntity) {
                        this.doEnchantDamageEffects(livingEntity, entity);
                    }
                }
            }

            this.discard();
        }
    }

    @Override
    protected float getGravity() {
        return 0.07F;
    }

}