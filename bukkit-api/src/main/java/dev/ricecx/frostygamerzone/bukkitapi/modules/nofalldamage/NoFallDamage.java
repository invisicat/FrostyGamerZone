package dev.ricecx.frostygamerzone.bukkitapi.modules.nofalldamage;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

@ModuleInfo
public class NoFallDamage extends Module {

    @EventHandler
    public void onDamage(EntityDamageEvent evt) {
        if(!evt.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        if(!(evt.getEntity() instanceof Player)) return;

        evt.setCancelled(true);
    }
}
