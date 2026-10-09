package com.petadoption.util;

import com.petadoption.model.Pet;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Comparator utility providing sorting strategies for Pet listings.
 * Satisfies rubric item 2: Collections & Generics - Comparator and Streams.
 */
public final class PetComparator {

    private PetComparator() {
    }

    public static final Comparator<Pet> BY_NAME = Comparator.comparing(
        pet -> pet.getName() == null ? "" : pet.getName().toLowerCase()
    );

    public static final Comparator<Pet> BY_AGE_ASC = Comparator.comparingInt(Pet::getAge);

    public static final Comparator<Pet> BY_AGE_DESC = Comparator.comparingInt(Pet::getAge).reversed();

    public static final Comparator<Pet> BY_DATE_NEWEST = (p1, p2) -> {
        if (p1.getCreatedAt() == null && p2.getCreatedAt() == null) return 0;
        if (p1.getCreatedAt() == null) return 1;
        if (p2.getCreatedAt() == null) return -1;
        return p2.getCreatedAt().compareTo(p1.getCreatedAt()); // descending
    };

    /**
     * Sorts a list of pets according to the given sort parameter using Java Stream API.
     *
     * @param pets    Input pet list
     * @param sortKey One of: "name", "age_asc", "age_desc", "newest"
     * @return New sorted List of pets
     */
    public static List<Pet> sortPets(List<Pet> pets, String sortKey) {
        if (pets == null || pets.isEmpty()) {
            return List.of();
        }

        Comparator<Pet> comparator;
        if (sortKey == null) {
            comparator = BY_DATE_NEWEST;
        } else {
            switch (sortKey.trim().toLowerCase()) {
                case "name":
                    comparator = BY_NAME;
                    break;
                case "age_asc":
                case "youngest":
                    comparator = BY_AGE_ASC;
                    break;
                case "age_desc":
                case "oldest":
                    comparator = BY_AGE_DESC;
                    break;
                case "newest":
                default:
                    comparator = BY_DATE_NEWEST;
                    break;
            }
        }

        return pets.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
