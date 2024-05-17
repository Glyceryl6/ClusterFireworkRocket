package com.glyceryl6.firework.crafting;

import com.glyceryl6.firework.registry.ModItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCFRRecipe extends CustomRecipe {

    public AbstractCFRRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<ItemStack> fireworkInput = new ArrayList<>();
        List<ItemStack> otherInput = new ArrayList<>();
        List<ItemStack> fortifierInput = new ArrayList<>();
        for (int j = 0; j < container.getContainerSize(); j++) {
            ItemStack slotStack = container.getItem(j);
            if (slotStack.getItem() instanceof FireworkRocketItem) {
                fireworkInput.add(slotStack);
            }

            if (slotStack.is(this.getBomletItem())) {
                otherInput.add(slotStack);
            }

            if (slotStack.is(this.getFortifier())) {
                fortifierInput.add(slotStack);
            }
        }

        return fireworkInput.size() == 1 && (otherInput.size() > 0 || fortifierInput.size() > 0);
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        final Item item = ModItems.CLUSTER_FIREWORK_ROCKET.get();
        ItemStack itemStack = item.getDefaultInstance();
        ItemStack tempStack = item.getDefaultInstance();
        List<ItemStack> otherInput = new ArrayList<>();
        List<ItemStack> fortifierInput = new ArrayList<>();
        for (int j = 0; j < container.getContainerSize(); j++) {
            ItemStack slotStack = container.getItem(j);
            if (slotStack.is(Items.FIREWORK_ROCKET) || slotStack.is(item)) {
                tempStack = slotStack.copy();
            }

            if (slotStack.is(this.getBomletItem())) {
                otherInput.add(slotStack);
            }

            if (slotStack.is(this.getFortifier())) {
                fortifierInput.add(slotStack);
            }
        }

        CompoundTag compoundTag1 = itemStack.getOrCreateTagElement("Fireworks");
        CompoundTag compoundTag2 = tempStack.getOrCreateTagElement("Fireworks");
        compoundTag1.putInt("BombletCount", compoundTag2.getInt("BombletCount") + otherInput.size());
        compoundTag1.putInt("EnhancementLevel", compoundTag2.getInt("EnhancementLevel") + fortifierInput.size());
        compoundTag1.putString("BombletType", this.getBomletType());
        String bombletType = compoundTag2.getString("BombletType");
        ListTag enchantmentTags = tempStack.getEnchantmentTags();
        for (int i = 0; i < enchantmentTags.size(); i++) {
            CompoundTag compoundTag = enchantmentTags.getCompound(i);
            ResourceLocation id = new ResourceLocation(compoundTag.getString("id"));
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(id);
            if (enchantment != null) {
                itemStack.enchant(enchantment, compoundTag.getShort("lvl"));
            }
        }

        boolean flag = tempStack.is(Items.FIREWORK_ROCKET) || tempStack.is(item) &&
                (bombletType.equals(this.getBomletType()) || bombletType.equals("Empty"));
        return (otherInput.size() > 0 || fortifierInput.size() > 0) && flag ? itemStack : ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    protected abstract Item getBomletItem();

    protected abstract Item getFortifier();

    protected abstract String getBomletType();

}