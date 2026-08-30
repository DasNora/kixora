package com.sneakershop.servlet;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class DatabaseShutdownListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Nothing needed on startup
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 1. Shut down the MySQL cleanup thread
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Throwable t) {
            sce.getServletContext().log("Failed to shut down MySQL cleanup thread", t);
        }

        // 2. Deregister JDBC drivers to prevent memory leaks
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == Thread.currentThread().getContextClassLoader()) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    sce.getServletContext().log("Failed to deregister driver: " + driver, e);
                }
            }
        }
    }
}

