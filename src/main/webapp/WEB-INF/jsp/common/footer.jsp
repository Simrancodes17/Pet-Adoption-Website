<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</main>

<footer class="footer-paw mt-5">
    <div class="container">
        <div class="row gy-4">
            <div class="col-lg-4 col-md-6">
                <div class="d-flex align-items-center gap-2 mb-3">
                    <span class="brand-icon d-inline-flex align-items-center justify-content-center text-white bg-success rounded-3 p-2" style="width: 32px; height: 32px;">
                        <i class="bi bi-heart-pulse-fill"></i>
                    </span>
                    <h5 class="fw-bold mb-0 text-white">PawHaven</h5>
                </div>
                <p class="small text-muted mb-3" style="max-width: 340px;">
                    A community-driven pet adoption directory connecting caring individuals with local rescue shelters. Dedicated to responsible adoption, welfare education, and lifetime pet care.
                </p>
                <div class="small text-muted">
                    <span><i class="bi bi-shield-check text-success me-1"></i> Transparent Adoption Process</span>
                </div>
            </div>

            <div class="col-lg-2 col-md-6 col-6">
                <h6 class="text-white fw-bold mb-3">Adopt &amp; Learn</h6>
                <ul class="list-unstyled small">
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/search">Browse Available Pets</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/breeds">Explore Breed Profiles</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/how-it-works">How Adoption Works</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/search?type=Dog">Dogs for Adoption</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/search?type=Cat">Cats for Adoption</a></li>
                </ul>
            </div>

            <div class="col-lg-2 col-md-6 col-6">
                <h6 class="text-white fw-bold mb-3">For Shelters</h6>
                <ul class="list-unstyled small">
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/login">Shelter Portal Sign In</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/register">Register Rescue Shelter</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/how-it-works">Application Workflow</a></li>
                </ul>
            </div>

            <div class="col-lg-4 col-md-6">
                <h6 class="text-white fw-bold mb-3">Trust &amp; Organization</h6>
                <ul class="list-unstyled small">
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/about">About Our Mission</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/contact">Contact &amp; Assistance</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/privacy">Privacy Policy</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/terms">Terms &amp; Conditions</a></li>
                </ul>
                <div class="text-muted small mt-2">
                    Questions about an application? Contact the caretaker shelter directly through your account messages.
                </div>
            </div>
        </div>

        <hr class="my-4">
        <div class="d-flex flex-wrap justify-content-between align-items-center small text-muted">
            <div>
                &copy; <%= java.time.Year.now().getValue() %> PawHaven Platform. Supporting responsible animal rescue and placement.
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/privacy" class="me-3">Privacy</a>
                <a href="${pageContext.request.contextPath}/terms">Terms</a>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap 5 Bundle JS (Includes Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- Platform JS -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
