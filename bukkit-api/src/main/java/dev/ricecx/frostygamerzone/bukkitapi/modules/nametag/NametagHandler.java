package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.Version;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NametagHandler implements Listener {
    public static final boolean DISABLE_PUSH = false;

    private final NametagModule nametagManager;
    private final Map<UUID, Nametag> nametags = new HashMap<>();

    public NametagHandler() {
        this.nametagManager = Module.getModule(NametagModule.class).get();
        Bukkit.getScheduler().runTaskTimer(CorePlugin.getInstance(), this::applyTags, 0L, 20L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoin(PlayerJoinEvent event) {
        nametagManager.sendTeams(event.getPlayer());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        nametagManager.reset(event.getPlayer().getName());
    }

    private void applyTags() {
        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(CorePlugin.getInstance(), this::applyTags);
            return;
        }
        for (Player online : CorePlugin.getAllPlayers())
            applyTagToPlayer(online);
    }

    private void applyTagToPlayer(Player player) {
        // If on the primary thread, run async
        if (Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTaskAsynchronously(CorePlugin.getInstance(), () -> applyTagToPlayer(player));
            return;
        }
        Nametag nametag = nametags.get(player.getUniqueId());
        if (nametag == null)
            return;
        Bukkit.getScheduler().runTask(CorePlugin.getInstance(), () -> nametagManager.setNametag(
                player,
                formatWithPlaceholders(player, nametag.getPrefix(), true),
                formatWithPlaceholders(player, nametag.getSuffix(), true),
                nametag.getPriority()
        ));
    }

    private String formatWithPlaceholders(Player player, String input, boolean limitChars) {
        if (input == null)
            return "";
        if (player == null)
            return input;
        String colored = Utils.color(input);
        String s = limitChars && colored.length() > 128 ? colored.substring(0, 128) : colored;
        switch (Version.getServerVersion()) {
            case v1_13_R1:
            case v1_14_R1:
            case v1_15_R1:
            case v1_16_R1:
            case v1_16_R2:
            case v1_16_R3: {
                return s;
            }
            default: {
                return limitChars && colored.length() > 16 ? colored.substring(0, 16) : colored;
            }
        }
    }
}