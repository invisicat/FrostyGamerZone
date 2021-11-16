package dev.ricecx.frostygamerzone.bukkitapi.user.utils;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.DataUser;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public interface UserHandler<V extends DataUser> extends UserRegister<V> {

    static void getOfflineUser(String name, Consumer<User> user) {
        user.accept(null);
    }


    Map<UUID, ? extends User> getUsers();

    @Nullable
    User getUser(UUID uuid);

    @Nullable
    default User getUser(String name) {
        if (name == null) {
            return null;
        }
        return getUser(Bukkit.getPlayer(name));
    }

    @Nullable
    default User getUser(Entity entity) {
        if (entity == null) {
            return null;
        }

        return getUser(entity.getUniqueId());
    }

    @javax.annotation.Nullable
    default User getUser(User user) {
        if (user == null) {
            return null;
        }

        return getUser(user.getUUID());
    }

    @Nullable
    default <T extends User> T getUser(UUID uuid, Class<T> clazz) {
        if (uuid == null) {
            return null;
        }

        User user = getUser(uuid);
        if (user == null) {
            return null;
        }

        if (clazz.isInstance(user)) {
            return clazz.cast(user);
        }

        return null;
    }

    @Nullable
    default <T extends User> T getUser(String s, Class<T> clazz) {
        if (s == null) {
            return null;
        }

        return getUser(Bukkit.getPlayer(s), clazz);
    }

    @javax.annotation.Nullable
    default <T extends User> T getUser(Entity entity, Class<T> clazz) {
        if (entity == null) {
            return null;
        }

        return getUser(entity.getUniqueId(), clazz);
    }

    @Nullable
    default <T extends User> T getUser(User user, Class<T> clazz) {
        if (user == null) {
            return null;
        }

        if (clazz.isInstance(user)) {
            return clazz.cast(user);
        }

        // Second try, for commands
        user = getUser(user);
        if (clazz.isInstance(user)) {
            return clazz.cast(user);
        }
        return null;
    }



    default <T extends User> List<T> getUserList(Class<T> clazz) {
        return getUsers().values()
                .stream()
                .map(user -> getUser(user, clazz))
                .filter(Objects::nonNull)
                .filter(u -> u.getPlayer() != null) // make sure player is active.
                .collect(Collectors.toList());
    }

}
