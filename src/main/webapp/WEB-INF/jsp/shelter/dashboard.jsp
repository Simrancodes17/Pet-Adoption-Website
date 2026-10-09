<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Shelter Operations Dashboard" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <!-- Shelter Header -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <div class="d-flex align-items-center gap-2 mb-1">
                <span class="badge bg-primary-subtle text-primary border border-primary-subtle rounded-pill px-3 py-1 fw-bold small text-uppercase">Shelter Operations</span>
                <span class="text-muted small">&bull; ${sessionScope.currentUser.email}</span>
            </div>
            <h2 class="fw-bold mb-0 text-dark">${sessionScope.currentUser.name}</h2>
            <p class="text-muted small mb-0"><i class="bi bi-geo-alt-fill text-danger me-1"></i> ${sessionScope.currentUser.contactInfo}</p>
        </div>
        <div class="d-flex gap-2 mt-3 mt-md-0">
            <a href="${pageContext.request.contextPath}/shelter/pets/create" class="btn btn-paw-primary btn-sm">
                <i class="bi bi-plus-circle-fill me-1"></i> List New Pet
            </a>
            <a href="${pageContext.request.contextPath}/shelter/applications" class="btn btn-outline-primary btn-sm">
                <i class="bi bi-journal-check me-1"></i> Applications
            </a>
            <a href="${pageContext.request.contextPath}/messages" class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-chat-dots me-1"></i> Messages
            </a>
        </div>
    </div>

    <!-- Shelter Operational Stats -->
    <div class="row g-3 mb-4">
        <div class="col-md-3 col-6">
            <div class="metric-box metric-teal shadow-sm">
                <div class="fs-6 opacity-75">Total Listed Pets</div>
                <div class="display-6 fw-bold">${stats.totalPets}</div>
                <div class="small opacity-75 mt-1">${stats.availablePets} Available &bull; ${stats.pendingApprovalPets} In Review</div>
                <i class="bi bi-card-list"></i>
            </div>
        </div>

        <div class="col-md-3 col-6">
            <div class="metric-box metric-coral shadow-sm">
                <div class="fs-6 opacity-75">Pending Applications</div>
                <div class="display-6 fw-bold">${stats.pendingApplications}</div>
                <div class="small opacity-75 mt-1">Requires your review</div>
                <i class="bi bi-hourglass-split"></i>
            </div>
        </div>

        <div class="col-md-3 col-6">
            <div class="metric-box metric-green shadow-sm">
                <div class="fs-6 opacity-75">Adoptions Finalized</div>
                <div class="display-6 fw-bold">${stats.adoptedPets}</div>
                <div class="small opacity-75 mt-1">Pets in forever homes</div>
                <i class="bi bi-award-fill"></i>
            </div>
        </div>

        <div class="col-md-3 col-6">
            <div class="metric-box metric-purple shadow-sm">
                <div class="fs-6 opacity-75">Total Applications</div>
                <div class="display-6 fw-bold">${stats.totalApplications}</div>
                <div class="small opacity-75 mt-1">Adopter interest received</div>
                <i class="bi bi-file-earmark-person"></i>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <!-- Shelter's Active Pet Listings -->
        <div class="col-lg-7">
            <div class="card card-paw shadow-sm h-100 border-0">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center border-bottom">
                    <h5 class="fw-bold mb-0 text-dark"><i class="bi bi-card-heading text-primary me-2"></i> Current Pet Listings</h5>
                    <a href="${pageContext.request.contextPath}/shelter/pets" class="btn btn-outline-primary btn-sm">View All (${pets.size()})</a>
                </div>
                <div class="card-body p-0">
                    <c:choose>
                        <c:when test="${empty pets}">
                            <div class="p-4 text-center text-muted">
                                <p class="mb-2">You have not listed any pets yet.</p>
                                <a href="${pageContext.request.contextPath}/shelter/pets/create" class="btn btn-paw-primary btn-sm">List First Pet</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-paw table-hover align-middle mb-0">
                                    <thead>
                                        <tr>
                                            <th>Pet</th>
                                            <th>Species &amp; Breed</th>
                                            <th>Status</th>
                                            <th>Approval</th>
                                            <th class="text-end">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="p" items="${pets}">
                                            <tr>
                                                <td>
                                                    <strong class="text-dark"><c:out value="${p.name}" /></strong>
                                                    <span class="small text-muted">(${p.age} yrs)</span>
                                                </td>
                                                <td class="small">
                                                    <span class="fw-semibold"><c:out value="${p.type}" /></span> &bull; <c:out value="${p.breed}" />
                                                </td>
                                                <td>
                                                    <span class="badge ${p.adoptionStatus == 'AVAILABLE' ? 'badge-available' : (p.adoptionStatus == 'PENDING' ? 'badge-pending' : 'badge-adopted')} rounded-pill small">
                                                        <c:out value="${p.adoptionStatus}" />
                                                    </span>
                                                </td>
                                                <td>
                                                    <span class="badge ${p.approvalStatus == 'APPROVED' ? 'badge-approved' : 'badge-pending'} rounded-pill small">
                                                        <c:out value="${p.approvalStatus}" />
                                                    </span>
                                                </td>
                                                <td class="text-end">
                                                    <a href="${pageContext.request.contextPath}/shelter/pets/edit?id=${p.id}" class="btn btn-sm btn-outline-secondary py-1 px-2" title="Edit">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/pets/details?id=${p.id}" class="btn btn-sm btn-outline-primary py-1 px-2" title="View Public Profile">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Recent Adoption Applications Received -->
        <div class="col-lg-5">
            <div class="card card-paw shadow-sm h-100 border-0">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center border-bottom">
                    <h5 class="fw-bold mb-0 text-dark"><i class="bi bi-journal-text text-danger me-2"></i> Recent Applications</h5>
                    <a href="${pageContext.request.contextPath}/shelter/applications" class="btn btn-outline-danger btn-sm">All Applications</a>
                </div>
                <div class="card-body p-0">
                    <c:choose>
                        <c:when test="${empty applications}">
                            <div class="p-4 text-center text-muted">
                                <p class="mb-0">No adoption applications submitted yet.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <ul class="list-group list-group-flush">
                                <c:forEach var="app" items="${applications}">
                                    <li class="list-group-item p-3">
                                        <div class="d-flex justify-content-between align-items-start mb-1">
                                            <div>
                                                <strong class="text-dark"><c:out value="${app.adopterName}" /></strong>
                                                <div class="small text-muted">For Pet: <strong><c:out value="${app.petName}" /></strong></div>
                                            </div>
                                            <span class="badge ${app.status == 'PENDING' ? 'badge-pending' : (app.status == 'APPROVED' ? 'badge-approved' : 'badge-rejected')} rounded-pill">
                                                <c:out value="${app.status}" />
                                            </span>
                                        </div>
                                        <div class="small text-secondary mb-2" style="font-size: 0.82rem;">
                                            <c:out value="${app.details.length() > 60 ? app.details.substring(0, 60).concat('...') : app.details}" />
                                        </div>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <span class="text-muted" style="font-size: 0.75rem;"><c:out value="${app.createdAt}" /></span>
                                            <a href="${pageContext.request.contextPath}/shelter/applications" class="btn btn-sm btn-outline-primary py-0 px-2" style="font-size: 0.75rem;">
                                                Review &raquo;
                                            </a>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
