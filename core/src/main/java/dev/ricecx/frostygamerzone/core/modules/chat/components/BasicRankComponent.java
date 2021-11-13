package dev.ricecx.frostygamerzone.core.modules.chat.components;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.core.modules.luckperms.LuckPermsModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BasicRankComponent implements ChatComponent {
    @Override
    public BaseComponent getComponent(Player player) {
        Optional<LuckPermsModule> lpm = Module.getModule(LuckPermsModule.class);

        if(lpm.isEmpty()) return null;

        String prefix = lpm.get().getPrefix(player.getUniqueId());

        if(prefix == null) return null;
        ComponentBuilder componentBuilder = new ComponentBuilder(Utils.color(prefix));

        return new TextComponent(componentBuilder.create());
    }
}
