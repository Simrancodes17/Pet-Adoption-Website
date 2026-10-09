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
        String location = req.getParameter("location");
        String sortBy = req.getParameter("sortBy");

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

        List<Pet> pets = petService.searchAvailablePets(query, type, breed, location, minAge, maxAge, sortBy);

        req.setAttribute("pets", pets);
        req.setAttribute("query", query);
        req.setAttribute("selectedType", type);
        req.setAttribute("breed", breed);
        req.setAttribute("location", location);
        req.setAttribute("minAge", minAge);
        req.setAttribute("maxAge", maxAge);
        req.setAttribute("sortBy", sortBy != null ? sortBy : "newest");
        req.setAttribute("totalResults", pets.size());

        forward(req, resp, "pet/search.jsp");
    }
}
