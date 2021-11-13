package dev.ricecx.frostygamerzone.bukkitapi.module;

import dev.ricecx.frostygamerzone.bukkitapi.file.ConfigAdapter;
import dev.ricecx.frostygamerzone.bukkitapi.file.ConfigField;
import dev.ricecx.frostygamerzone.bukkitapi.file.ConfigSerializable;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface ModuleConfig {

    default void setDefaultData(ModuleConfiguration.FileConfigurationPair configuration) {
        FileConfiguration config = configuration.getFileConfiguration();
        for (Field field : getAllConfigurationFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);

                if(field.isAnnotationPresent(ConfigAdapter.class)) {
                    ConfigSerializable<?> adapter = field.getAnnotation(ConfigAdapter.class).adapter().getDeclaredConstructor().newInstance();
                    config.addDefault(field.getName(), adapter.serializeByConfig(value));
                } else config.addDefault(field.getName(), value);


                configuration.saveFile();
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deserializer
     * @param configuration File configuration
     */
    default void setData(ModuleConfiguration.FileConfigurationPair configuration) {
        for (Field configField : getAllConfigurationFields()) {
            ConfigField configMetadata = configField.getAnnotation(ConfigField.class);
            try {
                configField.setAccessible(true);
                String key = !configMetadata.parentKey().isEmpty() ? configMetadata.parentKey() + "." + configField.getName() : configField.getName();
                if(configField.isAnnotationPresent(ConfigAdapter.class)) {
                    ConfigSerializable<?> adapter = configField.getAnnotation(ConfigAdapter.class).adapter().getDeclaredConstructor().newInstance();
                    configField.set(this, adapter.deserialize(configuration.getFileConfiguration().getConfigurationSection(key)));
                } else configField.set(this, configuration.getFileConfiguration().get(key));

            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    default List<Field> getAllConfigurationFields() {
        return Arrays.stream(this.getClass().getDeclaredFields()).filter((f) -> f.isAnnotationPresent(ConfigField.class)).collect(Collectors.toList());
    }
}
