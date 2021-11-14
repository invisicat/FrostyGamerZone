package dev.ricecx.frostygamerzone.bukkitapi;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandSenderType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static String arrayToString(String... array) {
        return arrayToString(Arrays.asList(array));
    }
    /**
     * Get a {@link String} based on the provided {@link List <String>}
     *
     * @param list - The string list
     * @return the string
     */
    public static String arrayToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String message : list)
            builder.append(message).append("\n");
        return builder.substring(0, builder.toString().length() - 1);
    }

    public static String[] colorList(String[] strings) {
        List<String> coloredStrings = new ArrayList<>();
        for (String string : strings) {
            coloredStrings.add(color(string));
        }

        return coloredStrings.toArray(String[]::new);
    }
    public static String color(String... strings) {
        return ChatColor.translateAlternateColorCodes('&', arrayToString(strings));
    }

    public static org.bukkit.command.Command createBukkitCommand(Command command) {
        CommandInfo commandInfo = command.getMetadata();
        org.bukkit.command.Command cmd = new org.bukkit.command.Command(commandInfo.name(), commandInfo.name(), "No usage available", List.of()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                List<CommandSenderType> sendTypes = Arrays.asList(commandInfo.senderTypes());
                if(sender instanceof ConsoleCommandSender && !sendTypes.contains(CommandSenderType.CONSOLE)) {
                    sender.sendMessage("This command can only be ran by players.");
                    return false;
                }
                command.run(sender, args);
                return true;
            }

            @NotNull
            @Override
            public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
                return command.tabComplete(sender, args);
            }
        };

        cmd.setPermission(commandInfo.permissions());

        return cmd;
    }
}
