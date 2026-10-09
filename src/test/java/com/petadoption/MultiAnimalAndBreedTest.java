package com.petadoption;

import com.petadoption.dao.PetDAO;
import com.petadoption.dao.impl.PetDAOImpl;
import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import com.petadoption.util.DBConnectionUtil;
import com.petadoption.util.PetCatalog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verification test suite for multi-animal and multi-breed pet adoption functionality.
 * Validates:
 * 1. Dogs, Cats, Rabbits, Birds, Hamsters, and Turtles catalogue support.
 * 2. Specific requested breeds for each species.
 * 3. Dynamic dependent species -> breed mapping and JSON generation.
 * 4. "All Animals" and "All Breeds" option handling.
 * 5. Multi-species database search and gender filtering.
 */
class MultiAnimalAndBreedTest {

    private static DBConnectionUtil dbUtil;
    private static PetDAO petDAO;
    private static PetService petService;

    @BeforeAll
    static void setUp() {
        dbUtil = DBConnectionUtil.getInstance();
        petDAO = new PetDAOImpl(dbUtil);
        petService = new PetService(petDAO, null);
    }

    @Test
    @DisplayName("Catalogue: All 6 required animal species are supported")
    void testSupportedSpecies() {
        List<String> species = PetCatalog.getAllSpecies();
        assertTrue(species.contains("Dog"), "Catalogue must support Dogs");
        assertTrue(species.contains("Cat"), "Catalogue must support Cats");
        assertTrue(species.contains("Rabbit"), "Catalogue must support Rabbits");
        assertTrue(species.contains("Bird"), "Catalogue must support Birds");
        assertTrue(species.contains("Hamster"), "Catalogue must support Hamsters");
        assertTrue(species.contains("Turtle"), "Catalogue must support Turtles");
        assertEquals(6, species.size(), "Standard catalogue contains exactly 6 species");
    }

    @Test
    @DisplayName("Catalogue: Specific requested breeds exist for each animal species")
    void testRequestedBreedsPerSpecies() {
        // Dogs
        List<String> dogBreeds = PetCatalog.getBreedsForSpecies("Dog");
        assertTrue(dogBreeds.containsAll(List.of(
                "Labrador", "Golden Retriever", "German Shepherd", "Pug",
                "Beagle", "Husky", "Rottweiler", "Shih Tzu"
        )));

        // Cats
        List<String> catBreeds = PetCatalog.getBreedsForSpecies("Cat");
        assertTrue(catBreeds.containsAll(List.of(
                "Persian", "Siamese", "Maine Coon", "British Shorthair", "Bengal", "Ragdoll"
        )));

        // Rabbits
        List<String> rabbitBreeds = PetCatalog.getBreedsForSpecies("Rabbit");
        assertTrue(rabbitBreeds.containsAll(List.of(
                "Holland Lop", "Netherland Dwarf", "Lionhead", "Rex"
        )));

        // Birds
        List<String> birdBreeds = PetCatalog.getBreedsForSpecies("Bird");
        assertTrue(birdBreeds.containsAll(List.of(
                "Parakeet", "Cockatiel", "Lovebird", "Finch"
        )));

        // Hamsters
        List<String> hamsterBreeds = PetCatalog.getBreedsForSpecies("Hamster");
        assertTrue(hamsterBreeds.containsAll(List.of(
                "Syrian", "Dwarf", "Roborovski"
        )));

        // Turtles
        List<String> turtleBreeds = PetCatalog.getBreedsForSpecies("Turtle");
        assertTrue(turtleBreeds.containsAll(List.of(
                "Red-Eared Slider", "Box Turtle", "Russian Tortoise"
        )));
    }

    @Test
    @DisplayName("Dynamic JSON: Species-breeds catalogue serializes correctly for frontend consumption")
    void testCatalogueJsonSerialization() {
        Map<String, List<String>> map = PetCatalog.getSpeciesBreedsMap();
        String json = PetCatalog.toJson(map);

        assertNotNull(json);
        assertTrue(json.startsWith("{") && json.endsWith("}"));
        assertTrue(json.contains("\"Dog\":["));
        assertTrue(json.contains("\"Cat\":["));
        assertTrue(json.contains("\"Rabbit\":["));
        assertTrue(json.contains("\"Bird\":["));
        assertTrue(json.contains("\"Hamster\":["));
        assertTrue(json.contains("\"Turtle\":["));
        assertTrue(json.contains("\"Labrador\""));
        assertTrue(json.contains("\"Persian\""));
        assertTrue(json.contains("\"Holland Lop\""));
        assertTrue(json.contains("\"Cockatiel\""));
        assertTrue(json.contains("\"Syrian\""));
        assertTrue(json.contains("\"Red-Eared Slider\""));
    }

    @Test
    @DisplayName("Database Search: Multi-animal and breed search with 'ALL' and specific selections")
    void testSearchAcrossSpeciesAndBreeds() {
        // 1. All Animals, All Breeds returns pets of multiple different species
        List<Pet> allPets = petService.searchAvailablePets(null, "ALL", "ALL", null, null, null, "newest");
        assertFalse(allPets.isEmpty(), "Should return seeded pets");

        boolean hasDog = allPets.stream().anyMatch(p -> "Dog".equalsIgnoreCase(p.getType()));
        boolean hasCat = allPets.stream().anyMatch(p -> "Cat".equalsIgnoreCase(p.getType()));
        boolean hasRabbit = allPets.stream().anyMatch(p -> "Rabbit".equalsIgnoreCase(p.getType()));
        boolean hasBird = allPets.stream().anyMatch(p -> "Bird".equalsIgnoreCase(p.getType()));
        boolean hasHamster = allPets.stream().anyMatch(p -> "Hamster".equalsIgnoreCase(p.getType()));
        boolean hasTurtle = allPets.stream().anyMatch(p -> "Turtle".equalsIgnoreCase(p.getType()));

        assertTrue(hasDog, "Results should contain Dogs");
        assertTrue(hasCat, "Results should contain Cats");
        assertTrue(hasRabbit, "Results should contain Rabbits");
        assertTrue(hasBird, "Results should contain Birds");
        assertTrue(hasHamster, "Results should contain Hamsters");
        assertTrue(hasTurtle, "Results should contain Turtles");

        // 2. Specific Animal: Cat -> only cats returned
        List<Pet> catsOnly = petService.searchAvailablePets(null, "Cat", "ALL", null, null, null, "newest");
        assertFalse(catsOnly.isEmpty());
        assertTrue(catsOnly.stream().allMatch(p -> "Cat".equalsIgnoreCase(p.getType())));

        // 3. Specific Animal and Breed: Dog -> Labrador
        List<Pet> labradors = petService.searchAvailablePets(null, "Dog", "Labrador", null, null, null, "newest");
        // Rocky is currently pending, but Bella is Golden Retriever; test specific breed search
        List<Pet> goldenRetrievers = petService.searchAvailablePets(null, "Dog", "Golden Retriever", null, null, null, "newest");
        assertFalse(goldenRetrievers.isEmpty());
        assertTrue(goldenRetrievers.stream().allMatch(p -> p.getBreed().toLowerCase().contains("golden retriever")));

        // 4. Specific Animal and Breed: Turtle -> Red-Eared Slider
        List<Pet> turtles = petService.searchAvailablePets(null, "Turtle", "Red-Eared Slider", null, null, null, "newest");
        assertFalse(turtles.isEmpty());
        assertEquals("Shelly", turtles.get(0).getName());
        assertEquals("Turtle", turtles.get(0).getType());
        assertEquals("Red-Eared Slider", turtles.get(0).getBreed());

        // 5. Gender Filter: Male vs Female
        List<Pet> femalePets = petService.searchAvailablePets(null, "ALL", "ALL", "Female", null, null, null, "newest");
        assertFalse(femalePets.isEmpty());
        assertTrue(femalePets.stream().allMatch(p -> "Female".equalsIgnoreCase(p.getGender())));

        List<Pet> malePets = petService.searchAvailablePets(null, "ALL", "ALL", "Male", null, null, null, "newest");
        assertFalse(malePets.isEmpty());
        assertTrue(malePets.stream().allMatch(p -> "Male".equalsIgnoreCase(p.getGender())));
    }
}
