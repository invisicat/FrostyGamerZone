package dev.ricecx.frostygamerzone.bukkitapi.module;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.file.InvalidConfigurationCastException;
import org.bukkit.Server;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class Module implements Listener {
    private static final Map<Class<? extends Module>, Module> modules = Maps.newConcurrentMap();

    private final ModuleConfig config;
    private AbstractPlayerConfig playerConfig;

    public Module() {
        ModuleInfo metadata = this.getClass().getAnnotation(ModuleInfo.class);
        this.config = loadConfiguration();
        if (metadata.playerConfigClass() != null)
            this.playerConfig = createObject(metadata.playerConfigClass());

        modules.put(this.getClass(), this);
        CorePlugin.getInstance().registerListeners(this);
    }

    public ModuleConfig loadConfiguration() {
        if(this.getClass().getAnnotation(ModuleInfo.class).configName().isEmpty()) return null;
        ModuleConfiguration.FileConfigurationPair fileConfiguration = ModuleConfiguration.getModuleFileConfiguration(this.getClass().getAnnotation(ModuleInfo.class).configName());
        ModuleConfig mc = createObject(this.getClass().getAnnotation(ModuleInfo.class).configClass());
        mc.setDefaultData(fileConfiguration);
        mc.setData(fileConfiguration);
        return mc;
    }

    private static <T> T createObject(Class<? extends T> clazz) {
        T object = null;
        try {
            object = clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
           //
        }

        return object;
    }


    public static Server getBukkitServer() {
        return CorePlugin.getInstance().getServer();
    }

    public boolean poke() {
        return true;
    }

    public <T> T getConfig(Class<? extends T> clazz) {
     if(!clazz.isInstance(this.config)) throw new InvalidConfigurationCastException("Check to make sure module annotation is setup properly.");
        return clazz.cast(this.config);
    }

    public static <T extends Module> Optional<T> getModule(Class<T> clazz) {
        Module module = modules.get(clazz);

        return module != null ? Optional.of(clazz.cast(modules.get(clazz))) : Optional.empty();
    }

    public static <T extends Module> T loadModule(Class<T> clazz) {
        if(modules.containsKey(clazz)) throw new RuntimeException("This module has already been loaded. Are you sure you want to load it twice?");

        T clazzObject = createObject(clazz);
        modules.put(clazz, clazzObject);

        return clazzObject;
    }

    public AbstractPlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public void cleanUp() {}

}
