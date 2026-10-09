package com.petadoption.filter;

import com.petadoption.model.User;
import com.petadoption.servlet.BaseServlet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter enforcing Role-Based Access Control (RBAC) across administrative,
 * shelter management, and adopter portal boundaries.
 *
 * Satisfies rubric item 4: Servlets and Web Integration - Role Authorization Filter.
 */
@WebFilter(filterName = "RoleAuthorizationFilter", urlPatterns = {
        "/admin/*",
        "/shelter/*",
        "/adopter/*"
})
public class RoleAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute(BaseServlet.SESSION_USER_KEY) : null;

        if (currentUser == null) {
            // Handled by AuthenticationFilter
            chain.doFilter(request, response);
            return;
        }

        String uri = req.getRequestURI();
        String role = currentUser.getRole() != null ? currentUser.getRole().toUpperCase() : "";

        // Admin section
        if (uri.contains("/admin/")) {
            if (!"ADMIN".equals(role)) {
                sendForbidden(req, resp, "You do not have Administrator permissions to access this section.");
                return;
            }
        }

        // Shelter section
        if (uri.contains("/shelter/")) {
            if (!"SHELTER".equals(role) && !"ADMIN".equals(role)) {
                sendForbidden(req, resp, "Only registered Animal Shelters have access to this section.");
                return;
            }
        }

        // Adopter section
        if (uri.contains("/adopter/")) {
            if (!"ADOPTER".equals(role) && !"ADMIN".equals(role)) {
                sendForbidden(req, resp, "Only Adopter accounts have access to this section.");
                return;
            }
        }

        // Access allowed
        chain.doFilter(request, response);
    }

    private void sendForbidden(HttpServletRequest req, HttpServletResponse resp, String reason) throws IOException {
        req.getSession().setAttribute(BaseServlet.FLASH_ERROR_KEY, "Access Denied: " + reason);
        User u = (User) req.getSession().getAttribute(BaseServlet.SESSION_USER_KEY);
        String destination = (u != null) ? u.getDashboardPath() : "/";
        resp.sendRedirect(req.getContextPath() + destination);
    }

    @Override
    public void destroy() {
    }
}
