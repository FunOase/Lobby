package com.rappytv.lobby;

import com.rappytv.lobby.command.LobbyCommand;
import com.rappytv.lobby.listeners.BlockListener;
import com.rappytv.lobby.listeners.InventoryClickListener;
import com.rappytv.lobby.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LobbyPlugin extends JavaPlugin {

    public static String prefix = "";
    private Location spawn;

    @Override
    public void onEnable() {
        // Save config
        saveDefaultConfig();

        // Set spawn
        spawn = new Location(
                Bukkit.getWorld(Objects.requireNonNull(getConfig().getString("spawn.world"), "World name not set!")),
                getConfig().getDouble("spawn.x"),
                getConfig().getDouble("spawn.y"),
                getConfig().getDouble("spawn.z"),
                (float) getConfig().getDouble("spawn.yaw"),
                (float) getConfig().getDouble("spawn.pitch")
        );

        // Plugin channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Register command
        Objects.requireNonNull(getCommand("lobby")).setExecutor(new LobbyCommand(this));

        // Register events
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockListener(), this);
        pm.registerEvents(new InventoryClickListener(this), this);
        pm.registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {}

    public Location getSpawn() {
        return spawn;
    }
}
