package net.pl3x.bukkit.basiccommands.configuration;

import com.google.common.base.Throwables;
import net.pl3x.bukkit.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Lang {
    public static String COMMAND_NO_PERMISSION = "&4You do not have permission for that command!";
    public static String PLAYER_COMMAND = "&4This command is only available to players!";

    public static String ADVANCEMENT_NOT_FOUND = "&4Advancement not found ({advancement})";

    public static String BIOMES_LIST = "&3Advancement &dAdventuring Time &3Progress&e:\n{list}";
    public static String FOODS_LIST = "&3Advancement &dA Balanced Diet &3Progress&e:\n{list}";
    public static String MOBS_LIST = "&3Advancement &dMonster Hunter &3Progress&e:\n{list}";

    public static String TPS_BAR = "&eTPS&3: {tps}";

    private static void init() {
        COMMAND_NO_PERMISSION = getString("command-no-permission", COMMAND_NO_PERMISSION);
        PLAYER_COMMAND = getString("player-command", PLAYER_COMMAND);

        ADVANCEMENT_NOT_FOUND = getString("advancement-not-found", ADVANCEMENT_NOT_FOUND);

        BIOMES_LIST = getString("biomes-list", BIOMES_LIST);
        FOODS_LIST = getString("foods-list", FOODS_LIST);
        MOBS_LIST = getString("mobs-list", MOBS_LIST);
    }

    // ############################  DO NOT EDIT BELOW THIS LINE  ############################

    /**
     * Reload the language file
     */
    public static void reload() {
        BasicCommands plugin = BasicCommands.getInstance();
        File configFile = new File(plugin.getDataFolder(), Config.LANGUAGE_FILE);
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load " + Config.LANGUAGE_FILE + ", please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }
        config.options().header("This is the main language file for " + plugin.getName());
        config.options().copyDefaults(true);

        Lang.init();

        try {
            config.save(configFile);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    private static YamlConfiguration config;

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    /**
     * Sends a message to a recipient
     *
     * @param recipient Recipient of message
     * @param message   Message to send
     */
    public static void send(CommandSender recipient, String message) {
        if (recipient != null) {
            for (String part : colorize(message).split("\n")) {
                recipient.sendMessage(part);
            }
        }
    }

    /**
     * Broadcast a message to server
     *
     * @param message Message to broadcast
     */
    public static void broadcast(String message) {
        for (String part : colorize(message).split("\n")) {
            Bukkit.getOnlinePlayers().forEach(recipient -> recipient.sendMessage(part));
            Bukkit.getConsoleSender().sendMessage(part);
        }
    }

    /**
     * Sends a message above a player's action bar
     *
     * @param player  Recipient of message
     * @param message Message to send
     */
    public static void sendActionBar(Player player, String message) {
        if (player != null && player.isOnline()) {
            message = colorize(message);
            if (!message.isEmpty()) {
                player.sendActionBar(message);
            }
        }
    }

    /**
     * Colorize a String
     *
     * @param str String to colorize
     * @return Colorized String
     */
    public static String colorize(String str) {
        if (str == null) {
            return "";
        }
        str = ChatColor.translateAlternateColorCodes('&', str);
        if (ChatColor.stripColor(str).isEmpty()) {
            return "";
        }
        return str;
    }
}
