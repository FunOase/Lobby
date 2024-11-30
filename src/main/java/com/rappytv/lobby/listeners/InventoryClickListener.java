package com.rappytv.lobby.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.rappytv.lobby.LobbyPlugin;
import com.rappytv.lobby.items.ItemManager;
import net.funoase.sahara.bukkit.i18n.I18n;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    private final LobbyPlugin plugin;

    public InventoryClickListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) return;

        ItemManager.ItemAction action = plugin.getItemManager().getItemAction(event.getCurrentItem());
        if(action == null) return;
        event.setCancelled(true);
        if(action.action() == ItemManager.ItemAction.Action.NONE) return;
        switch (action.action()) {
            case GUI -> {
                String gui = action.value();
                Inventory inventory = plugin.getInventoryManager().getInventory(gui, player);
                if(inventory == null) {
                    player.sendMessage(I18n.component(player, "lobby.listeners.unknown_page"));
                    return;
                }
                player.openInventory(inventory);
            }
            case SERVER -> {
                player.closeInventory();
                sendPlayerToServer(player, action.value());
            }
        }
    }

    private void sendPlayerToServer(Player player, String server) {
        player.sendMessage(I18n.component(player, "lobby.listeners.connecting"));
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
