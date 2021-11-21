package dev.ricecx.frostygamerzone.bukkitapi.user.impl;

import dev.ricecx.frostygamerzone.api.Rank;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.FrostUser;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FrostUserImpl extends DataUserImpl implements FrostUser {


    public FrostUserImpl(String username, UUID uuid) {
        super(uuid);

    }

    @Override
    public void loadUserData() {

        LoggingUtils.info("LOADING USER DATA");
    }

    @Override
    public void loadSecondary(Player player) {
        LoggingUtils.info("LOADING USER");
    }

    @Override
    public void save() {
        LoggingUtils.info("SAVING USER");
    }

    @Override
    public Rank getRank() {
        return null;
    }
}
