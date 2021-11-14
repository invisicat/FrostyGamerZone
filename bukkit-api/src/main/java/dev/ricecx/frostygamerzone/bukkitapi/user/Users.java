package dev.ricecx.frostygamerzone.bukkitapi.user;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.api.AuthResult;
import dev.ricecx.frostygamerzone.api.AuthenticationResult;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.common.database.SQLUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Users {
    private static final Map<UUID, FrostUser> users = Maps.newConcurrentMap();


    public static FrostUser getUser(UUID uuid) {
        FrostUser user = users.getOrDefault(uuid, new FrostUser(uuid));

        users.put(uuid, user);

        return user;
    }
    public static FrostUser getUser(Entity e) {
        Player player = ((Player) e);
        FrostUser user = users.getOrDefault(player.getUniqueId(), new FrostUser(player));

        users.put(player.getUniqueId(), user);
        return user;
    }



    public static boolean loadUser(UUID uuid) {
        if(users.containsKey(uuid)) return false;
        FrostUser user = getUser(uuid);
        try {
            user.load();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void removeUser(Player player) {
        users.remove(player.getUniqueId());
    }
}
