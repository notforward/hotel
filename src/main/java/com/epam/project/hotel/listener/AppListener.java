package com.epam.project.hotel.listener;

import com.epam.project.hotel.dao.CheckDAO;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.CreateDB;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebListener
public class AppListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(AppListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("AppListener#contextInit");
        try {
            CreateDB.createDB();
        } catch (DBException e) {
            log.error("Problem in create DB", e);
        }
        Terminator terminator = new Terminator();
        terminator.start();
    }
    private class Terminator extends Thread{
        @Override
        public void run() {
            log.info("Terminator started...");
            Factory factory = MySQLFactory.getInstance();
            CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
            List<Check> checks;
            Connection con = null;
            try {
                con = DataSource.getConnection();
                checks = checkDAO.findAllChecks(con);
                long millis = System.currentTimeMillis();
                java.sql.Date today = new java.sql.Date(millis);
                Date terminate;
                for (Check check: checks
                     ) {
                    terminate = check.getCheck_terminate();
                    if(today.after(terminate) && "NOT PAYED".equals(check.getCheck_status())){
                        checkDAO.updateCheckStatus(con, check, "TERMINATED");
                        con.commit();
                        log.info("killed check = " + check);
                    }
                }
                long timeoutToCheck = 69120000;
                synchronized (currentThread()) {
                    try {
                        Thread.currentThread().wait(timeoutToCheck);
                        run();
                    } catch (InterruptedException e) {
                        throw new DBException("Cannot check and terminate", e);
                    }
                }
            } catch (DBException | SQLException e) {
                log.error("Problem at terminator", e);
            }
            finally {
                checkDAO.close(con);
            }
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
