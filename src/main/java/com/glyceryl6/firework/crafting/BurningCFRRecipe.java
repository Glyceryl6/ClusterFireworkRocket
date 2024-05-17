package com.glyceryl6.firework.crafting;

import com.glyceryl6.firework.registry.ModRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BurningCFRRecipe extends AbstractCFRRecipe {

    public BurningCFRRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    protected Item getBomletItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    protected Item getFortifier() {
        return Items.BLAZE_POWDER;
    }

    @Override
    protected String getBomletType() {
        return "Burning";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BURNING_CLUSTER_FIREWORK_ROCKET.get();
    }

}