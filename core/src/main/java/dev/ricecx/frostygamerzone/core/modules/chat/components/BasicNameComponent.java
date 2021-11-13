package dev.ricecx.frostygamerzone.core.modules.chat.components;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

public class BasicNameComponent implements ChatComponent {
    @Override
    public BaseComponent getComponent(Player player) {
        ComponentBuilder componentBuilder = new ComponentBuilder(player.getName());

        componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.color(
                "&7Player: &6" + player.getName(),
                "&aClick to message me!")).create()));
        componentBuilder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName())).create();
        return new TextComponent(componentBuilder.create());
    }


}
