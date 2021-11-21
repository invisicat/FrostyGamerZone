package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.team;

import dev.ricecx.frostygamerzone.bukkitapi.Version;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Braydon (credits: https://github.com/sgtcaze/NametagEdit)
 */
@Data
public class FakeTeam {
    private static final String UNIQUE_ID;
    private static int ID;

    static {
        // Generating a unique ID and applying it to the field so it can be used in team names
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++)
            builder.append(chars.charAt((int) (Math.random() * chars.length())));
        UNIQUE_ID = builder.toString();
    }

    private final String name, prefix, suffix;
    private final List<String> members = new ArrayList<>();

    public FakeTeam(String prefix, String suffix, int priority) {
        // Priority
        String letter;
        if (priority < 0)
            letter = "Z";
        else {
            char letterCharacter = (char) ((priority / 5) + 65);
            int repeat = priority % 5 + 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < repeat; i++)
                builder.append(letterCharacter);
            letter = builder.toString();
        }
        String name;
        name = UNIQUE_ID + "_" + letter + ++ID;

        switch (Version.getServerVersion(Bukkit.getServer())) {
            case v1_13_R1:
            case v1_14_R1:
            case v1_15_R1:
            case v1_16_R1:
            case v1_16_R2:
            case v1_16_R3: {
                name = name.length() > 128 ? name.substring(0, 128) : name;
                break;
            }
            default: {
                name = name.length() > 16 ? name.substring(0, 16) : name;
            }
        }

        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * Add the given {@link Player} to the team
     *
     * @param player the player to add
     */
    public void addMember(Player player) {
        addMember(player.getName());
    }

    /**
     * Add the given player name to the team
     *
     * @param playerName the player name to add
     */
    public void addMember(String playerName) {
        if (!members.contains(playerName))
            members.add(playerName);
    }

    /**
     * Check if the given prefix and suffix is similar to the team prefix and suffix
     *
     * @param prefix the prefix
     * @param suffix the suffix
     * @return if the given prefix and suffix are similar
     */
    public boolean isSimilar(String prefix, String suffix) {
        return this.prefix.equals(prefix) && this.suffix.equals(suffix);
    }
}