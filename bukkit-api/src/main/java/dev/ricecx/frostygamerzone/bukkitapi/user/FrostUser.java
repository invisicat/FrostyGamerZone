package dev.ricecx.frostygamerzone.bukkitapi.user;

import dev.ricecx.frostygamerzone.api.Rank;
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

    String displayName;

    Rank rank;

    String id;


    public FrostUser(Player player) {
        this.player = player;
    }
    public FrostUser(UUID uuid) {
        this.player = Bukkit.getServer().getPlayer(uuid);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
