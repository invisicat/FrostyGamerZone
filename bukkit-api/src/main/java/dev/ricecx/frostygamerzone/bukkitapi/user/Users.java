package dev.ricecx.frostygamerzone.bukkitapi.user;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.UserHandler;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.User;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class Users {
    @Nonnull
    public static Map<UUID, ? extends User> getUsers() {
        return CorePlugin.getInstance().getUserHandler().getUsers();
    }

    @Nonnull
    public static <T extends User> List<T> getUserList(Class<T> clazz) {
        return CorePlugin.getInstance().getUserHandler().getUserList(clazz);
    }

    @Nullable
    public static User getUser(String s) {
        return CorePlugin.getInstance().getUserHandler().getUser(s);
    }

    @Nullable
    public static User getUser(UUID uuid) {
        return CorePlugin.getInstance().getUserHandler().getUser(uuid);
    }

    @Nullable
    public static User getUser(Entity entity) {
        return CorePlugin.getInstance().getUserHandler().getUser(entity);
    }

    @Nullable
    public static <T extends User> T getUser(String s, Class<T> clazz) {
        return CorePlugin.getInstance().getUserHandler().getUser(s, clazz);
    }

    @Nullable
    public static <T extends User> T getUser(UUID uuid, Class<T> clazz) {
        return CorePlugin.getInstance().getUserHandler().getUser(uuid, clazz);
    }

    @Nullable
    public static <T extends User> T getUser(Entity entity, Class<T> clazz) {
        return CorePlugin.getInstance().getUserHandler().getUser(entity, clazz);
    }

    @Nullable
    public static <T extends User> T getUser(User user, Class<T> clazz) {
        return CorePlugin.getInstance().getUserHandler().getUser(user, clazz);
    }

    public static void getOfflineUser(String name, Consumer<User> consumer) {
        UserHandler.getOfflineUser(name, consumer);
    }

}
