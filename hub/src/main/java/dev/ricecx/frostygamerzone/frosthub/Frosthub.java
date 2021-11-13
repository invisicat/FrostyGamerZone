package dev.ricecx.frostygamerzone.frosthub;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.modules.nofalldamage.NoFallDamage;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.frosthub.modules.platelaunchers.PlateLauncherModules;

public final class Frosthub extends CorePlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new PlateLauncherModules();
        new NoFallDamage();
        LoggingUtils.info("Frost hub has loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
