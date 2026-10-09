package com.petadoption.model;

import java.io.Serializable;

/**
 * Model representing educational breed reference data.
 * Keeps breed characteristics clearly distinct from live adoptable animal listings.
 */
public class BreedProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String species;
    private String name;
    private String size;
    private String temperament;
    private String activityLevel;
    private String groomingCare;
    private String bestSuitedFor;
    private String photoUrl;
    private String description;

    public BreedProfile() {}

    public BreedProfile(String species, String name, String size, String temperament,
                        String activityLevel, String groomingCare, String bestSuitedFor,
                        String photoUrl, String description) {
        this.species = species;
        this.name = name;
        this.size = size;
        this.temperament = temperament;
        this.activityLevel = activityLevel;
        this.groomingCare = groomingCare;
        this.bestSuitedFor = bestSuitedFor;
        this.photoUrl = photoUrl;
        this.description = description;
    }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getTemperament() { return temperament; }
    public void setTemperament(String temperament) { this.temperament = temperament; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public String getGroomingCare() { return groomingCare; }
    public void setGroomingCare(String groomingCare) { this.groomingCare = groomingCare; }

    public String getBestSuitedFor() { return bestSuitedFor; }
    public void setBestSuitedFor(String bestSuitedFor) { this.bestSuitedFor = bestSuitedFor; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
