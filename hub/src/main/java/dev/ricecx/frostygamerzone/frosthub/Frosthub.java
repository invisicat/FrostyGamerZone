package dev.ricecx.frostygamerzone.frosthub;

import dev.ricecx.frostygamerzone.api.plugins.core.FrostyGamerCoreAPI;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.modules.noattacking.NoAttackingModule;
import dev.ricecx.frostygamerzone.bukkitapi.modules.nofalldamage.NoFallDamage;
import dev.ricecx.frostygamerzone.bukkitapi.modules.scoreboard.ScoreboardModule;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.frosthub.modules.lobbyitems.LobbyItemsModule;
import dev.ricecx.frostygamerzone.frosthub.modules.platelaunchers.PlateLauncherModules;
import dev.ricecx.frostygamerzone.frosthub.modules.scoreboards.LobbyScoreboardModule;
import dev.ricecx.frostygamerzone.frosthub.scoreboards.LobbyBoard;
import org.bukkit.Bukkit;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;

public final class Frosthub extends CorePlugin {


    private ScoreboardModule scoreboardModule;
    private LobbyTabList tabList;
    private FrostyGamerCoreAPI coreAPI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        FrostyGamerCoreAPI coreAPI = CorePlugin.getService(FrostyGamerCoreAPI.class);

        if (coreAPI == null) throw new RuntimeException("FrostyGamerCoreAPI isn't loaded!");
        this.coreAPI = coreAPI;

        scoreboardModule = Module.loadModule(ScoreboardModule.class);
        CorePlugin.getAllPlayers().forEach((p) -> scoreboardModule.provideScoreboard(p, new LobbyBoard(p)));

        tabList = new LobbyTabList();

        Module.loadModule(LobbyScoreboardModule.class);
        Module.loadModule(PlateLauncherModules.class);
        Module.loadModule(NoFallDamage.class);
        Module.loadModule(LobbyItemsModule.class);
        Module.loadModule(NoAttackingModule.class);

        LoggingUtils.info("Frost hub has loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getOnlinePlayers().forEach(scoreboardModule::removeScoreboard);
    }

    public FrostyGamerCoreAPI getCoreAPI() {
        return coreAPI;
    }

    public LobbyTabList getTabList() {
        return tabList;
    }

    public static Frosthub getInstance() {
        return getPlugin(Frosthub.class);
    }

}
