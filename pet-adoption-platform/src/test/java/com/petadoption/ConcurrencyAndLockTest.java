package com.petadoption;

import com.petadoption.model.Application;
import com.petadoption.thread.AdoptionApprovalLockManager;
import com.petadoption.thread.NotificationThreadPool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies Rubric Item 2: Multithreading & Synchronization
 * ReentrantLock per-pet synchronization and ExecutorService async notifications.
 */
class ConcurrencyAndLockTest {

    @Test
    @DisplayName("Synchronization: AdoptionApprovalLockManager serializes concurrent approvals for the same pet")
    void testAdoptionApprovalLockManagerSerialization() throws Exception {
        AdoptionApprovalLockManager lockManager = AdoptionApprovalLockManager.getInstance();
        int petId = 999;
        int numberOfThreads = 10;

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(numberOfThreads);

        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger maxConcurrencyObserved = new AtomicInteger(0);
        AtomicInteger currentInCriticalSection = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // Synchronize all threads to fire simultaneously

                    lockManager.executeWithLock(petId, () -> {
                        int current = currentInCriticalSection.incrementAndGet();
                        maxConcurrencyObserved.updateAndGet(max -> Math.max(max, current));

                        // Simulate critical approval operation
                        Thread.sleep(20);

                        counter.incrementAndGet();
                        currentInCriticalSection.decrementAndGet();
                        return null;
                    });
                } catch (Exception e) {
                    fail("Lock execution threw exception: " + e.getMessage());
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        // Release all threads simultaneously
        startLatch.countDown();
        boolean completed = finishLatch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertTrue(completed, "All threads should complete within timeout");
        assertEquals(numberOfThreads, counter.get(), "All executions should have run");
        assertEquals(1, maxConcurrencyObserved.get(), "At no point should more than 1 thread be inside the critical section for pet #" + petId);
    }

    @Test
    @DisplayName("Multithreading: NotificationThreadPool executes tasks asynchronously via ExecutorService")
    void testNotificationThreadPool() throws Exception {
        NotificationThreadPool pool = NotificationThreadPool.getInstance();

        Application app = new Application();
        app.setAdopterEmail("adopter.test@example.com");
        app.setAdopterName("Alice");
        app.setPetName("Milo");
        app.setStatus(Application.STATUS_APPROVED);

        Future<?> future = pool.sendNotificationAsync(app);
        assertNotNull(future);

        // Wait for async task completion
        future.get(5, TimeUnit.SECONDS);

        assertFalse(pool.getRecentDeliveryLog().isEmpty(), "Recent delivery log should record the async notification");
    }
}
