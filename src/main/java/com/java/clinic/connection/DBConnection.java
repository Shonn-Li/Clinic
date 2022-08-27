package com.java.clinic.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // cloud
    private static final String protocol = "jdbc:";
    private static final String vendorName = "mysql:";
    private static final String ipAddress = "//clinic.c9ysiejxmta6.us-west-1.rds.amazonaws.com:3306/clinic";
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "admin";
    private static final String password = "shonnlee2003";

    // local
//    private static final String protocol = "jdbc:";
//    private static final String vendorName = "mysql:";
//    private static final String ipAddress = "//localhost:3306/clinic";
//    private static final String jdbcURL = protocol + vendorName + ipAddress;
//    private static final String Driver = "com.mysql.cj.jdbc.Driver";
//    private static final String username = "root";
//    private static final String password = "Shonnlee2003";
    public static Connection conn;

    public static void connect() {
        try {
            Class.forName(Driver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to MySQL Database");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error: " + e.getErrorCode());
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}
