package com.petadoption.servlet;

import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the dedicated Pet Feed discovery page.
 * Provides social-feed exploration of real pets from the database with story categories,
 * species filtering, real-time search, and direct adoption actions.
 */
@WebServlet(name = "FeedServlet", urlPatterns = {"/feed"})
public class FeedServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private PetService petService;

    @Override
    public void init() {
        this.petService = new PetService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter"); // all, new, urgent, seniors, puppies, kittens, adopted
        String species = req.getParameter("species"); // ALL, Dog, Cat, Other, etc.
        String breed = req.getParameter("breed");
        String q = req.getParameter("q");

        if (filter == null || filter.isBlank()) filter = "all";
        if (species == null || species.isBlank()) species = "ALL";
        if (breed == null || breed.isBlank()) breed = "ALL";

        // Fetch all verified/approved pets
        List<Pet> allPets = petService.searchAvailablePets("", "ALL", "ALL", null, null, null, "newest");
        List<Pet> filteredList = new ArrayList<>(allPets);

        // 1. Filter by Search Query
        if (q != null && !q.trim().isEmpty()) {
            String queryLower = q.trim().toLowerCase();
            filteredList = filteredList.stream()
                    .filter(p -> (p.getName() != null && p.getName().toLowerCase().contains(queryLower)) ||
                                 (p.getBreed() != null && p.getBreed().toLowerCase().contains(queryLower)) ||
                                 (p.getLocation() != null && p.getLocation().toLowerCase().contains(queryLower)) ||
                                 (p.getType() != null && p.getType().toLowerCase().contains(queryLower)) ||
                                 (p.getDescription() != null && p.getDescription().toLowerCase().contains(queryLower)))
                    .collect(Collectors.toList());
        }

        // 2. Filter by Species
        if (!"ALL".equalsIgnoreCase(species)) {
            if ("Other".equalsIgnoreCase(species)) {
                filteredList = filteredList.stream()
                        .filter(p -> !"Dog".equalsIgnoreCase(p.getType()) && !"Cat".equalsIgnoreCase(p.getType()))
                        .collect(Collectors.toList());
            } else {
                final String targetSpecies = species;
                filteredList = filteredList.stream()
                        .filter(p -> targetSpecies.equalsIgnoreCase(p.getType()))
                        .collect(Collectors.toList());
            }
        }

        // 3. Filter by Breed
        if (!"ALL".equalsIgnoreCase(breed)) {
            final String targetBreed = breed;
            filteredList = filteredList.stream()
                    .filter(p -> targetBreed.equalsIgnoreCase(p.getBreed()))
                    .collect(Collectors.toList());
        }

        // 4. Story / Discovery Category Filter
        switch (filter.toLowerCase()) {
            case "new":
                // Newest first (by ID or created date)
                filteredList.sort((a, b) -> Integer.compare(b.getId(), a.getId()));
                break;
            case "urgent":
                // Pets waiting longest or marked pending/available
                filteredList = filteredList.stream()
                        .filter(p -> "AVAILABLE".equalsIgnoreCase(p.getAdoptionStatus()) || "PENDING".equalsIgnoreCase(p.getAdoptionStatus()))
                        .sorted((a, b) -> Integer.compare(b.getAge(), a.getAge()))
                        .collect(Collectors.toList());
                break;
            case "seniors":
                // Senior pets (age >= 5 or 7)
                filteredList = filteredList.stream()
                        .filter(p -> p.getAge() >= 4)
                        .sorted((a, b) -> Integer.compare(b.getAge(), a.getAge()))
                        .collect(Collectors.toList());
                break;
            case "puppies":
                // Young dogs
                filteredList = filteredList.stream()
                        .filter(p -> "Dog".equalsIgnoreCase(p.getType()) && p.getAge() <= 2)
                        .collect(Collectors.toList());
                break;
            case "kittens":
                // Young cats
                filteredList = filteredList.stream()
                        .filter(p -> "Cat".equalsIgnoreCase(p.getType()) && p.getAge() <= 2)
                        .collect(Collectors.toList());
                break;
            case "adopted":
                // Celebrated adopted pets
                filteredList = filteredList.stream()
                        .filter(p -> "ADOPTED".equalsIgnoreCase(p.getAdoptionStatus()))
                        .collect(Collectors.toList());
                break;
            case "all":
            default:
                // Show available and pending first, then adopted
                filteredList.sort((a, b) -> {
                    if ("AVAILABLE".equalsIgnoreCase(a.getAdoptionStatus()) && !"AVAILABLE".equalsIgnoreCase(b.getAdoptionStatus())) return -1;
                    if (!"AVAILABLE".equalsIgnoreCase(a.getAdoptionStatus()) && "AVAILABLE".equalsIgnoreCase(b.getAdoptionStatus())) return 1;
                    return Integer.compare(b.getId(), a.getId());
                });
                break;
        }

        req.setAttribute("feedPets", filteredList);
        req.setAttribute("totalFeedPets", filteredList.size());
        req.setAttribute("currentFilter", filter);
        req.setAttribute("currentSpecies", species);
        req.setAttribute("currentBreed", breed);
        req.setAttribute("searchQuery", q != null ? q : "");
        req.setAttribute("speciesBreedsMap", petService.getSpeciesAndBreedsMap());

        forward(req, resp, "pet/feed.jsp");
    }
}
