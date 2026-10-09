package com.petadoption.servlet;

import com.petadoption.model.Application;
import com.petadoption.model.Pet;
import com.petadoption.model.User;
import com.petadoption.service.AnalyticsService;
import com.petadoption.service.ApplicationService;
import com.petadoption.service.PetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Controller for Shelter Dashboard.
 * Displays shelter-specific listings, pending applications, and adoption statistics.
 */
@WebServlet(name = "ShelterDashboardServlet", urlPatterns = {"/shelter/dashboard"})
public class ShelterDashboardServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private PetService petService;
    private ApplicationService applicationService;
    private AnalyticsService analyticsService;

    @Override
    public void init() {
        this.petService = new PetService();
        this.applicationService = new ApplicationService();
        this.analyticsService = new AnalyticsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getLoggedInUser(req);
        if (currentUser == null) {
            redirect(req, resp, "/login");
            return;
        }

        int shelterId = currentUser.getId();
        List<Pet> pets = petService.getShelterPets(shelterId);
        List<Application> applications = applicationService.getShelterApplications(shelterId);
        Map<String, Object> stats = analyticsService.getShelterStats(shelterId);

        req.setAttribute("pets", pets);
        req.setAttribute("applications", applications);
        req.setAttribute("stats", stats);

        forward(req, resp, "shelter/dashboard.jsp");
    }
}
