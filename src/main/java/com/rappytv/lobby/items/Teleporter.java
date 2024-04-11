package com.rappytv.lobby.items;

import com.rappytv.lobby.LobbyPlugin;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Teleporter extends ItemStack {

    @SuppressWarnings("ConstantConditions")
    public Teleporter(LobbyPlugin plugin) {
        super(Material.COMPASS);
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(plugin.i18n().translate("teleporter.name"));
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
    }
}
