package dev.ricecx.frostygamerzone.bukkitapi.user.impl;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.DataUser;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.WaitLoader;

import java.util.UUID;

public abstract class DataUserImpl extends UserImpl implements DataUser {

    public DataUserImpl(UUID uuid) {
        super(uuid);
    }


    /**
     * Load the user's data here.
     */
    public abstract void loadUserData();

    @Override
    public void load(WaitLoader loader) {
        long start = System.nanoTime();

        // load from database here

        // load user data
        loadUserData();
    }
}
