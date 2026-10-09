package com.petadoption.service;

import com.petadoption.dao.PetDAO;
import com.petadoption.dao.impl.PetDAOImpl;
import com.petadoption.exception.PetNotFoundException;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.Pet;
import com.petadoption.thread.SettingsCache;
import com.petadoption.util.PetComparator;
import com.petadoption.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service managing pet listings, search filters, sorting, and administrative approval workflows.
 */
public class PetService {

    private static final Logger logger = LoggerFactory.getLogger(PetService.class);
    private final PetDAO petDAO;
    private final SettingsCache settingsCache;

    public PetService() {
        this.petDAO = new PetDAOImpl();
        this.settingsCache = SettingsCache.getInstance();
    }

    public PetService(PetDAO petDAO, SettingsCache settingsCache) {
        this.petDAO = petDAO;
        this.settingsCache = settingsCache;
    }

    /**
     * Lists a new pet for adoption. Checks the platform auto-approval policy.
     */
    public Result<Pet> listPet(Pet pet) throws ValidationException {
        validatePet(pet);

        // Check if admin auto-approval is toggled on in settings
        boolean autoApprove = Boolean.parseBoolean(settingsCache.get("admin.auto_approve_pets", "false"));
        if (autoApprove) {
            pet.setApprovalStatus(Pet.APPROVAL_APPROVED);
        } else {
            pet.setApprovalStatus(Pet.APPROVAL_PENDING);
        }
        pet.setAdoptionStatus(Pet.ADOPTION_AVAILABLE);

        int id = petDAO.save(pet);
        if (id > 0) {
            pet.setId(id);
            String msg = autoApprove ?
                    "Pet listed and published to adopters successfully!" :
                    "Pet submitted for review. It will become visible once approved by an administrator.";
            return Result.success(msg, pet);
        }
        return Result.failure("Failed to create pet listing.");
    }

    public Result<Pet> updatePet(Pet pet, int requesterUserId, boolean isAdmin) throws ValidationException, PetNotFoundException {
        validatePet(pet);
        Pet existing = getPetById(pet.getId());

        // Authorization check: Shelter must own the pet listing unless Admin
        if (!isAdmin && existing.getShelterId() != requesterUserId) {
            return Result.failure("Unauthorized: You do not own this pet listing.");
        }

        existing.setName(pet.getName());
        existing.setType(pet.getType());
        existing.setBreed(pet.getBreed());
        existing.setAge(pet.getAge());
        existing.setLocation(pet.getLocation());
        existing.setDescription(pet.getDescription());
        if (pet.getPhotoPath() != null && !pet.getPhotoPath().isBlank()) {
            existing.setPhotoPath(pet.getPhotoPath());
        }
        if (pet.getAdoptionStatus() != null) {
            existing.setAdoptionStatus(pet.getAdoptionStatus());
        }

        boolean ok = petDAO.update(existing);
        return ok ? Result.success("Pet listing updated successfully.", existing) : Result.failure("Failed to update listing.");
    }

    public Result<Boolean> deletePet(int petId, int requesterUserId, boolean isAdmin) throws PetNotFoundException {
        Pet existing = getPetById(petId);
        if (!isAdmin && existing.getShelterId() != requesterUserId) {
            return Result.failure("Unauthorized: You do not have permission to delete this listing.");
        }

        boolean ok = petDAO.deleteById(petId);
        return ok ? Result.success("Pet listing removed successfully.", true) : Result.failure("Failed to delete pet listing.");
    }

    public Pet getPetById(int id) throws PetNotFoundException {
        Optional<Pet> opt = petDAO.findById(id);
        if (opt.isEmpty()) {
            throw new PetNotFoundException(id);
        }
        return opt.get();
    }

    /**
     * Searches and sorts available pets using Collections, Generics, and Streams.
     */
    public List<Pet> searchAvailablePets(String query, String type, String breed, String location,
                                         Integer minAge, Integer maxAge, String sortBy) {
        List<Pet> rawResults = petDAO.searchPets(query, type, breed, location, minAge, maxAge);
        return PetComparator.sortPets(rawResults, sortBy);
    }

    public List<Pet> getShelterPets(int shelterId) {
        return petDAO.findByShelterId(shelterId);
    }

    public List<Pet> getPendingApprovalPets() {
        return petDAO.findPendingApprovalPets();
    }

    public List<Pet> getAllPets() {
        return petDAO.findAll();
    }

    public Result<Boolean> approvePet(int petId) {
        boolean ok = petDAO.updateApprovalStatus(petId, Pet.APPROVAL_APPROVED);
        return ok ? Result.success("Pet listing approved and is now publicly visible.", true)
                  : Result.failure("Failed to approve pet.");
    }

    public Result<Boolean> rejectPet(int petId) {
        boolean ok = petDAO.updateApprovalStatus(petId, Pet.APPROVAL_REJECTED);
        return ok ? Result.success("Pet listing rejected.", true)
                  : Result.failure("Failed to reject pet.");
    }

    private void validatePet(Pet pet) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if (pet.getName() == null || pet.getName().isBlank()) {
            errors.add("Pet name cannot be blank.");
        }
        if (pet.getType() == null || pet.getType().isBlank()) {
            errors.add("Pet type must be specified.");
        }
        if (pet.getBreed() == null || pet.getBreed().isBlank()) {
            errors.add("Pet breed must be specified.");
        }
        if (pet.getAge() < 0) {
            errors.add("Age cannot be negative.");
        }
        if (pet.getLocation() == null || pet.getLocation().isBlank()) {
            errors.add("Location cannot be blank.");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
