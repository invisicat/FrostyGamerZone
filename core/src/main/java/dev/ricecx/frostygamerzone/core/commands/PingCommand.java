package dev.ricecx.frostygamerzone.core.commands;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.Styles;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(
        name = "ping"
)
public class PingCommand implements Command {

    @Override
    public void run(CommandSender sender, String[] args) {

        if(args.length >= 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null) sender.sendMessage(Styles.main(Styles.PREFIX, player.getDisplayName() +"'s ping is: &a" + player.getPing() + "ms"));
        } else {
            sender.sendMessage(Styles.main(Styles.PREFIX, "Your ping is: &a" + ((Player) sender).getPing() + "ms"));

        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return CorePlugin.getAllPlayers().parallelStream().map(HumanEntity::getName).collect(Collectors.toList());
    }
}
