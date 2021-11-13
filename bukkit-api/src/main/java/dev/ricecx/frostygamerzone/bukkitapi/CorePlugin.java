package dev.ricecx.frostygamerzone.bukkitapi;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public abstract class CorePlugin extends JavaPlugin {

    private static CommandMap internalCommands;

    static {
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            internalCommands = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
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

    public static List<Player> getAllPlayers() {
        return Bukkit.getOnlinePlayers().parallelStream().collect(Collectors.toList());
    }

    public void registerCommands(Command ...commands) {
        for (Command command : commands) {
            internalCommands.register("frost", Utils.createBukkitCommand(command));
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


    public static CorePlugin getInstance() {
        return getPlugin(CorePlugin.class);
    }
}
