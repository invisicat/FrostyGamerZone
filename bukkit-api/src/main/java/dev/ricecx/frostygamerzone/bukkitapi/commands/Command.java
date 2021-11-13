package dev.ricecx.frostygamerzone.bukkitapi.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Command {
    void run(CommandSender sender, String[] args);

    default List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return List.of();
    }

    default CommandInfo getMetadata() {
        return this.getClass().getAnnotation(CommandInfo.class);
    }
}
