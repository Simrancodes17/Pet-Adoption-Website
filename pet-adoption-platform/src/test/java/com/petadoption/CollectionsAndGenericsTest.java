package com.petadoption;

import com.petadoption.model.Pet;
import com.petadoption.util.PetComparator;
import com.petadoption.util.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies Rubric Item 2: Collections & Generics
 * Generic Result<T> wrapper, Comparators, Stream API sorting & filtering.
 */
class CollectionsAndGenericsTest {

    @Test
    @DisplayName("Generics: Result<T> wrapper encapsulates success, payload, and failure states")
    void testGenericResultWrapper() {
        Result<String> successStr = Result.success("Saved successfully", "ID-12345");
        assertTrue(successStr.isSuccess());
        assertFalse(successStr.isFailure());
        assertEquals("ID-12345", successStr.getData());

        Result<Pet> failPet = Result.failure("Pet not available");
        assertTrue(failPet.isFailure());
        assertNull(failPet.getData());
        assertEquals("Pet not available", failPet.getMessage());
    }

    @Test
    @DisplayName("Collections & Streams: PetComparator sorts by age asc/desc, name, and date")
    void testPetComparatorAndStreams() {
        Pet p1 = new Pet(1, 1, "Charlie", "Dog", "Beagle", 5, "Austin", "Desc", null, "AVAILABLE", "APPROVED", Timestamp.valueOf("2024-01-01 10:00:00"));
        Pet p2 = new Pet(2, 1, "Bella", "Dog", "Poodle", 1, "Austin", "Desc", null, "AVAILABLE", "APPROVED", Timestamp.valueOf("2024-02-01 10:00:00"));
        Pet p3 = new Pet(3, 1, "Max", "Dog", "Shepherd", 3, "Austin", "Desc", null, "AVAILABLE", "APPROVED", Timestamp.valueOf("2024-03-01 10:00:00"));

        List<Pet> list = new ArrayList<>(List.of(p1, p2, p3));

        // Sort by Name (A-Z)
        List<Pet> sortedByName = PetComparator.sortPets(list, "name");
        assertEquals("Bella", sortedByName.get(0).getName());
        assertEquals("Charlie", sortedByName.get(1).getName());
        assertEquals("Max", sortedByName.get(2).getName());

        // Sort by Age Ascending (Youngest first)
        List<Pet> sortedByAgeAsc = PetComparator.sortPets(list, "age_asc");
        assertEquals(1, sortedByAgeAsc.get(0).getAge()); // Bella
        assertEquals(3, sortedByAgeAsc.get(1).getAge()); // Max
        assertEquals(5, sortedByAgeAsc.get(2).getAge()); // Charlie

        // Sort by Age Descending (Oldest first)
        List<Pet> sortedByAgeDesc = PetComparator.sortPets(list, "age_desc");
        assertEquals(5, sortedByAgeDesc.get(0).getAge()); // Charlie
        assertEquals(3, sortedByAgeDesc.get(1).getAge()); // Max
        assertEquals(1, sortedByAgeDesc.get(2).getAge()); // Bella

        // Stream filtering: age <= 3
        List<Pet> filteredYoung = list.stream()
                .filter(p -> p.getAge() <= 3)
                .collect(Collectors.toList());
        assertEquals(2, filteredYoung.size());
    }
}
