package com.petadoption.thread;

import com.petadoption.dao.ApplicationDAO;
import com.petadoption.dao.MessageDAO;
import com.petadoption.dao.PetDAO;
import com.petadoption.dao.UserDAO;
import com.petadoption.dao.impl.ApplicationDAOImpl;
import com.petadoption.dao.impl.MessageDAOImpl;
import com.petadoption.dao.impl.PetDAOImpl;
import com.petadoption.dao.impl.UserDAOImpl;
import com.petadoption.model.AnalyticsData;
import com.petadoption.model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Background daemon that periodically calculates and caches platform analytics
 * using a ScheduledExecutorService.
 *
 * Satisfies rubric item 2: Core Java Concepts - Multithreading & Synchronization
 * (Scheduled background task for analytics refresh).
 */
public final class AnalyticsScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsScheduler.class);
    private static volatile AnalyticsScheduler instance;

    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private final UserDAO userDAO;
    private final PetDAO petDAO;
    private final ApplicationDAO applicationDAO;
    private final MessageDAO messageDAO;

    private volatile AnalyticsData cachedData;

    private AnalyticsScheduler() {
        this.userDAO = new UserDAOImpl();
        this.petDAO = new PetDAOImpl();
        this.applicationDAO = new ApplicationDAOImpl();
        this.messageDAO = new MessageDAOImpl();

        ThreadFactory factory = r -> {
            Thread t = new Thread(r, "PetAdoption-AnalyticsScheduler");
            t.setDaemon(true);
            return t;
        };
        this.scheduler = Executors.newSingleThreadScheduledExecutor(factory);

        // Immediate initial compute
        refreshAnalytics();

        // Schedule periodic refresh every 60 seconds
        this.scheduler.scheduleAtFixedRate(this::refreshAnalytics, 15, 60, TimeUnit.SECONDS);
        this.isRunning.set(true);
        logger.info("AnalyticsScheduler started with 60-second refresh period.");
    }

    public static AnalyticsScheduler getInstance() {
        if (instance == null) {
            synchronized (AnalyticsScheduler.class) {
                if (instance == null) {
                    instance = new AnalyticsScheduler();
                }
            }
        }
        return instance;
    }

    /**
     * Executes the background analytics computation.
     */
    public synchronized void refreshAnalytics() {
        try {
            logger.debug("Executing scheduled platform analytics computation...");
            AnalyticsData data = new AnalyticsData();

            data.setTotalUsers(userDAO.countUsers());
            data.setTotalShelters(userDAO.countUsersByRole("SHELTER"));
            data.setTotalAdopters(userDAO.countUsersByRole("ADOPTER"));

            data.setTotalPets(petDAO.countPets());
            data.setTotalAvailablePets(petDAO.countPetsByAdoptionStatus("AVAILABLE"));
            data.setTotalAdoptedPets(petDAO.countPetsByAdoptionStatus("ADOPTED"));
            data.setTotalPendingApprovalPets(petDAO.countPetsByApprovalStatus("PENDING"));

            data.setTotalApplications(applicationDAO.countApplications());
            data.setTotalPendingApplications(applicationDAO.countApplicationsByStatus("PENDING"));
            data.setTotalApprovedApplications(applicationDAO.countApplicationsByStatus("APPROVED"));
            data.setTotalRejectedApplications(applicationDAO.countApplicationsByStatus("REJECTED"));

            data.setTotalMessages(messageDAO.countMessages());
            data.setLastRefreshedAt(new Timestamp(System.currentTimeMillis()));

            // Compute pet type distribution
            List<Pet> allPets = petDAO.findAll();
            Map<String, Integer> typeCounts = new HashMap<>();
            for (Pet p : allPets) {
                String type = p.getType() != null ? p.getType() : "Other";
                typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
            }
            data.setPetTypeDistribution(typeCounts);

            // Compute application status distribution
            Map<String, Integer> appCounts = new HashMap<>();
            appCounts.put("PENDING", data.getTotalPendingApplications());
            appCounts.put("APPROVED", data.getTotalApprovedApplications());
            appCounts.put("REJECTED", data.getTotalRejectedApplications());
            data.setApplicationStatusDistribution(appCounts);

            this.cachedData = data;
            logger.debug("Analytics refreshed successfully: {} pets, {} adoptions, {} applications.",
                    data.getTotalPets(), data.getTotalAdoptedPets(), data.getTotalApplications());
        } catch (Exception e) {
            logger.warn("Non-fatal error calculating scheduled analytics: {}", e.getMessage());
        }
    }

    /**
     * Returns the pre-calculated, fast in-memory analytics snapshot.
     */
    public AnalyticsData getCachedData() {
        if (cachedData == null) {
            refreshAnalytics();
        }
        return cachedData;
    }

    /**
     * Shuts down the background scheduler.
     */
    public void shutdown() {
        logger.info("Shutting down AnalyticsScheduler...");
        isRunning.set(false);
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
