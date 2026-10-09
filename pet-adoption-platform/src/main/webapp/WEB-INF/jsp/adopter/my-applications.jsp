<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Adoption Applications" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">My Adoption Application History</h3>
            <p class="text-muted small mb-0">Track real-time decision updates from rescue shelters</p>
        </div>
        <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary btn-sm">
            <i class="bi bi-search me-1"></i> Browse More Pets
        </a>
    </div>

    <div class="card card-paw shadow-sm">
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty applications}">
                    <div class="p-5 text-center text-muted">
                        <i class="bi bi-journal-text fs-1 text-secondary"></i>
                        <h5 class="mt-3">No applications found</h5>
                        <p class="small mb-3">You have not submitted any pet adoption applications yet.</p>
                        <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary btn-sm">Find Pets to Adopt</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light small">
                                <tr>
                                    <th>App #</th>
                                    <th>Target Pet</th>
                                    <th>Rescue Shelter</th>
                                    <th>Submitted Questionnaire Details</th>
                                    <th>Status</th>
                                    <th>Date</th>
                                    <th class="text-end">Actions</th>
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
                                            <div class="small text-muted"><c:out value="${app.petType}" /> &bull; <c:out value="${app.petBreed}" /></div>
                                        </td>
                                        <td>
                                            <div class="fw-semibold small"><c:out value="${app.shelterName}" /></div>
                                            <div class="text-muted" style="font-size: 0.75rem;"><c:out value="${app.shelterEmail}" /></div>
                                        </td>
                                        <td class="small text-secondary" style="max-width: 250px;">
                                            <c:out value="${app.details}" />
                                        </td>
                                        <td>
                                            <span class="badge ${app.status == 'APPROVED' ? 'bg-success' : (app.status == 'PENDING' ? 'bg-warning text-dark' : (app.status == 'REJECTED' ? 'bg-danger' : 'bg-secondary'))} rounded-pill">
                                                <c:out value="${app.status}" />
                                            </span>
                                        </td>
                                        <td class="small text-muted"><c:out value="${app.createdAt}" /></td>
                                        <td class="text-end" style="min-width: 140px;">
                                            <a href="${pageContext.request.contextPath}/messages?recipientId=${app.shelterId}&applicationId=${app.id}"
                                               class="btn btn-sm btn-outline-info py-1 px-2" title="Chat with Shelter">
                                                <i class="bi bi-chat-dots-fill"></i>
                                            </a>
                                            <c:if test="${app.status == 'PENDING'}">
                                                <form action="${pageContext.request.contextPath}/applications/cancel" method="post" class="d-inline">
                                                    <input type="hidden" name="id" value="${app.id}">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger py-1 px-2 confirm-action"
                                                            data-confirm="Are you sure you want to cancel your application for ${app.petName}?"
                                                            title="Cancel Application">
                                                        <i class="bi bi-x-circle"></i> Cancel
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

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
