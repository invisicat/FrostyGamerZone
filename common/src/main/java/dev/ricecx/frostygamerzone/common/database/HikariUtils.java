package dev.ricecx.frostygamerzone.common.database;

import com.zaxxer.hikari.HikariConfig;

public class HikariUtils {

    public static HikariConfig generateConfig(SQLTypes type, HikariAuthentication authentication) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(type.getDriverName());
        config.setUsername(authentication.username());
        config.setPassword(authentication.password());
        config.setJdbcUrl(generateURL(type.getDriverURL(), authentication.host(), "3306", authentication.database()));
        config.setConnectionTimeout(5000);
        config.setMaximumPoolSize(10);

        return config;
    }

    public static HikariConfig generateConfig(SQLTypes type, HikariAuthentication authentication, int port) {
        HikariConfig config = generateConfig(type, authentication);
        config.setJdbcUrl(generateURL(type.getDriverURL(), authentication.host(), String.valueOf(port), authentication.database()));

        return config;
    }

    public static HikariConfig generateConfig(String username, String password, String driverClassName, String jdbcURL, int connectionTimeout, int maximumPoolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(jdbcURL);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaximumPoolSize(maximumPoolSize);

        return config;
    }

    public static String generateURL(String jdurl, String host, String port, String database) {
        String url = jdurl.replace("{host}", host);
        url = url.replace("{port}", port);
        url = url.replace("{database}", database);

        return url;
    }
}

