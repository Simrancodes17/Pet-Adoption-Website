package com.petadoption.servlet;

import com.petadoption.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Abstract BaseServlet providing reusable helpers for MVC forwarding, redirection,
 * session extraction, and flash messaging across all controllers.
 */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String SESSION_USER_KEY = "currentUser";
    public static final String FLASH_SUCCESS_KEY = "flashSuccess";
    public static final String FLASH_ERROR_KEY = "flashError";

    /**
     * Forwards the request to a JSP view under /WEB-INF/jsp/.
     */
    protected void forward(HttpServletRequest req, HttpServletResponse resp, String jspPath)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/" + jspPath).forward(req, resp);
    }

    /**
     * Redirects to an application context-relative URL.
     */
    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String path)
            throws IOException {
        String contextPath = req.getContextPath();
        if (path.startsWith("/")) {
            resp.sendRedirect(contextPath + path);
        } else {
            resp.sendRedirect(contextPath + "/" + path);
        }
    }

    /**
     * Sets a temporary success flash message stored in the session.
     */
    protected void setFlashSuccess(HttpServletRequest req, String message) {
        HttpSession session = req.getSession(true);
        session.setAttribute(FLASH_SUCCESS_KEY, message);
    }

    /**
     * Sets a temporary error flash message stored in the session.
     */
    protected void setFlashError(HttpServletRequest req, String message) {
        HttpSession session = req.getSession(true);
        session.setAttribute(FLASH_ERROR_KEY, message);
    }

    /**
     * Retrieves the currently logged-in user from the session, if present.
     */
    protected User getLoggedInUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object obj = session.getAttribute(SESSION_USER_KEY);
            if (obj instanceof User) {
                return (User) obj;
            }
        }
        return null;
    }

    /**
     * Safe integer parser with fallback.
     */
    protected int getIntParameter(HttpServletRequest req, String paramName, int defaultValue) {
        String val = req.getParameter(paramName);
        if (val == null || val.isBlank()) return defaultValue;
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
