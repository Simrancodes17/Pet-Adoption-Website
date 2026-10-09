<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Adoption Applications Review" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Adoption Application Management</h3>
            <p class="text-muted small mb-0">
                Review applicant questionnaires, message prospective adopters, and finalize adoption approvals
            </p>
        </div>
        <div class="small text-muted bg-white px-3 py-2 rounded shadow-sm border">
            <i class="bi bi-shield-lock-fill text-success me-1"></i> Concurrency-Safe Transactional Approvals
        </div>
    </div>

    <div class="card card-paw shadow-sm">
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty applications}">
                    <div class="p-5 text-center text-muted">
                        <i class="bi bi-inbox fs-1 text-secondary"></i>
                        <h5 class="mt-3">No adoption applications currently pending</h5>
                        <p class="small mb-0">Applications submitted by interested adopters will appear here for evaluation.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light small">
                                <tr>
                                    <th>Application ID</th>
                                    <th>Pet</th>
                                    <th>Applicant Name &amp; Email</th>
                                    <th>Application Details</th>
                                    <th>Status</th>
                                    <th>Received Date</th>
                                    <th class="text-end">Decision Actions</th>
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
                                            <div class="fw-semibold text-dark"><c:out value="${app.adopterName}" /></div>
                                            <div class="small text-muted"><c:out value="${app.adopterEmail}" /></div>
                                        </td>
                                        <td style="max-width: 280px;">
                                            <div class="small text-secondary text-truncate" title="<c:out value='${app.details}' />">
                                                <c:out value="${app.details}" />
                                            </div>
                                            <button type="button" class="btn btn-link btn-sm p-0 small text-decoration-none"
                                                    data-bs-toggle="modal" data-bs-target="#reviewModal"
                                                    data-app-id="${app.id}"
                                                    data-applicant-name="<c:out value='${app.adopterName}' />"
                                                    data-pet-name="<c:out value='${app.petName}' />"
                                                    data-details="<c:out value='${app.details}' />">
                                                Read Full Questionnaire &raquo;
                                            </button>
                                        </td>
                                        <td>
                                            <span class="badge ${app.status == 'APPROVED' ? 'bg-success' : (app.status == 'PENDING' ? 'bg-warning text-dark' : (app.status == 'REJECTED' ? 'bg-danger' : 'bg-secondary'))} rounded-pill">
                                                <c:out value="${app.status}" />
                                            </span>
                                        </td>
                                        <td class="small text-muted"><c:out value="${app.createdAt}" /></td>
                                        <td class="text-end" style="min-width: 210px;">
                                            <!-- Message Adopter -->
                                            <a href="${pageContext.request.contextPath}/messages?recipientId=${app.adopterId}&applicationId=${app.id}"
                                               class="btn btn-sm btn-outline-info py-1 px-2 me-1" title="Message Adopter">
                                                <i class="bi bi-chat-dots-fill"></i>
                                            </a>

                                            <c:if test="${app.status == 'PENDING'}">
                                                <!-- Approve Form (Triggers Atomic Transaction + Concurrency Lock) -->
                                                <form action="${pageContext.request.contextPath}/shelter/applications/review" method="post" class="d-inline">
                                                    <input type="hidden" name="id" value="${app.id}">
                                                    <input type="hidden" name="action" value="approve">
                                                    <button type="submit" class="btn btn-sm btn-success py-1 px-2 confirm-action"
                                                            data-confirm="Approve this application? This will atomically mark the pet as ADOPTED and reject other pending applications."
                                                            title="Approve Adoption">
                                                        <i class="bi bi-check-lg"></i> Approve
                                                    </button>
                                                </form>

                                                <!-- Reject Form -->
                                                <form action="${pageContext.request.contextPath}/shelter/applications/review" method="post" class="d-inline">
                                                    <input type="hidden" name="id" value="${app.id}">
                                                    <input type="hidden" name="action" value="reject">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger py-1 px-2 confirm-action"
                                                            data-confirm="Reject this application?" title="Reject Application">
                                                        <i class="bi bi-x-lg"></i>
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

<!-- Modal: Read Full Application Questionnaire -->
<div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="reviewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title fw-bold" id="reviewModalLabel">
                    <i class="bi bi-person-lines-fill text-primary me-2"></i> Application Details
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row g-3 mb-3">
                    <div class="col-6">
                        <div class="small text-muted">Applicant:</div>
                        <h6 class="fw-bold" id="modalApplicantName"></h6>
                    </div>
                    <div class="col-6">
                        <div class="small text-muted">Target Pet:</div>
                        <h6 class="fw-bold" id="modalPetName"></h6>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label small fw-semibold text-muted">Applicant Responses &amp; Environment Questionnaire:</label>
                    <div class="p-3 bg-light rounded border text-secondary" id="modalDetails" style="white-space: pre-line; max-height: 250px; overflow-y: auto;">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <form action="${pageContext.request.contextPath}/shelter/applications/review" method="post" class="d-inline me-auto">
                    <input type="hidden" name="id" id="modalAppId" value="">
                    <input type="hidden" name="action" value="approve">
                    <button type="submit" class="btn btn-success confirm-action"
                            data-confirm="Approve this application and finalize adoption?">
                        <i class="bi bi-check-circle me-1"></i> Approve Adoption
                    </button>
                </form>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
