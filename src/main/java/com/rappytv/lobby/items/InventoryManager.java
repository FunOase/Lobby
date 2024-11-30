package com.rappytv.lobby.items;

import com.rappytv.lobby.LobbyPlugin;
import net.funoase.sahara.bukkit.i18n.I18n;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InventoryManager {

    private final LobbyPlugin plugin;

    public InventoryManager(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Nullable
    public Inventory getInventory(String name, Player player) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("guis." + name);
        if(section == null) return null;
        int slots = section.getInt("rows", 3) * 9;
        String filler = section.getString("filler", "none");
        Inventory inventory = Bukkit.createInventory(null, slots, I18n.component(player, "lobby.guis." + name));
        ConfigurationSection items = section.getConfigurationSection("slots");
        if(items == null) return inventory;
        for(String itemSlot : items.getKeys(false)) {
            int slot;
            try {
                slot = Integer.parseInt(itemSlot);
            } catch (NumberFormatException e) {
                continue;
            }
            ItemStack item = plugin.getItemManager().getItem(items.getString(itemSlot), player);
            if(item == null) continue;
            inventory.setItem(slot, item);
        }
        if(!filler.equalsIgnoreCase("none")) {
            ItemStack fillerItem = plugin.getItemManager().getItem(filler, player);
            if(fillerItem == null) return inventory;
            for(int i = 0; i < inventory.getSize(); i++) {
                if(inventory.getItem(i) != null) continue;
                inventory.setItem(i, fillerItem);
            }
        }
        return inventory;
    }
}
