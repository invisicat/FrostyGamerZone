package dev.ricecx.frostygamerzone.bukkitapi.listeners;

import dev.ricecx.frostygamerzone.api.AuthenticationResult;
import dev.ricecx.frostygamerzone.bukkitapi.user.FrostUser;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.CompletableFuture;

public class PlayerConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoinDelayed(PlayerJoinEvent evt) {
        FrostUser user = Users.getUser(evt.getPlayer());
        try {
            user.load();
        } catch (Exception e) {
            e.printStackTrace();
            evt.getPlayer().kickPlayer("Your data could not be loaded.");
        }
    }

    @EventHandler
    private void playerLeave(PlayerQuitEvent evt) {
        Users.removeUser(evt.getPlayer());
    }
}
