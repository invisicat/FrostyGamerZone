package dev.ricecx.frostygamerzone.bukkitapi.tab;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.common.task.GlobalTimer;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public abstract class Tablist implements Listener {

    public static Field subTitle;

    static {
        try {
            subTitle = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
            subTitle.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public Tablist() {
        CorePlugin.getInstance().registerListeners(this);
    }

    public void start() {
        new GlobalTimer(1, 1, TimeUnit.SECONDS, this::updateTabList);
    }

    public abstract void sendTabList(Player player);

    public void updateTabList() {
        for (Player player : CorePlugin.getAllPlayers()) {
            sendTabList(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        sendTabList(evt.getPlayer());
    }
}
