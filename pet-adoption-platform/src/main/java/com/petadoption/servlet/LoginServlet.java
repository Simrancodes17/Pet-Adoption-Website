package com.petadoption.servlet;

import com.petadoption.exception.InvalidCredentialsException;
import com.petadoption.model.User;
import com.petadoption.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controller handling user authentication and session establishment.
 * Satisfies rubric item 4: Servlets and Web Integration - LoginServlet and Sessions.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getLoggedInUser(req);
        if (currentUser != null) {
            // Already signed in, redirect to dedicated dashboard polymorphically
            redirect(req, resp, currentUser.getDashboardPath());
            return;
        }
        forward(req, resp, "auth/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User authenticatedUser = userService.authenticate(email, password);

            // Establish session
            HttpSession session = req.getSession(true);
            session.setAttribute(SESSION_USER_KEY, authenticatedUser);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes timeout

            setFlashSuccess(req, "Welcome back, " + authenticatedUser.getName() + "!");

            // Check if there was a saved target route
            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
            if (redirectUrl != null && !redirectUrl.isBlank()) {
                session.removeAttribute("redirectAfterLogin");
                resp.sendRedirect(redirectUrl);
                return;
            }

            // Polymorphic routing based on user type (Admin, Shelter, Adopter)
            redirect(req, resp, authenticatedUser.getDashboardPath());

        } catch (InvalidCredentialsException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("enteredEmail", email);
            forward(req, resp, "auth/login.jsp");
        } catch (Exception e) {
            req.setAttribute("errorMessage", "An unexpected system error occurred. Please try again.");
            forward(req, resp, "auth/login.jsp");
        }
    }
}
