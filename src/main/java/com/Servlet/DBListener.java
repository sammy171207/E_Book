package com.Servlet;

import com.DB.DBconnect;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class DBListener implements ServletContextListener {
    
    private static final Logger logger = Logger.getLogger(DBListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Initialize the database connection when the application starts
            Connection connection = DBconnect.getMyConn();
            ServletContext servletContext = sce.getServletContext();
            servletContext.setAttribute("conn", connection);
            logger.log(Level.INFO, "Database connection initialized.");
        } catch (SQLException | IOException e) {
            logger.log(Level.SEVERE, "Error initializing database connection", e);
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = (Connection) sce.getServletContext().getAttribute("conn");
        if (connection != null) {
            try {
                connection.close();
                logger.log(Level.INFO, "Database connection closed.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }
}
