package net.pl3x.bukkit.basiccommands.command;

import net.pl3x.bukkit.basiccommands.configuration.Lang;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CmdListBreeds implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.listbreeds")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Lang.send(sender, Lang.PLAYER_COMMAND);
            return true;
        }

        Advancement advancement = Bukkit.getAdvancement(new NamespacedKey("minecraft", "husbandry/bred_all_animals"));
        if (advancement == null) {
            Lang.send(sender, Lang.ADVANCEMENT_NOT_FOUND
                    .replace("{advancement}", "husbandry/bred_all_animals"));
            return true;
        }

        AdvancementProgress progress = ((Player) sender).getAdvancementProgress(advancement);

        List<String> done = new ArrayList<>(progress.getAwardedCriteria());
        List<String> needed = new ArrayList<>(progress.getRemainingCriteria());

        Collections.sort(done);
        Collections.sort(needed);

        StringBuilder sb = new StringBuilder();
        for (Mobs mob : Mobs.values()) {
            if (sb.length() != 0) {
                sb.append("&7, ");
            }

            String name = "minecraft:" + mob.name().toLowerCase();
            if (done.contains(name)) {
                sb.append("&a");
            } else if (needed.contains(name)) {
                sb.append("&c");
            } else {
                sb.append("&d");
            }

            sb.append(mob.getName());
        }

        Lang.send(sender, Lang.BREEDS_LIST
                .replace("{list}", sb));
        return true;
    }

    public enum Mobs {
        CAT("Cat"),
        CHICKEN("Chicken"),
        COW("Cow"),
        FOX("Fox"),
        HORSE("Horse"),
        LLAMA("Llama"),
        MOOSHROOM("Mooshroom"),
        OCELOT("Ocelot"),
        PANDA("Panda"),
        PIG("Pig"),
        RABBIT("Rabbit"),
        SHEEP("Sheep"),
        WOLF("Wolf"),
        TURTLE("Turtle");

        private final String name;

        Mobs(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

}
