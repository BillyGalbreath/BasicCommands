package net.pl3x.bukkit.basiccommands.command;

import net.pl3x.bukkit.basiccommands.configuration.Lang;
import net.pl3x.bukkit.basiccommands.task.TPSBarTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CmdTPSBar implements TabExecutor {
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

        TPSBarTask.togglePlayer((Player) sender);
        return true;
    }
}
