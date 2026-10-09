<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${pet.name} - Adoptable Companion" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb small">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/search" class="text-decoration-none">Browse Pets</a></li>
            <li class="breadcrumb-item active" aria-current="page"><c:out value="${pet.name}" /></li>
        </ol>
    </nav>

    <div class="row g-5">
        <!-- Photo and Visual Highlights -->
        <div class="col-lg-6">
            <div class="pet-details-gallery-card">
                <c:choose>
                    <c:when test="${pet.photoPath.startsWith('http')}">
                        <img src="${pet.photoPath}" class="pet-details-main-img pet-card-img" alt="${pet.name}" data-species="${pet.type}">
                    </c:when>
                    <c:otherwise>
                        <c:set var="detailFbUrl" value="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=800&q=80" />
                        <c:if test="${pet.type == 'Cat'}"><c:set var="detailFbUrl" value="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=800&q=80" /></c:if>
                        <c:if test="${pet.type == 'Rabbit'}"><c:set var="detailFbUrl" value="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=800&q=80" /></c:if>
                        <c:if test="${pet.type == 'Bird'}"><c:set var="detailFbUrl" value="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=800&q=80" /></c:if>
                        <c:if test="${pet.type == 'Hamster'}"><c:set var="detailFbUrl" value="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=800&q=80" /></c:if>
                        <c:if test="${pet.type == 'Turtle'}"><c:set var="detailFbUrl" value="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=800&q=80" /></c:if>
                        <img src="${detailFbUrl}" class="pet-details-main-img pet-card-img" alt="${pet.name}" data-species="${pet.type}">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Pet Profile Details -->
        <div class="col-lg-6">
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-2">
                <h1 class="display-5 fw-bold text-dark mb-0"><c:out value="${pet.name}" /></h1>
                <span class="badge ${pet.adoptionStatus == 'AVAILABLE' ? 'badge-available' : (pet.adoptionStatus == 'PENDING' ? 'badge-pending' : 'badge-adopted')} fs-6 px-3 py-2 rounded-pill">
                    <c:out value="${pet.adoptionStatus}" />
                </span>
            </div>

            <div class="d-flex align-items-center gap-2 mb-4">
                <span class="badge bg-primary-subtle text-primary border border-primary-subtle fs-6 px-3 py-1 rounded-pill"><c:out value="${pet.type}" /></span>
                <span class="text-secondary fs-5">&bull;</span>
                <span class="fs-5 fw-semibold text-dark"><c:out value="${pet.breed}" /></span>
                <span class="text-secondary fs-5">&bull;</span>
                <span class="fs-5 text-muted">${pet.age} ${pet.age == 1 ? 'Year' : 'Years'} Old</span>
            </div>

            <!-- Specs Grid -->
            <div class="row g-3 mb-4">
                <div class="col-4">
                    <div class="spec-tile">
                        <div class="spec-tile-icon"><i class="bi bi-geo-alt-fill text-danger"></i></div>
                        <div class="spec-tile-label">Location</div>
                        <div class="spec-tile-value text-truncate"><c:out value="${pet.location}" /></div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="spec-tile">
                        <div class="spec-tile-icon">
                            <i class="bi ${pet.gender == 'Male' ? 'bi-gender-male text-primary' : (pet.gender == 'Female' ? 'bi-gender-female text-danger' : 'bi-gender-ambiguous text-secondary')}"></i>
                        </div>
                        <div class="spec-tile-label">Gender</div>
                        <div class="spec-tile-value"><c:out value="${pet.gender != null ? pet.gender : 'Unknown'}" /></div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="spec-tile">
                        <div class="spec-tile-icon"><i class="bi bi-calendar-check text-success"></i></div>
                        <div class="spec-tile-label">Listed</div>
                        <div class="spec-tile-value text-truncate"><c:out value="${pet.createdAt}" /></div>
                    </div>
                </div>
            </div>

            <!-- About Section -->
            <div class="mb-4">
                <h5 class="fw-bold text-dark mb-2">About <c:out value="${pet.name}" /></h5>
                <div class="p-3 bg-white rounded-3 shadow-sm border">
                    <p class="text-secondary mb-0" style="white-space: pre-line; line-height: 1.7;">
                        <c:out value="${pet.description}" />
                    </p>
                </div>
            </div>

            <!-- Shelter / Rescue Info -->
            <div class="shelter-box-card mb-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="bg-primary text-white rounded-3 p-3 display-6 d-inline-flex align-items-center justify-content-center" style="width: 52px; height: 52px;">
                        <i class="bi bi-building"></i>
                    </div>
                    <div>
                        <div class="small text-muted text-uppercase fw-bold" style="font-size: 0.72rem; letter-spacing: 0.05em;">Caretaker Shelter</div>
                        <h6 class="fw-bold mb-0 text-dark"><c:out value="${pet.shelterName}" /></h6>
                        <span class="small text-muted"><c:out value="${pet.shelterEmail}" /></span>
                    </div>
                </div>
            </div>

            <!-- Adoption Action Controls -->
            <div class="d-grid gap-2">
                <c:choose>
                    <c:when test="${pet.adoptionStatus != 'AVAILABLE'}">
                        <button class="btn btn-secondary py-3 fw-bold disabled" disabled>
                            <i class="bi bi-slash-circle me-1"></i> Not Currently Available for Applications
                        </button>
                    </c:when>
                    <c:when test="${empty sessionScope.currentUser}">
                        <a href="${pageContext.request.contextPath}/applications/apply?petId=${pet.id}" class="btn btn-paw-secondary btn-adopt-cta py-3 fw-bold shadow">
                            <i class="bi bi-heart-fill me-1"></i> Apply to Adopt <c:out value="${pet.name}" />
                        </a>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.role == 'ADOPTER'}">
                        <a href="${pageContext.request.contextPath}/applications/apply?petId=${pet.id}" class="btn btn-paw-secondary btn-adopt-cta py-3 fw-bold shadow">
                            <i class="bi bi-heart-fill me-1"></i> Submit Adoption Application
                        </a>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.role == 'SHELTER' and sessionScope.currentUser.id == pet.shelterId}">
                        <a href="${pageContext.request.contextPath}/shelter/pets/edit?id=${pet.id}" class="btn btn-warning py-3 fw-bold">
                            <i class="bi bi-pencil-square me-1"></i> Edit This Pet Listing
                        </a>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info small mb-0">
                            <i class="bi bi-info-circle me-1"></i> Logged in as ${sessionScope.currentUser.role}.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
