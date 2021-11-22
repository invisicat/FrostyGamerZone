package dev.ricecx.frostygamerzone.bukkitapi.user.impl;

import dev.ricecx.frostygamerzone.api.SQLQueries;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.DataUser;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.WaitLoader;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DataUserImpl extends UserImpl implements DataUser {

    @Getter private final UUID dataUserUUID;
    public DataUserImpl(UUID uuid) {
        super(uuid);
        dataUserUUID = uuid;
    }


    /**
     * Load the user's data here.
     */
    public abstract void loadUserData();

    @Override
    public void load(WaitLoader loader) {
        long start = System.nanoTime();

        DatabaseManager.getSQLUtils().executeUpdate(SQLQueries.ON_JOIN.getStatement(), (ps) -> {
            ps.setString(1, dataUserUUID.toString());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        });
        LoggingUtils.info("Loaded user " + dataUserUUID + " in " + (System.currentTimeMillis() - start) + "ms");

        loader.verifyResponse(true);
        // load user data
        loadUserData();
    }
}
