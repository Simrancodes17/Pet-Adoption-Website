<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="How Pet Adoption Works" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <!-- Header -->
    <div class="text-center mb-5">
        <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Step-by-Step Guide</span>
        <h1 class="fw-bold mt-1 mb-2">How Adoption Works on PawHaven</h1>
        <p class="text-secondary mx-auto" style="max-width: 600px;">
            Our mission is to help animals transition smoothly into permanent, loving homes while supporting the shelters that rescue them.
        </p>
    </div>

    <!-- Step Breakdown -->
    <div class="row g-4 mb-5">
        <div class="col-md-6 col-lg-3">
            <div class="step-card">
                <div class="step-number">1</div>
                <h4 class="step-title">Explore &amp; Filter</h4>
                <p class="step-desc">
                    Search by species, breed, age, and location. Read each pet profile carefully to understand their temperament, medical background, and living preferences.
                </p>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="step-card">
                <div class="step-number">2</div>
                <h4 class="step-title">Assess Your Home</h4>
                <p class="step-desc">
                    Consider your daily routine, yard access, existing pets, and financial readiness for veterinary expenses and high-quality nutrition before applying.
                </p>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="step-card">
                <div class="step-number">3</div>
                <h4 class="step-title">Submit Application</h4>
                <p class="step-desc">
                    Fill out our comprehensive questionnaire. Detail your home environment, schedule, and experience to help shelters evaluate match compatibility.
                </p>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="step-card">
                <div class="step-number">4</div>
                <h4 class="step-title">Meet &amp; Finalize</h4>
                <p class="step-desc">
                    Directly message the caretaker shelter, schedule an in-person or video meet &amp; greet, and finalize the adoption agreement once approved.
                </p>
            </div>
        </div>
    </div>

    <!-- What Information Adopters Need to Provide -->
    <div class="card card-paw p-4 p-md-5 mb-5 border">
        <h3 class="fw-bold mb-3 text-dark">What Information Do Adopters Need to Provide?</h3>
        <p class="text-secondary mb-4">
            Shelters prioritize animal welfare and safety. During the adoption application, you will be asked to share:
        </p>
        <div class="row g-3">
            <div class="col-md-4">
                <div class="p-3 bg-light rounded-3 border h-100">
                    <h6 class="fw-bold text-dark mb-2"><i class="bi bi-house-door text-success me-2"></i> Household Environment</h6>
                    <p class="small text-muted mb-0">Housing type (apartment/house), whether you own or rent, yard enclosure safety, and landlord pet permissions.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-3 bg-light rounded-3 border h-100">
                    <h6 class="fw-bold text-dark mb-2"><i class="bi bi-people text-success me-2"></i> Family &amp; Resident Pets</h6>
                    <p class="small text-muted mb-0">Ages of children in the household, presence of existing cats/dogs/small pets, and compatibility experience.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-3 bg-light rounded-3 border h-100">
                    <h6 class="fw-bold text-dark mb-2"><i class="bi bi-clock-history text-success me-2"></i> Daily Routine &amp; Care</h6>
                    <p class="small text-muted mb-0">Hours per day the pet may spend unattended, exercise plans, veterinary budget, and long-term care dedication.</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Responsible Adoption Call to Action -->
    <div class="text-center p-4 bg-light rounded-3 border">
        <h4 class="fw-bold text-dark mb-2">Ready to Meet Your Future Companion?</h4>
        <p class="text-muted small mb-3">Begin your search across verified adoptable pets today.</p>
        <div class="d-flex justify-content-center gap-2">
            <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary">
                Browse Available Pets
            </a>
            <a href="${pageContext.request.contextPath}/breeds" class="btn btn-outline-secondary">
                Explore Breeds
            </a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
