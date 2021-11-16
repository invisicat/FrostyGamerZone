package dev.ricecx.frostygamerzone.core.commands;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "network"
)
public class NetworkCommand implements Command {
    @Override
    public void run(CommandSender sender, String[] args) {
        CorePlugin.getInstance().getRedis().setAsync(((Player) sender).getUniqueId().toString(), args[0]).whenComplete((c, i) -> LoggingUtils.info("SET SHIT YES."));
    }
}
