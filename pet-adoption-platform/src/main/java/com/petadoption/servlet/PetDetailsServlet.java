package com.petadoption.servlet;

import com.petadoption.exception.PetNotFoundException;
import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller displaying detailed information about a specific pet listing.
 */
@WebServlet(name = "PetDetailsServlet", urlPatterns = {"/pets/details"})
public class PetDetailsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private PetService petService;

    @Override
    public void init() {
        this.petService = new PetService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int petId = getIntParameter(req, "id", -1);
        if (petId <= 0) {
            setFlashError(req, "Invalid pet ID specified.");
            redirect(req, resp, "/search");
            return;
        }

        try {
            Pet pet = petService.getPetById(petId);
            req.setAttribute("pet", pet);
            forward(req, resp, "pet/details.jsp");
        } catch (PetNotFoundException e) {
            setFlashError(req, e.getMessage());
            redirect(req, resp, "/search");
        }
    }
}
