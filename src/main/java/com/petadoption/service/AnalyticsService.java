package com.petadoption.service;

import com.petadoption.dao.ApplicationDAO;
import com.petadoption.dao.PetDAO;
import com.petadoption.dao.impl.ApplicationDAOImpl;
import com.petadoption.dao.impl.PetDAOImpl;
import com.petadoption.model.AnalyticsData;
import com.petadoption.model.Application;
import com.petadoption.model.Pet;
import com.petadoption.thread.AnalyticsScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service providing high-level analytics for administrators and shelters.
 */
public class AnalyticsService {

    private final AnalyticsScheduler analyticsScheduler;
    private final PetDAO petDAO;
    private final ApplicationDAO applicationDAO;

    public AnalyticsService() {
        this.analyticsScheduler = AnalyticsScheduler.getInstance();
        this.petDAO = new PetDAOImpl();
        this.applicationDAO = new ApplicationDAOImpl();
    }

    public AnalyticsService(AnalyticsScheduler scheduler, PetDAO petDAO, ApplicationDAO applicationDAO) {
        this.analyticsScheduler = scheduler;
        this.petDAO = petDAO;
        this.applicationDAO = applicationDAO;
    }

    /**
     * Retrieves the platform-wide analytics computed by the background scheduler.
     */
    public AnalyticsData getPlatformAnalytics() {
        return analyticsScheduler.getCachedData();
    }

    /**
     * Computes statistics specific to an individual shelter.
     */
    public Map<String, Object> getShelterStats(int shelterId) {
        Map<String, Object> stats = new HashMap<>();

        List<Pet> pets = petDAO.findByShelterId(shelterId);
        long available = pets.stream().filter(p -> Pet.ADOPTION_AVAILABLE.equalsIgnoreCase(p.getAdoptionStatus())).count();
        long adopted = pets.stream().filter(p -> Pet.ADOPTION_ADOPTED.equalsIgnoreCase(p.getAdoptionStatus())).count();
        long pendingApproval = pets.stream().filter(p -> Pet.APPROVAL_PENDING.equalsIgnoreCase(p.getApprovalStatus())).count();

        List<Application> apps = applicationDAO.findByShelterId(shelterId);
        long pendingApps = apps.stream().filter(a -> Application.STATUS_PENDING.equalsIgnoreCase(a.getStatus())).count();
        long approvedApps = apps.stream().filter(a -> Application.STATUS_APPROVED.equalsIgnoreCase(a.getStatus())).count();

        stats.put("totalPets", pets.size());
        stats.put("availablePets", available);
        stats.put("adoptedPets", adopted);
        stats.put("pendingApprovalPets", pendingApproval);
        stats.put("totalApplications", apps.size());
        stats.put("pendingApplications", pendingApps);
        stats.put("approvedApplications", approvedApps);

        return stats;
    }
}
