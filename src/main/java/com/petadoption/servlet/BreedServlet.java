package com.petadoption.servlet;

import com.petadoption.model.BreedProfile;
import com.petadoption.util.BreedDirectory;
import com.petadoption.util.PetCatalog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller serving the educational Breed Directory section.
 * Educates prospective pet parents on size, temperament, grooming, and care requirements.
 */
@WebServlet(name = "BreedServlet", urlPatterns = {"/breeds"})
public class BreedServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String species = req.getParameter("species");
        if (species == null || species.isBlank()) {
            species = "ALL";
        }

        List<BreedProfile> breeds = BreedDirectory.getBreedsBySpecies(species);

        req.setAttribute("breeds", breeds);
        req.setAttribute("selectedSpecies", species);
        req.setAttribute("allSpeciesList", PetCatalog.getAllSpecies());

        forward(req, resp, "pet/breeds.jsp");
    }
}
