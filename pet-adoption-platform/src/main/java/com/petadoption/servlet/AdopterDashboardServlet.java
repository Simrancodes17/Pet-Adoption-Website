package com.petadoption.servlet;

import com.petadoption.model.Application;
import com.petadoption.model.Message;
import com.petadoption.model.User;
import com.petadoption.service.ApplicationService;
import com.petadoption.service.MessageService;
import com.petadoption.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller for Adopter Dashboard.
 * Displays submitted applications, adoption statuses, and direct communications.
 */
@WebServlet(name = "AdopterDashboardServlet", urlPatterns = {"/adopter/dashboard"})
public class AdopterDashboardServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private ApplicationService applicationService;
    private MessageService messageService;
    private UserService userService;

    @Override
    public void init() {
        this.applicationService = new ApplicationService();
        this.messageService = new MessageService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getLoggedInUser(req);
        if (currentUser == null) {
            redirect(req, resp, "/login");
            return;
        }

        int adopterId = currentUser.getId();
        List<Application> applications = applicationService.getAdopterApplications(adopterId);
        List<Message> messages = messageService.getUserMessages(adopterId);

        req.setAttribute("applications", applications);
        req.setAttribute("messages", messages);
        req.setAttribute("user", currentUser);

        forward(req, resp, "adopter/dashboard.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Update profile details
        User currentUser = getLoggedInUser(req);
        if (currentUser == null) {
            redirect(req, resp, "/login");
            return;
        }

        String name = req.getParameter("name");
        String contactInfo = req.getParameter("contactInfo");

        try {
            userService.updateUser(currentUser.getId(), name, currentUser.getEmail(), currentUser.getRole(), contactInfo);
            currentUser.setName(name);
            currentUser.setContactInfo(contactInfo);
            req.getSession().setAttribute(SESSION_USER_KEY, currentUser);

            setFlashSuccess(req, "Profile details updated successfully!");
        } catch (Exception e) {
            setFlashError(req, "Failed to update profile: " + e.getMessage());
        }

        redirect(req, resp, "/adopter/dashboard");
    }
}
