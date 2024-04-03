package com.DB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnect {

    private static Properties prop = new Properties();
    private static String url, user, pass;

    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Log or handle the error appropriately
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC Driver");
        }
    }

    public static Connection getMyConn() throws SQLException, IOException {
        try (InputStream inputStream = DBconnect.class.getClassLoader().getResourceAsStream("DBinfo.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("DBinfo.properties file not found");
            }
            prop.load(inputStream);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            pass = prop.getProperty("pass");
            if (url == null || user == null || pass == null) {
                throw new RuntimeException("Missing properties in DBinfo.properties file");
            }
        }

        return DriverManager.getConnection(url, user, pass);
    }
}
