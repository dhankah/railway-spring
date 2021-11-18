package com.mospan.railwayspring.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;


import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {
    private static ConnectionPool instance;
    private ComboPooledDataSource cpds;

    public static synchronized ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    private ConnectionPool() {
        try {
            createPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = this.cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createPool() throws Exception {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/railway-spring?useUnicode=true&characterEncoding=utf8");
        cpds.setUser("root");
        cpds.setPassword("root");
        cpds.setMaxStatements(180);
    }
}