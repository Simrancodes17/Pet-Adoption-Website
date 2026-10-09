package com.petadoption.servlet;

import com.petadoption.model.AnalyticsData;
import com.petadoption.model.Pet;
import com.petadoption.service.AnalyticsService;
import com.petadoption.service.PetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller serving the landing page with featured pets and platform highlights.
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"", "/home"})
public class HomeServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private PetService petService;
    private AnalyticsService analyticsService;

    @Override
    public void init() {
        this.petService = new PetService();
        this.analyticsService = new AnalyticsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Pet> availablePets = petService.searchAvailablePets(null, null, null, null, null, null, "newest");
        AnalyticsData analytics = analyticsService.getPlatformAnalytics();

        // Limit featured to top 6
        List<Pet> featured = (availablePets.size() > 6) ? availablePets.subList(0, 6) : availablePets;

        req.setAttribute("featuredPets", featured);
        req.setAttribute("analytics", analytics);
        forward(req, resp, "pet/home.jsp");
    }
}
