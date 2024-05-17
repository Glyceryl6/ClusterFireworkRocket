package com.glyceryl6.firework.mixin;

import com.glyceryl6.firework.entities.ClusterFireworkRocketEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public abstract class MixinArrow extends AbstractArrow {

    @Shadow protected abstract void makeParticle(int particleAmount);

    protected MixinArrow(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;tick()V"))
    public void tick(CallbackInfo ci) {
        if (this.level().isClientSide && this.getOwner() instanceof ClusterFireworkRocketEntity) {
            if (this.getRemainingFireTicks() > 0) {
                if (!this.inGround) {
                    for (int i = 0; i < 3; i++) {
                        this.makeTrail(ParticleTypes.FLAME);
                    }
                }
            } else {
                this.makeParticle(this.inGround ? 2 : 5);
            }
        }
    }

    public void makeTrail(ParticleOptions particle) {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.level().addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

}