<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${pet.name} - Adoptable Pet" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/search">Browse Pets</a></li>
            <li class="breadcrumb-item active" aria-current="page"><c:out value="${pet.name}" /></li>
        </ol>
    </nav>

    <div class="row g-5">
        <!-- Photo and Visual Highlights -->
        <div class="col-lg-6">
            <div class="card card-paw overflow-hidden shadow">
                <c:choose>
                    <c:when test="${pet.photoPath.startsWith('http')}">
                        <img src="${pet.photoPath}" class="img-fluid w-100" style="max-height: 480px; object-fit: cover;" alt="${pet.name}">
                    </c:when>
                    <c:otherwise>
                        <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=800&q=80"
                             class="img-fluid w-100" style="max-height: 480px; object-fit: cover;" alt="${pet.name}">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Pet Profile Details -->
        <div class="col-lg-6">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h1 class="display-5 fw-bold text-dark mb-0"><c:out value="${pet.name}" /></h1>
                <span class="badge ${pet.adoptionStatus == 'AVAILABLE' ? 'badge-available' : (pet.adoptionStatus == 'PENDING' ? 'badge-pending' : 'badge-adopted')} fs-6 px-3 py-2 rounded-pill">
                    <c:out value="${pet.adoptionStatus}" />
                </span>
            </div>

            <p class="text-muted fs-5 mb-4">
                <span class="badge bg-light text-dark border me-2"><c:out value="${pet.type}" /></span>
                <c:out value="${pet.breed}" /> &bull; ${pet.age} Years Old
            </p>

            <div class="row g-3 mb-4">
                <div class="col-6">
                    <div class="p-3 bg-white rounded-3 shadow-sm border">
                        <div class="small text-muted"><i class="bi bi-geo-alt-fill text-danger"></i> Location</div>
                        <div class="fw-semibold text-dark"><c:out value="${pet.location}" /></div>
                    </div>
                </div>
                <div class="col-6">
                    <div class="p-3 bg-white rounded-3 shadow-sm border">
                        <div class="small text-muted"><i class="bi bi-clock-history text-primary"></i> Listed On</div>
                        <div class="fw-semibold text-dark"><c:out value="${pet.createdAt}" /></div>
                    </div>
                </div>
            </div>

            <h5 class="fw-bold mb-2">About <c:out value="${pet.name}" /></h5>
            <div class="p-3 bg-white rounded-3 shadow-sm border mb-4">
                <p class="text-secondary mb-0" style="white-space: pre-line;">
                    <c:out value="${pet.description}" />
                </p>
            </div>

            <!-- Shelter / Rescue Info -->
            <div class="card bg-light border p-3 mb-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="display-6 text-primary"><i class="bi bi-building"></i></div>
                    <div>
                        <div class="small text-muted text-uppercase fw-semibold">Shelter Organization</div>
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
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-paw-primary py-3 fw-bold shadow">
                            <i class="bi bi-box-arrow-in-right me-1"></i> Sign In to Apply for Adoption
                        </a>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.role == 'ADOPTER'}">
                        <a href="${pageContext.request.contextPath}/applications/apply?petId=${pet.id}" class="btn btn-paw-primary py-3 fw-bold shadow">
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
