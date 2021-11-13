package dev.ricecx.frostygamerzone.core.modules.chat.components;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

public interface ChatComponent {
    BaseComponent getComponent(Player player);
}
