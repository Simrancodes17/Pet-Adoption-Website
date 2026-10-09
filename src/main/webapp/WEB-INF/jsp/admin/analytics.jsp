<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Platform Analytics & Telemetry" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Platform Analytics &amp; Engagement</h3>
            <p class="text-muted small mb-0">
                Periodic calculations by <code>ScheduledExecutorService</code> &bull; Last refreshed: ${analytics.lastRefreshedAt}
            </p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/analytics?refresh=true" class="btn btn-outline-primary btn-sm">
                <i class="bi bi-arrow-clockwise me-1"></i> Force Compute Refresh
            </a>
        </div>
    </div>

    <!-- Core Metrics -->
    <div class="row g-3 mb-4">
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-start border-4 border-success shadow-sm">
                <div class="text-muted small fw-semibold">TOTAL ADOPTIONS</div>
                <div class="display-6 fw-bold text-success">${analytics.totalAdoptedPets}</div>
                <div class="small text-muted">Placed companions</div>
            </div>
        </div>
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-start border-4 border-primary shadow-sm">
                <div class="text-muted small fw-semibold">ACTIVE LISTINGS</div>
                <div class="display-6 fw-bold text-primary">${analytics.totalAvailablePets}</div>
                <div class="small text-muted">Available to browse</div>
            </div>
        </div>
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-start border-4 border-warning shadow-sm">
                <div class="text-muted small fw-semibold">PENDING APPLICATIONS</div>
                <div class="display-6 fw-bold text-warning">${analytics.totalPendingApplications}</div>
                <div class="small text-muted">Awaiting shelter decisions</div>
            </div>
        </div>
        <div class="col-md-3 col-6">
            <div class="card card-paw p-3 border-start border-4 border-info shadow-sm">
                <div class="text-muted small fw-semibold">TOTAL MESSAGES</div>
                <div class="display-6 fw-bold text-info">${analytics.totalMessages}</div>
                <div class="small text-muted">Shelter-Adopter chats</div>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <!-- Pet Type Breakdown -->
        <div class="col-lg-6">
            <div class="card card-paw shadow-sm h-100 border-0">
                <div class="card-header bg-white py-3 border-bottom">
                    <h5 class="fw-bold mb-0 text-dark"><i class="bi bi-pie-chart-fill text-primary me-2"></i> Pet Distribution by Species</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty analytics.petTypeDistribution}">
                            <p class="text-muted text-center py-4">No pet species data available yet.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="entry" items="${analytics.petTypeDistribution}">
                                <div class="mb-3">
                                    <div class="d-flex justify-content-between small fw-semibold mb-1">
                                        <span class="text-dark"><c:out value="${entry.key}" /></span>
                                        <span class="text-muted">${entry.value} pets</span>
                                    </div>
                                    <div class="progress" style="height: 10px; border-radius: var(--paw-radius-full);">
                                        <div class="progress-bar bg-primary" role="progressbar"
                                             style="width: ${(entry.value * 100) / (analytics.totalPets > 0 ? analytics.totalPets : 1)}%">
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Application Status Distribution -->
        <div class="col-lg-6">
            <div class="card card-paw shadow-sm h-100 border-0">
                <div class="card-header bg-white py-3 border-bottom">
                    <h5 class="fw-bold mb-0 text-dark"><i class="bi bi-bar-chart-fill text-success me-2"></i> Application Funnel</h5>
                </div>
                <div class="card-body">
                    <div class="row text-center py-4">
                        <div class="col-4">
                            <div class="fs-2 fw-bold text-warning">${analytics.totalPendingApplications}</div>
                            <span class="badge bg-warning text-dark rounded-pill">PENDING</span>
                        </div>
                        <div class="col-4">
                            <div class="fs-2 fw-bold text-success">${analytics.totalApprovedApplications}</div>
                            <span class="badge bg-success rounded-pill">APPROVED</span>
                        </div>
                        <div class="col-4">
                            <div class="fs-2 fw-bold text-danger">${analytics.totalRejectedApplications}</div>
                            <span class="badge bg-danger rounded-pill">REJECTED</span>
                        </div>
                    </div>
                    <div class="small text-muted border-top pt-3">
                        <i class="bi bi-lightbulb-fill text-warning me-1"></i>
                        When an application is approved, the pet is marked <strong>ADOPTED</strong> atomically, and competing applications are resolved.
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Live Multithreaded Notification Worker Execution Log -->
    <div class="card card-paw shadow-sm border-0">
        <div class="card-header bg-dark text-white py-3 d-flex justify-content-between align-items-center">
            <h5 class="fw-bold mb-0 small text-uppercase font-monospace text-light">
                <i class="bi bi-terminal me-2 text-success"></i> Notification Thread Pool Dispatch Log
            </h5>
            <span class="badge bg-success font-monospace">ExecutorService Active</span>
        </div>
        <div class="card-body bg-light font-monospace small" style="max-height: 240px; overflow-y: auto;">
            <c:choose>
                <c:when test="${empty deliveryLog}">
                    <div class="text-muted p-2">Thread pool awaiting new application or messaging events...</div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="logLine" items="${deliveryLog}">
                        <div class="text-secondary mb-1 border-bottom pb-1">
                            <i class="bi bi-chevron-right text-primary me-1"></i> <c:out value="${logLine}" />
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
