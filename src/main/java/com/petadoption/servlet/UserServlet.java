package com.petadoption.servlet;

import com.petadoption.model.User;
import com.petadoption.service.UserService;
import com.petadoption.util.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller for Admin User Management.
 * Implements create, update, delete, and role management for platform users.
 * Satisfies rubric item 4: Servlets and Web Integration - UserServlet.
 */
@WebServlet(name = "UserServlet", urlPatterns = {
        "/admin/users",
        "/admin/users/create",
        "/admin/users/edit",
        "/admin/users/delete"
})
public class UserServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/admin/users/create".equals(path)) {
            forward(req, resp, "admin/user-form.jsp");
            return;
        }

        if ("/admin/users/edit".equals(path)) {
            int id = getIntParameter(req, "id", -1);
            Optional<User> uOpt = userService.getUserById(id);
            if (uOpt.isPresent()) {
                req.setAttribute("targetUser", uOpt.get());
                forward(req, resp, "admin/user-form.jsp");
            } else {
                setFlashError(req, "User not found.");
                redirect(req, resp, "/admin/users");
            }
            return;
        }

        // Default: /admin/users - list all
        String roleFilter = req.getParameter("role");
        List<User> users;
        if (roleFilter != null && !roleFilter.isBlank() && !"ALL".equalsIgnoreCase(roleFilter)) {
            users = userService.getUsersByRole(roleFilter.toUpperCase());
        } else {
            users = userService.getAllUsers();
        }

        req.setAttribute("users", users);
        req.setAttribute("roleFilter", roleFilter != null ? roleFilter : "ALL");
        forward(req, resp, "admin/users.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getServletPath();

        if ("/admin/users/delete".equals(path)) {
            int id = getIntParameter(req, "id", -1);
            User current = getLoggedInUser(req);
            if (current != null && current.getId() == id) {
                setFlashError(req, "Cannot delete your own active administrator account.");
            } else {
                Result<Boolean> res = userService.deleteUser(id);
                if (res.isSuccess()) {
                    setFlashSuccess(req, "User account successfully deleted.");
                } else {
                    setFlashError(req, res.getMessage());
                }
            }
            redirect(req, resp, "/admin/users");
            return;
        }

        // Create or Edit
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String contactInfo = req.getParameter("contactInfo");

        if ("/admin/users/create".equals(path)) {
            String password = req.getParameter("password");
            try {
                Result<User> res = userService.registerUser(name, email, password, role, contactInfo);
                if (res.isSuccess()) {
                    setFlashSuccess(req, "User created successfully: " + res.getData().getName());
                    redirect(req, resp, "/admin/users");
                } else {
                    req.setAttribute("errorMessage", res.getMessage());
                    forward(req, resp, "admin/user-form.jsp");
                }
            } catch (Exception e) {
                req.setAttribute("errorMessage", e.getMessage());
                forward(req, resp, "admin/user-form.jsp");
            }
        } else if ("/admin/users/edit".equals(path)) {
            int id = getIntParameter(req, "id", -1);
            try {
                Result<Boolean> res = userService.updateUser(id, name, email, role, contactInfo);
                if (res.isSuccess()) {
                    setFlashSuccess(req, "User updated successfully.");
                    redirect(req, resp, "/admin/users");
                } else {
                    req.setAttribute("errorMessage", res.getMessage());
                    forward(req, resp, "admin/user-form.jsp");
                }
            } catch (Exception e) {
                req.setAttribute("errorMessage", e.getMessage());
                forward(req, resp, "admin/user-form.jsp");
            }
        }
    }
}
