package dev.ricecx.frostygamerzone.bukkitapi.user.impl;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.User;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;


@Getter
public class UserImpl implements User {

    private Player player;


    public UserImpl(UUID uuid) {
        this(Bukkit.getPlayer(uuid));
    }

    public UserImpl(Player player) {
        this.player = player;
    }

    public UserImpl(String name) {
        this(Bukkit.getPlayer(name));
    }


    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void cleanup() {

    }
}
