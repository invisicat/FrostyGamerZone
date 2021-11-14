package dev.ricecx.frostygamerzone.core.api;

import dev.ricecx.frostygamerzone.api.plugins.core.FrostyGamerCoreAPI;
import dev.ricecx.frostygamerzone.api.plugins.core.LuckPermsAPI;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.core.modules.luckperms.LuckPermsModule;

public class FrostyCoreAPI implements FrostyGamerCoreAPI {
    @Override
    public LuckPermsAPI getLuckPermsAPI() {
        if(Module.getModule(LuckPermsModule.class).isEmpty()) return null;
        return Module.getModule(LuckPermsModule.class).get();
    }
}
