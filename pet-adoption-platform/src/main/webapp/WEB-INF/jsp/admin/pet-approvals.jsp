<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Pet Listing Approvals" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="mb-4">
        <h3 class="fw-bold mb-0">Pet Listing Verification &amp; Approvals</h3>
        <p class="text-muted small mb-0">Review shelter pet submissions before publishing them to public search</p>
    </div>

    <!-- Section 1: Pending Approvals Queue -->
    <div class="card card-paw shadow-sm mb-5">
        <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
            <h5 class="fw-bold mb-0 text-warning">
                <i class="bi bi-clock-history me-1"></i> Awaiting Review (${pendingPets.size()})
            </h5>
            <span class="badge bg-warning text-dark">Action Required</span>
        </div>
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty pendingPets}">
                    <div class="p-5 text-center text-muted">
                        <i class="bi bi-check-circle-fill text-success fs-1"></i>
                        <h5 class="mt-3">Approval queue is empty</h5>
                        <p class="small mb-0">All submitted pets have been reviewed and published.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light small">
                                <tr>
                                    <th>Pet Photo &amp; Details</th>
                                    <th>Shelter</th>
                                    <th>Location</th>
                                    <th>Description</th>
                                    <th class="text-end">Verification Decision</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="pet" items="${pendingPets}">
                                    <tr>
                                        <td style="min-width: 220px;">
                                            <div class="d-flex align-items-center gap-3">
                                                <c:choose>
                                                    <c:when test="${pet.photoPath.startsWith('http')}">
                                                        <img src="${pet.photoPath}" class="rounded-3" style="width: 50px; height: 50px; object-fit: cover;" alt="${pet.name}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=150&q=80"
                                                             class="rounded-3" style="width: 50px; height: 50px; object-fit: cover;" alt="${pet.name}">
                                                    </c:otherwise>
                                                </c:choose>
                                                <div>
                                                    <h6 class="fw-bold mb-0 text-dark"><c:out value="${pet.name}" /></h6>
                                                    <div class="small text-muted"><c:out value="${pet.type}" /> &bull; <c:out value="${pet.breed}" /> (${pet.age} yrs)</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="small fw-semibold"><c:out value="${pet.shelterName}" /></div>
                                            <div class="text-muted" style="font-size: 0.75rem;"><c:out value="${pet.shelterEmail}" /></div>
                                        </td>
                                        <td class="small"><c:out value="${pet.location}" /></td>
                                        <td class="small text-secondary" style="max-width: 250px;">
                                            <c:out value="${pet.description.length() > 90 ? pet.description.substring(0, 90).concat('...') : pet.description}" />
                                        </td>
                                        <td class="text-end" style="min-width: 170px;">
                                            <form action="${pageContext.request.contextPath}/admin/pets/approve" method="post" class="d-inline">
                                                <input type="hidden" name="id" value="${pet.id}">
                                                <button type="submit" class="btn btn-sm btn-success px-3 me-1">
                                                    <i class="bi bi-check-circle me-1"></i> Approve
                                                </button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/admin/pets/reject" method="post" class="d-inline">
                                                <input type="hidden" name="id" value="${pet.id}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger px-2 confirm-action"
                                                        data-confirm="Reject pet listing '${pet.name}'?">
                                                    <i class="bi bi-x-circle me-1"></i> Reject
                                                </button>
                                            </form>
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

    <!-- Section 2: All Platform Listings Status -->
    <div class="card card-paw shadow-sm">
        <div class="card-header bg-white py-3 border-bottom">
            <h5 class="fw-bold mb-0"><i class="bi bi-list-ul me-1"></i> All Platform Pet Listings</h5>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light small">
                        <tr>
                            <th>ID</th>
                            <th>Pet Name</th>
                            <th>Species &amp; Breed</th>
                            <th>Shelter</th>
                            <th>Adoption Status</th>
                            <th>Approval Status</th>
                            <th class="text-end">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pet" items="${allPets}">
                            <tr>
                                <td>#${pet.id}</td>
                                <td><strong><c:out value="${pet.name}" /></strong></td>
                                <td class="small"><c:out value="${pet.type}" /> - <c:out value="${pet.breed}" /></td>
                                <td class="small text-muted"><c:out value="${pet.shelterName}" /></td>
                                <td>
                                    <span class="badge ${pet.adoptionStatus == 'AVAILABLE' ? 'badge-available' : (pet.adoptionStatus == 'PENDING' ? 'badge-pending' : 'badge-adopted')} rounded-pill">
                                        <c:out value="${pet.adoptionStatus}" />
                                    </span>
                                </td>
                                <td>
                                    <span class="badge ${pet.approvalStatus == 'APPROVED' ? 'bg-success' : (pet.approvalStatus == 'PENDING' ? 'bg-warning text-dark' : 'bg-danger')} rounded-pill">
                                        <c:out value="${pet.approvalStatus}" />
                                    </span>
                                </td>
                                <td class="text-end">
                                    <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-sm btn-outline-primary py-1 px-2" title="View Profile">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
