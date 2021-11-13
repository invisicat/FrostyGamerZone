package dev.ricecx.frostygamerzone.core.modules.luckperms;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.LuckPermsEvent;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeClearEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.event.user.UserLoadEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@ModuleInfo
public class LuckPermsModule extends Module {

    @Getter private final LuckPerms luckPerms;

    public LuckPermsModule() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.luckPerms = provider.getProvider();
            LoggingUtils.info("hooked into LuckPerms!"

            );
            luckPerms.getEventBus().subscribe(UserLoadEvent.class, this::onUserLoad);
            luckPerms.getEventBus().subscribe(NodeClearEvent.class, this::onNodeClear);
            luckPerms.getEventBus().subscribe(NodeAddEvent.class, this::onNodeAdd);
            luckPerms.getEventBus().subscribe(NodeRemoveEvent.class, this::onNodeRemove);

        } else {
            this.luckPerms = null;
            LoggingUtils.error("LuckPerms is not installed :(");
        }
    }

    private void onUserLoad(UserLoadEvent t) {
        //
    }
    private void onNodeClear(NodeClearEvent evt) {
        if(!evt.isUser()) return;
        User target = (User) evt.getTarget();
        Player player = Bukkit.getPlayer(target.getUniqueId());
        if (player == null) return;
        player.sendMessage(Utils.color("[Rank]", "Your ranks has been cleared!"));
    }

    private void onNodeAdd(NodeAddEvent evt) {
        if(!evt.isUser()) return;

        User target = (User) evt.getTarget();
        Node node = evt.getNode();
        Player player = Bukkit.getPlayer(target.getUniqueId());
        if(player == null) return; // aren't in this server.
        if(node instanceof PermissionNode) {
            String permission = ((PermissionNode) node).getPermission();
            player.sendMessage(Utils.color("The permission &b", permission, " &7was added to your account!"));
        } else if(node instanceof InheritanceNode) {
            Optional<Group> group = Optional.ofNullable(luckPerms.getGroupManager().getGroup(((InheritanceNode) node).getGroupName()));
            if (group.isEmpty())
                return;

            player.sendMessage(Utils.color("The rank &b", group.get().getDisplayName(), " &7was added to your account!"));
        }
    }
    private void onNodeRemove(NodeRemoveEvent evt) {
        if(!evt.isUser()) return;

        User target = (User) evt.getTarget();
        Node node = evt.getNode();
        Player player = Bukkit.getPlayer(target.getUniqueId());
        if(player == null) return; // aren't in this server.
        if(node instanceof PermissionNode) {
            String permission = ((PermissionNode) node).getPermission();
            player.sendMessage(Utils.color("The permission &b", permission, " &7was removed from your account!"));
        } else if(node instanceof InheritanceNode) {
            Optional<Group> group = Optional.ofNullable(luckPerms.getGroupManager().getGroup(((InheritanceNode) node).getGroupName()));
            if (group.isEmpty())
                return;

            player.sendMessage(Utils.color("The rank &b", group.get().getDisplayName(), " &7was removed from your account!"));
        }
    }

    public CompletableFuture<User> getOfflineUser(UUID uuid) {
        Objects.requireNonNull(luckPerms);
        return luckPerms.getUserManager().loadUser(uuid);
    }
    public User getUser(UUID uuid) {
        Objects.requireNonNull(luckPerms);
        return luckPerms.getUserManager().getUser(uuid);
    }

    public final String getMeta(@NotNull UUID uuid, String key) {
        User user = getUser(uuid);
        if (user == null)
            return "";
        return user.getCachedData().getMetaData(user.getQueryOptions()).getMetaValue(key);
    }

    @SneakyThrows
    public final String getPrefix(UUID uuid) {
        User luckUser = getUser(uuid);

        if(luckUser == null) {
            CompletableFuture<User> offlineLuckUser = getOfflineUser(uuid);
            if(offlineLuckUser == null)
                return "";
            luckUser = offlineLuckUser.get();
        }
        return luckUser.getCachedData().getMetaData(luckUser.getQueryOptions()).getPrefix();
    }


}
