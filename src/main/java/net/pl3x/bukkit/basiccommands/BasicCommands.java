package net.pl3x.bukkit.basiccommands;

import net.pl3x.bukkit.basiccommands.command.CmdBasicCommands;
import net.pl3x.bukkit.basiccommands.command.CmdListBiomes;
import net.pl3x.bukkit.basiccommands.command.CmdListFoods;
import net.pl3x.bukkit.basiccommands.command.CmdListMobs;
import net.pl3x.bukkit.basiccommands.command.CmdTPSBar;
import net.pl3x.bukkit.basiccommands.configuration.Config;
import net.pl3x.bukkit.basiccommands.configuration.Lang;
import net.pl3x.bukkit.basiccommands.listener.PlayerListener;
import net.pl3x.bukkit.basiccommands.task.TPSBarTask;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicCommands extends JavaPlugin {
    private static BasicCommands instance;
    private TPSBarTask tpsBarTask;

    public BasicCommands() {
        instance = this;
    }

    public void onEnable() {
        Config.reload();
        Lang.reload();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("basiccommands").setExecutor(new CmdBasicCommands(this));
        getCommand("listbiomes").setExecutor(new CmdListBiomes());
        getCommand("listfoods").setExecutor(new CmdListFoods());
        getCommand("listmobs").setExecutor(new CmdListMobs());
        getCommand("tpsbar").setExecutor(new CmdTPSBar());

        tpsBarTask = new TPSBarTask();
        tpsBarTask.runTaskTimerAsynchronously(this, 20L, 20L);
    }

    public void onDisable() {
        if (tpsBarTask != null) {
            tpsBarTask.cancel();
            tpsBarTask = null;
        }
    }

    public static BasicCommands getInstance() {
        return instance;
    }
}
