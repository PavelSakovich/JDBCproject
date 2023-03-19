package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public  class ConnectionManager {
    private static  String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static  String USERNAME = "postgres";
    private static  String PASSWORD = "1";
    private static  Logger logger = Logger.getLogger("org.example.util.ConnectionManager");

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        ConnectionManager.URL = URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        ConnectionManager.USERNAME = USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        ConnectionManager.PASSWORD = PASSWORD;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ConnectionManager.logger = logger;
    }

    public ConnectionManager() {
    }

    public static Connection open () {
        try {
            Connection connection = DriverManager.getConnection( URL, USERNAME, PASSWORD);
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