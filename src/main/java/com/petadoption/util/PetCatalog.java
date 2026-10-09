package com.petadoption.util;

import java.util.*;

/**
 * Centralized dynamic catalogue for pet species and their corresponding breeds.
 * Designed to make the platform easily extensible: adding a new species or breed here
 * (or dynamically via registerBreed) instantly propagates across the frontend and backend.
 */
public final class PetCatalog {

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";
    public static final String GENDER_UNKNOWN = "Unknown";

    private static final Map<String, List<String>> DEFAULT_SPECIES_BREEDS = new LinkedHashMap<>();

    static {
        DEFAULT_SPECIES_BREEDS.put("Dog", List.of(
                "Labrador",
                "Golden Retriever",
                "German Shepherd",
                "Pug",
                "Beagle",
                "Husky",
                "Rottweiler",
                "Shih Tzu"
        ));

        DEFAULT_SPECIES_BREEDS.put("Cat", List.of(
                "Persian",
                "Siamese",
                "Maine Coon",
                "British Shorthair",
                "Bengal",
                "Ragdoll"
        ));

        DEFAULT_SPECIES_BREEDS.put("Rabbit", List.of(
                "Holland Lop",
                "Netherland Dwarf",
                "Lionhead",
                "Rex"
        ));

        DEFAULT_SPECIES_BREEDS.put("Bird", List.of(
                "Parakeet",
                "Cockatiel",
                "Lovebird",
                "Finch"
        ));

        DEFAULT_SPECIES_BREEDS.put("Hamster", List.of(
                "Syrian",
                "Dwarf",
                "Roborovski"
        ));

        DEFAULT_SPECIES_BREEDS.put("Turtle", List.of(
                "Red-Eared Slider",
                "Box Turtle",
                "Russian Tortoise"
        ));
    }

    private PetCatalog() {}

    /**
     * Returns an unmodifiable view of all standard species.
     */
    public static List<String> getAllSpecies() {
        return new ArrayList<>(DEFAULT_SPECIES_BREEDS.keySet());
    }

    /**
     * Returns the breeds for a specific species, or an empty list if unknown.
     */
    public static List<String> getBreedsForSpecies(String species) {
        if (species == null) return Collections.emptyList();
        for (Map.Entry<String, List<String>> entry : DEFAULT_SPECIES_BREEDS.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(species.trim())) {
                return new ArrayList<>(entry.getValue());
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns the full species-to-breeds map.
     */
    public static Map<String, List<String>> getSpeciesBreedsMap() {
        Map<String, List<String>> copy = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : DEFAULT_SPECIES_BREEDS.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    /**
     * Merges standard species-breeds with any extra distinct breeds discovered from the database.
     */
    public static Map<String, List<String>> mergeWithDbBreeds(Map<String, List<String>> dbBreeds) {
        Map<String, List<String>> merged = getSpeciesBreedsMap();
        if (dbBreeds != null) {
            for (Map.Entry<String, List<String>> entry : dbBreeds.entrySet()) {
                String species = entry.getKey();
                if (species == null || species.isBlank()) continue;

                // Find matching standard key or add new
                String matchedKey = null;
                for (String existingKey : merged.keySet()) {
                    if (existingKey.equalsIgnoreCase(species)) {
                        matchedKey = existingKey;
                        break;
                    }
                }
                if (matchedKey == null) {
                    matchedKey = species;
                    merged.put(matchedKey, new ArrayList<>());
                }

                List<String> currentBreeds = merged.get(matchedKey);
                for (String b : entry.getValue()) {
                    if (b != null && !b.isBlank() && !containsIgnoreCase(currentBreeds, b)) {
                        currentBreeds.add(b);
                    }
                }
            }
        }
        return merged;
    }

    private static boolean containsIgnoreCase(List<String> list, String target) {
        for (String item : list) {
            if (item.equalsIgnoreCase(target)) return true;
        }
        return false;
    }

    /**
     * Serializes a species-to-breeds map to JSON without requiring external dependencies.
     */
    public static String toJson(Map<String, List<String>> map) {
        StringBuilder sb = new StringBuilder("{");
        boolean firstKey = true;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (!firstKey) sb.append(",");
            firstKey = false;
            sb.append("\"").append(escapeJson(entry.getKey())).append("\":[");
            boolean firstVal = true;
            for (String val : entry.getValue()) {
                if (!firstVal) sb.append(",");
                firstVal = false;
                sb.append("\"").append(escapeJson(val)).append("\"");
            }
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
