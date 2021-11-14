package dev.ricecx.frostygamerzone.common.database;

import com.zaxxer.hikari.HikariDataSource;
import dev.ricecx.frostygamerzone.common.LoggingUtils;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager implements Closeable {
    private static final SQLUtils sqlUtils = new SQLUtils();
    private final HikariDataSource dataSource;


    public DatabaseManager(SQLTypes types) {
        dataSource = new HikariDataSource(HikariUtils.generateConfig(
                types,
                new HikariAuthentication("frost", "nigger", "localhost", "postgres"), 5432
        ));
        SQLUtils.setConnection(getConnection());
        LoggingUtils.info("Connected to " + types.name() + "!");
    }

    public Connection getConnection() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        try {

            return dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        this.dataSource.close();
    }

    public boolean isClosed() {
        return dataSource == null || dataSource.isClosed();
    }

    public static SQLUtils getSQLUtils() {
        return sqlUtils;
    }
}
