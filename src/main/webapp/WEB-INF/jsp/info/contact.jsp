<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Contact Care & Shelter Support | PawHaven" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <!-- Header -->
    <div class="text-center mb-5">
        <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Get in Touch</span>
        <h1 class="fw-bold mt-1 mb-2">We're Here to Help</h1>
        <p class="text-secondary mx-auto" style="max-width: 620px;">
            Have questions regarding shelter verification, adoption applications, or platform operations? Connect with our dedicated support team.
        </p>
    </div>

    <div class="row g-4 mb-5">
        <!-- Contact Guidance / Information -->
        <div class="col-lg-5">
            <div class="card card-paw h-100 p-4 border">
                <h4 class="fw-bold text-dark mb-3">Direct Inquiries</h4>
                <p class="text-muted small mb-4">
                    For questions about a specific adoptable pet, we encourage messaging the shelter directly through the pet's listing page. For platform questions, use the details below:
                </p>

                <div class="d-flex align-items-start gap-3 mb-4">
                    <div class="rounded bg-light p-2 text-success">
                        <i class="bi bi-envelope-fill fs-5"></i>
                    </div>
                    <div>
                        <div class="fw-bold text-dark small">General &amp; Technical Support</div>
                        <div class="text-muted small">support@pawhaven.org</div>
                    </div>
                </div>

                <div class="d-flex align-items-start gap-3 mb-4">
                    <div class="rounded bg-light p-2 text-success">
                        <i class="bi bi-building-check fs-5"></i>
                    </div>
                    <div>
                        <div class="fw-bold text-dark small">Shelter &amp; Rescue Partnerships</div>
                        <div class="text-muted small">shelters@pawhaven.org</div>
                    </div>
                </div>

                <div class="d-flex align-items-start gap-3 mb-4">
                    <div class="rounded bg-light p-2 text-success">
                        <i class="bi bi-clock-history fs-5"></i>
                    </div>
                    <div>
                        <div class="fw-bold text-dark small">Support Hours</div>
                        <div class="text-muted small">Monday – Saturday: 9:00 AM – 6:00 PM EST</div>
                    </div>
                </div>

                <div class="p-3 bg-light rounded-3 border mt-auto">
                    <div class="fw-bold text-dark small mb-1"><i class="bi bi-info-circle text-primary me-1"></i> Already Applied for a Pet?</div>
                    <p class="text-muted small mb-2">You can check application review status and message the assigned shelter directly in your account dashboard.</p>
                    <a href="${pageContext.request.contextPath}/adopter/applications" class="small fw-bold text-decoration-none text-success">
                        Go to My Applications &rarr;
                    </a>
                </div>
            </div>
        </div>

        <!-- Contact Form -->
        <div class="col-lg-7">
            <div class="card card-paw p-4 p-md-5 border">
                <h4 class="fw-bold text-dark mb-2">Send Us a Message</h4>
                <p class="text-muted small mb-4">Our coordination team typically responds to inquiries within 24 to 48 business hours.</p>

                <form onsubmit="event.preventDefault(); document.getElementById('contactFeedback').classList.remove('d-none'); this.reset();">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label small fw-bold text-dark">Your Name</label>
                            <input type="text" class="form-control" placeholder="e.g. Maya Lin" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label small fw-bold text-dark">Email Address</label>
                            <input type="email" class="form-control" placeholder="name@example.com" required>
                        </div>
                        <div class="col-12">
                            <label class="form-label small fw-bold text-dark">Topic</label>
                            <select class="form-select" required>
                                <option value="">Select inquiry reason</option>
                                <option value="shelter-verification">Shelter Onboarding / Verification</option>
                                <option value="application-help">Application Process Question</option>
                                <option value="technical">Technical Account Issue</option>
                                <option value="feedback">Community Feedback / Welfare Concern</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <label class="form-label small fw-bold text-dark">Message</label>
                            <textarea class="form-control" rows="5" placeholder="Please describe how we can assist you..." required></textarea>
                        </div>
                        <div class="col-12">
                            <div id="contactFeedback" class="alert alert-success d-none mb-3 py-2 small">
                                <i class="bi bi-check-circle me-1"></i> Thank you! Your message has been received. Our support team will respond shortly.
                            </div>
                            <button type="submit" class="btn btn-paw-primary px-4">
                                <i class="bi bi-send me-1"></i> Send Inquiry
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
