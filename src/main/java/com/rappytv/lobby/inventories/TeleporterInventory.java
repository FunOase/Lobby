package com.rappytv.lobby.inventories;

import com.rappytv.lobby.LobbyPlugin;
import com.rappytv.lobby.items.CustomItem;
import com.rappytv.lobby.items.Teleporter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeleporterInventory {

    private static LobbyPlugin plugin;
    public static Map<Player, String> page = new HashMap<>();

    public static void setInstance(LobbyPlugin lobbyPlugin) {
        plugin = lobbyPlugin;
    }

    private TeleporterInventory() {}

    public static Inventory get(Player player) {
        return get(player, "default");
    }

    public static Inventory get(Player player, String page) {
        Inventory inventory = Bukkit.createInventory(null, 27, new Teleporter().getItemMeta().getDisplayName());
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("teleporter." + page);
        if(section == null) {
            plugin.getLogger().severe("Config section teleporter." + page + " cannot be null!");
            return null;
        }
        TeleporterInventory.page.put(player, page);

        for(int i = 0; i < inventory.getSize(); i++) {
            ConfigurationSection slot = section.getConfigurationSection(i + 1 + "");
            if(slot == null) continue;
            if(slot.contains("permission") && !player.hasPermission(slot.getString("permission"))) continue;

            try {
                String name = slot.contains("name") ? slot.getString("name") : String.format("§cName not set for slot %s on page %s", i + 1 + "", page);
                Material material = slot.contains("material") ? Material.matchMaterial(slot.getString("material")) : Material.BARRIER;
                ArrayList<String> lore = new ArrayList<>(slot.getStringList("lore"));
                boolean enchanted = slot.contains("enchanted") && slot.getBoolean("enchanted");

                inventory.setItem(i, new CustomItem(name, material, lore, enchanted));
            } catch (NullPointerException e) {
                plugin.getLogger().severe(String.format(
                        "Invalid material in slot %s on page %s!",
                        i + 1,
                        page
                ));
            }
        }
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, new CustomItem("§0", Material.GRAY_STAINED_GLASS_PANE, null, false));

        return inventory;
    }
}
