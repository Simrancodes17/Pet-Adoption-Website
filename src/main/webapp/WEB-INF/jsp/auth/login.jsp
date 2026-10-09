<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Sign In" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
            <div class="card card-paw shadow-lg border-0">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <div class="brand-icon mx-auto mb-3" style="width: 52px; height: 52px; font-size: 1.5rem;">
                            <i class="bi bi-heart-pulse-fill"></i>
                        </div>
                        <h3 class="fw-bold mb-1">Welcome Back</h3>
                        <p class="text-muted small">Sign in to your PawHaven account to manage listings or adopt</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                <input type="email" class="form-control" id="email" name="email"
                                       value="<c:out value='${enteredEmail}' />" required placeholder="name@example.com">
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Password</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                <input type="password" class="form-control" id="password" name="password" required placeholder="Enter password">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-paw-primary w-100 py-2 mb-3">
                            <i class="bi bi-box-arrow-in-right"></i> Sign In to Account
                        </button>
                    </form>

                    <div class="text-center small text-muted">
                        Don't have an account yet?
                        <a href="${pageContext.request.contextPath}/register" class="fw-semibold text-primary text-decoration-none">Create an account</a>
                    </div>

                    <hr class="my-4">

                    <!-- Quick Demo Role Sign-In Helpers -->
                    <div>
                        <div class="small fw-bold text-uppercase text-muted mb-2 text-center" style="font-size: 0.72rem; letter-spacing: 0.05em;">
                            <i class="bi bi-lightning-charge-fill text-warning"></i> One-Click Demo Accounts
                        </div>

                        <button type="button" class="demo-role-btn w-100 demo-fill-btn"
                                data-email="admin@pawhaven.org" data-password="password">
                            <div>
                                <span class="badge bg-danger me-2">ADMIN</span>
                                <span class="fw-semibold small">admin@pawhaven.org</span>
                            </div>
                            <span class="text-muted small">Fill <i class="bi bi-arrow-up-right"></i></span>
                        </button>

                        <button type="button" class="demo-role-btn w-100 demo-fill-btn"
                                data-email="shelter1@pawhaven.org" data-password="password">
                            <div>
                                <span class="badge bg-primary me-2">SHELTER</span>
                                <span class="fw-semibold small">shelter1@pawhaven.org</span>
                            </div>
                            <span class="text-muted small">Fill <i class="bi bi-arrow-up-right"></i></span>
                        </button>

                        <button type="button" class="demo-role-btn w-100 demo-fill-btn"
                                data-email="jane.doe@example.com" data-password="password">
                            <div>
                                <span class="badge bg-success me-2">ADOPTER</span>
                                <span class="fw-semibold small">jane.doe@example.com</span>
                            </div>
                            <span class="text-muted small">Fill <i class="bi bi-arrow-up-right"></i></span>
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
