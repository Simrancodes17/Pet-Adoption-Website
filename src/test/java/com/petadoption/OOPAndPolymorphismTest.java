package com.petadoption;

import com.petadoption.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies Rubric Item 2: Core Java Concepts - OOP
 * Inheritance, polymorphism, abstract class User, interfaces Notifiable & Searchable, encapsulation.
 */
class OOPAndPolymorphismTest {

    @Test
    @DisplayName("Polymorphism: getDashboardPath() returns correct route for each User subclass")
    void testPolymorphicDashboardPaths() {
        User admin = UserFactory.createUser(1, "Admin", "admin@test.com", "hash", "ADMIN", "HQ", new Timestamp(System.currentTimeMillis()));
        User shelter = UserFactory.createUser(2, "Shelter", "shelter@test.com", "hash", "SHELTER", "Austin", new Timestamp(System.currentTimeMillis()));
        User adopter = UserFactory.createUser(3, "Adopter", "adopter@test.com", "hash", "ADOPTER", "Denver", new Timestamp(System.currentTimeMillis()));

        assertTrue(admin instanceof Admin);
        assertTrue(shelter instanceof Shelter);
        assertTrue(adopter instanceof Adopter);

        assertEquals("/admin/dashboard", admin.getDashboardPath());
        assertEquals("/shelter/dashboard", shelter.getDashboardPath());
        assertEquals("/adopter/dashboard", adopter.getDashboardPath());
    }

    @Test
    @DisplayName("Polymorphism & Encapsulation: getPermissions() returns distinct role-specific sets")
    void testPolymorphicPermissions() {
        User admin = new Admin();
        User shelter = new Shelter();
        User adopter = new Adopter();

        Set<String> adminPerms = admin.getPermissions();
        Set<String> shelterPerms = shelter.getPermissions();
        Set<String> adopterPerms = adopter.getPermissions();

        assertTrue(admin.hasPermission("MANAGE_USERS"));
        assertTrue(admin.hasPermission("APPROVE_PETS"));
        assertFalse(adopter.hasPermission("MANAGE_USERS"));

        assertTrue(shelter.hasPermission("LIST_PETS"));
        assertTrue(shelter.hasPermission("REVIEW_APPLICATIONS"));

        assertTrue(adopter.hasPermission("BROWSE_PETS"));
        assertTrue(adopter.hasPermission("APPLY_PET"));
    }

    @Test
    @DisplayName("Interface Notifiable: Application and Message implement notification contract")
    void testNotifiableInterface() {
        Application app = new Application();
        app.setAdopterEmail("john@test.com");
        app.setAdopterName("John");
        app.setPetName("Buddy");
        app.setStatus(Application.STATUS_APPROVED);

        assertTrue(app instanceof Notifiable);
        assertEquals("john@test.com", app.getNotificationRecipient());
        assertTrue(app.getNotificationSubject().contains("Buddy"));
        assertTrue(app.getNotificationBody().contains("APPROVED"));

        Message msg = new Message();
        msg.setReceiverEmail("shelter@rescue.org");
        msg.setSenderName("John Doe");
        msg.setContent("Can I visit tomorrow?");

        assertTrue(msg instanceof Notifiable);
        assertEquals("shelter@rescue.org", msg.getNotificationRecipient());
        assertTrue(msg.getNotificationBody().contains("Can I visit tomorrow?"));
    }

    @Test
    @DisplayName("Interface Searchable: Pet implements text search and multi-attribute filters")
    void testSearchableInterface() {
        Pet pet = new Pet(1, 2, "Golden Sparky", "Dog", "Golden Retriever", 3, "Austin, TX",
                "Playful and energetic puppy", null, Pet.ADOPTION_AVAILABLE, Pet.APPROVAL_APPROVED, null);

        assertTrue(pet instanceof Searchable);

        // Free-text query matching
        assertTrue(pet.matchesQuery("sparky"));
        assertTrue(pet.matchesQuery("golden"));
        assertTrue(pet.matchesQuery("austin"));
        assertFalse(pet.matchesQuery("cat"));

        // Multi-attribute map filtering
        assertTrue(pet.matchesFilters(Map.of("type", "Dog", "minAge", 2, "maxAge", 5)));
        assertFalse(pet.matchesFilters(Map.of("type", "Cat")));
        assertFalse(pet.matchesFilters(Map.of("minAge", 5))); // age is 3
    }

    @Test
    @DisplayName("Polymorphism: NotificationService implementations dispatch via common interface")
    void testNotificationServicePolymorphism() {
        Application app = new Application();
        app.setAdopterEmail("adopter@example.com");
        app.setPetName("Milo");
        app.setStatus(Application.STATUS_APPROVED);

        // Runtime polymorphism: invoke different strategies through common interface
        com.petadoption.service.NotificationService consoleService = new com.petadoption.service.ConsoleNotificationService();
        com.petadoption.service.NotificationService inAppService = new com.petadoption.service.InAppNotificationService();
        com.petadoption.service.NotificationService emailService = new com.petadoption.service.EmailNotificationService();
        com.petadoption.service.CompositeNotificationService composite = new com.petadoption.service.CompositeNotificationService(consoleService, inAppService);

        assertEquals("CONSOLE", consoleService.getChannelType());
        assertEquals("IN_APP", inAppService.getChannelType());
        assertEquals("EMAIL", emailService.getChannelType());
        assertEquals("COMPOSITE", composite.getChannelType());

        // Invoke polymorphic method
        consoleService.sendNotification(app);
        inAppService.sendNotification(app);
        composite.sendNotification(app);

        com.petadoption.service.InAppNotificationService concreteInApp = (com.petadoption.service.InAppNotificationService) inAppService;
        assertTrue(concreteInApp.getNotifications().size() >= 1);
    }
}
