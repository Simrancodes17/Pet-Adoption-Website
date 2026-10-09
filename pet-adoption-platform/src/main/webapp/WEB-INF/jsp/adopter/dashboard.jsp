<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Adopter Dashboard" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <!-- Header -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Welcome, ${sessionScope.currentUser.name}</h3>
            <p class="text-muted small mb-0">Track your adoption applications, update your profile, and browse adoptable companions</p>
        </div>
        <div class="d-flex gap-2 mt-2 mt-md-0">
            <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary btn-sm">
                <i class="bi bi-search me-1"></i> Browse Pets
            </a>
            <a href="${pageContext.request.contextPath}/adopter/applications" class="btn btn-outline-primary btn-sm">
                <i class="bi bi-journal-check me-1"></i> My Applications (${applications.size()})
            </a>
            <a href="${pageContext.request.contextPath}/messages" class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-chat-dots me-1"></i> Messages
            </a>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <!-- Application Status Overview -->
        <div class="col-lg-8">
            <div class="card card-paw shadow-sm h-100">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-file-earmark-check text-primary me-2"></i> Active Adoption Applications
                    </h5>
                    <a href="${pageContext.request.contextPath}/adopter/applications" class="btn btn-outline-primary btn-sm">
                        View History
                    </a>
                </div>
                <div class="card-body p-0">
                    <c:choose>
                        <c:when test="${empty applications}">
                            <div class="p-5 text-center text-muted">
                                <i class="bi bi-search-heart fs-1 text-danger"></i>
                                <h5 class="mt-3">You haven't submitted any applications yet</h5>
                                <p class="small mb-3">Browse our listings to find your ideal companion and submit an adoption inquiry.</p>
                                <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary btn-sm">
                                    <i class="bi bi-search"></i> Browse Adoptable Pets
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover align-middle mb-0">
                                    <thead class="table-light small">
                                        <tr>
                                            <th>Application</th>
                                            <th>Pet Name &amp; Breed</th>
                                            <th>Shelter</th>
                                            <th>Current Status</th>
                                            <th class="text-end">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="app" items="${applications}">
                                            <tr>
                                                <td><strong>#${app.id}</strong></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/pets/details?id=${app.petId}" class="fw-bold text-decoration-none">
                                                        <c:out value="${app.petName}" />
                                                    </a>
                                                    <div class="small text-muted"><c:out value="${app.petBreed}" /></div>
                                                </td>
                                                <td class="small text-muted"><c:out value="${app.shelterName}" /></td>
                                                <td>
                                                    <span class="badge ${app.status == 'APPROVED' ? 'bg-success' : (app.status == 'PENDING' ? 'bg-warning text-dark' : (app.status == 'REJECTED' ? 'bg-danger' : 'bg-secondary'))} rounded-pill">
                                                        <c:out value="${app.status}" />
                                                    </span>
                                                </td>
                                                <td class="text-end">
                                                    <a href="${pageContext.request.contextPath}/messages?recipientId=${app.shelterId}&applicationId=${app.id}"
                                                       class="btn btn-sm btn-outline-primary py-1 px-2" title="Chat with Shelter">
                                                        <i class="bi bi-chat-dots"></i> Message
                                                    </a>
                                                    <c:if test="${app.status == 'PENDING'}">
                                                        <form action="${pageContext.request.contextPath}/applications/cancel" method="post" class="d-inline">
                                                            <input type="hidden" name="id" value="${app.id}">
                                                            <button type="submit" class="btn btn-sm btn-outline-danger py-1 px-2 confirm-action"
                                                                    data-confirm="Cancel this adoption application?" title="Cancel Application">
                                                                <i class="bi bi-x-circle"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>
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

        <!-- Adopter Profile Management -->
        <div class="col-lg-4" id="profile">
            <div class="card card-paw shadow-sm h-100">
                <div class="card-header bg-white py-3 border-bottom">
                    <h5 class="fw-bold mb-0"><i class="bi bi-person-circle text-danger me-2"></i> Profile Management</h5>
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/adopter/dashboard" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label small fw-semibold">Your Name</label>
                            <input type="text" class="form-control" id="name" name="name"
                                   value="<c:out value='${user.name}' />" required>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label small fw-semibold">Email Address</label>
                            <input type="email" class="form-control" id="email" value="<c:out value='${user.email}' />" disabled>
                            <div class="form-text" style="font-size: 0.75rem;">Email cannot be modified directly.</div>
                        </div>

                        <div class="mb-4">
                            <label for="contactInfo" class="form-label small fw-semibold">Contact Phone &amp; Address</label>
                            <textarea class="form-control" id="contactInfo" name="contactInfo" rows="3"
                                      placeholder="City, State, Phone"><c:out value='${user.contactInfo}' /></textarea>
                        </div>

                        <button type="submit" class="btn btn-paw-primary w-100 py-2">
                            <i class="bi bi-check2-circle me-1"></i> Update Profile
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
