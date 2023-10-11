package com.rappytv.lobby.scoreboard;

import com.rappytv.scoreboard.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SidebarScoreboard extends ScoreboardBuilder {

    public SidebarScoreboard(Player player) {
        super(player, "  §b§lLOBBY  ");
    }

    public static void init() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            new SidebarScoreboard(player);
        }
    }

    @Override
    public void createScoreboard() {
        setScore("§0", 6);
        setScore(" §f§lSpielername", 5);
        setScore("  §8○ §b" + player.getName(), 4);
        setScore("§1", 3);
        setScore(" §f§lServer", 2);
        setScore("  §8○ §bLOBBY", 1);
        setScore("§2", 0);
    }
}
