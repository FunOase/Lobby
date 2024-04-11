package com.rappytv.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        e.setCancelled(!player.hasPermission("lobby.block.place"));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        e.setCancelled(!player.hasPermission("lobby.block.break"));
    }
}
