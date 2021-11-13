package dev.ricecx.frostygamerzone.bukkitapi.file;

import org.bukkit.configuration.ConfigurationSection;

public abstract class ConfigSerializable<T> {

    protected abstract Object serialize(T t);

    @SuppressWarnings("unchecked")
    public Object serializeByConfig(Object tv) {
       return serialize((T) tv);
    }

    public abstract T deserialize(ConfigurationSection section);
}
