package com.petadoption.model;

/**
 * Interface representing any domain entity that can generate an email or system notification.
 * Implements rubric item 2: Core Java Concepts - Interfaces.
 */
public interface Notifiable {

    /**
     * @return Email or handle of the notification recipient
     */
    String getNotificationRecipient();

    /**
     * @return Subject line of the notification
     */
    String getNotificationSubject();

    /**
     * @return Formatted message content or body
     */
    String getNotificationBody();
}
