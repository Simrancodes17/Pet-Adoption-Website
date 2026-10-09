<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="System Configuration Settings" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <div class="card card-paw shadow-sm">
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <div>
                        <h4 class="fw-bold mb-0">System Settings Panel</h4>
                        <p class="text-muted small mb-0">Manage platform-wide configuration and concurrency caching</p>
                    </div>
                    <span class="badge bg-dark"><i class="bi bi-cpu me-1"></i> ConcurrentHashMap Cache Backed</span>
                </div>
                <div class="card-body p-4 p-md-5">
                    <form action="${pageContext.request.contextPath}/admin/settings" method="post">
                        <!-- Platform Identity Section -->
                        <h5 class="fw-bold text-primary mb-3">
                            <i class="bi bi-info-circle me-1"></i> Platform Branding &amp; Identity
                        </h5>

                        <div class="mb-3">
                            <label for="platformName" class="form-label fw-semibold">Platform Name</label>
                            <input type="text" class="form-control" id="platformName" name="setting.platform.name"
                                   value="<c:out value='${settings["platform.name"]}' />" required>
                        </div>

                        <div class="mb-3">
                            <label for="platformTagline" class="form-label fw-semibold">Tagline / Mission Statement</label>
                            <input type="text" class="form-control" id="platformTagline" name="setting.platform.tagline"
                                   value="<c:out value='${settings["platform.tagline"]}' />">
                        </div>

                        <div class="row g-3 mb-4">
                            <div class="col-md-6">
                                <label for="contactEmail" class="form-label fw-semibold">Support Email Address</label>
                                <input type="email" class="form-control" id="contactEmail" name="setting.platform.contact_email"
                                       value="<c:out value='${settings["platform.contact_email"]}' />">
                            </div>
                            <div class="col-md-6">
                                <label for="contactPhone" class="form-label fw-semibold">Support Phone</label>
                                <input type="text" class="form-control" id="contactPhone" name="setting.platform.contact_phone"
                                       value="<c:out value='${settings["platform.contact_phone"]}' />">
                            </div>
                        </div>

                        <hr class="my-4">

                        <!-- Automation & Business Policies -->
                        <h5 class="fw-bold text-primary mb-3">
                            <i class="bi bi-sliders me-1"></i> Automation &amp; Moderation Policies
                        </h5>

                        <div class="form-check form-switch mb-3">
                            <input class="form-check-input" type="checkbox" role="switch" id="autoApprove"
                                   name="setting.admin.auto_approve_pets" value="true"
                                   ${settings["admin.auto_approve_pets"] == 'true' ? 'checked' : ''}>
                            <label class="form-check-label fw-semibold" for="autoApprove">
                                Auto-Approve Shelter Pet Listings
                            </label>
                            <div class="form-text">When enabled, newly listed pets become available immediately without manual administrator review.</div>
                        </div>

                        <div class="form-check form-switch mb-3">
                            <input class="form-check-input" type="checkbox" role="switch" id="asyncEmail"
                                   name="setting.notifications.async_email_enabled" value="true"
                                   ${settings["notifications.async_email_enabled"] == 'true' ? 'checked' : ''}>
                            <label class="form-check-label fw-semibold" for="asyncEmail">
                                Asynchronous Email &amp; Push Notifications
                            </label>
                            <div class="form-text">Dispatches applicant email updates via the background ExecutorService thread pool.</div>
                        </div>

                        <div class="mb-4">
                            <label for="maxPending" class="form-label fw-semibold">Maximum Simultaneous Pending Applications per Adopter</label>
                            <input type="number" class="form-control" id="maxPending" name="setting.adoptions.max_pending_per_user"
                                   style="max-width: 160px;" min="1" max="10"
                                   value="<c:out value='${settings["adoptions.max_pending_per_user"]}' />">
                            <div class="form-text">Restricts spam by capping active pending adoptions.</div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center pt-3 border-top">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline-secondary">
                                Back to Dashboard
                            </a>
                            <button type="submit" class="btn btn-paw-primary px-4 py-2">
                                <i class="bi bi-save me-1"></i> Save Configuration
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
