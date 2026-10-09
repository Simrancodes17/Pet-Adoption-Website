package com.petadoption.servlet;

import com.petadoption.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller providing a unified /profile endpoint that polymorphically delegates
 * to the appropriate role-based dashboard/profile view (Adopter, Shelter, Admin).
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = getLoggedInUser(req);
        if (currentUser == null) {
            req.getSession(true).setAttribute("redirectAfterLogin", req.getContextPath() + "/profile");
            redirect(req, resp, "/login");
            return;
        }

        // Redirect to role-specific dashboard/profile
        redirect(req, resp, currentUser.getDashboardPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
