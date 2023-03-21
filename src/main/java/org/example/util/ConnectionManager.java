package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";
    private static Logger logger = Logger.getLogger("org.example.util.ConnectionManager");

    public static String getURL() {
        return URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static Logger getLogger() {
        return logger;
    }

    public ConnectionManager() {
    }

    public static Connection open() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (null != connection) {
                logger.info("---------Соединение установлено------------");
            }
            return connection;
        } catch (SQLException e) {
            logger.info("---------Соединение не установлено------------");
            e.getStackTrace();
            return null;
        }
    }
}