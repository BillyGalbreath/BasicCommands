package net.pl3x.bukkit.basiccommands.task;

import net.pl3x.bukkit.basiccommands.configuration.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class TPSBarTask extends BukkitRunnable {
    private final static Set<Player> players = new HashSet<>();

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static void removePlayer(Player player) {
        players.remove(player);
    }

    public static void togglePlayer(Player player) {
        if (players.contains(player)) {
            players.remove(player);
        } else {
            players.add(player);
        }
    }

    @Override
    public void run() {
        double tps = Bukkit.getTPS()[0];

        String color;
        if (tps >= 18) {
            color = "&a";
        } else if (tps >= 15) {
            color = "&e";
        } else {
            color = "&c";
        }

        String message = color + String.format("%.2f", tps);

        for (Player player : players) {
            Lang.sendActionBar(player, Lang.TPS_BAR
                    .replace("{tps}", message));
        }
    }
}
