package dev.ricecx.frostygamerzone.bukkitapi.user.listeners;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.DataUser;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.ServerUserHandler;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.UserRegister;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.WaitLoader;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class UserConnectionListener<T extends DataUser> implements Listener {

    private final ServerUserHandler<T> handler;
    private final UserRegister<T> register;

    private final Map<UUID, T> tempCache = new ConcurrentHashMap<>();

    public UserConnectionListener(ServerUserHandler<T> userHandler, UserRegister<T> userRegister) {
        this.handler = userHandler;
        this.register = userRegister;
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent evt) {

        String name = evt.getName();
        UUID uuid = evt.getUniqueId();


        final AtomicBoolean finished = new AtomicBoolean(false);
        final AtomicBoolean failed = new AtomicBoolean(false);
        final AtomicReference<String> disallowMessage = new AtomicReference<>(null);


        try {
            T user = register.registerUser(name, uuid);

            user.load(new WaitLoader() {
                @Override
                public void verifyResponse(boolean success) {
                    failed.set(!success);

                    synchronized (finished) {
                        finished.notifyAll();
                        finished.set(true);
                    }
                }

                @Override
                public void disallow(String message) {
                    disallowMessage.set(Utils.color(message));
                    failed.set(true);

                    synchronized (finished) {
                        finished.notifyAll();
                        finished.set(true);
                    }
                }
            });

            synchronized (finished) {
                while (!finished.get()) {
                    try {
                        //wait 2 seconds max. if the database hasn't given any response after 2 seconds, disallow the login
                        finished.wait(2000);
                        if (!finished.get()) {
                            LoggingUtils.error("Database request for " + name + " timed out...");
                            finished.set(true);
                            synchronized (failed) {
                                failed.set(true);
                            }
                        }
                    } catch (InterruptedException ex) {
                        synchronized (failed) {
                            failed.set(true);
                        }
                    }
                }
            }

            if (failed.get()) {
                //data request failed.
                evt.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, disallowMessage.get() == null ? "There was an error" : disallowMessage.get());
                return;
            }

            tempCache.put(uuid, user);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent evt) {
        final Player player = evt.getPlayer();
        final UUID uuid = player.getUniqueId();

        if(handler.isSet(uuid)) return;
        T user = tempCache.get(uuid);

        if(user == null) {
            evt.disallow(PlayerLoginEvent.Result.KICK_OTHER, "There was an error loading your data.");
            return;
        }

        Function<UUID, Void> addDenyLogin = u -> null;

        user.handleLoginEvent(evt, player, null);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        final Player player = evt.getPlayer();
        final UUID uuid = player.getUniqueId();

        T user = tempCache.get(uuid);

        if (user == null) {
            // This should never happen, but just to make sure.
            player.kickPlayer("There was an error loading your ingame data.");
            return;
        }

        try {
            user.loadSecondary(player);
        } catch(Exception ex) {
            player.kickPlayer("Your ingame data could not be loaded.");

            tempCache.remove(uuid);
        }

        handler.add(user, uuid);
        tempCache.remove(uuid);
        user.handleJoinEvent(evt, player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent evt) {
        evt.setQuitMessage(null);
        final Player player = evt.getPlayer();
        final UUID uuid = player.getUniqueId();

        T user = handler.remove(uuid);

        if(user != null) {
            user.save();

            user.cleanup();
        }
    }

}
