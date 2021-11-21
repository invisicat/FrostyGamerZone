package dev.ricecx.frostygamerzone.bukkitapi;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(ItemBuilder itemBuilder) {
        this(itemBuilder.toItemStack().clone());
    }
    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Preconditions.checkNotNull(itemMeta);
        itemMeta.setDisplayName(Utils.color(name));

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the lore of the item.
     * As with {@link #setName(String)}, color codes will be replaced. Each string represents
     * a line of the lore.
     *
     * Lines will not be automatically wrapped or truncated, so it is recommended you take
     * some consideration into how the item will be rendered with the lore.
     *
     * @param lore The desired lore of the item, with each line as a separate string.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder lore(List<String> lore) {
        for(int i = 0; i < lore.size(); i++){
            lore.set(i, Utils.color(lore.get(i)));
        }

        ItemMeta stackMeta = itemStack.getItemMeta();
        Preconditions.checkNotNull(stackMeta);
        stackMeta.setLore(lore);
        itemStack.setItemMeta(stackMeta);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        itemStack.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = itemStack.getItemMeta();
        Preconditions.checkNotNull(im);
        im.addEnchant(ench, level, true);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addBookEnchant(Enchantment ench, int level) {
        EnchantmentStorageMeta im = ((EnchantmentStorageMeta) itemStack.getItemMeta());
        Preconditions.checkNotNull(im);
        im.addStoredEnchant(ench, level, true);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantGlow(Enchantment ench, int level) {
        ItemMeta im = itemStack.getItemMeta();
        Preconditions.checkNotNull(im);
        im.addEnchant(ench, level, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Sets the lore of the item. This method is a var-args alias for the
     * {@link #lore(List)} method.
     *
     * @param lore The desired lore of the item, with each line as a separate string.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder hideAttributes() {
        ItemMeta im = itemStack.getItemMeta();
        Preconditions.checkNotNull(im);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemStack toItemStack() {
        return itemStack;
    }
}
