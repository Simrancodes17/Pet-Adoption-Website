<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Create Account" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
            <div class="card card-paw shadow">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <div class="display-6 text-danger mb-2">
                            <i class="bi bi-person-heart"></i>
                        </div>
                        <h3 class="fw-bold">Create Your Account</h3>
                        <p class="text-muted small">Join our compassionate adoption community today</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="row g-3">
                            <div class="col-12">
                                <label for="name" class="form-label fw-semibold">Full Name or Shelter Organization Name</label>
                                <input type="text" class="form-control" id="name" name="name"
                                       value="<c:out value='${name}' />" required placeholder="e.g. Austin Pet Rescue or John Doe">
                            </div>

                            <div class="col-12">
                                <label for="email" class="form-label fw-semibold">Email Address</label>
                                <input type="email" class="form-control" id="email" name="email"
                                       value="<c:out value='${email}' />" required placeholder="name@example.com">
                            </div>

                            <div class="col-md-6">
                                <label for="password" class="form-label fw-semibold">Password</label>
                                <input type="password" class="form-control" id="password" name="password" required minlength="6" placeholder="Min 6 characters">
                            </div>

                            <div class="col-md-6">
                                <label for="confirmPassword" class="form-label fw-semibold">Confirm Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required minlength="6" placeholder="Re-type password">
                            </div>

                            <div class="col-12">
                                <label class="form-label fw-semibold">Account Type (Role)</label>
                                <div class="d-flex gap-4 p-2 bg-light rounded border">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="role" id="roleAdopter" value="ADOPTER"
                                               ${empty role || role == 'ADOPTER' ? 'checked' : ''}>
                                        <label class="form-check-label fw-medium" for="roleAdopter">
                                            <i class="bi bi-house-heart text-success me-1"></i> Pet Adopter
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="role" id="roleShelter" value="SHELTER"
                                               ${role == 'SHELTER' ? 'checked' : ''}>
                                        <label class="form-check-label fw-medium" for="roleShelter">
                                            <i class="bi bi-building text-primary me-1"></i> Animal Shelter
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-12">
                                <label for="contactInfo" class="form-label fw-semibold">Contact Details (Phone / Address)</label>
                                <input type="text" class="form-control" id="contactInfo" name="contactInfo"
                                       value="<c:out value='${contactInfo}' />" placeholder="e.g. Austin, TX | (512) 555-1234">
                            </div>

                            <div class="col-12 mt-4">
                                <button type="submit" class="btn btn-paw-primary w-100 py-2">
                                    <i class="bi bi-person-check-fill me-1"></i> Complete Registration
                                </button>
                            </div>
                        </div>
                    </form>

                    <div class="text-center small text-muted mt-4">
                        Already have an account?
                        <a href="${pageContext.request.contextPath}/login" class="fw-semibold text-danger">Sign in here</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
