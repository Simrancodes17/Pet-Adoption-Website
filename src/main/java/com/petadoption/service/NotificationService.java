package com.petadoption.service;

import com.petadoption.model.Notifiable;

/**
 * Common interface defining notification delivery operations.
 * Demonstrates runtime polymorphism (Part 2: Core Java Concepts - Polymorphism & Interfaces).
 * Different concrete strategies (Email, In-App, Console, Composite) implement this interface,
 * allowing clients to interact with notification services via their common type.
 */
public interface NotificationService {

    /**
     * Dispatches a notification for a domain entity implementing Notifiable.
     *
     * @param notifiable Entity containing recipient, subject, and body details
     */
    void sendNotification(Notifiable notifiable);

    /**
     * Dispatches a notification to an explicit recipient with subject and content.
     *
     * @param recipient Target email or user handle
     * @param subject   Notification subject line
     * @param content   Notification body text
     */
    void sendNotification(String recipient, String subject, String content);

    /**
     * Identifies the notification channel type.
     *
     * @return Channel identifier (e.g., "EMAIL", "IN_APP", "CONSOLE")
     */
    String getChannelType();
}
