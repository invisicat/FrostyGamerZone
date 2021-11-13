package dev.ricecx.frostygamerzone.bukkitapi.user;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class FrostUser implements User {

    /**
     * Bukkit player object
     */
    Player player;

    public FrostUser(Player player) {
        this.player = player;
    }
    public FrostUser(UUID uuid) {
        this.player = Bukkit.getServer().getPlayer(uuid);
    }
}
