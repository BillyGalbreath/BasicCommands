package net.pl3x.bukkit.basiccommands;

import net.pl3x.bukkit.basiccommands.command.CmdBasicCommands;
import net.pl3x.bukkit.basiccommands.command.CmdListBiomes;
import net.pl3x.bukkit.basiccommands.command.CmdListBreeds;
import net.pl3x.bukkit.basiccommands.command.CmdListFoods;
import net.pl3x.bukkit.basiccommands.command.CmdListMobs;
import net.pl3x.bukkit.basiccommands.command.CmdTPSBar;
import net.pl3x.bukkit.basiccommands.configuration.Config;
import net.pl3x.bukkit.basiccommands.configuration.Lang;
import net.pl3x.bukkit.basiccommands.listener.PlayerListener;
import net.pl3x.bukkit.basiccommands.task.TPSBarTask;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicCommands extends JavaPlugin {
    private TPSBarTask tpsBarTask;

    public void onEnable() {
        Config.reload(this);
        Lang.reload(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        getCommand("basiccommands").setExecutor(new CmdBasicCommands(this));
        getCommand("listbiomes").setExecutor(new CmdListBiomes());
        getCommand("listbreeds").setExecutor(new CmdListBreeds());
        getCommand("listfoods").setExecutor(new CmdListFoods());
        getCommand("listmobs").setExecutor(new CmdListMobs());
        getCommand("tpsbar").setExecutor(new CmdTPSBar(this));

        tpsBarTask = new TPSBarTask(this);
        tpsBarTask.runTaskTimerAsynchronously(this, 20L, 20L);
    }

    public void onDisable() {
        if (tpsBarTask != null) {
            tpsBarTask.cancel();
            tpsBarTask = null;
        }
    }

    public TPSBarTask getTPSBarTask() {
        return tpsBarTask;
    }
}
