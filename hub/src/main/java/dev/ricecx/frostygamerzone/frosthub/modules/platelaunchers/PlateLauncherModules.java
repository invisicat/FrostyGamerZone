package dev.ricecx.frostygamerzone.frosthub.modules.platelaunchers;


import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


@ModuleInfo
public class PlateLauncherModules extends Module {

    @EventHandler
    public void onPressurePlate(PlayerInteractEvent evt) {
        if(!evt.getAction().equals(Action.PHYSICAL)) return;

        Location pressurePlateBlock = evt.getPlayer().getLocation();
        if(!pressurePlateBlock.getBlock().getType().name().contains("_PLATE")) return;

        Vector playerVelocity = evt.getPlayer().getFacing().getDirection().clone();
        playerVelocity.multiply(5);
        playerVelocity.setY(2);
        evt.getPlayer().setVelocity(playerVelocity);
    }
}
