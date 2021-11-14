package dev.ricecx.frostygamerzone.bukkitapi.modules.nohunger;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@ModuleInfo
public class NoHungerModule extends Module {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent evt) {
        evt.setCancelled(true);
    }
}
