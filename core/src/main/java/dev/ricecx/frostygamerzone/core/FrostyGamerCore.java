package dev.ricecx.frostygamerzone.core;


import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.core.api.FrostyGamerCoreAPI;
import dev.ricecx.frostygamerzone.core.commands.PingCommand;
import dev.ricecx.frostygamerzone.core.modules.Modules;

public final class FrostyGamerCore extends CorePlugin implements FrostyGamerCoreAPI {

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        getLogger().info("Loading FrostyGamerCore!");


        Modules.LUCK_PERMS.enable();
        Modules.CHAT.enable();
        Modules.NO_BLOCK_BREAK.enable();

        loadCommands();
        getLogger().info("Loaded FrostyGamerCore in " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public void loadCommands() {
        registerCommands(
                new PingCommand()
        );
    }

    @Override
    public void onDisable() {}
}