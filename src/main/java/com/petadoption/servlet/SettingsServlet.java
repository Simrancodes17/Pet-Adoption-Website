package com.petadoption.servlet;

import com.petadoption.service.SettingsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Controller for Admin System Settings management.
 * Writes to both the relational database and the in-memory ConcurrentHashMap cache.
 */
@WebServlet(name = "SettingsServlet", urlPatterns = {"/admin/settings"})
public class SettingsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private SettingsService settingsService;

    @Override
    public void init() {
        this.settingsService = new SettingsService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> settings = settingsService.getAllSettings();
        req.setAttribute("settings", settings);
        forward(req, resp, "admin/settings.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("setting.")) {
                String settingKey = key.substring("setting.".length());
                String value = entry.getValue()[0];
                settingsService.updateSetting(settingKey, value);
            }
        }

        // Checkbox settings that may not be sent if unchecked
        if (!parameterMap.containsKey("setting.admin.auto_approve_pets")) {
            settingsService.updateSetting("admin.auto_approve_pets", "false");
        }
        if (!parameterMap.containsKey("setting.notifications.async_email_enabled")) {
            settingsService.updateSetting("notifications.async_email_enabled", "false");
        }

        setFlashSuccess(req, "System settings updated successfully and applied to active cache!");
        redirect(req, resp, "/admin/settings");
    }
}
