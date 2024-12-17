package com.rappytv.lobby;

import com.rappytv.lobby.command.LobbyCommand;
import com.rappytv.lobby.items.InventoryManager;
import com.rappytv.lobby.items.ItemManager;
import com.rappytv.lobby.listeners.BlockListener;
import com.rappytv.lobby.listeners.PlayerListener;
import net.funoase.sahara.bukkit.Sahara;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LobbyPlugin extends JavaPlugin {

    private Location spawn;
    private InventoryManager inventoryManager;
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Sahara.get().getI18nManager().saveTranslations(this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.inventoryManager = new InventoryManager(this);
        this.itemManager = new ItemManager(this);

        // Set spawn
        this.setSpawn();
        this.getInventoryManager().registerInventories();

        // Register commands & events
        new LobbyCommand("lobby", this).register();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockListener(), this);
        pm.registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public void setSpawn() {
        this.spawn = new Location(
                Bukkit.getWorld(Objects.requireNonNull(this.getConfig().getString("spawn.world"), "World name not set!")),
                this.getConfig().getDouble("spawn.x"),
                this.getConfig().getDouble("spawn.y"),
                this.getConfig().getDouble("spawn.z"),
                (float) this.getConfig().getDouble("spawn.yaw"),
                (float) this.getConfig().getDouble("spawn.pitch")
        );
    }
}
