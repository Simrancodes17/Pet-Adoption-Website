package com.petadoption.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controller handling user sign out and session invalidation.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Clear persistent authentication cookie
        com.petadoption.util.AuthTokenUtil.clearAuthCookie(resp);

        // Invalidate server-side session
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Start clean flash session to convey confirmation message
        setFlashSuccess(req, "You have been logged out successfully.");
        redirect(req, resp, "/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
