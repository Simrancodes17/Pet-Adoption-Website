package com.petadoption.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller serving informative and institutional pages:
 * How Adoption Works, About Us, Contact, Privacy Policy, and Terms & Conditions.
 */
@WebServlet(name = "InfoServlet", urlPatterns = {"/how-it-works", "/about", "/contact", "/privacy", "/terms"})
public class InfoServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();

        switch (servletPath) {
            case "/how-it-works":
                req.setAttribute("pageTitle", "How Adoption Works");
                forward(req, resp, "info/how-it-works.jsp");
                break;
            case "/about":
                req.setAttribute("pageTitle", "About PawHaven");
                forward(req, resp, "info/about.jsp");
                break;
            case "/contact":
                req.setAttribute("pageTitle", "Contact Care & Shelter Support");
                forward(req, resp, "info/contact.jsp");
                break;
            case "/privacy":
                req.setAttribute("pageTitle", "Privacy Policy");
                forward(req, resp, "info/privacy.jsp");
                break;
            case "/terms":
                req.setAttribute("pageTitle", "Terms of Service");
                forward(req, resp, "info/terms.jsp");
                break;
            default:
                redirect(req, resp, "/home");
                break;
        }
    }
}
