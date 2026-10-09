<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Apply to Adopt ${pet.name}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card card-paw shadow">
                <div class="card-header bg-white py-3 border-bottom">
                    <div class="d-flex align-items-center gap-3">
                        <div class="display-6 text-danger"><i class="bi bi-file-earmark-medical"></i></div>
                        <div>
                            <h4 class="fw-bold mb-0">Adoption Application Form</h4>
                            <p class="text-muted small mb-0">Applying to adopt <strong><c:out value="${pet.name}" /></strong> (${pet.breed}, ${pet.age} yrs)</p>
                        </div>
                    </div>
                </div>

                <div class="card-body p-4 p-md-5">
                    <form action="${pageContext.request.contextPath}/applications/apply" method="post">
                        <input type="hidden" name="petId" value="${pet.id}">

                        <!-- Pet Summary Preview -->
                        <div class="d-flex align-items-center gap-3 p-3 bg-light rounded-3 mb-4 border">
                            <c:choose>
                                <c:when test="${pet.photoPath.startsWith('http')}">
                                    <img src="${pet.photoPath}" class="rounded-circle" style="width: 60px; height: 60px; object-fit: cover;" alt="${pet.name}">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=150&q=80"
                                         class="rounded-circle" style="width: 60px; height: 60px; object-fit: cover;" alt="${pet.name}">
                                </c:otherwise>
                            </c:choose>
                            <div>
                                <h6 class="fw-bold mb-0"><c:out value="${pet.name}" /></h6>
                                <span class="small text-muted"><c:out value="${pet.type}" /> &bull; <c:out value="${pet.breed}" /> &bull; <c:out value="${pet.location}" /></span>
                            </div>
                            <div class="ms-auto">
                                <span class="badge badge-available">Ready for Adoption</span>
                            </div>
                        </div>

                        <!-- Applicant Details -->
                        <div class="mb-4">
                            <label class="form-label fw-semibold">Applicant Contact Information</label>
                            <input type="text" class="form-control" value="${sessionScope.currentUser.name} (${sessionScope.currentUser.email})" disabled>
                            <div class="form-text">Your profile contact info will automatically be transmitted to the shelter.</div>
                        </div>

                        <!-- Household & Lifestyle Details -->
                        <div class="mb-4">
                            <label for="details" class="form-label fw-semibold">
                                Household & Lifestyle Information <span class="text-danger">*</span>
                            </label>
                            <textarea class="form-control" id="details" name="details" rows="6" required minlength="10"
                                      placeholder="Please describe your living environment (e.g. apartment/house, yard size, presence of other pets or children, work schedule, experience with this breed, and why you feel you would be a great match for ${pet.name})."></textarea>
                            <div class="form-text">Minimum 10 characters. Thoughtful responses significantly improve application approval rates.</div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mt-4">
                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left"></i> Cancel
                            </a>
                            <button type="submit" class="btn btn-paw-primary px-4 py-2">
                                <i class="bi bi-send-fill me-1"></i> Submit Application
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
