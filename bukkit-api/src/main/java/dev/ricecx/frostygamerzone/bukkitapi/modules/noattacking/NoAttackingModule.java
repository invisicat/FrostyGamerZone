package dev.ricecx.frostygamerzone.bukkitapi.modules.noattacking;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@ModuleInfo
public class NoAttackingModule extends Module {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent evt) {
        evt.setCancelled(true);
    }
}
