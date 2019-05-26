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

public class CmdListFoods implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.listfoods")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Lang.send(sender, Lang.PLAYER_COMMAND);
            return true;
        }

        Advancement advancement = Bukkit.getAdvancement(new NamespacedKey("minecraft", "husbandry/balanced_diet"));
        if (advancement == null) {
            Lang.send(sender, Lang.ADVANCEMENT_NOT_FOUND
                    .replace("{advancement}", "adventure/adventuring_time"));
            return true;
        }

        AdvancementProgress progress = ((Player) sender).getAdvancementProgress(advancement);

        List<String> done = new ArrayList<>(progress.getAwardedCriteria());
        List<String> needed = new ArrayList<>(progress.getRemainingCriteria());

        Collections.sort(done);
        Collections.sort(needed);

        StringBuilder sb = new StringBuilder();
        for (Foods food : Foods.values()) {
            if (sb.length() != 0) {
                sb.append("&7, ");
            }

            String name = food.name().toLowerCase();
            if (done.contains(name)) {
                sb.append("&a");
            } else if (needed.contains(name)) {
                sb.append("&c");
            } else {
                sb.append("&d");
            }

            sb.append(food.getName());
        }

        Lang.send(sender, Lang.FOODS_LIST
                .replace("{list}", sb));
        return true;
    }

    public enum Foods {
        APPLE("Apple"),
        BAKED_POTATO("Baked Potato"),
        BEEF("Raw Beef"),
        BEETROOT("Beetroot"),
        BEETROOT_SOUP("Beetroot Soup"),
        BREAD("Bread"),
        CARROT("Carrot"),
        CHICKEN("Raw Chicken"),
        CHORUS_FRUIT("Chorus Fruit"),
        COD("Raw Cod"),
        COOKED_BEEF("Steak"),
        COOKED_CHICKEN("Cooked Chicken"),
        COOKED_COD("Cooked Fish"),
        COOKED_MUTTON("Cooked Mutton"),
        COOKED_PORKCHOP("Cooked Porkchop"),
        COOKED_RABBIT("Cooked Rabbit"),
        COOKED_SALMON("Cooked Salmon"),
        COOKIE("Cookie"),
        DRIED_KELP("Dried Kelp"),
        ENCHANTED_GOLDEN_APPLE("Golden Enchanted Apple"),
        GOLDEN_APPLE("Golden Apple"),
        GOLDEN_CARROT("Golden Carrot"),
        MELON_SLICE("Melon Slice"),
        MUSHROOM_STEW("Mushroom Stew"),
        MUTTON("Raw Mutton"),
        POISONOUS_POTATO("Poisonous Potato"),
        PORKCHOP("Raw Porkchop"),
        POTATO("Potato"),
        PUFFERFISH("Pufferfish"),
        PUMPKIN_PIE("Pumpkin Pie"),
        RABBIT("Raw Rabbit"),
        RABBIT_STEW("Rabbit Stew"),
        ROTTEN_FLESH("Rotten Flesh"),
        SALMON("Raw Salmon"),
        SPIDER_EYE("Spider Eye"),
        SUSPICIOUS_STEW("Suspicious Stew"),
        SWEET_BERRIES("Sweet Berries"),
        TROPICAL_FISH("Tropical Fish");

        private final String name;

        Foods(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }
}
