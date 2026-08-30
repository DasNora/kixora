package com.sneakershop.util;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBConnection manages a connection pool using Apache Tomcat DBCP2.
 *
 * <p>It reads database settings from db.properties (host, port, name, user, password)
 * and creates a pool of ready-to-use connections. This avoids the overhead of
 * opening a new connection for every request.</p>
 *
 * <p>Java 8 compatible — uses explicit type declarations and traditional IO.</p>
 */
public class DBConnection {

    private static BasicDataSource dataSource;

    // Static initializer — runs once when the class is first loaded
    static {
        try {
            Properties props = new Properties();
            InputStream input = DBConnection.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties not found in classpath");
            }

            props.load(input);
            input.close();

            String host = props.getProperty("db.host", "localhost");
            String port = props.getProperty("db.port", "3306");
            String dbName = props.getProperty("db.name", "sneaker_shop_db");
            String user = props.getProperty("db.user", "root");
            String password = props.getProperty("db.password", "root");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                    + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setMinIdle(Integer.parseInt(props.getProperty("db.pool.minIdle", "5")));
            dataSource.setMaxTotal(Integer.parseInt(props.getProperty("db.pool.maxTotal", "20")));
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize connection pool", e);
        }
    }

    /**
     * Borrow a connection from the pool.
     * Always close it in a finally block to return it to the pool.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Close the entire pool — typically called on application shutdown.
     */
    public static void closePool() {
        try {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}