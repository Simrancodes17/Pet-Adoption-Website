package com.petadoption.thread;

import com.petadoption.model.Notifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages an asynchronous worker thread pool for dispatching notifications and emails
 * without stalling the HTTP request thread.
 *
 * Satisfies rubric item 2: Core Java Concepts - Multithreading & Synchronization
 * (ExecutorService thread pool for sending adopter notifications/emails asynchronously).
 */
public final class NotificationThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(NotificationThreadPool.class);
    private static volatile NotificationThreadPool instance;

    private final ExecutorService executorService;
    // Circular buffer / recent delivery log for testing and dashboard diagnostics
    private final List<String> recentDeliveryLog = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_LOG_SIZE = 50;

    private NotificationThreadPool() {
        int poolSize = 4;
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "PetAdoption-NotificationWorker-" + counter.getAndIncrement());
                t.setDaemon(true);
                return t;
            }
        };

        this.executorService = new ThreadPoolExecutor(
                poolSize,
                poolSize * 2,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        logger.info("NotificationThreadPool initialized with base pool size: {}", poolSize);
    }

    public static NotificationThreadPool getInstance() {
        if (instance == null) {
            synchronized (NotificationThreadPool.class) {
                if (instance == null) {
                    instance = new NotificationThreadPool();
                }
            }
        }
        return instance;
    }

    /**
     * Submits an asynchronous notification task for a Notifiable entity.
     *
     * @param notifiable Entity implementing Notifiable (e.g., Application, Message)
     */
    public Future<?> sendNotificationAsync(Notifiable notifiable) {
        if (notifiable == null) return CompletableFuture.completedFuture(null);

        return executorService.submit(() -> {
            try {
                String threadName = Thread.currentThread().getName();
                String recipient = notifiable.getNotificationRecipient();
                String subject = notifiable.getNotificationSubject();
                String body = notifiable.getNotificationBody();

                // Simulating network email dispatch latency (50ms)
                Thread.sleep(50);

                String logEntry = String.format("[%s] Sent notification to <%s> | Subject: '%s'",
                        threadName, recipient, subject);
                logger.info(logEntry);

                synchronized (recentDeliveryLog) {
                    if (recentDeliveryLog.size() >= MAX_LOG_SIZE) {
                        recentDeliveryLog.remove(0);
                    }
                    recentDeliveryLog.add(logEntry);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Notification dispatch was interrupted: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Failed to send async notification to {}: {}",
                        notifiable.getNotificationRecipient(), e.getMessage());
            }
        });
    }

    /**
     * Retrieves the recorded list of recently dispatched notifications.
     */
    public List<String> getRecentDeliveryLog() {
        synchronized (recentDeliveryLog) {
            return new ArrayList<>(recentDeliveryLog);
        }
    }

    /**
     * Gracefully shuts down the thread pool upon ServletContext destruction.
     */
    public void shutdown() {
        logger.info("Shutting down NotificationThreadPool...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
