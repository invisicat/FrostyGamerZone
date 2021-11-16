package dev.ricecx.frostygamerzone.bukkitapi.user.core;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.WaitLoader;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;
import java.util.function.Function;

public interface DataUser extends User {

    /**
     * Checks to see if the user is in the database, if not just add them in.
     */
    void load(WaitLoader loader);

    /**
     * Loads secondary stuff for the user, i.e minigame stuff or do stuff to them
     */
    void loadSecondary(Player player);

    default void handleLoginEvent(PlayerLoginEvent e, Player player, Function<UUID, Void> add) {
        // use this to deny any connections to the server.
    }

    default void handleJoinEvent(PlayerJoinEvent e, Player player) {
        // use this to deny any connections to the server.
    }

    /**
     * Syncronchous saving
     */
    void save();

    default void saveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(CorePlugin.getInstance(), () -> {
            long now = System.currentTimeMillis();
            save();
            LoggingUtils.info("Saved user data for " + getName() + " (" + getUUID() + ") " + "in " + (System.currentTimeMillis() - now) + "ms");
        });
    }

    /**
     * Saves user data and runs supplied runnable after
     * finishing
     * @param callback Runnable to call back to
     */
    default void saveWithCallback(Runnable callback) {
        Bukkit.getScheduler().runTaskAsynchronously(CorePlugin.getInstance(), () -> {
            save();
            callback.run();
        });
    }
}
