package com.glyceryl6.firework.registry;

import com.glyceryl6.firework.Main;
import com.glyceryl6.firework.items.ClusterFireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final RegistryObject<Item> CLUSTER_FIREWORK_ROCKET = ITEMS.register("cluster_firework_rocket", ClusterFireworkRocketItem::new);

}