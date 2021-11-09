package dev.ricecx.frostygamerzone.bukkitapi;

import dev.ricecx.frostygamerzone.common.LoggingUtils;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public abstract class CorePlugin extends JavaPlugin {

    /**
     * Whether paper apis are available
     */
    private final boolean paper;

    public CorePlugin() {
        paper = isUsingPaper();
    }

    /**
     * Register a listener to Bukkit
     *
     * @param listeners Listeners to register
     */
    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            LoggingUtils.debug("Registering listener " + listener.toString());
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private boolean isUsingPaper() {
        try {
            Class.forName("com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent");
            this.getLogger().info("Paper events will be used in order to improve performance");
            return true;
        } catch (ClassNotFoundException e) {
            this.getLogger().info("This server doesn't seem to be running Paper or a paper-fork, falling back to Spigot events.");
            return false;
        }
    }

}
