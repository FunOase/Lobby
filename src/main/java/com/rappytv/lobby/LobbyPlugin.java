package com.rappytv.lobby;

import com.rappytv.lobby.command.LobbyCommand;
import com.rappytv.lobby.inventories.TeleporterInventory;
import com.rappytv.lobby.listeners.BlockListener;
import com.rappytv.lobby.listeners.InventoryClickListener;
import com.rappytv.lobby.listeners.PlayerListener;
import com.rappytv.rylib.util.I18n;
import com.rappytv.rylib.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LobbyPlugin extends JavaPlugin {

    private Location spawn;
    private I18n i18n;

    @Override
    public void onEnable() {
        // Save config
        saveDefaultConfig();

        i18n = new I18n(this);
        new UpdateChecker<>(
                this,
                () -> getConfig().getBoolean("checkForUpdates")
        ).setArtifactFormat(
                "ci.rappytv.com",
                getName(),
                "com.rappytv",
                "Minecraft Plugins"
        );

        // Set spawn
        setSpawn();

        // Plugin channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Register command
        new LobbyCommand("lobby", this);

        // Register TeleporterInventory
        TeleporterInventory.setInstance(this);

        // Register events
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockListener(), this);
        pm.registerEvents(new InventoryClickListener(this), this);
        pm.registerEvents(new PlayerListener(this), this);
    }

    public I18n i18n() {
        return i18n;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn() {
        this.spawn = new Location(
                Bukkit.getWorld(Objects.requireNonNull(getConfig().getString("spawn.world"), "World name not set!")),
                getConfig().getDouble("spawn.x"),
                getConfig().getDouble("spawn.y"),
                getConfig().getDouble("spawn.z"),
                (float) getConfig().getDouble("spawn.yaw"),
                (float) getConfig().getDouble("spawn.pitch")
        );
    }
}
