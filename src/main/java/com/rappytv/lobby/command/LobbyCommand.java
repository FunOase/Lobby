package com.rappytv.lobby.command;

import com.rappytv.lobby.LobbyPlugin;
import com.rappytv.rylib.RyLib;
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
            sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
            return;
        }
        plugin.reloadConfig();
        plugin.setSpawn();
        sender.sendMessage(plugin.i18n().translate("reload.success"));
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
