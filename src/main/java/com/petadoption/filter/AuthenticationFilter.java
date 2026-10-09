package com.petadoption.filter;

import com.petadoption.dao.UserDAO;
import com.petadoption.dao.impl.UserDAOImpl;
import com.petadoption.model.User;
import com.petadoption.servlet.BaseServlet;
import com.petadoption.util.AuthTokenUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Global authentication and session persistence filter.
 * - Restores authenticated sessions seamlessly from persistent remember-me cookies across browser reopens.
 * - Protects authenticated routes (/admin, /shelter, /adopter, /messages, /applications, /profile).
 * - Enforces role-based access control.
 * - Remembers requested target URLs so users return directly to their destination upon login.
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        // Skip static asset filtering for optimal performance
        if (isStaticAsset(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute(BaseServlet.SESSION_USER_KEY) : null;

        // 1. If not authenticated in session, check persistent remember-me cookie
        if (currentUser == null) {
            currentUser = AuthTokenUtil.validateAuthCookie(req, userDAO);
            if (currentUser != null) {
                session = req.getSession(true);
                session.setAttribute(BaseServlet.SESSION_USER_KEY, currentUser);
                session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 days session
                logger.debug("Restored session for user [{}] via persistent auth cookie", currentUser.getEmail());
            }
        }

        // 2. Check if the path requires authentication
        if (isProtectedPath(path)) {
            if (currentUser == null) {
                // Save target URL including query parameters
                String target = req.getRequestURI();
                if (req.getQueryString() != null) {
                    target += "?" + req.getQueryString();
                }

                req.getSession(true).setAttribute("redirectAfterLogin", target);
                req.getSession().setAttribute(BaseServlet.FLASH_ERROR_KEY, "Please sign in to access that page.");
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            // 3. Role-Based Access Control checks
            String role = currentUser.getRole();
            if (path.startsWith("/admin") && !"ADMIN".equalsIgnoreCase(role)) {
                req.getSession().setAttribute(BaseServlet.FLASH_ERROR_KEY, "Access denied: Administrator privileges required.");
                resp.sendRedirect(req.getContextPath() + currentUser.getDashboardPath());
                return;
            }

            if (path.startsWith("/shelter") && !"SHELTER".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role)) {
                req.getSession().setAttribute(BaseServlet.FLASH_ERROR_KEY, "Access denied: Rescue Shelter privileges required.");
                resp.sendRedirect(req.getContextPath() + currentUser.getDashboardPath());
                return;
            }

            if (path.startsWith("/adopter") && !"ADOPTER".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role)) {
                req.getSession().setAttribute(BaseServlet.FLASH_ERROR_KEY, "Access denied: Adopter privileges required.");
                resp.sendRedirect(req.getContextPath() + currentUser.getDashboardPath());
                return;
            }
        }

        // Authenticated or public route, proceed normally
        chain.doFilter(request, response);
    }

    private boolean isProtectedPath(String path) {
        if (path == null) return false;
        return path.startsWith("/admin") ||
               path.startsWith("/shelter") ||
               path.startsWith("/adopter") ||
               path.startsWith("/messages") ||
               path.startsWith("/applications") ||
               path.equals("/profile");
    }

    private boolean isStaticAsset(String path) {
        if (path == null) return false;
        String lower = path.toLowerCase();
        return lower.endsWith(".css") ||
               lower.endsWith(".js") ||
               lower.endsWith(".png") ||
               lower.endsWith(".jpg") ||
               lower.endsWith(".jpeg") ||
               lower.endsWith(".gif") ||
               lower.endsWith(".svg") ||
               lower.endsWith(".ico") ||
               lower.endsWith(".woff") ||
               lower.endsWith(".woff2") ||
               lower.endsWith(".ttf") ||
               lower.startsWith("/css/") ||
               lower.startsWith("/js/") ||
               lower.startsWith("/assets/") ||
               lower.startsWith("/images/");
    }

    @Override
    public void destroy() {
    }
}
