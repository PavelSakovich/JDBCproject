package org.example.util;

import lombok.Value;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
@Log
@Value
public  class ConnectionManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";

    public static Connection open () {
        try {
            Connection connection = DriverManager.getConnection( URL, USERNAME, PASSWORD);
            if (null != connection) {
                log.info("---------Соединение установлено------------");
            }
            return connection;
        } catch (SQLException e) {
            log.info("---------Соединение не установлено------------");
            e.getStackTrace();
            return null;
        }
    }
}