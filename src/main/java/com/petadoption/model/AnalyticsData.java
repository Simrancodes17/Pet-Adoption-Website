package com.petadoption.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates system metrics and adoption analytics.
 * Populated periodically by the ScheduledExecutorService background worker.
 */
public class AnalyticsData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalUsers;
    private int totalShelters;
    private int totalAdopters;
    private int totalPets;
    private int totalAvailablePets;
    private int totalPendingApprovalPets;
    private int totalAdoptedPets;
    private int totalApplications;
    private int totalPendingApplications;
    private int totalApprovedApplications;
    private int totalRejectedApplications;
    private int totalMessages;
    private Timestamp lastRefreshedAt;

    private Map<String, Integer> petTypeDistribution = new HashMap<>();
    private Map<String, Integer> applicationStatusDistribution = new HashMap<>();

    public AnalyticsData() {
        this.lastRefreshedAt = new Timestamp(System.currentTimeMillis());
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalShelters() {
        return totalShelters;
    }

    public void setTotalShelters(int totalShelters) {
        this.totalShelters = totalShelters;
    }

    public int getTotalAdopters() {
        return totalAdopters;
    }

    public void setTotalAdopters(int totalAdopters) {
        this.totalAdopters = totalAdopters;
    }

    public int getTotalPets() {
        return totalPets;
    }

    public void setTotalPets(int totalPets) {
        this.totalPets = totalPets;
    }

    public int getTotalAvailablePets() {
        return totalAvailablePets;
    }

    public void setTotalAvailablePets(int totalAvailablePets) {
        this.totalAvailablePets = totalAvailablePets;
    }

    public int getTotalPendingApprovalPets() {
        return totalPendingApprovalPets;
    }

    public void setTotalPendingApprovalPets(int totalPendingApprovalPets) {
        this.totalPendingApprovalPets = totalPendingApprovalPets;
    }

    public int getTotalAdoptedPets() {
        return totalAdoptedPets;
    }

    public void setTotalAdoptedPets(int totalAdoptedPets) {
        this.totalAdoptedPets = totalAdoptedPets;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }

    public int getTotalPendingApplications() {
        return totalPendingApplications;
    }

    public void setTotalPendingApplications(int totalPendingApplications) {
        this.totalPendingApplications = totalPendingApplications;
    }

    public int getTotalApprovedApplications() {
        return totalApprovedApplications;
    }

    public void setTotalApprovedApplications(int totalApprovedApplications) {
        this.totalApprovedApplications = totalApprovedApplications;
    }

    public int getTotalRejectedApplications() {
        return totalRejectedApplications;
    }

    public void setTotalRejectedApplications(int totalRejectedApplications) {
        this.totalRejectedApplications = totalRejectedApplications;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }

    public Timestamp getLastRefreshedAt() {
        return lastRefreshedAt;
    }

    public void setLastRefreshedAt(Timestamp lastRefreshedAt) {
        this.lastRefreshedAt = lastRefreshedAt;
    }

    public Map<String, Integer> getPetTypeDistribution() {
        return petTypeDistribution;
    }

    public void setPetTypeDistribution(Map<String, Integer> petTypeDistribution) {
        this.petTypeDistribution = petTypeDistribution;
    }

    public Map<String, Integer> getApplicationStatusDistribution() {
        return applicationStatusDistribution;
    }

    public void setApplicationStatusDistribution(Map<String, Integer> applicationStatusDistribution) {
        this.applicationStatusDistribution = applicationStatusDistribution;
    }
}
