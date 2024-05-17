package com.glyceryl6.firework.crafting;

import com.glyceryl6.firework.registry.ModItems;
import com.glyceryl6.firework.registry.ModRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CFRCombineRecipe extends CustomRecipe {

    public CFRCombineRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<ItemStack> fireworkInput = new ArrayList<>();
        for (int j = 0; j < container.getContainerSize(); j++) {
            ItemStack slotStack = container.getItem(j);
            if (slotStack.is(ModItems.CLUSTER_FIREWORK_ROCKET.get())) {
                fireworkInput.add(slotStack);
            }
        }

        return fireworkInput.size() > 1;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        final Item item = ModItems.CLUSTER_FIREWORK_ROCKET.get();
        List<ItemStack> fireworkInput = new ArrayList<>();
        ItemStack itemStack = item.getDefaultInstance();
        for (int j = 0; j < container.getContainerSize(); j++) {
            ItemStack slotStack = container.getItem(j);
            if (slotStack.is(item)) {
                fireworkInput.add(slotStack);
            }
        }

        int i = 0, j = 0;
        ItemStack firstStack = fireworkInput.get(0);
        CompoundTag compoundTag = firstStack.getOrCreateTagElement("Fireworks");
        String bombletType = compoundTag.getString("BombletType");
        if (!firstStack.isEmpty()) {
            for (ItemStack stack : fireworkInput) {
                CompoundTag tag = stack.getOrCreateTagElement("Fireworks");
                if (!tag.getString("BombletType").equals(bombletType)) {
                    return ItemStack.EMPTY;
                } else {
                    i += tag.getInt("BombletCount");
                    j += tag.getInt("EnhancementLevel");
                }
            }
        }

        CompoundTag tag = itemStack.getOrCreateTagElement("Fireworks");
        tag.putInt("BombletCount", i);
        tag.putInt("EnhancementLevel", j);
        tag.putString("BombletType", bombletType);
        return itemStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CLUSTER_FIREWORK_ROCKET_COMBINE.get();
    }

}