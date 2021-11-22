package dev.ricecx.frostygamerzone.bukkitapi.user.impl;

import dev.ricecx.frostygamerzone.api.Rank;
import dev.ricecx.frostygamerzone.api.SQLQueries;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.FrostUser;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class FrostUserImpl extends DataUserImpl implements FrostUser {

    private int coins;
    private int level;

    public FrostUserImpl(String username, UUID uuid) {
        super(uuid);

    }

    @Override
    public void loadUserData() {

        DatabaseManager.getSQLUtils().executeQuery(SQLQueries.FROSTUSER.getStatement(), (ps) -> ps.setString(1, getDataUserUUID().toString()), (rs) -> {
            if (rs.next()) {
                setCoins(rs.getInt("coins"));
                setLevel(rs.getInt("level"));
            }
            return rs;
        });
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
