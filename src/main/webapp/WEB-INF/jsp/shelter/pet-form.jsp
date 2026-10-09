<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="isEdit" value="${not empty pet and pet.id > 0}" />
<c:set var="pageTitle" value="${isEdit ? 'Edit Pet Listing' : 'List New Pet'}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card card-paw shadow-sm border-0">
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-heart-pulse-fill text-danger me-2"></i> ${isEdit ? 'Edit Pet Profile' : 'List a Pet for Adoption'}
                    </h5>
                    <span class="badge bg-light text-dark border">
                        <i class="bi bi-file-earmark-arrow-up"></i> Photo Upload Enabled
                    </span>
                </div>
                <div class="card-body p-4 p-md-5">
                    <form action="${pageContext.request.contextPath}/shelter/pets/${isEdit ? 'edit' : 'create'}"
                          method="post" enctype="multipart/form-data">

                        <c:if test="${isEdit}">
                            <input type="hidden" name="id" value="${pet.id}">
                        </c:if>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="name" class="form-label">Pet Name <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="name" name="name"
                                       value="<c:out value='${isEdit ? pet.name : ""}' />" required placeholder="e.g. Bella">
                            </div>

                            <div class="col-md-6">
                                <label for="type" class="form-label">Animal Species <span class="text-danger">*</span></label>
                                <select class="form-select" id="type" name="type" required>
                                    <c:forEach var="entry" items="${speciesBreedsMap}">
                                        <option value="${entry.key}" ${isEdit and pet.type == entry.key ? 'selected' : ''}>${entry.key}</option>
                                    </c:forEach>
                                    <c:if test="${empty speciesBreedsMap}">
                                        <option value="Dog" ${isEdit and pet.type == 'Dog' ? 'selected' : ''}>Dog</option>
                                        <option value="Cat" ${isEdit and pet.type == 'Cat' ? 'selected' : ''}>Cat</option>
                                        <option value="Rabbit" ${isEdit and pet.type == 'Rabbit' ? 'selected' : ''}>Rabbit</option>
                                        <option value="Bird" ${isEdit and pet.type == 'Bird' ? 'selected' : ''}>Bird</option>
                                        <option value="Hamster" ${isEdit and pet.type == 'Hamster' ? 'selected' : ''}>Hamster</option>
                                        <option value="Turtle" ${isEdit and pet.type == 'Turtle' ? 'selected' : ''}>Turtle</option>
                                    </c:if>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="breedSelect" class="form-label">Breed <span class="text-danger">*</span></label>
                                <select class="form-select" id="breedSelect" name="breed" required>
                                    <option value="">-- Select Animal Species First --</option>
                                </select>
                                <input type="text" class="form-control mt-2 d-none" id="customBreedInput"
                                       placeholder="Type custom breed name..."
                                       value="<c:out value='${isEdit ? pet.breed : ""}' />">
                            </div>

                            <div class="col-md-6">
                                <label for="gender" class="form-label">Gender <span class="text-danger">*</span></label>
                                <select class="form-select" id="gender" name="gender" required>
                                    <option value="Male" ${isEdit and pet.gender == 'Male' ? 'selected' : ''}>Male</option>
                                    <option value="Female" ${isEdit and pet.gender == 'Female' ? 'selected' : ''}>Female</option>
                                    <option value="Unknown" ${isEdit and (pet.gender == 'Unknown' or empty pet.gender) ? 'selected' : ''}>Unknown</option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="age" class="form-label">Age (Years) <span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="age" name="age" min="0" max="30"
                                       value="${isEdit ? pet.age : 1}" required>
                            </div>

                            <div class="col-md-6">
                                <label for="adoptionStatus" class="form-label">Adoption Status</label>
                                <select class="form-select" id="adoptionStatus" name="adoptionStatus">
                                    <option value="AVAILABLE" ${isEdit and pet.adoptionStatus == 'AVAILABLE' ? 'selected' : ''}>AVAILABLE</option>
                                    <option value="PENDING" ${isEdit and pet.adoptionStatus == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                    <option value="ADOPTED" ${isEdit and pet.adoptionStatus == 'ADOPTED' ? 'selected' : ''}>ADOPTED</option>
                                </select>
                            </div>

                            <div class="col-12">
                                <label for="location" class="form-label">Location / Facility <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="location" name="location"
                                       value="<c:out value='${isEdit ? pet.location : ""}' />" required placeholder="e.g. Austin, TX">
                            </div>

                            <div class="col-12">
                                <label for="description" class="form-label">Detailed Personality &amp; Care Needs</label>
                                <textarea class="form-control" id="description" name="description" rows="5"
                                          placeholder="Describe temperament, medical status, vaccination, house training, and good family fit..."><c:out value='${isEdit ? pet.description : ""}' /></textarea>
                            </div>

                            <!-- Multipart Photo Upload -->
                            <div class="col-12">
                                <label for="photo" class="form-label">Pet Photo (File Upload)</label>
                                <input type="file" class="form-control" id="photo" name="photo" accept="image/png, image/jpeg, image/webp">
                                <div class="form-text">Supports JPG, PNG, WEBP (Max 5MB). Processed securely via <code>@MultipartConfig</code>.</div>
                                <c:if test="${isEdit and not empty pet.photoPath}">
                                    <div class="mt-2 small text-muted">
                                        Current photo: <code><c:out value="${pet.photoPath}" /></code>
                                    </div>
                                </c:if>
                            </div>

                            <div class="col-12 pt-3 border-top d-flex justify-content-between align-items-center">
                                <a href="${pageContext.request.contextPath}/shelter/pets" class="btn btn-outline-secondary">
                                    Cancel
                                </a>
                                <button type="submit" class="btn btn-paw-primary px-4">
                                    <i class="bi bi-cloud-arrow-up-fill me-1"></i> ${isEdit ? 'Update Pet Listing' : 'Publish Pet Listing'}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const breedsMap = ${not empty speciesBreedsJson ? speciesBreedsJson : '{}'};
        const currentBreed = '<c:out value="${isEdit ? pet.breed : ''}" />';
        const breedSelect = document.getElementById('breedSelect');
        const customInput = document.getElementById('customBreedInput');

        initSpeciesBreedDropdown('type', 'breedSelect', currentBreed, breedsMap, true);

        // Handle switching between dropdown and custom breed input
        if (breedSelect && customInput) {
            breedSelect.addEventListener('change', function () {
                if (this.value === '__custom__') {
                    customInput.classList.remove('d-none');
                    customInput.name = 'breed';
                    breedSelect.name = 'breed_select_ignored';
                    customInput.focus();
                } else {
                    customInput.classList.add('d-none');
                    customInput.name = 'breed_custom_ignored';
                    breedSelect.name = 'breed';
                }
            });
        }
    });
</script>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
