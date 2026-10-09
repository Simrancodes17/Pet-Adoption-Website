package com.petadoption.servlet;

import com.petadoption.exception.ValidationException;
import com.petadoption.model.User;
import com.petadoption.service.UserService;
import com.petadoption.util.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller handling user registration for Adopters and Shelters.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends BaseServlet {
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
            redirect(req, resp, currentUser.getDashboardPath());
            return;
        }
        forward(req, resp, "auth/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String role = req.getParameter("role");
        String contactInfo = req.getParameter("contactInfo");

        if (password == null || !password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Passwords do not match.");
            req.setAttribute("name", name);
            req.setAttribute("email", email);
            req.setAttribute("role", role);
            req.setAttribute("contactInfo", contactInfo);
            forward(req, resp, "auth/register.jsp");
            return;
        }

        try {
            Result<User> result = userService.registerUser(name, email, password, role, contactInfo);
            if (result.isSuccess()) {
                User newUser = result.getData();

                // Automatically log the user in with authenticated session
                jakarta.servlet.http.HttpSession session = req.getSession(true);
                session.setAttribute(SESSION_USER_KEY, newUser);
                session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 days

                // Issue persistent remember-me cookie
                com.petadoption.util.AuthTokenUtil.issueAuthCookie(resp, newUser);

                setFlashSuccess(req, "Welcome to PawHaven, " + newUser.getName() + "! Your account has been created.");

                // Redirect to pending target (e.g. adopt pet application) or dashboard
                String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
                if (redirectUrl != null && !redirectUrl.isBlank()) {
                    session.removeAttribute("redirectAfterLogin");
                    resp.sendRedirect(redirectUrl);
                    return;
                }

                redirect(req, resp, newUser.getDashboardPath());
                return;
            } else {
                req.setAttribute("errorMessage", result.getMessage());
                req.setAttribute("name", name);
                req.setAttribute("email", email);
                req.setAttribute("role", role);
                req.setAttribute("contactInfo", contactInfo);
                forward(req, resp, "auth/register.jsp");
            }
        } catch (ValidationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("name", name);
            req.setAttribute("email", email);
            req.setAttribute("role", role);
            req.setAttribute("contactInfo", contactInfo);
            forward(req, resp, "auth/register.jsp");
        } catch (Exception e) {
            req.setAttribute("errorMessage", "An error occurred during registration. Please try again.");
            forward(req, resp, "auth/register.jsp");
        }
    }
}
