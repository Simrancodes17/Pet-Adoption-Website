<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Shelter Pet Listings" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Shelter Pet Listings</h3>
            <p class="text-muted small mb-0">Manage adoptable animals under your shelter's care</p>
        </div>
        <a href="${pageContext.request.contextPath}/shelter/pets/create" class="btn btn-paw-primary btn-sm">
            <i class="bi bi-plus-circle-fill me-1"></i> Add New Pet Listing
        </a>
    </div>

    <div class="card card-paw shadow-sm">
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty pets}">
                    <div class="p-5 text-center text-muted">
                        <i class="bi bi-card-list fs-1 text-secondary"></i>
                        <h5 class="mt-3">No pet listings registered yet</h5>
                        <p class="small mb-3">Begin by listing animals ready for loving adopters.</p>
                        <a href="${pageContext.request.contextPath}/shelter/pets/create" class="btn btn-paw-primary btn-sm">Create First Listing</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light small">
                                <tr>
                                    <th>Pet Photo &amp; Name</th>
                                    <th>Species &amp; Breed</th>
                                    <th>Age &amp; Location</th>
                                    <th>Adoption Status</th>
                                    <th>Admin Approval</th>
                                    <th class="text-end">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="p" items="${pets}">
                                    <tr>
                                        <td>
                                            <div class="d-flex align-items-center gap-3">
                                                <c:choose>
                                                    <c:when test="${p.photoPath.startsWith('http')}">
                                                        <img src="${p.photoPath}" class="rounded-3" style="width: 45px; height: 45px; object-fit: cover;" alt="${p.name}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=150&q=80"
                                                             class="rounded-3" style="width: 45px; height: 45px; object-fit: cover;" alt="${p.name}">
                                                    </c:otherwise>
                                                </c:choose>
                                                <div>
                                                    <strong><c:out value="${p.name}" /></strong>
                                                    <div class="text-muted" style="font-size: 0.75rem;">ID #${p.id}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="small">
                                            <div><c:out value="${p.type}" /></div>
                                            <span class="text-muted"><c:out value="${p.breed}" /></span>
                                        </td>
                                        <td class="small">
                                            <div>${p.age} years old</div>
                                            <span class="text-muted"><c:out value="${p.location}" /></span>
                                        </td>
                                        <td>
                                            <span class="badge ${p.adoptionStatus == 'AVAILABLE' ? 'badge-available' : (p.adoptionStatus == 'PENDING' ? 'badge-pending' : 'badge-adopted')} rounded-pill">
                                                <c:out value="${p.adoptionStatus}" />
                                            </span>
                                        </td>
                                        <td>
                                            <span class="badge ${p.approvalStatus == 'APPROVED' ? 'bg-success' : (p.approvalStatus == 'PENDING' ? 'bg-warning text-dark' : 'bg-danger')} rounded-pill">
                                                <c:out value="${p.approvalStatus}" />
                                            </span>
                                        </td>
                                        <td class="text-end">
                                            <a href="${pageContext.request.contextPath}/pets/details?id=${p.id}" class="btn btn-sm btn-outline-info py-1 px-2" title="View Public Page">
                                                <i class="bi bi-eye"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/shelter/pets/edit?id=${p.id}" class="btn btn-sm btn-outline-secondary py-1 px-2" title="Edit">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <form action="${pageContext.request.contextPath}/shelter/pets/delete" method="post" class="d-inline">
                                                <input type="hidden" name="id" value="${p.id}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger py-1 px-2 confirm-action"
                                                        data-confirm="Delete pet listing '${p.name}'?" title="Delete Listing">
                                                    <i class="bi bi-trash"></i>
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

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
