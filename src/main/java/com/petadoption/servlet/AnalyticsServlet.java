package com.petadoption.servlet;

import com.petadoption.model.AnalyticsData;
import com.petadoption.service.AnalyticsService;
import com.petadoption.thread.AnalyticsScheduler;
import com.petadoption.thread.NotificationThreadPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller displaying platform analytics, metrics distributions, and background task logs.
 */
@WebServlet(name = "AnalyticsServlet", urlPatterns = {"/admin/analytics"})
public class AnalyticsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private AnalyticsService analyticsService;

    @Override
    public void init() {
        this.analyticsService = new AnalyticsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String refresh = req.getParameter("refresh");
        if ("true".equalsIgnoreCase(refresh)) {
            AnalyticsScheduler.getInstance().refreshAnalytics();
            setFlashSuccess(req, "Analytics data recomputed!");
            redirect(req, resp, "/admin/analytics");
            return;
        }

        AnalyticsData analytics = analyticsService.getPlatformAnalytics();
        List<String> deliveryLog = NotificationThreadPool.getInstance().getRecentDeliveryLog();

        req.setAttribute("analytics", analytics);
        req.setAttribute("deliveryLog", deliveryLog);

        forward(req, resp, "admin/analytics.jsp");
    }
}
