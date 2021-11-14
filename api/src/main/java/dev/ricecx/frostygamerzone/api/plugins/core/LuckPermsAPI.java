package dev.ricecx.frostygamerzone.api.plugins.core;

import lombok.SneakyThrows;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface LuckPermsAPI {
    CompletableFuture<User> getOfflineUser(UUID uuid);

    User getUser(UUID uuid);

    String getMeta(@NotNull UUID uuid, String key);

    @SneakyThrows
    String getPrefix(UUID uuid);

    net.luckperms.api.LuckPerms getLuckPerms();
}
