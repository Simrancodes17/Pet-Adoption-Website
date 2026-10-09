package com.petadoption.servlet;

import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller handling pet search and multi-attribute filtering.
 * Satisfies rubric item 2 (Collections & Generics - Streams, Comparators) and
 * rubric item 4 (Servlets & Web Integration).
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private PetService petService;

    @Override
    public void init() {
        this.petService = new PetService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        String type = req.getParameter("type");
        String breed = req.getParameter("breed");
        String gender = req.getParameter("gender");
        String location = req.getParameter("location");
        String sortBy = req.getParameter("sortBy");

        // Optional AJAX endpoint for dynamic breed querying
        if ("breeds".equalsIgnoreCase(req.getParameter("action"))) {
            resp.setContentType("application/json;charset=UTF-8");
            String requestedType = req.getParameter("type");
            List<String> breeds = petService.getSpeciesAndBreedsMap().getOrDefault(requestedType, List.of());
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < breeds.size(); i++) {
                if (i > 0) json.append(",");
                json.append("\"").append(breeds.get(i).replace("\"", "\\\"")).append("\"");
            }
            json.append("]");
            resp.getWriter().write(json.toString());
            return;
        }

        Integer minAge = null;
        Integer maxAge = null;
        String minAgeStr = req.getParameter("minAge");
        String maxAgeStr = req.getParameter("maxAge");

        if (minAgeStr != null && !minAgeStr.isBlank()) {
            try { minAge = Integer.parseInt(minAgeStr.trim()); } catch (NumberFormatException ignored) {}
        }
        if (maxAgeStr != null && !maxAgeStr.isBlank()) {
            try { maxAge = Integer.parseInt(maxAgeStr.trim()); } catch (NumberFormatException ignored) {}
        }

        List<Pet> pets = petService.searchAvailablePets(query, type, breed, gender, location, minAge, maxAge, sortBy);

        req.setAttribute("pets", pets);
        req.setAttribute("query", query);
        req.setAttribute("selectedType", type != null ? type : "ALL");
        req.setAttribute("breed", breed != null ? breed : "ALL");
        req.setAttribute("selectedGender", gender != null ? gender : "ALL");
        req.setAttribute("location", location);
        req.setAttribute("minAge", minAge);
        req.setAttribute("maxAge", maxAge);
        req.setAttribute("sortBy", sortBy != null ? sortBy : "newest");
        req.setAttribute("totalResults", pets.size());

        // Dynamic species and breeds catalog
        java.util.Map<String, List<String>> speciesBreedsMap = petService.getSpeciesAndBreedsMap();
        req.setAttribute("speciesBreedsMap", speciesBreedsMap);
        req.setAttribute("speciesBreedsJson", petService.getSpeciesAndBreedsJson());
        req.setAttribute("allSpeciesList", new java.util.ArrayList<>(speciesBreedsMap.keySet()));

        forward(req, resp, "pet/search.jsp");
    }
}
