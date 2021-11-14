package dev.ricecx.frostygamerzone.frosthub.modules.lobbyitems;

import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.PlayerInventory;

@ModuleInfo
public class LobbyItemsModule extends Module {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        evt.getPlayer().getInventory().clear();

        PlayerInventory playerInventory = evt.getPlayer().getInventory();

        playerInventory.setItem(0, new ItemBuilder(Material.BOW).setName("&3&lLauncher").hideAttributes().addEnchantGlow(Enchantment.ARROW_INFINITE, 1).toItemStack());
        playerInventory.setItem(4, new ItemBuilder(Material.COMPASS).setName("&a&lServer Selector").toItemStack());
        playerInventory.setItem(8, new ItemBuilder(Material.CLOCK).setName("&b&lSettings").toItemStack());

        playerInventory.setHeldItemSlot(4);
    }

    @EventHandler
    public void onItemInteract(InventoryClickEvent evt) {
        evt.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent evt) {
        evt.setCancelled(true);
    }
    @EventHandler
    public void onPlayerSwap(PlayerSwapHandItemsEvent evt) {
        evt.setCancelled(true);
    }
}
