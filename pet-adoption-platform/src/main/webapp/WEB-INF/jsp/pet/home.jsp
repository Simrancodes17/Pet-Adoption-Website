<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Find Your Forever Friend" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<!-- Hero Section -->
<div class="hero-section">
    <div class="container text-center">
        <h1 class="display-4 fw-bold mb-3">Every Pet Deserves a Loving Home</h1>
        <p class="lead mb-4 col-lg-8 mx-auto opacity-90">
            Browse verified adoptable animals from certified rescue shelters and compassionate caretakers. Find your loyal companion today.
        </p>

        <!-- Quick Search Form -->
        <div class="row justify-content-center">
            <div class="col-lg-9">
                <div class="hero-search-card">
                    <form action="${pageContext.request.contextPath}/search" method="get" class="row g-2 align-items-center">
                        <div class="col-md-5">
                            <div class="input-group">
                                <span class="input-group-text bg-white border-end-0"><i class="bi bi-search text-muted"></i></span>
                                <input type="text" name="q" class="form-control border-start-0" placeholder="Search by name, breed, or traits...">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <select name="type" class="form-select">
                                <option value="ALL">All Pet Types</option>
                                <option value="Dog">Dogs</option>
                                <option value="Cat">Cats</option>
                                <option value="Rabbit">Rabbits</option>
                                <option value="Bird">Birds</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <input type="text" name="location" class="form-control" placeholder="City or State">
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-paw-primary w-100">
                                Find Pets
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Platform Impact Metrics -->
<div class="container mt-n4 mb-5">
    <div class="row g-3 justify-content-center text-center">
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-top border-4 border-success">
                <div class="display-6 fw-bold text-success">${analytics.totalAdoptedPets}</div>
                <div class="text-muted small fw-semibold">Successful Adoptions</div>
            </div>
        </div>
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-top border-4 border-primary">
                <div class="display-6 fw-bold text-primary">${analytics.totalAvailablePets}</div>
                <div class="text-muted small fw-semibold">Pets Awaiting Homes</div>
            </div>
        </div>
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-top border-4 border-info">
                <div class="display-6 fw-bold text-info">${analytics.totalShelters}</div>
                <div class="text-muted small fw-semibold">Partner Shelters</div>
            </div>
        </div>
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-top border-4 border-warning">
                <div class="display-6 fw-bold text-warning">${analytics.totalApplications}</div>
                <div class="text-muted small fw-semibold">Applications Processed</div>
            </div>
        </div>
    </div>
</div>

<!-- Category Shortcuts -->
<div class="container my-5">
    <div class="text-center mb-4">
        <h3 class="fw-bold">Browse By Companion Type</h3>
        <p class="text-muted small">Explore adoptable pets categorized by species</p>
    </div>

    <div class="row g-3 text-center justify-content-center">
        <div class="col-lg-2 col-md-3 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Dog" class="text-decoration-none">
                <div class="card card-paw p-3">
                    <div class="display-5 text-primary mb-2"><i class="bi bi-emoji-smile"></i></div>
                    <h6 class="fw-bold text-dark mb-0">Dogs</h6>
                </div>
            </a>
        </div>
        <div class="col-lg-2 col-md-3 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Cat" class="text-decoration-none">
                <div class="card card-paw p-3">
                    <div class="display-5 text-success mb-2"><i class="bi bi-moon-stars"></i></div>
                    <h6 class="fw-bold text-dark mb-0">Cats</h6>
                </div>
            </a>
        </div>
        <div class="col-lg-2 col-md-3 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Rabbit" class="text-decoration-none">
                <div class="card card-paw p-3">
                    <div class="display-5 text-warning mb-2"><i class="bi bi-flower1"></i></div>
                    <h6 class="fw-bold text-dark mb-0">Rabbits</h6>
                </div>
            </a>
        </div>
        <div class="col-lg-2 col-md-3 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Bird" class="text-decoration-none">
                <div class="card card-paw p-3">
                    <div class="display-5 text-info mb-2"><i class="bi bi-soundwave"></i></div>
                    <h6 class="fw-bold text-dark mb-0">Birds</h6>
                </div>
            </a>
        </div>
    </div>
</div>

<!-- Featured Adoptable Pets -->
<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Featured Available Pets</h3>
            <p class="text-muted small mb-0">Meet these loving companions looking for forever homes</p>
        </div>
        <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-outline btn-sm">
            View All Pets <i class="bi bi-arrow-right"></i>
        </a>
    </div>

    <div class="row g-4">
        <c:forEach var="pet" items="${featuredPets}">
            <div class="col-lg-4 col-md-6">
                <div class="card card-paw h-100">
                    <div class="pet-card-img-wrapper">
                        <c:choose>
                            <c:when test="${pet.photoPath.startsWith('http')}">
                                <img src="${pet.photoPath}" class="pet-card-img" alt="${pet.name}">
                            </c:when>
                            <c:otherwise>
                                <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80"
                                     class="pet-card-img" alt="${pet.name}">
                            </c:otherwise>
                        </c:choose>
                        <span class="badge badge-available position-absolute top-0 end-0 m-3 px-3 py-2 rounded-pill shadow-sm">
                            <i class="bi bi-check-circle me-1"></i> Available
                        </span>
                    </div>
                    <div class="card-body d-flex flex-column">
                        <div class="d-flex justify-content-between align-items-start mb-2">
                            <h5 class="card-title fw-bold mb-0 text-dark"><c:out value="${pet.name}" /></h5>
                            <span class="badge bg-light text-dark border"><c:out value="${pet.type}" /></span>
                        </div>
                        <div class="text-muted small mb-2">
                            <i class="bi bi-tag-fill me-1 text-danger"></i> <c:out value="${pet.breed}" /> &bull; ${pet.age} yrs old
                        </div>
                        <div class="text-muted small mb-3">
                            <i class="bi bi-geo-alt-fill me-1 text-primary"></i> <c:out value="${pet.location}" />
                        </div>
                        <p class="card-text small text-secondary flex-grow-1">
                            <c:out value="${pet.description.length() > 90 ? pet.description.substring(0, 90).concat('...') : pet.description}" />
                        </p>
                        <div class="pt-3 border-top d-flex justify-content-between align-items-center mt-auto">
                            <span class="small text-muted">
                                <i class="bi bi-building"></i> <c:out value="${pet.shelterName}" />
                            </span>
                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-paw-primary btn-sm">
                                View Profile <i class="bi bi-chevron-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
