package com.glyceryl6.firework.mixin;

import com.glyceryl6.firework.registry.ModItems;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CrossbowItem.class)
public class MixinCrossbowItem {

    @Inject(method = "getSupportedHeldProjectiles", at = @At(value = "HEAD"), cancellable = true)
    public void getSupportedHeldProjectiles(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(ProjectileWeaponItem.ARROW_OR_FIREWORK.or(stack -> stack.is(ModItems.CLUSTER_FIREWORK_ROCKET.get())));
    }

}