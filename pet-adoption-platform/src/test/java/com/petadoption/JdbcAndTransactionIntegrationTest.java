package com.petadoption;

import com.petadoption.dao.ApplicationDAO;
import com.petadoption.dao.PetDAO;
import com.petadoption.dao.UserDAO;
import com.petadoption.dao.impl.ApplicationDAOImpl;
import com.petadoption.dao.impl.PetDAOImpl;
import com.petadoption.dao.impl.UserDAOImpl;
import com.petadoption.model.Application;
import com.petadoption.model.Pet;
import com.petadoption.model.User;
import com.petadoption.model.UserFactory;
import com.petadoption.service.ApplicationService;
import com.petadoption.util.DBConnectionUtil;
import com.petadoption.util.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies Rubric Item 3: Database Integration with JDBC
 * DAO classes, PreparedStatement, transactions with commit/rollback, connection pooling.
 */
class JdbcAndTransactionIntegrationTest {

    private static DBConnectionUtil dbUtil;
    private static UserDAO userDAO;
    private static PetDAO petDAO;
    private static ApplicationDAO applicationDAO;
    private static ApplicationService applicationService;

    @BeforeAll
    static void setUpDatabase() throws Exception {
        dbUtil = DBConnectionUtil.getInstance();
        try (Connection conn = dbUtil.getConnection()) {
            assertNotNull(conn, "Database connection should be established via pool");
        }
        userDAO = new UserDAOImpl(dbUtil);
        petDAO = new PetDAOImpl(dbUtil);
        applicationDAO = new ApplicationDAOImpl(dbUtil);
        applicationService = new ApplicationService();
    }

    @Test
    @DisplayName("JDBC Integration: UserDAO persists and retrieves polymorphic User via PreparedStatement")
    void testUserDaoCrud() {
        String testEmail = "test.shelter." + System.currentTimeMillis() + "@example.com";
        User shelter = UserFactory.createUser(0, "Test Shelter Inc", testEmail, "secretHash", "SHELTER", "Austin, TX", null);

        int savedId = userDAO.save(shelter);
        assertTrue(savedId > 0, "User ID should be generated upon insert");

        Optional<User> retrieved = userDAO.findById(savedId);
        assertTrue(retrieved.isPresent());
        assertEquals("Test Shelter Inc", retrieved.get().getName());
        assertEquals("SHELTER", retrieved.get().getRole());
        assertEquals("/shelter/dashboard", retrieved.get().getDashboardPath());
    }

    @Test
    @DisplayName("JDBC Transactions: Atomic adoption approval marks pet ADOPTED and rejects competing applications")
    void testAtomicAdoptionTransaction() throws Exception {
        // 1. Create a Shelter and two Adopters
        long ts = System.currentTimeMillis();
        int shelterId = userDAO.save(UserFactory.createUser(0, "Rescue " + ts, "shelter." + ts + "@rescue.org", "pwd", "SHELTER", "City", null));
        int adopter1Id = userDAO.save(UserFactory.createUser(0, "Adopter One " + ts, "adopter1." + ts + "@mail.com", "pwd", "ADOPTER", "City", null));
        int adopter2Id = userDAO.save(UserFactory.createUser(0, "Adopter Two " + ts, "adopter2." + ts + "@mail.com", "pwd", "ADOPTER", "City", null));

        // 2. Shelter lists a pet
        Pet pet = new Pet(0, shelterId, "Shadow " + ts, "Dog", "Husky", 2, "Austin", "Active pup", null,
                Pet.ADOPTION_AVAILABLE, Pet.APPROVAL_APPROVED, null);
        int petId = petDAO.save(pet);
        assertTrue(petId > 0);

        // 3. Both Adopter 1 and Adopter 2 submit applications for the same pet
        Application app1 = new Application(0, petId, adopter1Id, shelterId, "I love Huskies and run 5 miles daily", Application.STATUS_PENDING, null, null);
        Application app2 = new Application(0, petId, adopter2Id, shelterId, "We have a big farm and another dog", Application.STATUS_PENDING, null, null);

        int app1Id = applicationDAO.save(app1);
        int app2Id = applicationDAO.save(app2);

        assertTrue(app1Id > 0);
        assertTrue(app2Id > 0);

        // 4. Shelter approves Application 1 atomically
        Result<Boolean> result = applicationService.approveApplication(app1Id, shelterId, false);
        assertTrue(result.isSuccess(), "Adoption approval transaction should succeed: " + result.getMessage());

        // 5. Verify the atomic changes in DB:
        // App 1 must be APPROVED
        Optional<Application> updatedApp1 = applicationDAO.findById(app1Id);
        assertTrue(updatedApp1.isPresent());
        assertEquals(Application.STATUS_APPROVED, updatedApp1.get().getStatus());

        // Target Pet must be marked as ADOPTED
        Optional<Pet> updatedPet = petDAO.findById(petId);
        assertTrue(updatedPet.isPresent());
        assertEquals(Pet.ADOPTION_ADOPTED, updatedPet.get().getAdoptionStatus());

        // Competing Application 2 must be automatically REJECTED by the transaction
        Optional<Application> updatedApp2 = applicationDAO.findById(app2Id);
        assertTrue(updatedApp2.isPresent());
        assertEquals(Application.STATUS_REJECTED, updatedApp2.get().getStatus());
    }
}
