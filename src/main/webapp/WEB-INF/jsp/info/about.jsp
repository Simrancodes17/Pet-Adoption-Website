<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="About PawHaven | Ethical Pet Adoption Platform" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <!-- Header -->
    <div class="text-center mb-5">
        <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Our Mission</span>
        <h1 class="fw-bold mt-1 mb-2">Connecting Compassionate Homes with Animals in Need</h1>
        <p class="text-secondary mx-auto" style="max-width: 680px;">
            PawHaven is a community-focused pet adoption network built to simplify and humanize the adoption process for animal shelters, rescue volunteers, and loving adopters.
        </p>
    </div>

    <!-- Philosophy Grid -->
    <div class="row g-4 mb-5">
        <div class="col-md-4">
            <div class="card card-paw h-100 p-4 border">
                <div class="rounded-circle d-inline-flex align-items-center justify-content-center bg-light text-success mb-3" style="width: 52px; height: 52px; font-size: 1.4rem;">
                    <i class="bi bi-heart-pulse"></i>
                </div>
                <h5 class="fw-bold text-dark mb-2">Welfare Before Numbers</h5>
                <p class="text-muted small mb-0">
                    We do not treat companion animals as commodities. Every pet listing includes honest temperament evaluations, health disclosures, and specific living requirements so pets are placed in enduring homes.
                </p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-paw h-100 p-4 border">
                <div class="rounded-circle d-inline-flex align-items-center justify-content-center bg-light text-success mb-3" style="width: 52px; height: 52px; font-size: 1.4rem;">
                    <i class="bi bi-shield-check"></i>
                </div>
                <h5 class="fw-bold text-dark mb-2">Verified Rescue Partners</h5>
                <p class="text-muted small mb-0">
                    All participating organizations undergo administrator review before posting. This safeguards adopters against puppy mills, unlicensed commercial breeders, and deceptive listings.
                </p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-paw h-100 p-4 border">
                <div class="rounded-circle d-inline-flex align-items-center justify-content-center bg-light text-success mb-3" style="width: 52px; height: 52px; font-size: 1.4rem;">
                    <i class="bi bi-chat-dots"></i>
                </div>
                <h5 class="fw-bold text-dark mb-2">Direct Shelter Dialogue</h5>
                <p class="text-muted small mb-0">
                    Adopters communicate directly with the shelter staff who know each animal best. This transparency ensures that quirks, medical needs, and behavior traits are clear before bringing a pet home.
                </p>
            </div>
        </div>
    </div>

    <!-- The Adoption Philosophy Story -->
    <div class="card card-paw p-4 p-md-5 mb-5 border">
        <div class="row align-items-center g-4">
            <div class="col-lg-7">
                <span class="badge bg-light text-success border px-3 py-2 rounded-pill mb-2">Why Adoption Matters</span>
                <h3 class="fw-bold text-dark mb-3">Every Year Millions of Pets Seek a Second Chance</h3>
                <p class="text-secondary mb-3">
                    Overcrowded shelters and rescue centers work tirelessly every day to rehabilitate abandoned, surrendered, and rescued animals. While traditional adoption processes can often feel fragmented or opaque, PawHaven provides clean digital tools that bridge the gap between conscientious pet seekers and local caretakers.
                </p>
                <p class="text-secondary mb-0">
                    Whether you are welcoming an energetic puppy, a senior cat, a gentle rabbit, or a friendly pair of parakeets, our platform supports you with educational breed guidance, realistic care expectations, and direct shelter communication every step of the way.
                </p>
            </div>
            <div class="col-lg-5">
                <div class="p-4 bg-light rounded-3 border">
                    <h6 class="fw-bold text-dark mb-3"><i class="bi bi-check-circle-fill text-success me-2"></i> Our Ethical Commitments</h6>
                    <ul class="list-unstyled mb-0 small text-muted d-flex flex-column gap-2">
                        <li><i class="bi bi-check text-success me-1 fw-bold"></i> <strong>Zero commercial sales</strong>: We do not permit puppy mills or commercial pet trade.</li>
                        <li><i class="bi bi-check text-success me-1 fw-bold"></i> <strong>Multi-species advocacy</strong>: Equal attention to dogs, cats, rabbits, birds, hamsters, and turtles.</li>
                        <li><i class="bi bi-check text-success me-1 fw-bold"></i> <strong>Transparent medical history</strong>: Vaccination, neutering, and health status stated upfront.</li>
                        <li><i class="bi bi-check text-success me-1 fw-bold"></i> <strong>Post-adoption support</strong>: Shelters remain available for advice and follow-up guidance.</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- Call to Action -->
    <div class="text-center p-4 bg-light rounded-3 border">
        <h4 class="fw-bold text-dark mb-2">Find Your New Family Member</h4>
        <p class="text-muted small mb-3">Browse our directory of adoptable companions or learn more about caring for different breeds.</p>
        <div class="d-flex justify-content-center gap-2">
            <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary">
                Browse Pets
            </a>
            <a href="${pageContext.request.contextPath}/how-it-works" class="btn btn-outline-secondary">
                How It Works
            </a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
