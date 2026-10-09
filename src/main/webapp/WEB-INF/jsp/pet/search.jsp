<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Browse Adoptable Pets" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <!-- Header Summary -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h2 class="fw-bold mb-1">Adoptable Animal Companions</h2>
            <p class="text-muted small mb-0">Browse through rescued animals waiting for their forever homes</p>
        </div>
        <div>
            <span class="badge bg-white text-dark border px-3 py-2 rounded-pill shadow-sm">
                <i class="bi bi-grid-fill text-primary me-1"></i> <strong>${totalResults}</strong> Pets Available
            </span>
        </div>
    </div>

    <div class="row g-4">
        <!-- Sidebar Filters -->
        <div class="col-lg-3">
            <div class="filter-card sticky-top" style="top: 80px;">
                <div class="filter-card-header">
                    <h5 class="filter-title">
                        <i class="bi bi-sliders text-primary"></i> Filter Pets
                    </h5>
                    <a href="${pageContext.request.contextPath}/search" class="filter-reset-link">
                        <i class="bi bi-arrow-counterclockwise"></i> Reset All
                    </a>
                </div>

                <form action="${pageContext.request.contextPath}/search" method="get">
                    <!-- Keywords -->
                    <div class="mb-3">
                        <label class="form-label">Search Keywords</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-search"></i></span>
                            <input type="text" name="q" class="form-control form-control-sm" placeholder="Name, breed, traits..."
                                   value="<c:out value='${query}' />">
                        </div>
                    </div>

                    <!-- Animal Type -->
                    <div class="mb-3">
                        <label class="form-label">Animal Species</label>
                        <select name="type" id="speciesSelect" class="form-select form-select-sm">
                            <option value="ALL" ${selectedType == 'ALL' ? 'selected' : ''}>All Animals</option>
                            <c:forEach var="sp" items="${allSpeciesList}">
                                <option value="${sp}" ${selectedType == sp ? 'selected' : ''}>${sp}s</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Dynamic Breed -->
                    <div class="mb-3">
                        <label class="form-label">Breed</label>
                        <select name="breed" id="breedSelect" class="form-select form-select-sm">
                            <option value="ALL">All Breeds</option>
                        </select>
                        <div class="form-text" style="font-size: 0.74rem;">Filtered automatically by selected species.</div>
                    </div>

                    <!-- Gender -->
                    <div class="mb-3">
                        <label class="form-label">Gender</label>
                        <select name="gender" class="form-select form-select-sm">
                            <option value="ALL" ${selectedGender == 'ALL' ? 'selected' : ''}>All Genders</option>
                            <option value="Male" ${selectedGender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${selectedGender == 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                    </div>

                    <!-- Location -->
                    <div class="mb-3">
                        <label class="form-label">Location / City</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-geo-alt"></i></span>
                            <input type="text" name="location" class="form-control form-control-sm" placeholder="e.g. Austin, Denver"
                                   value="<c:out value='${location}' />">
                        </div>
                    </div>

                    <!-- Age Range -->
                    <div class="row g-2 mb-3">
                        <div class="col-6">
                            <label class="form-label">Min Age (yrs)</label>
                            <input type="number" name="minAge" class="form-control form-control-sm" min="0" max="25"
                                   value="${minAge}">
                        </div>
                        <div class="col-6">
                            <label class="form-label">Max Age (yrs)</label>
                            <input type="number" name="maxAge" class="form-control form-control-sm" min="0" max="25"
                                   value="${maxAge}">
                        </div>
                    </div>

                    <!-- Sort -->
                    <div class="mb-3">
                        <label class="form-label">Sort Order</label>
                        <select name="sortBy" class="form-select form-select-sm">
                            <option value="newest" ${sortBy == 'newest' ? 'selected' : ''}>Newest Listed</option>
                            <option value="name" ${sortBy == 'name' ? 'selected' : ''}>Name (A-Z)</option>
                            <option value="age_asc" ${sortBy == 'age_asc' ? 'selected' : ''}>Age: Youngest First</option>
                            <option value="age_desc" ${sortBy == 'age_desc' ? 'selected' : ''}>Age: Oldest First</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-paw-primary w-100 py-2 mt-2">
                        <i class="bi bi-funnel-fill"></i> Apply Filters
                    </button>
                </form>
            </div>
        </div>

        <!-- Pets Results Grid -->
        <div class="col-lg-9">
            <c:choose>
                <c:when test="${empty pets}">
                    <div class="card card-paw p-5 text-center my-3">
                        <div class="mb-3 text-muted display-4"><i class="bi bi-search"></i></div>
                        <h4 class="fw-bold mb-2">No Matching Pets Found</h4>
                        <p class="text-muted small mb-4" style="max-width: 420px; margin: 0 auto;">
                            We couldn't find any pets matching your filter criteria. Try clearing some filters or searching for "All Animals".
                        </p>
                        <div>
                            <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary btn-sm">
                                <i class="bi bi-arrow-repeat me-1"></i> Reset All Filters
                            </a>
                        </div>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="row g-4">
                        <c:forEach var="pet" items="${pets}">
                            <div class="col-md-6 col-xl-4">
                                <div class="card card-paw h-100 d-flex flex-column scroll-reveal">
                                    <div class="pet-card-img-wrapper">
                                        <c:choose>
                                            <c:when test="${pet.photoPath.startsWith('http')}">
                                                <img src="${pet.photoPath}" class="pet-card-img" alt="${pet.name}" data-species="${pet.type}">
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="fallbackUrl" value="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80" />
                                                <c:if test="${pet.type == 'Cat'}"><c:set var="fallbackUrl" value="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=600&q=80" /></c:if>
                                                <c:if test="${pet.type == 'Rabbit'}"><c:set var="fallbackUrl" value="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80" /></c:if>
                                                <c:if test="${pet.type == 'Bird'}"><c:set var="fallbackUrl" value="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=600&q=80" /></c:if>
                                                <c:if test="${pet.type == 'Hamster'}"><c:set var="fallbackUrl" value="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80" /></c:if>
                                                <c:if test="${pet.type == 'Turtle'}"><c:set var="fallbackUrl" value="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=600&q=80" /></c:if>
                                                <img src="${fallbackUrl}" class="pet-card-img" alt="${pet.name}" data-species="${pet.type}">
                                            </c:otherwise>
                                        </c:choose>

                                        <span class="pet-card-species-badge">
                                            <c:out value="${pet.type}" />
                                        </span>

                                        <span class="pet-card-status-badge badge-available">
                                            <i class="bi bi-check-circle-fill me-1"></i> Available
                                        </span>
                                    </div>

                                    <div class="pet-card-body d-flex flex-column flex-grow-1">
                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                            <h4 class="pet-name-title mb-0"><c:out value="${pet.name}" /></h4>
                                            <c:choose>
                                                <c:when test="${pet.gender == 'Male'}">
                                                    <span class="pet-gender-pill gender-male"><i class="bi bi-gender-male"></i> Male</span>
                                                </c:when>
                                                <c:when test="${pet.gender == 'Female'}">
                                                    <span class="pet-gender-pill gender-female"><i class="bi bi-gender-female"></i> Female</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="pet-gender-pill gender-unknown">Unknown</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="pet-meta-row">
                                            <span class="fw-semibold text-dark"><c:out value="${pet.breed}" /></span>
                                            <span>&bull;</span>
                                            <span>${pet.age} ${pet.age == 1 ? 'yr' : 'yrs'}</span>
                                        </div>

                                        <div class="small text-muted mb-2">
                                            <i class="bi bi-geo-alt-fill text-danger me-1"></i> <c:out value="${pet.location}" />
                                        </div>

                                        <p class="pet-card-desc flex-grow-1">
                                            <c:out value="${pet.description}" />
                                        </p>

                                        <div class="pet-card-footer mt-auto">
                                            <span class="small text-muted text-truncate" style="max-width: 130px;">
                                                <i class="bi bi-building me-1"></i> <c:out value="${pet.shelterName}" />
                                            </span>
                                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-paw-primary btn-sm">
                                                Meet ${pet.name} <i class="bi bi-chevron-right"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const breedsMap = ${not empty speciesBreedsJson ? speciesBreedsJson : '{}'};
        const currentBreed = '<c:out value="${breed}" />';
        initSpeciesBreedDropdown('speciesSelect', 'breedSelect', currentBreed, breedsMap, false);
    });
</script>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
