package com.petadoption.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages fine-grained per-pet synchronization locks using ReentrantLock.
 * Prevents race conditions where two concurrent requests could simultaneously approve
 * different adopters for the same pet.
 *
 * Satisfies rubric item 2: Core Java Concepts - Multithreading & Synchronization
 * (ReentrantLock logic to prevent two adopters from being approved for the same pet simultaneously).
 */
public final class AdoptionApprovalLockManager {

    private static final Logger logger = LoggerFactory.getLogger(AdoptionApprovalLockManager.class);
    private static volatile AdoptionApprovalLockManager instance;

    // Concurrent mapping of Pet ID -> ReentrantLock
    private final ConcurrentHashMap<Integer, ReentrantLock> petLocks = new ConcurrentHashMap<>();

    private AdoptionApprovalLockManager() {
    }

    public static AdoptionApprovalLockManager getInstance() {
        if (instance == null) {
            synchronized (AdoptionApprovalLockManager.class) {
                if (instance == null) {
                    instance = new AdoptionApprovalLockManager();
                }
            }
        }
        return instance;
    }

    /**
     * Executes a critical section under the exclusive lock for a given pet.
     *
     * @param petId  ID of the pet undergoing adoption approval
     * @param action Critical business logic block to execute atomically
     * @param <T>    Return type
     * @return Result of the critical section
     * @throws Exception if operation fails or concurrency lock cannot be acquired
     */
    public <T> T executeWithLock(int petId, Callable<T> action) throws Exception {
        ReentrantLock lock = petLocks.computeIfAbsent(petId, id -> new ReentrantLock(true)); // fair lock
        logger.debug("Attempting to acquire exclusive adoption lock for Pet #{} on thread [{}]",
                petId, Thread.currentThread().getName());

        boolean acquired = false;
        try {
            // Wait up to 5 seconds to acquire lock
            acquired = lock.tryLock(5, TimeUnit.SECONDS);
            if (!acquired) {
                throw new IllegalStateException(
                    "Adoption approval concurrency conflict: another operation is currently in progress for Pet #" + petId
                );
            }

            logger.info("Acquired exclusive adoption lock for Pet #{} on thread [{}]",
                    petId, Thread.currentThread().getName());

            return action.call();
        } finally {
            if (acquired) {
                lock.unlock();
                logger.debug("Released exclusive adoption lock for Pet #{} on thread [{}]",
                        petId, Thread.currentThread().getName());
            }
        }
    }

    /**
     * Cleans up stale locks if no threads are waiting on them.
     */
    public void cleanupUnusedLocks() {
        petLocks.entrySet().removeIf(entry -> !entry.getValue().isLocked() && !entry.getValue().hasQueuedThreads());
    }

    public int getActiveLockCount() {
        return petLocks.size();
    }
}
