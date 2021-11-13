package dev.ricecx.frostygamerzone.bukkitapi.module;

import org.bukkit.entity.Player;

public abstract class AbstractPlayerConfig {

    private final PlayerConfig config;

    public AbstractPlayerConfig() {
        config = this.getClass().getAnnotation(PlayerConfig.class);
    }

    public abstract void onMenuOpen(Player player);

    public PlayerConfig getConfig() {
        return config;
    }
}
