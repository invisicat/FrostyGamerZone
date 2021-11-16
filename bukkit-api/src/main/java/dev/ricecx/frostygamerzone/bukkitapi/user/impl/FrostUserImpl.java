package dev.ricecx.frostygamerzone.bukkitapi.user.impl;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.FrostUser;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FrostUserImpl extends DataUserImpl implements FrostUser {


    public FrostUserImpl(String username, UUID uuid) {
        super(uuid);

    }

    @Override
    public void loadUserData() {

    }

    @Override
    public void loadSecondary(Player player) {

    }

    @Override
    public void save() {

    }
}
