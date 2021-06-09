package com.epam.project.hotel.listener;

import com.epam.project.hotel.sql.CreateDB;
import com.epam.project.hotel.sql.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Calendar;

@WebListener
public class AppListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(AppListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("AppListener#contextInit");
        try {
            CreateDB.createDB();
        } catch (DBException e) {
            log.error("Problem in create DB",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
