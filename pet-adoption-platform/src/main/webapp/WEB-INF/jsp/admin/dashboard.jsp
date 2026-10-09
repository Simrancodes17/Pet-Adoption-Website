<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Administrator Control Panel" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <!-- Header Title -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Platform Administration Overview</h3>
            <p class="text-muted small mb-0">System metrics, pending shelter listings, user management, and configuration</p>
        </div>
        <div class="d-flex gap-2 mt-2 mt-md-0">
            <a href="${pageContext.request.contextPath}/admin/pets" class="btn btn-warning btn-sm">
                <i class="bi bi-clock-history me-1"></i> Review Pet Approvals
            </a>
            <a href="${pageContext.request.contextPath}/admin/settings" class="btn btn-dark btn-sm">
                <i class="bi bi-gear-fill me-1"></i> System Settings
            </a>
        </div>
    </div>

    <!-- Analytics Key Metric Cards -->
    <div class="row g-3 mb-4">
        <div class="col-lg-3 col-sm-6">
            <div class="metric-box metric-purple shadow-sm">
                <div class="fs-6 opacity-75">Total Users</div>
                <div class="display-6 fw-bold">${analytics.totalUsers}</div>
                <div class="small opacity-75 mt-1">${analytics.totalShelters} Shelters &bull; ${analytics.totalAdopters} Adopters</div>
                <i class="bi bi-people-fill"></i>
            </div>
        </div>

        <div class="col-lg-3 col-sm-6">
            <div class="metric-box metric-coral shadow-sm">
                <div class="fs-6 opacity-75">Pending Pet Approvals</div>
                <div class="display-6 fw-bold">${analytics.totalPendingApprovalPets}</div>
                <div class="small opacity-75 mt-1">Requires Administrator review</div>
                <i class="bi bi-shield-exclamation"></i>
            </div>
        </div>

        <div class="col-lg-3 col-sm-6">
            <div class="metric-box metric-green shadow-sm">
                <div class="fs-6 opacity-75">Successful Adoptions</div>
                <div class="display-6 fw-bold">${analytics.totalAdoptedPets}</div>
                <div class="small opacity-75 mt-1">Happily placed companions</div>
                <i class="bi bi-heart-fill"></i>
            </div>
        </div>

        <div class="col-lg-3 col-sm-6">
            <div class="metric-box metric-blue shadow-sm">
                <div class="fs-6 opacity-75">Total Active Listings</div>
                <div class="display-6 fw-bold">${analytics.totalAvailablePets}</div>
                <div class="small opacity-75 mt-1">Visible to prospective adopters</div>
                <i class="bi bi-search-heart"></i>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <!-- Pending Listing Approval Queue -->
        <div class="col-lg-7">
            <div class="card card-paw shadow-sm h-100">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-hourglass-split text-warning me-1"></i> Pending Shelter Listings (${pendingPets.size()})
                    </h5>
                    <a href="${pageContext.request.contextPath}/admin/pets" class="btn btn-outline-primary btn-sm">Full Approval Queue</a>
                </div>
                <div class="card-body p-0">
                    <c:choose>
                        <c:when test="${empty pendingPets}">
                            <div class="p-4 text-center text-muted">
                                <i class="bi bi-check2-all text-success fs-1"></i>
                                <p class="mb-0 mt-2">All shelter listings are currently reviewed and approved!</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover align-middle mb-0">
                                    <thead class="table-light small">
                                        <tr>
                                            <th>Pet</th>
                                            <th>Shelter</th>
                                            <th>Breed &amp; Age</th>
                                            <th class="text-end">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="pet" items="${pendingPets}">
                                            <tr>
                                                <td>
                                                    <strong><c:out value="${pet.name}" /></strong>
                                                    <span class="badge bg-light text-dark border ms-1"><c:out value="${pet.type}" /></span>
                                                </td>
                                                <td class="small text-muted"><c:out value="${pet.shelterName}" /></td>
                                                <td class="small"><c:out value="${pet.breed}" />, ${pet.age} yrs</td>
                                                <td class="text-end">
                                                    <form action="${pageContext.request.contextPath}/admin/pets/approve" method="post" class="d-inline">
                                                        <input type="hidden" name="id" value="${pet.id}">
                                                        <button type="submit" class="btn btn-sm btn-success px-2 py-1" title="Approve Listing">
                                                            <i class="bi bi-check-lg"></i> Approve
                                                        </button>
                                                    </form>
                                                    <form action="${pageContext.request.contextPath}/admin/pets/reject" method="post" class="d-inline">
                                                        <input type="hidden" name="id" value="${pet.id}">
                                                        <button type="submit" class="btn btn-sm btn-outline-danger px-2 py-1 confirm-action"
                                                                data-confirm="Reject this listing?" title="Reject Listing">
                                                            <i class="bi bi-x-lg"></i>
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
        </div>

        <!-- Recent Users & Role Overview -->
        <div class="col-lg-5">
            <div class="card card-paw shadow-sm h-100">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0"><i class="bi bi-people text-primary me-1"></i> User Accounts</h5>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-primary btn-sm">Manage Users</a>
                </div>
                <div class="card-body p-0">
                    <ul class="list-group list-group-flush">
                        <c:forEach var="u" items="${recentUsers}">
                            <li class="list-group-item d-flex justify-content-between align-items-center py-2 px-3">
                                <div>
                                    <div class="fw-semibold small"><c:out value="${u.name}" /></div>
                                    <div class="text-muted" style="font-size: 0.75rem;"><c:out value="${u.email}" /></div>
                                </div>
                                <span class="badge ${u.role == 'ADMIN' ? 'bg-danger' : (u.role == 'SHELTER' ? 'bg-primary' : 'bg-success')} rounded-pill small">
                                    <c:out value="${u.role}" />
                                </span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
