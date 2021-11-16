package dev.ricecx.frostygamerzone.bukkitapi.user.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface User {
    UUID getUUID();

    String getName();

    default Player getPlayer() {
        return Bukkit.getPlayer(getUUID());
    }

    void cleanup();
}
