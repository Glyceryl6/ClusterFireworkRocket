package com.glyceryl6.firework.items;

import com.glyceryl6.firework.entities.ClusterFireworkRocketEntity;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.FireworkStarItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class ClusterFireworkRocketItem extends FireworkRocketItem {

    public ClusterFireworkRocketItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            Vec3 vec3 = context.getClickLocation();
            ItemStack itemInHand = context.getItemInHand();
            Direction direction = context.getClickedFace();
            double x = vec3.x + direction.getStepX() * 0.15D;
            double y = vec3.y + direction.getStepY() * 0.15D;
            double z = vec3.z + direction.getStepZ() * 0.15D;
            ClusterFireworkRocketEntity entity = new ClusterFireworkRocketEntity(level, context.getPlayer(), x, y, z, itemInHand);
            spawnFireworkRocket(itemInHand, entity, level);
            itemInHand.shrink(1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (player.isFallFlying()) {
            if (!level.isClientSide) {
                ClusterFireworkRocketEntity entity = new ClusterFireworkRocketEntity(level, itemInHand, player);
                spawnFireworkRocket(itemInHand, entity, level);
                if (!player.getAbilities().instabuild) {
                    itemInHand.shrink(1);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }

            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
        } else {
            return InteractionResultHolder.pass(itemInHand);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag compoundTag = stack.getTagElement("Fireworks");
        if (compoundTag != null) {
            if (compoundTag.contains("BombletCount", 99)) {
                tooltip.add(Component.translatable("item.cfr.cluster_firework_rocket.bomblet_count")
                        .append(String.valueOf(compoundTag.getInt("BombletCount") * 3)).withStyle(ChatFormatting.GRAY));
            }

            String bombletTypeKey = "item.cfr.cluster_firework_rocket.bomblet_type";
            String bomletType = compoundTag.getString("BombletType").toLowerCase(Locale.ROOT);
            MutableComponent component = Component.translatable(bombletTypeKey + "." + bomletType);
            tooltip.add(Component.translatable(bombletTypeKey).append(component).withStyle(ChatFormatting.GRAY));
            if (compoundTag.contains("EnhancementLevel", 99)) {
                tooltip.add(Component.translatable("item.cfr.cluster_firework_rocket.enhancement_level")
                        .append(String.valueOf(compoundTag.getInt("EnhancementLevel"))).withStyle(ChatFormatting.GRAY));
            }

            if (compoundTag.contains("Flight", 99)) {
                tooltip.add(Component.translatable("item.minecraft.firework_rocket.flight")
                        .append(String.valueOf(compoundTag.getByte("Flight"))).withStyle(ChatFormatting.GRAY));
            }

            ListTag listTag = compoundTag.getList("Explosions", 10);
            if (!listTag.isEmpty()) {
                for (int i = 0; i < listTag.size(); ++i) {
                    CompoundTag listTagCompound = listTag.getCompound(i);
                    List<Component> list = Lists.newArrayList();
                    FireworkStarItem.appendHoverText(listTagCompound, list);
                    if (!list.isEmpty()) {
                        for (int j = 1; j < list.size(); ++j) {
                            list.set(j, Component.literal("  ").append(list.get(j)).withStyle(ChatFormatting.GRAY));
                        }

                        tooltip.addAll(list);
                    }
                }
            }
        }
    }

    private static void spawnFireworkRocket(ItemStack stack, ClusterFireworkRocketEntity fireworkRocketEntity, Level level) {
        CompoundTag element = stack.getOrCreateTagElement("Fireworks");
        fireworkRocketEntity.bombletCount = element.getInt("BombletCount") * 3;
        fireworkRocketEntity.enhancementLevel = element.getInt("EnhancementLevel");
        fireworkRocketEntity.bombletType = element.getString("BombletType");
        level.addFreshEntity(fireworkRocketEntity);
    }

    public static class Shoot extends DefaultDispenseItemBehavior {

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            ClusterFireworkRocketEntity entity = new ClusterFireworkRocketEntity(source.getLevel(), stack, source.x(), source.y(), source.z());
            DispenseItemBehavior.setEntityPokingOutOfBlock(source, entity, direction);
            entity.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 0.5F, 1.0F);
            spawnFireworkRocket(stack, entity, source.getLevel());
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(1004, source.getPos(), 0);
        }

    }

}