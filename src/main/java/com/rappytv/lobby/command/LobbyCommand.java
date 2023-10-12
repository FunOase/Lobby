package com.rappytv.lobby.command;

import com.rappytv.lobby.LobbyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class LobbyCommand implements CommandExecutor, TabExecutor {

    private final LobbyPlugin plugin;

    public LobbyCommand(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("lobby.reload")) {
            sender.sendMessage(LobbyPlugin.prefix + "§cDazu hast du keine Berechtigung!");
            return false;
        }
        plugin.reloadConfig();
        plugin.setSpawn();
        sender.sendMessage(LobbyPlugin.prefix + "§7Die Config wurde neu geladen!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
