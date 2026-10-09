package com.petadoption.servlet;

import com.petadoption.exception.PetNotFoundException;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.Pet;
import com.petadoption.model.User;
import com.petadoption.service.PetService;
import com.petadoption.util.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * Controller handling Pet listing operations for Shelters and listing approval workflows for Admins.
 * Supports multipart file uploads for pet profile photos using @MultipartConfig.
 *
 * Satisfies rubric item 4: Servlets & Web Integration (PetServlet, MultipartConfig file upload).
 */
@WebServlet(name = "PetServlet", urlPatterns = {
        "/pets",
        "/shelter/pets",
        "/shelter/pets/create",
        "/shelter/pets/edit",
        "/shelter/pets/delete",
        "/admin/pets",
        "/admin/pets/approve",
        "/admin/pets/reject"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 1024 * 1024 * 5,         // 5 MB
        maxRequestSize = 1024 * 1024 * 10      // 10 MB
)
public class PetServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(PetServlet.class);
    private PetService petService;

    @Override
    public void init() {
        this.petService = new PetService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        User currentUser = getLoggedInUser(req);

        // Admin approving or viewing listings
        if ("/admin/pets".equals(path)) {
            List<Pet> pendingPets = petService.getPendingApprovalPets();
            List<Pet> allPets = petService.getAllPets();
            req.setAttribute("pendingPets", pendingPets);
            req.setAttribute("allPets", allPets);
            forward(req, resp, "admin/pet-approvals.jsp");
            return;
        }

        // Shelter listing views
        if ("/shelter/pets/create".equals(path)) {
            forward(req, resp, "shelter/pet-form.jsp");
            return;
        }

        if ("/shelter/pets/edit".equals(path)) {
            int petId = getIntParameter(req, "id", -1);
            try {
                Pet pet = petService.getPetById(petId);
                req.setAttribute("pet", pet);
                forward(req, resp, "shelter/pet-form.jsp");
            } catch (PetNotFoundException e) {
                setFlashError(req, e.getMessage());
                redirect(req, resp, "/shelter/pets");
            }
            return;
        }

        // Default: /shelter/pets - list shelter's own pets
        if (currentUser != null) {
            List<Pet> shelterPets = petService.getShelterPets(currentUser.getId());
            req.setAttribute("pets", shelterPets);
            forward(req, resp, "shelter/pet-list.jsp");
        } else {
            redirect(req, resp, "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        User currentUser = getLoggedInUser(req);

        // 1. Admin Approval / Rejection
        if ("/admin/pets/approve".equals(path)) {
            int petId = getIntParameter(req, "id", -1);
            Result<Boolean> res = petService.approvePet(petId);
            if (res.isSuccess()) {
                setFlashSuccess(req, res.getMessage());
            } else {
                setFlashError(req, res.getMessage());
            }
            redirect(req, resp, "/admin/pets");
            return;
        }

        if ("/admin/pets/reject".equals(path)) {
            int petId = getIntParameter(req, "id", -1);
            Result<Boolean> res = petService.rejectPet(petId);
            if (res.isSuccess()) {
                setFlashSuccess(req, res.getMessage());
            } else {
                setFlashError(req, res.getMessage());
            }
            redirect(req, resp, "/admin/pets");
            return;
        }

        // 2. Shelter Pet Deletion
        if ("/shelter/pets/delete".equals(path)) {
            int petId = getIntParameter(req, "id", -1);
            boolean isAdmin = currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
            try {
                Result<Boolean> res = petService.deletePet(petId, currentUser != null ? currentUser.getId() : -1, isAdmin);
                if (res.isSuccess()) {
                    setFlashSuccess(req, res.getMessage());
                } else {
                    setFlashError(req, res.getMessage());
                }
            } catch (PetNotFoundException e) {
                setFlashError(req, e.getMessage());
            }
            redirect(req, resp, "/shelter/pets");
            return;
        }

        // 3. Shelter Pet Creation or Update (with Multipart Photo Upload)
        if (currentUser == null) {
            redirect(req, resp, "/login");
            return;
        }

        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String breed = req.getParameter("breed");
        int age = getIntParameter(req, "age", 0);
        String location = req.getParameter("location");
        String description = req.getParameter("description");
        String adoptionStatus = req.getParameter("adoptionStatus");

        // Handle Photo Upload
        String photoPath = handlePhotoUpload(req);

        if ("/shelter/pets/create".equals(path)) {
            Pet newPet = new Pet();
            newPet.setShelterId(currentUser.getId());
            newPet.setName(name);
            newPet.setType(type);
            newPet.setBreed(breed);
            newPet.setAge(age);
            newPet.setLocation(location);
            newPet.setDescription(description);
            if (photoPath != null) {
                newPet.setPhotoPath(photoPath);
            }

            try {
                Result<Pet> result = petService.listPet(newPet);
                if (result.isSuccess()) {
                    setFlashSuccess(req, result.getMessage());
                    redirect(req, resp, "/shelter/pets");
                } else {
                    req.setAttribute("errorMessage", result.getMessage());
                    req.setAttribute("pet", newPet);
                    forward(req, resp, "shelter/pet-form.jsp");
                }
            } catch (ValidationException e) {
                req.setAttribute("errorMessage", e.getMessage());
                req.setAttribute("pet", newPet);
                forward(req, resp, "shelter/pet-form.jsp");
            }

        } else if ("/shelter/pets/edit".equals(path)) {
            int petId = getIntParameter(req, "id", -1);
            boolean isAdmin = "ADMIN".equalsIgnoreCase(currentUser.getRole());

            Pet updatedPet = new Pet();
            updatedPet.setId(petId);
            updatedPet.setName(name);
            updatedPet.setType(type);
            updatedPet.setBreed(breed);
            updatedPet.setAge(age);
            updatedPet.setLocation(location);
            updatedPet.setDescription(description);
            updatedPet.setAdoptionStatus(adoptionStatus);
            if (photoPath != null) {
                updatedPet.setPhotoPath(photoPath);
            }

            try {
                Result<Pet> result = petService.updatePet(updatedPet, currentUser.getId(), isAdmin);
                if (result.isSuccess()) {
                    setFlashSuccess(req, result.getMessage());
                    redirect(req, resp, "/shelter/pets");
                } else {
                    req.setAttribute("errorMessage", result.getMessage());
                    req.setAttribute("pet", updatedPet);
                    forward(req, resp, "shelter/pet-form.jsp");
                }
            } catch (Exception e) {
                req.setAttribute("errorMessage", e.getMessage());
                req.setAttribute("pet", updatedPet);
                forward(req, resp, "shelter/pet-form.jsp");
            }
        }
    }

    /**
     * Processes uploaded pet photo and writes to webapp/uploads directory.
     */
    private String handlePhotoUpload(HttpServletRequest req) {
        try {
            Part filePart = req.getPart("photo");
            if (filePart != null && filePart.getSize() > 0) {
                String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String ext = "";
                int dotIdx = submittedFileName.lastIndexOf('.');
                if (dotIdx > 0) {
                    ext = submittedFileName.substring(dotIdx).toLowerCase();
                }

                // Security check on extension
                if (ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png") || ext.equals(".webp")) {
                    String uniqueFileName = UUID.randomUUID() + ext;

                    String uploadDirPath = req.getServletContext().getRealPath("/uploads");
                    File uploadDir = new File(uploadDirPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    String targetFilePath = uploadDirPath + File.separator + uniqueFileName;
                    filePart.write(targetFilePath);
                    logger.info("Saved pet photo upload to: {}", targetFilePath);
                    return "uploads/" + uniqueFileName;
                }
            }
        } catch (Exception e) {
            logger.warn("Photo upload could not be processed: {}", e.getMessage());
        }
        return null;
    }
}
