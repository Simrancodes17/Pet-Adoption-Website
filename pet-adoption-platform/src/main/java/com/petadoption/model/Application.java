package com.petadoption.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Model representing an Adoption Application submitted by an Adopter for a Pet.
 * Implements Notifiable interface so changes in application status trigger asynchronous
 * notifications via the background thread pool.
 */
public class Application implements Serializable, Notifiable {
    private static final long serialVersionUID = 1L;

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    private int id;
    private int petId;
    private int adopterId;
    private int shelterId;
    private String details;
    private String status; // PENDING, APPROVED, REJECTED, CANCELLED
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Joined view fields
    private String petName;
    private String petBreed;
    private String petType;
    private String petPhotoPath;
    private String adopterName;
    private String adopterEmail;
    private String shelterName;
    private String shelterEmail;

    public Application() {
        this.status = STATUS_PENDING;
    }

    public Application(int id, int petId, int adopterId, int shelterId, String details,
                       String status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.petId = petId;
        this.adopterId = adopterId;
        this.shelterId = shelterId;
        this.details = details;
        this.status = (status == null) ? STATUS_PENDING : status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Notifiable implementation
    @Override
    public String getNotificationRecipient() {
        return (adopterEmail != null && !adopterEmail.isBlank()) ? adopterEmail : "adopter@" + adopterId;
    }

    @Override
    public String getNotificationSubject() {
        return "Adoption Application Update for " + (petName != null ? petName : "Pet #" + petId);
    }

    @Override
    public String getNotificationBody() {
        return String.format(
            "Dear %s,\n\nYour adoption application for %s (ID #%d) has been updated to status: [%s].\n" +
            "Shelter: %s\n\nThank you for choosing PawHaven Pet Adoption Platform!",
            (adopterName != null ? adopterName : "Applicant"),
            (petName != null ? petName : "your selected pet"),
            petId,
            status,
            (shelterName != null ? shelterName : "The Rescue Shelter")
        );
    }

    // Encapsulation: Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(int adopterId) {
        this.adopterId = adopterId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetPhotoPath() {
        return petPhotoPath;
    }

    public void setPetPhotoPath(String petPhotoPath) {
        this.petPhotoPath = petPhotoPath;
    }

    public String getAdopterName() {
        return adopterName;
    }

    public void setAdopterName(String adopterName) {
        this.adopterName = adopterName;
    }

    public String getAdopterEmail() {
        return adopterEmail;
    }

    public void setAdopterEmail(String adopterEmail) {
        this.adopterEmail = adopterEmail;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getShelterEmail() {
        return shelterEmail;
    }

    public void setShelterEmail(String shelterEmail) {
        this.shelterEmail = shelterEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
