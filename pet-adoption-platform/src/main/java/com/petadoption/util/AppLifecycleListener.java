package com.petadoption.util;

import com.petadoption.thread.AnalyticsScheduler;
import com.petadoption.thread.NotificationThreadPool;
import com.petadoption.thread.SettingsCache;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener managing background threads and database pool lifecycle.
 * Prevents memory leaks upon Tomcat reload or redeployment.
 */
@WebListener
public class AppLifecycleListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppLifecycleListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("=================================================================");
        logger.info("  PAWHAVEN PET ADOPTION PLATFORM INITIALIZING (Tomcat 10)");
        logger.info("=================================================================");

        try {
            // Eagerly initialize core singletons
            DBConnectionUtil.getInstance();
            SettingsCache.getInstance();
            NotificationThreadPool.getInstance();
            AnalyticsScheduler.getInstance();

            logger.info("PawHaven Platform services initialized successfully.");
        } catch (Exception e) {
            logger.error("Initialization failure during startup: {}", e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down PawHaven Pet Adoption Platform services...");

        try {
            AnalyticsScheduler.getInstance().shutdown();
            NotificationThreadPool.getInstance().shutdown();
            DBConnectionUtil.getInstance().closePool();
            logger.info("All background threads and database connection pools cleanly stopped.");
        } catch (Exception e) {
            logger.error("Error during graceful shutdown: {}", e.getMessage(), e);
        }
    }
}
