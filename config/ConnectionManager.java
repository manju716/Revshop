package com.config;

import com.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    DatabaseConfig.URL,
                    DatabaseConfig.USERNAME,
                    DatabaseConfig.PASSWORD
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
