package com.petadoption.service;

import com.petadoption.model.Notifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite implementation of NotificationService allowing multi-channel delivery.
 * Dispatches notifications across multiple registered NotificationService instances.
 */
public class CompositeNotificationService implements NotificationService {

    private final List<NotificationService> delegates = new ArrayList<>();

    public CompositeNotificationService() {
    }

    public CompositeNotificationService(NotificationService... services) {
        for (NotificationService s : services) {
            if (s != null) {
                delegates.add(s);
            }
        }
    }

    public void addService(NotificationService service) {
        if (service != null && !delegates.contains(service)) {
            delegates.add(service);
        }
    }

    @Override
    public void sendNotification(Notifiable notifiable) {
        for (NotificationService service : delegates) {
            service.sendNotification(notifiable);
        }
    }

    @Override
    public void sendNotification(String recipient, String subject, String content) {
        for (NotificationService service : delegates) {
            service.sendNotification(recipient, subject, content);
        }
    }

    @Override
    public String getChannelType() {
        return "COMPOSITE";
    }

    public List<NotificationService> getDelegates() {
        return new ArrayList<>(delegates);
    }
}
