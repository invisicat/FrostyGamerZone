package dev.ricecx.frostygamerzone.bukkitapi.user;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PreAuthenticatedUser implements User {

    private UUID uuid;

    public PreAuthenticatedUser(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
