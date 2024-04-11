package com.rappytv.lobby.command;

import com.rappytv.lobby.LobbyPlugin;
import com.rappytv.rylib.util.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LobbyCommand extends Command<LobbyPlugin> {

    public LobbyCommand(String name, LobbyPlugin plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!sender.hasPermission("lobby.reload")) {
            sender.sendMessage(LobbyPlugin.prefix + "§cDazu hast du keine Berechtigung!");
            return;
        }
        plugin.reloadConfig();
        plugin.setSpawn();
        sender.sendMessage(LobbyPlugin.prefix + "§7Die Config wurde neu geladen!");
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
