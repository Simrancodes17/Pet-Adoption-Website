package com.petadoption.service;

import com.petadoption.model.Notifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete implementation of NotificationService for in-app alert buffering.
 * Stores in-memory notification histories for user message centres.
 */
public class InAppNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(InAppNotificationService.class);
    private final List<String> inAppNotifications = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void sendNotification(Notifiable notifiable) {
        if (notifiable == null) return;
        String formatted = String.format("[IN-APP] To: %s | Subject: %s | Body: %s",
                notifiable.getNotificationRecipient(),
                notifiable.getNotificationSubject(),
                notifiable.getNotificationBody());
        logger.info(formatted);
        inAppNotifications.add(formatted);
    }

    @Override
    public void sendNotification(String recipient, String subject, String content) {
        String formatted = String.format("[IN-APP] To: %s | Subject: %s | Body: %s",
                recipient, subject, content);
        logger.info(formatted);
        inAppNotifications.add(formatted);
    }

    public List<String> getNotifications() {
        synchronized (inAppNotifications) {
            return new ArrayList<>(inAppNotifications);
        }
    }

    @Override
    public String getChannelType() {
        return "IN_APP";
    }
}
