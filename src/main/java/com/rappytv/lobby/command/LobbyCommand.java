package com.rappytv.lobby.command;

import com.rappytv.lobby.LobbyPlugin;
import net.funoase.sahara.bukkit.i18n.I18n;
import net.funoase.sahara.bukkit.util.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LobbyCommand extends Command<LobbyPlugin> {

    public LobbyCommand(String name, LobbyPlugin plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!sender.hasPermission("lobby.reload")) {
            sender.sendMessage(I18n.component(sender, "sahara.errors.missing_permissions"));
            return;
        }
        plugin.reloadConfig();
        plugin.setSpawn();
        sender.sendMessage(I18n.component(sender, "lobby.commands.reload.success"));
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
