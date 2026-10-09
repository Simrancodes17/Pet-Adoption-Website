package com.petadoption.service;

import com.petadoption.model.Notifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete implementation of NotificationService for console and log output.
 * Demonstrates runtime polymorphism where different delivery channels implement
 * the common NotificationService interface.
 */
public class ConsoleNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleNotificationService.class);

    @Override
    public void sendNotification(Notifiable notifiable) {
        if (notifiable == null) return;
        logger.info("[CONSOLE NOTIFICATION] >>> To: {} | Subject: '{}' | Message: {}",
                notifiable.getNotificationRecipient(),
                notifiable.getNotificationSubject(),
                notifiable.getNotificationBody());
    }

    @Override
    public void sendNotification(String recipient, String subject, String content) {
        logger.info("[CONSOLE NOTIFICATION] >>> To: {} | Subject: '{}' | Message: {}",
                recipient, subject, content);
    }

    @Override
    public String getChannelType() {
        return "CONSOLE";
    }
}
