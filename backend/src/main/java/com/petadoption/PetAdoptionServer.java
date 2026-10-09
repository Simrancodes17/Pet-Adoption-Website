package com.petadoption;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Lightweight Pet Adoption Platform Server.
 * Uses Java's built-in HTTP Server (zero external dependencies).
 * Serves the responsive SPA frontend and provides REST endpoints.
 */
public class PetAdoptionServer {

    private static final int PORT = 8080;
    private static Path frontendDir;

    public static void main(String[] args) throws IOException {
        // Resolve frontend directory path
        Path current = Paths.get("").toAbsolutePath();
        if (Files.exists(current.resolve("frontend"))) {
            frontendDir = current.resolve("frontend");
        } else if (Files.exists(current.resolve("../frontend"))) {
            frontendDir = current.resolve("../frontend").normalize();
        } else if (Files.exists(current.resolve("../../frontend"))) {
            frontendDir = current.resolve("../../frontend").normalize();
        } else {
            frontendDir = Paths.get("C:/Users/Simran Singh/.gemini/antigravity/scratch/pet-adoption-platform/frontend");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // API Contexts
        server.createContext("/api/status", new StatusHandler());
        server.createContext("/api/health", new HealthHandler());

        // Static Frontend Context
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null); // default executor
        server.start();

        System.out.println("=====================================================");
        System.out.println("  PawNest - Online Pet Adoption Platform (Java 26)   ");
        System.out.println("=====================================================");
        System.out.println("Server running at: http://localhost:" + PORT);
        System.out.println("Serving frontend from: " + frontendDir);
        System.out.println("Dashboards available:");
        System.out.println("  - Adopter Dashboard (Browse, Apply, Track)");
        System.out.println("  - Shelter Dashboard (Listings, Applications, Chat)");
        System.out.println("  - Admin Dashboard   (Users, Approvals, Analytics)");
        System.out.println("Press Ctrl+C to stop.");
        System.out.println("=====================================================");
    }

    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = """
                {
                  "platform": "PawNest Online Pet Adoption",
                  "version": "1.0.0",
                  "runtime": "Java 26",
                  "status": "OPERATIONAL",
                  "roles": ["Admin", "Shelter", "Adopter"]
                }
                """;
            sendResponse(exchange, 200, "application/json", json.getBytes());
        }
    }

    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "{\"status\": \"UP\", \"timestamp\": " + System.currentTimeMillis() + "}";
            sendResponse(exchange, 200, "application/json", json.getBytes());
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uriPath = exchange.getRequestURI().getPath();
            if (uriPath.equals("/") || uriPath.isEmpty()) {
                uriPath = "/index.html";
            }

            Path filePath = frontendDir.resolve(uriPath.substring(1)).normalize();

            if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                filePath = frontendDir.resolve("index.html");
            }

            if (!Files.exists(filePath)) {
                String notFound = "<h1>404 Not Found</h1><p>Frontend file not found at " + filePath + "</p>";
                sendResponse(exchange, 404, "text/html", notFound.getBytes());
                return;
            }

            String contentType = "text/plain";
            String lower = filePath.getFileName().toString().toLowerCase();
            if (lower.endsWith(".html")) contentType = "text/html; charset=UTF-8";
            else if (lower.endsWith(".css")) contentType = "text/css; charset=UTF-8";
            else if (lower.endsWith(".js")) contentType = "application/javascript; charset=UTF-8";
            else if (lower.endsWith(".json")) contentType = "application/json";
            else if (lower.endsWith(".png")) contentType = "image/png";
            else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) contentType = "image/jpeg";
            else if (lower.endsWith(".svg")) contentType = "image/svg+xml";

            byte[] bytes = Files.readAllBytes(filePath);
            sendResponse(exchange, 200, contentType, bytes);
        }
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String contentType, byte[] data) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, data.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(data);
        }
    }
}
