package com.rappytv.lobby.listeners;

import com.rappytv.lobby.LobbyPlugin;
import com.rappytv.lobby.items.Teleporter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class PlayerListener implements Listener {

    private final LobbyPlugin plugin;

    public PlayerListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();

        sendToSpawn(player);
        setPlayerInventory(player);
//        new PlayerScoreboard(event.getPlayer());
        player.setFoodLevel(20);
        player.setHealth(20.0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        event.getEntity().setFoodLevel(20);
    }

    @EventHandler
    public void openInventory(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if(event.getInventory().getType() != InventoryType.PLAYER && !player.hasPermission("lobby.gui"))
            event.setCancelled(true);
    }

    public void sendToSpawn(Player player) {
        player.teleport(plugin.getSpawn());
    }

    public void setPlayerInventory(Player player) {
        Inventory inv = player.getInventory();
        inv.clear();
        inv.setItem(4, new Teleporter());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) && !(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        Player attacker = (Player) e.getDamager();
        if(attacker.hasPermission("lobby.attack")) {
            if(player.hasPermission("lobby.attack.block"))
                e.setCancelled(true);
        } else
            e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        e.setDroppedExp(0);
        e.getDrops().clear();
    }
}
