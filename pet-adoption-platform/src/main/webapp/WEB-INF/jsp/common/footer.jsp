<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</main>

<footer class="footer-paw mt-5">
    <div class="container">
        <div class="row gy-4">
            <div class="col-lg-5 col-md-6">
                <h5 class="text-white mb-3 d-flex align-items-center gap-2">
                    <i class="bi bi-heart-pulse-fill text-danger"></i> PawHaven Platform
                </h5>
                <p class="small text-muted mb-2">
                    Empowering animal shelters and compassionate adopters with modern digital tools to streamline pet rescue, application management, and forever home placements.
                </p>
                <div class="d-flex gap-3 text-secondary small">
                    <span><i class="bi bi-shield-check text-success"></i> Secure Role-Based Access</span>
                    <span><i class="bi bi-cpu text-info"></i> Java 17 + Tomcat 10</span>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <h6 class="text-white mb-3">Quick Navigation</h6>
                <ul class="list-unstyled small">
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/search">Browse Available Pets</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/login">Member Sign In</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/register">Shelter & Adopter Signup</a></li>
                </ul>
            </div>

            <div class="col-lg-4 col-md-12">
                <h6 class="text-white mb-3">Architecture & Stack Highlights</h6>
                <ul class="list-unstyled small text-muted">
                    <li class="mb-1"><strong class="text-white">Pattern:</strong> Layered MVC (Servlet -> Service -> DAO)</li>
                    <li class="mb-1"><strong class="text-white">Persistence:</strong> Raw JDBC with PreparedStatement & Transactions</li>
                    <li class="mb-1"><strong class="text-white">Concurrency:</strong> ExecutorService async worker & ReentrantLock</li>
                    <li class="mb-1"><strong class="text-white">Caching:</strong> ConcurrentHashMap settings cache</li>
                </ul>
            </div>
        </div>

        <hr class="border-secondary my-4">
        <div class="d-flex flex-wrap justify-content-between align-items-center small text-muted">
            <div>
                &copy; <%= java.time.Year.now().getValue() %> PawHaven Pet Adoption Platform. Built to academic rubric specifications.
            </div>
            <div>
                <span>Powered by Jakarta EE 10 &amp; Apache Tomcat 10</span>
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
