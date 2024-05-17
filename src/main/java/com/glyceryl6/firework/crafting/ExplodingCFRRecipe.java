package com.glyceryl6.firework.crafting;

import com.glyceryl6.firework.registry.ModRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ExplodingCFRRecipe extends AbstractCFRRecipe {

    public ExplodingCFRRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    protected Item getBomletItem() {
        return Items.TNT;
    }

    @Override
    protected Item getFortifier() {
        return Items.GUNPOWDER;
    }

    @Override
    protected String getBomletType() {
        return "Exploding";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EXPLODING_CLUSTER_FIREWORK_ROCKET.get();
    }

}