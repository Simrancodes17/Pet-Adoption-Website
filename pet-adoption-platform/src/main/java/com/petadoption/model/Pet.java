package com.petadoption.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Model representing a Pet listing.
 * Implements Searchable interface to support stream and in-memory multi-attribute filtering.
 */
public class Pet implements Serializable, Searchable {
    private static final long serialVersionUID = 1L;

    public static final String ADOPTION_AVAILABLE = "AVAILABLE";
    public static final String ADOPTION_PENDING = "PENDING";
    public static final String ADOPTION_ADOPTED = "ADOPTED";

    public static final String APPROVAL_PENDING = "PENDING";
    public static final String APPROVAL_APPROVED = "APPROVED";
    public static final String APPROVAL_REJECTED = "REJECTED";

    private int id;
    private int shelterId;
    private String name;
    private String type;         // Dog, Cat, Rabbit, Bird, etc.
    private String breed;
    private int age;             // in years
    private String location;
    private String description;
    private String photoPath;
    private String adoptionStatus; // AVAILABLE, PENDING, ADOPTED
    private String approvalStatus; // PENDING, APPROVED, REJECTED
    private Timestamp createdAt;

    // Joined view fields
    private String shelterName;
    private String shelterEmail;

    public Pet() {
        this.adoptionStatus = ADOPTION_AVAILABLE;
        this.approvalStatus = APPROVAL_PENDING;
        this.photoPath = "assets/images/default-pet.png";
    }

    public Pet(int id, int shelterId, String name, String type, String breed, int age,
               String location, String description, String photoPath,
               String adoptionStatus, String approvalStatus, Timestamp createdAt) {
        this.id = id;
        this.shelterId = shelterId;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.location = location;
        this.description = description;
        this.photoPath = (photoPath == null || photoPath.isBlank()) ? "assets/images/default-pet.png" : photoPath;
        this.adoptionStatus = (adoptionStatus == null) ? ADOPTION_AVAILABLE : adoptionStatus;
        this.approvalStatus = (approvalStatus == null) ? APPROVAL_PENDING : approvalStatus;
        this.createdAt = createdAt;
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null || query.isBlank()) {
            return true;
        }
        String q = query.trim().toLowerCase();
        return (name != null && name.toLowerCase().contains(q)) ||
               (type != null && type.toLowerCase().contains(q)) ||
               (breed != null && breed.toLowerCase().contains(q)) ||
               (location != null && location.toLowerCase().contains(q)) ||
               (description != null && description.toLowerCase().contains(q));
    }

    @Override
    public boolean matchesFilters(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) {
            return true;
        }

        if (filters.containsKey("type")) {
            String fType = String.valueOf(filters.get("type"));
            if (!fType.isBlank() && (type == null || !type.equalsIgnoreCase(fType))) {
                return false;
            }
        }

        if (filters.containsKey("breed")) {
            String fBreed = String.valueOf(filters.get("breed"));
            if (!fBreed.isBlank() && (breed == null || !breed.toLowerCase().contains(fBreed.toLowerCase()))) {
                return false;
            }
        }

        if (filters.containsKey("location")) {
            String fLoc = String.valueOf(filters.get("location"));
            if (!fLoc.isBlank() && (location == null || !location.toLowerCase().contains(fLoc.toLowerCase()))) {
                return false;
            }
        }

        if (filters.containsKey("adoptionStatus")) {
            String fStatus = String.valueOf(filters.get("adoptionStatus"));
            if (!fStatus.isBlank() && (adoptionStatus == null || !adoptionStatus.equalsIgnoreCase(fStatus))) {
                return false;
            }
        }

        if (filters.containsKey("approvalStatus")) {
            String fAppStatus = String.valueOf(filters.get("approvalStatus"));
            if (!fAppStatus.isBlank() && (approvalStatus == null || !approvalStatus.equalsIgnoreCase(fAppStatus))) {
                return false;
            }
        }

        if (filters.containsKey("minAge")) {
            try {
                int minAge = Integer.parseInt(String.valueOf(filters.get("minAge")));
                if (age < minAge) return false;
            } catch (NumberFormatException ignored) {}
        }

        if (filters.containsKey("maxAge")) {
            try {
                int maxAge = Integer.parseInt(String.valueOf(filters.get("maxAge")));
                if (age > maxAge) return false;
            } catch (NumberFormatException ignored) {}
        }

        return true;
    }

    // Encapsulation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getAdoptionStatus() {
        return adoptionStatus;
    }

    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
        Pet pet = (Pet) o;
        return id == pet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
