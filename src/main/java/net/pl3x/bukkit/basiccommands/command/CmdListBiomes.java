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

public class CmdListBiomes implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.listbiomes")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Lang.send(sender, Lang.PLAYER_COMMAND);
            return true;
        }

        Advancement advancement = Bukkit.getAdvancement(new NamespacedKey("minecraft", "adventure/adventuring_time"));
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
        for (Biomes biome : Biomes.values()) {
            if (sb.length() != 0) {
                sb.append("&7, ");
            }

            String name = "minecraft:" + biome.name().toLowerCase();
            if (done.contains(name)) {
                sb.append("&a");
            } else if (needed.contains(name)) {
                sb.append("&c");
            } else {
                sb.append("&d");
            }

            sb.append(biome.getName());
        }

        Lang.send(sender, Lang.BIOMES_LIST
                .replace("{list}", sb));
        return true;
    }

    public enum Biomes {
        BADLANDS("Badlands"),
        BADLANDS_PLATEAU("Badlands Plateau"),
        BAMBOO_JUNGLE("Bamboo Jungle"),
        BAMBOO_JUNGLE_HILLS("Bamboo Jungle Hills"),
        BEACH("Beach"),
        BIRCH_FOREST("Birch Forest"),
        BIRCH_FOREST_HILLS("Birch Forest Hills"),
        COLD_OCEAN("Cold Ocean"),
        DARK_FOREST("Dark Forest"),
        DEEP_COLD_OCEAN("Deep Cold Ocean"),
        DEEP_FROZEN_OCEAN("Deep Frozen Ocean"),
        DEEP_LUKEWARM_OCEAN("Deep Lukewarm Ocean"),
        DESERT("Desert"),
        DESERT_HILLS("Desert Hills"),
        FOREST("Forest"),
        FROZEN_RIVER("Frozen River"),
        GIANT_TREE_TAIGA("Giant Tree Taiga"),
        GIANT_TREE_TAIGA_HILLS("Giant Tree Taiga Hills"),
        JUNGLE("Jungle"),
        JUNGLE_EDGE("Jungle Edge"),
        JUNGLE_HILLS("Jungle Hills"),
        LUKEWARM_OCEAN("Lukewarm Ocean"),
        MOUNTAINS("Mountains"),
        MUSHROOM_FIELD_SHORE("Mushroom Field Shore"),
        MUSHROOM_FIELDS("Mushroom Fields"),
        PLAINS("Plains"),
        RIVER("River"),
        SAVANNA("Savanna"),
        SAVANNA_PLATEAU("Savanna Plateau"),
        SNOWY_BEACH("Snowy Beach"),
        SNOWY_MOUNTAINS("Snowy Mountains"),
        SNOWY_TAIGA("Snowy Taiga"),
        SNOWY_TAIGA_HILLS("Snowy Taiga Hills"),
        SNOWY_TUNDRA("Snowy Tundra"),
        STONE_SHORE("Stone Shore"),
        SWAMP("Swamp"),
        TAIGA("Taiga"),
        TAIGA_HILLS("Taiga Hills"),
        WARM_OCEAN("Warm Ocean"),
        WOODED_BADLANDS_PLATEAU("Wooded Badlands Plateau"),
        WOODED_HILLS("Wooded Hills"),
        WOODED_MOUNTAINS("Wooded Mountains");

        private final String name;

        Biomes(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }
}
