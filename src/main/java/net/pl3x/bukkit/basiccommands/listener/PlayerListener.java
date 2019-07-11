package net.pl3x.bukkit.basiccommands.listener;

import net.pl3x.bukkit.basiccommands.BasicCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final BasicCommands plugin;

    public PlayerListener(BasicCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getTPSBarTask().removePlayer(event.getPlayer());
    }
}
