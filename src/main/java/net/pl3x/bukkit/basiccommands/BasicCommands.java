package net.pl3x.bukkit.basiccommands;

import net.pl3x.bukkit.basiccommands.command.CmdBasicCommands;
import net.pl3x.bukkit.basiccommands.configuration.Config;
import net.pl3x.bukkit.basiccommands.configuration.Lang;
import net.pl3x.bukkit.basiccommands.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicCommands extends JavaPlugin {
    private static BasicCommands instance;

    public BasicCommands() {
        instance = this;
    }

    public void onEnable() {
        Config.reload();
        Lang.reload();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("basiccommands").setExecutor(new CmdBasicCommands(this));
    }

    public static BasicCommands getInstance() {
        return instance;
    }
}
