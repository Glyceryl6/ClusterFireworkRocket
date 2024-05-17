package com.glyceryl6.firework.registry;

import com.glyceryl6.firework.Main;
import com.glyceryl6.firework.crafting.ArrowCFRRecipe;
import com.glyceryl6.firework.crafting.BurningCFRRecipe;
import com.glyceryl6.firework.crafting.CFRCombineRecipe;
import com.glyceryl6.firework.crafting.ExplodingCFRRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Main.MOD_ID);
    public static final RegistryObject<RecipeSerializer<ExplodingCFRRecipe>> EXPLODING_CLUSTER_FIREWORK_ROCKET = RECIPE_SERIALIZERS.register(
            "exploding_cluster_firework_rocket", () -> new SimpleCraftingRecipeSerializer<>(ExplodingCFRRecipe::new));
    public static final RegistryObject<RecipeSerializer<BurningCFRRecipe>> BURNING_CLUSTER_FIREWORK_ROCKET = RECIPE_SERIALIZERS.register(
            "burning_cluster_firework_rocket", () -> new SimpleCraftingRecipeSerializer<>(BurningCFRRecipe::new));
    public static final RegistryObject<RecipeSerializer<ArrowCFRRecipe>> ARROW_CLUSTER_FIREWORK_ROCKET = RECIPE_SERIALIZERS.register(
            "arrow_cluster_firework_rocket", () -> new SimpleCraftingRecipeSerializer<>(ArrowCFRRecipe::new));
    public static final RegistryObject<RecipeSerializer<CFRCombineRecipe>> CLUSTER_FIREWORK_ROCKET_COMBINE = RECIPE_SERIALIZERS.register(
            "cluster_firework_rocket_combine", () -> new SimpleCraftingRecipeSerializer<>(CFRCombineRecipe::new));

}