package dev.ricecx.frostygamerzone.bukkitapi;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.common.database.SQLTypes;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public abstract class CorePlugin extends JavaPlugin {

    private static CommandMap internalCommands;
    private final DatabaseManager databaseManager;

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
        databaseManager = new DatabaseManager(SQLTypes.POSTGRES);
    }


    public <T> void registerService(@NotNull Class<T> service, @NotNull T provider) {
        LoggingUtils.info("Service " + service.getName() + " has been registered.");
        registerService(service, provider, ServicePriority.Highest);
    }

    public static <T> T getService(Class<T> serviceClazz) {
        RegisteredServiceProvider<T> provider = Bukkit.getServicesManager().getRegistration(serviceClazz);
        Bukkit.getServicesManager().getKnownServices().forEach((c) -> LoggingUtils.info(c.getName()));
        if(provider != null) {
            return provider.getProvider();
        } else {
            return null;
        }
    }

    public <T> void registerService(@NotNull Class<T> service, @NotNull T provider, @NotNull ServicePriority priority) {
        Bukkit.getServicesManager().register(service, provider, this, priority);
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


    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static CorePlugin getInstance() {
        return getPlugin(CorePlugin.class);
    }
}
