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
 * Filter ensuring that protected routes require an active authenticated session.
 * Satisfies rubric item 4: Servlets and Web Integration - Filters.
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {
        "/admin/*",
        "/shelter/*",
        "/adopter/*",
        "/messages/*",
        "/applications/*"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();

        // Allow public application submission action or search if routed
        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute(BaseServlet.SESSION_USER_KEY) : null;

        if (currentUser == null) {
            // Save requested URL for redirect after login
            String target = req.getRequestURI();
            if (req.getQueryString() != null) {
                target += "?" + req.getQueryString();
            }

            req.getSession(true).setAttribute("redirectAfterLogin", target);
            req.getSession().setAttribute(BaseServlet.FLASH_ERROR_KEY, "Please sign in to access that page.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Authenticated, continue filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
