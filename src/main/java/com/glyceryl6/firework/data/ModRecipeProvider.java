package com.glyceryl6.firework.data;

import com.glyceryl6.firework.registry.ModRecipeSerializers;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        SpecialRecipeBuilder.special(ModRecipeSerializers.EXPLODING_CLUSTER_FIREWORK_ROCKET.get()).save(writer, "exploding_cluster_firework_rocket");
        SpecialRecipeBuilder.special(ModRecipeSerializers.BURNING_CLUSTER_FIREWORK_ROCKET.get()).save(writer, "burning_cluster_firework_rocket");
        SpecialRecipeBuilder.special(ModRecipeSerializers.ARROW_CLUSTER_FIREWORK_ROCKET.get()).save(writer, "arrow_cluster_firework_rocket");
        SpecialRecipeBuilder.special(ModRecipeSerializers.CLUSTER_FIREWORK_ROCKET_COMBINE.get()).save(writer, "cluster_firework_rocket_combine");
    }

}