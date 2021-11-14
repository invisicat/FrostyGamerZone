package dev.ricecx.frostygamerzone.bukkitapi;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Styles {

    public static final String PREFIX = "&b&lFrosty&9&lNetwork";
    public static final String IP = "&bplay.frostynetwork.net";

    /**
     * Return the invalid account error for the given player name with the given prefix
     *
     * @return the error
     */
    public static String noPermission() {
        return Styles.color("&bFrosty&9Network &8» &7You do not have permission to use this command!");
    }

    /**
     * Return the invalid account error for the given player name with the given prefix
     *
     * @param prefix the prefix
     * @return the error
     */
    public static String noPermission(String prefix) {
        return Styles.error(prefix, "§7You do not have permission to use this command!");
    }

    /**
     * Return the invalid account error for the given player name with the given prefix
     *
     * @param prefix the prefix
     * @param playerName the player name
     * @return the error
     */
    public static String invalidAccount(String prefix, String playerName) {
        return Styles.error(prefix, "§7Could not find a Minecraft account with the name §b" + playerName);
    }

    /**
     * Return the default chat format with the given prefix and message
     *
     * @param prefix the prefix of the message
     * @param message the message
     * @return the formatted message
     */
    public static String main(String prefix, String message) {
        return color("&a&l" + prefix + " &8» &7" + message);
    }

    /**
     * Return the default error chat format with the given prefix and message
     *
     * @param prefix the prefix of the message
     * @param message the message
     * @return the formatted message
     */
    public static String error(String prefix, String message) {
        return color("&c&l" + prefix + " &8» &7" + message);
    }

    /**
     * Color the provided {@link Collection <String>} using {@link ChatColor}
     *
     * @param lines the lines to color
     * @return the colored lines
     */
    public static List<String> colorLines(Collection<String> lines) {
        List<String> newLines = new ArrayList<>();
        for (String line : lines)
            newLines.add(color(line));
        return newLines;
    }

    /**
     * Color the provided message using {@link ChatColor}
     *
     * @param message the message to color
     * @return the colored message
     */
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("§", "&"));
    }
}