package net.pl3x.bukkit.basiccommands.command;

import net.pl3x.bukkit.basiccommands.BasicCommands;
import net.pl3x.bukkit.basiccommands.configuration.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CmdTPSBar implements TabExecutor {
    private final BasicCommands plugin;

    public CmdTPSBar(BasicCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.send(sender, Lang.PLAYER_COMMAND);
            return true;
        }

        if (!sender.hasPermission("command.tpsbar")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        plugin.getTPSBarTask().togglePlayer((Player) sender);
        return true;
    }
}
