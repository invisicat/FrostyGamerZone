package dev.ricecx.frostygamerzone.bukkitapi.modules.novanillajoinmessage;

import dev.ricecx.frostygamerzone.api.joinmessages.JoinMessages;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@ModuleInfo
public class NoVanillaJoinMessageModule extends Module {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent evt) {
        evt.setJoinMessage(Utils.color(JoinMessages.DEFAULT.getMessage(evt.getPlayer().getDisplayName())));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt) {
        evt.setQuitMessage(null);
    }
}
