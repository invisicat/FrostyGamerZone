package dev.ricecx.frostygamerzone.api;

import org.intellij.lang.annotations.Language;

public enum DefaultTables {
    PLAYER_TABLE("CREATE TABLE IF NOT EXISTS `players` (" +
            "`uuid` VARCHAR(255) UNIQUE NULL PRIMARY KEY," +
            "`firstIp` VARCHAR(255)," +
            "`lastIpJoined` VARCHAR(255)," +
            "`firstJoined` TIMESTAMP," +
            "`lastJoined` TIMESTAMP" +
            ")");

    DefaultTables(@Language("sql") String sql) {
    }
}
