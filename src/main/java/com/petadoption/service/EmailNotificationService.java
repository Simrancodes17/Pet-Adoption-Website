package com.petadoption.service;

import com.petadoption.model.Notifiable;
import com.petadoption.thread.NotificationThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete implementation of NotificationService for asynchronous Email delivery.
 * Dispatches emails through a managed background worker pool without stalling HTTP threads.
 */
public class EmailNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
    private final NotificationThreadPool threadPool;

    public EmailNotificationService() {
        this.threadPool = NotificationThreadPool.getInstance();
    }

    public EmailNotificationService(NotificationThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void sendNotification(Notifiable notifiable) {
        if (notifiable == null) return;
        logger.info("[EMAIL] Queueing async email to <{}>: '{}'",
                notifiable.getNotificationRecipient(), notifiable.getNotificationSubject());
        threadPool.sendNotificationAsync(notifiable);
    }

    @Override
    public void sendNotification(String recipient, String subject, String content) {
        if (recipient == null || recipient.isBlank()) return;
        Notifiable entity = new Notifiable() {
            @Override
            public String getNotificationRecipient() {
                return recipient;
            }

            @Override
            public String getNotificationSubject() {
                return subject;
            }

            @Override
            public String getNotificationBody() {
                return content;
            }
        };
        sendNotification(entity);
    }

    @Override
    public String getChannelType() {
        return "EMAIL";
    }
}
