package dev.ricecx.frostygamerzone.api;

import org.intellij.lang.annotations.Language;

public enum SQLQueries {

    ON_JOIN("INSERT INTO global (uuid, last_join) VALUES (?,?) ON CONFLICT (uuid) DO UPDATE SET last_join = ?"),
    FROSTUSER("SELECT * FROM global WHERE uuid = ?")
    ;


    private final String statement;
    SQLQueries(@Language("sql")String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
