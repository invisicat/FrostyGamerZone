package dev.ricecx.frostygamerzone.bukkitapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    private void playerJoin(AsyncPlayerPreLoginEvent evt) {
    }

    @EventHandler
    private void playerLeave(PlayerQuitEvent evt) {
    }
}
