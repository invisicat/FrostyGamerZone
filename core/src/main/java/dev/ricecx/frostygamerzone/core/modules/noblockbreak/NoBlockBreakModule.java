package dev.ricecx.frostygamerzone.core.modules.noblockbreak;


import dev.ricecx.frostygamerzone.api.permissions.Permissions;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;


@ModuleInfo
public class NoBlockBreakModule extends Module {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent evt) {
        if(!evt.getPlayer().hasPermission(Permissions.ADMIN_BLOCK_BREAK.getPermission()) || !evt.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            evt.setCancelled(true);
    }
}
