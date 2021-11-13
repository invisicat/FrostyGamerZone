package dev.ricecx.frostygamerzone.core.modules.chat;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.core.modules.chat.components.BasicNameComponent;
import dev.ricecx.frostygamerzone.core.modules.chat.components.BasicRankComponent;
import dev.ricecx.frostygamerzone.core.modules.chat.components.ChatComponent;
import dev.ricecx.frostygamerzone.core.modules.luckperms.LuckPermsModule;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ModuleInfo(

)
public class ChatModule extends Module {

    private final ChatComponent[] chatComponents = new ChatComponent[]{
            new BasicRankComponent(),
            new BasicNameComponent(),
    };

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(AsyncPlayerChatEvent evt) {
        if (evt.isCancelled()) return;

        Player player = evt.getPlayer();
        String message = evt.getMessage();

        if (message.trim().isEmpty()) {
            player.sendMessage("You cannot send empty chat messages");
        }

        evt.setCancelled(true);
        List<BaseComponent> components = new ArrayList<>();
        String defaultColor = getLuckPermsProvider().getMeta(player.getUniqueId(), "chatColor");
        ChatColor chatColor = defaultColor == null ? ChatColor.GRAY : ChatColor.of(defaultColor);

        components.add(new TextComponent("§8▸ §7"));

        for (ChatComponent chatComponent : chatComponents) {
            BaseComponent component = chatComponent.getComponent(player);
            if (component != null) components.add(component);
        }

        components.add(new TextComponent("§8 » §7"));
        components.addAll(Arrays.asList(new ComponentBuilder(message).color(chatColor).create()));
        BaseComponent[] baseComponents = components.toArray(new BaseComponent[0]);
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(baseComponents);
        }
    }

    private LuckPermsModule getLuckPermsProvider() {
        Optional<LuckPermsModule> module = Module.getModule(LuckPermsModule.class);

        if (module.isEmpty()) {
            LoggingUtils.error("Luck perms module is not active. wtf?");
            throw new RuntimeException("Luck perms");
        }

        return module.get();
    }
}
