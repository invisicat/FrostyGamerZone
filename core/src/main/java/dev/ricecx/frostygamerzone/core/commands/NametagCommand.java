package dev.ricecx.frostygamerzone.core.commands;

import dev.ricecx.frostygamerzone.bukkitapi.Version;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.NametagModule;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;


@CommandInfo(
        name = "nametag"
)
public class NametagCommand implements Command {
    @Override
    public void run(CommandSender sender, String[] args) {
        Optional<NametagModule> optMod = Module.getModule(NametagModule.class);

        if(optMod.isEmpty()) {
            sender.sendMessage("Nametag module is not loaded.");
            return;
        }

        NametagModule module = optMod.get();
        sender.sendMessage("Version " + Version.getServerVersion().name() + " is not compatible :( nms " + Version.NMS_VERSION);

        module.setNametag(((Player) sender), args[0], null, 1);
    }
}
