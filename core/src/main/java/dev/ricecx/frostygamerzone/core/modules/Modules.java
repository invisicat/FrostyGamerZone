package dev.ricecx.frostygamerzone.core.modules;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.core.modules.chat.ChatModule;
import dev.ricecx.frostygamerzone.core.modules.luckperms.LuckPermsModule;
import dev.ricecx.frostygamerzone.core.modules.noblockbreak.NoBlockBreakModule;

public enum Modules {
    LUCK_PERMS(LuckPermsModule.class),
    CHAT(ChatModule.class),
    NO_BLOCK_BREAK(NoBlockBreakModule.class)
    ;

    private final Class<? extends Module> module;

    Modules(Class<? extends Module> module) {
        this.module = module;
    }

    public void enable() {
        Module.loadModule(module);
    }
}
