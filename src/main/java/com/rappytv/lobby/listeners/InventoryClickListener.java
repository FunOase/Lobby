package com.rappytv.lobby.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.rappytv.lobby.LobbyPlugin;
import com.rappytv.lobby.inventories.TeleporterInventory;
import com.rappytv.lobby.items.Teleporter;
import com.rappytv.rylib.RyLib;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("ConstantConditions")
public class InventoryClickListener implements Listener {

    private final LobbyPlugin plugin;

    public InventoryClickListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player player)) return;

        String title = e.getView().getTitle();
        Teleporter teleporter = new Teleporter();

        if(teleporter.getItemMeta() != null && title.equalsIgnoreCase(teleporter.getItemMeta().getDisplayName())) {
            e.setCancelled(true);

            String page = TeleporterInventory.page.get(player);
            if(page == null) {
                player.closeInventory();
                player.sendMessage(plugin.i18n().translate("teleporter.unknownPage"));
                return;
            }
            int slot = e.getRawSlot();
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("teleporter." + page + "." + (slot + 1));

            if(section == null) return;

            boolean missingPermission = section.contains("permission") && !player.hasPermission(section.getString("permission"));
            if(missingPermission) {
                player.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }
            String type = section.contains("type") ? section.getString("type").toLowerCase() : "none";
            switch(type) {
                case "server" -> {
                    player.closeInventory();
                    sendPlayerToServer(player, section.getString("server"));
                }
                case "page" -> {
                    String destination = section.getString("page");
                    if(destination == null || !plugin.getConfig().isConfigurationSection("teleporter." + destination)) {
                        player.sendMessage(plugin.i18n().translate("teleporter.unknownPage"));
                        return;
                    }
                    Inventory inventory = TeleporterInventory.get(player, destination);
                    if(inventory == null) {
                        player.sendMessage(plugin.i18n().translate("teleporter.checkConsole"));
                        return;
                    }
                    player.openInventory(inventory);
                }
            }
        } else {
            if(!player.hasPermission("lobby.items.move")) {
                e.setCancelled(true);
            }
        }
    }

    private void sendPlayerToServer(Player player, String server) {
        player.sendMessage(plugin.i18n().translate("teleporter.connecting"));
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
