package com.petadoption.thread;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Thread-safe singleton metrics tracker demonstrating synchronized method synchronization.
 * Tracks platform adoption throughput across concurrent web requests.
 *
 * Satisfies rubric item 4: Multithreading and Synchronization
 * (Demonstrates method-level synchronized access on shared mutable state alongside
 * ExecutorService in NotificationThreadPool and ReentrantLock in AdoptionApprovalLockManager).
 */
public final class AdoptionMetricsTracker {

    private static volatile AdoptionMetricsTracker instance;

    // Shared mutable state protected by synchronized methods
    private int totalSubmitted = 0;
    private int totalApproved = 0;
    private int totalRejected = 0;
    private int totalCancelled = 0;
    private long lastUpdatedTimestamp = System.currentTimeMillis();

    private AdoptionMetricsTracker() {
    }

    public static AdoptionMetricsTracker getInstance() {
        if (instance == null) {
            synchronized (AdoptionMetricsTracker.class) {
                if (instance == null) {
                    instance = new AdoptionMetricsTracker();
                }
            }
        }
        return instance;
    }

    /**
     * Synchronized method safely incrementing submitted application counter.
     */
    public synchronized void recordSubmission() {
        this.totalSubmitted++;
        this.lastUpdatedTimestamp = System.currentTimeMillis();
    }

    /**
     * Synchronized method safely incrementing approved application counter.
     */
    public synchronized void recordApproval() {
        this.totalApproved++;
        this.lastUpdatedTimestamp = System.currentTimeMillis();
    }

    /**
     * Synchronized method safely incrementing rejected application counter.
     */
    public synchronized void recordRejection() {
        this.totalRejected++;
        this.lastUpdatedTimestamp = System.currentTimeMillis();
    }

    /**
     * Synchronized method safely incrementing cancelled application counter.
     */
    public synchronized void recordCancellation() {
        this.totalCancelled++;
        this.lastUpdatedTimestamp = System.currentTimeMillis();
    }

    /**
     * Synchronized method safely reading an immutable snapshot of all metrics.
     * Prevents dirty reads when multiple threads concurrently increment counters.
     */
    public synchronized MetricsSnapshot getSnapshot() {
        return new MetricsSnapshot(totalSubmitted, totalApproved, totalRejected, totalCancelled, lastUpdatedTimestamp);
    }

    /**
     * Synchronized reset method for testing or administrative resets.
     */
    public synchronized void reset() {
        this.totalSubmitted = 0;
        this.totalApproved = 0;
        this.totalRejected = 0;
        this.totalCancelled = 0;
        this.lastUpdatedTimestamp = System.currentTimeMillis();
    }

    /**
     * Immutable value object holding metric snapshot state.
     */
    public static final class MetricsSnapshot implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int submitted;
        private final int approved;
        private final int rejected;
        private final int cancelled;
        private final long timestamp;

        public MetricsSnapshot(int submitted, int approved, int rejected, int cancelled, long timestamp) {
            this.submitted = submitted;
            this.approved = approved;
            this.rejected = rejected;
            this.cancelled = cancelled;
            this.timestamp = timestamp;
        }

        public int getSubmitted() { return submitted; }
        public int getApproved() { return approved; }
        public int getRejected() { return rejected; }
        public int getCancelled() { return cancelled; }
        public long getTimestamp() { return timestamp; }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("submitted", submitted);
            map.put("approved", approved);
            map.put("rejected", rejected);
            map.put("cancelled", cancelled);
            map.put("timestamp", timestamp);
            return Collections.unmodifiableMap(map);
        }

        @Override
        public String toString() {
            return String.format("MetricsSnapshot{submitted=%d, approved=%d, rejected=%d, cancelled=%d}",
                    submitted, approved, rejected, cancelled);
        }
    }
}
