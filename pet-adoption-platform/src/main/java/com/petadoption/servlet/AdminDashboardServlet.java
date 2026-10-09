package com.petadoption.servlet;

import com.petadoption.model.AnalyticsData;
import com.petadoption.model.Pet;
import com.petadoption.model.User;
import com.petadoption.service.AnalyticsService;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller for Administrator Dashboard.
 * Displays platform metrics, pending pet listings, recent users, and quick actions.
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private AnalyticsService analyticsService;
    private PetService petService;
    private UserService userService;

    @Override
    public void init() {
        this.analyticsService = new AnalyticsService();
        this.petService = new PetService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnalyticsData analytics = analyticsService.getPlatformAnalytics();
        List<Pet> pendingPets = petService.getPendingApprovalPets();
        List<User> recentUsers = userService.getAllUsers();
        if (recentUsers.size() > 8) {
            recentUsers = recentUsers.subList(0, 8);
        }

        req.setAttribute("analytics", analytics);
        req.setAttribute("pendingPets", pendingPets);
        req.setAttribute("recentUsers", recentUsers);

        forward(req, resp, "admin/dashboard.jsp");
    }
}
