package com.petadoption.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Model representing direct communication between Shelters and Adopters regarding an adoption application.
 * Implements Notifiable interface.
 */
public class Message implements Serializable, Notifiable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int senderId;
    private int receiverId;
    private Integer applicationId; // Nullable if general inquiry
    private String content;
    private Timestamp createdAt;

    // Joined view fields
    private String senderName;
    private String senderRole;
    private String receiverName;
    private String receiverEmail;
    private String petName;

    public Message() {
    }

    public Message(int id, int senderId, int receiverId, Integer applicationId, String content, Timestamp createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.applicationId = applicationId;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String getNotificationRecipient() {
        return (receiverEmail != null && !receiverEmail.isBlank()) ? receiverEmail : "user@" + receiverId;
    }

    @Override
    public String getNotificationSubject() {
        return "New message on PawHaven from " + (senderName != null ? senderName : "a user");
    }

    @Override
    public String getNotificationBody() {
        return String.format(
            "Hello %s,\n\nYou received a new message from %s regarding %s:\n\n\"%s\"\n\n" +
            "Log in to PawHaven to reply: /messages",
            (receiverName != null ? receiverName : "Pet Lover"),
            (senderName != null ? senderName : "User #" + senderId),
            (petName != null ? petName : "your pet application"),
            content
        );
    }

    // Encapsulation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
