package net.pl3x.bukkit.basiccommands.task;

import net.pl3x.bukkit.basiccommands.BasicCommands;
import net.pl3x.bukkit.basiccommands.configuration.Lang;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TPSBarTask extends BukkitRunnable {
    private final BasicCommands plugin;
    private final BossBar bossbar;

    public TPSBarTask(BasicCommands plugin) {
        NamespacedKey key = getKey();
        BossBar bossbar = plugin.getServer().getBossBar(key);
        if (bossbar == null) {
            bossbar = plugin.getServer().createBossBar(key, "TPS: 20.0", BarColor.RED, BarStyle.SEGMENTED_20);
        }
        bossbar.setVisible(true);
        bossbar.setProgress(1.0D);

        this.plugin = plugin;
        this.bossbar = bossbar;
    }

    @Override
    public void run() {
        if (bossbar.getPlayers().isEmpty()) {
            return;
        }

        double tps = plugin.getServer().getTPS()[0];

        bossbar.setVisible(true);
        bossbar.setProgress(getProgress(tps));

        String color;
        if (tps >= 18) {
            color = "&2";
            bossbar.setColor(BarColor.GREEN);
        } else if (tps >= 15) {
            color = "&e";
            bossbar.setColor(BarColor.YELLOW);
        } else {
            color = "&4";
            bossbar.setColor(BarColor.RED);
        }

        String message = color + String.format("%.2f", tps);

        bossbar.setTitle(ChatColor.translateAlternateColorCodes('&', Lang.TPS_BAR.replace("{tps}", message)));
    }

    private double getProgress(double tps) {
        tps /= 20.0D;
        if (tps < 0.0D) {
            return 0.0D;
        }
        if (tps > 1.0D) {
            return 1.0D;
        }
        return tps;
    }

    public void removePlayer(Player player) {
        bossbar.removePlayer(player);
    }

    public void togglePlayer(Player player) {
        if (bossbar.getPlayers().contains(player)) {
            bossbar.removePlayer(player);
        } else {
            bossbar.addPlayer(player);
            run();
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        bossbar.setVisible(false);
        bossbar.removeAll();
        plugin.getServer().removeBossBar(getKey());
    }

    private NamespacedKey getKey() {
        return new NamespacedKey(plugin, "tpsbar");
    }
}
