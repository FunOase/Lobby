package com.rappytv.lobby.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CustomItem extends ItemStack {

    public CustomItem(String name, Material material, ArrayList<String> lore, boolean enchanted) {
        super(material);
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(name);
        if(enchanted) meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(lore != null && !lore.isEmpty()) meta.setLore(lore);
        setItemMeta(meta);
    }
}
