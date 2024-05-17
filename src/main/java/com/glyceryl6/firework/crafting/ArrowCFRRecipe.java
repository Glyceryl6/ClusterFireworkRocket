package com.glyceryl6.firework.crafting;

import com.glyceryl6.firework.registry.ModRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ArrowCFRRecipe extends AbstractCFRRecipe {

    public ArrowCFRRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    protected Item getBomletItem() {
        return Items.ARROW;
    }

    @Override
    protected Item getFortifier() {
        return Items.IRON_INGOT;
    }

    @Override
    protected String getBomletType() {
        return "Arrow";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ARROW_CLUSTER_FIREWORK_ROCKET.get();
    }

}