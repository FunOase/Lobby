package com.rappytv.lobby.items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Teleporter extends ItemStack {

    public Teleporter() {
        super(Material.COMPASS);
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(
                ChatColor.of("#5e17eb") + "§lT" +
                ChatColor.of("#5b42eb") + "§le" +
                ChatColor.of("#5865eb") + "§ll" +
                ChatColor.of("#577feb") + "§le" +
                ChatColor.of("#549ceb") + "§lp" +
                ChatColor.of("#53b8ec") + "§lo" +
                ChatColor.of("#53b8ec") + "§lr" +
                ChatColor.of("#53b8ec") + "§lt" +
                ChatColor.of("#53b8ec") + "§le" +
                ChatColor.of("#4efeec") + "§lr"
        );
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
    }
}
