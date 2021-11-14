package dev.ricecx.frostygamerzone.frosthub.modules.scoreboards;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import dev.ricecx.frostygamerzone.bukkitapi.modules.scoreboard.ScoreboardModule;
import dev.ricecx.frostygamerzone.frosthub.scoreboards.LobbyBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

@ModuleInfo
public class LobbyScoreboardModule extends Module {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Optional<ScoreboardModule> module = getModule(ScoreboardModule.class);
        module.ifPresent(scoreboardModule -> scoreboardModule.provideScoreboard(evt.getPlayer(), new LobbyBoard(evt.getPlayer())));
    }
}
