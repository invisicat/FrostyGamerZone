package dev.ricecx.frostygamerzone.bukkitapi.modules.scoreboard;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import dev.ricecx.frostygamerzone.bukkitapi.scoreboard.FrostBoard;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.task.GlobalTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ModuleInfo
public class ScoreboardModule extends Module {

    private final Map<UUID, FrostBoard> boards = Maps.newConcurrentMap();
    private final GlobalTimer globalTimer;

    public ScoreboardModule() {
        globalTimer = new GlobalTimer(1, 1, TimeUnit.SECONDS, this::updateBoards).start();

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt) {
        removeScoreboard(evt.getPlayer());
    }

    public void provideScoreboard(Player player, FrostBoard board) {
        boards.put(player.getUniqueId(), board);
        board.updateBoard();
        LoggingUtils.info("Providing " + board.getTitle() + " to " + player.getName());
    }

    public void removeScoreboard(Player player) {
        FrostBoard board = boards.remove(player.getUniqueId());

        if(board != null) {
            board.delete();
        }
    }

    /**
     * Terminate the update task
     */
    private void pauseScoreboards() {
        globalTimer.terminate();
    }

    /**
     * Update boards
     */
    private void updateBoards() {
        for (FrostBoard board : boards.values()) {
            board.updateBoard();
        }
    }
}
