package net.pl3x.bukkit.basiccommands.listener;

import net.pl3x.bukkit.basiccommands.task.TPSBarTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        TPSBarTask.removePlayer(event.getPlayer());
    }
}
