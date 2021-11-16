package dev.ricecx.frostygamerzone.bukkitapi.user.utils;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.DataUser;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.User;
import dev.ricecx.frostygamerzone.bukkitapi.user.listeners.UserConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerUserHandler<T extends DataUser> implements UserHandler<T> {


    private final UserRegister<T> register;
    private final AtomicInteger loadingPlayers = new AtomicInteger(0);
    public Map<UUID, T> users = new ConcurrentHashMap<>();

    public ServerUserHandler(UserRegister<T> register) {
        this.register = register;
        // Register our listener
        CorePlugin.getInstance().registerListeners(new UserConnectionListener<>(this,register));


    }

    private void check() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            String name = player.getName();
            UUID uuid = player.getUniqueId();
            if (!getUsers().containsKey(uuid)) {
                T member = register.registerUser(name, uuid);
                member.load(new WaitLoader() {
                    @Override
                    public void verifyResponse(boolean success) {
                        if (!success) {
                            Bukkit.getScheduler().runTask(CorePlugin.getInstance(), () -> player.kickPlayer("There was an issue loading your data in."));
                            return;
                        }

                        member.loadSecondary(player);
                        add(member, uuid);
                    }

                    @Override
                    public void disallow(String message) {
                        // ignore
                    }
                });
            }
        }
    }

    @Override
    public Map<UUID, ? extends User> getUsers() {
        return users;
    }

    @Override
    public @Nullable User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public boolean isSet(UUID uuid) {
        return users.containsKey(uuid);
    }

    public T remove(UUID uuid) {
        return users.remove(uuid);
    }

    public void add(T member, UUID uuid) {
        users.put(uuid, member);
    }

    @Override
    public T registerUser(String name, UUID uuid) {
        return register.registerUser(name, uuid);
    }
}
