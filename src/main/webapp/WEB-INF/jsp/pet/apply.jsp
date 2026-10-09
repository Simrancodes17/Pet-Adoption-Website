<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Apply to Adopt ${pet.name}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card card-paw shadow-sm border-0">
                <div class="card-header bg-white py-3 border-bottom d-flex align-items-center gap-3">
                    <div class="bg-primary-subtle text-primary rounded-3 p-2 display-6 d-inline-flex align-items-center justify-content-center" style="width: 48px; height: 48px;">
                        <i class="bi bi-file-earmark-medical"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">Adoption Application</h4>
                        <p class="text-muted small mb-0">Applying to adopt <strong><c:out value="${pet.name}" /></strong> (${pet.breed}, ${pet.age} yrs)</p>
                    </div>
                </div>

                <div class="card-body p-4 p-md-5">
                    <form action="${pageContext.request.contextPath}/applications/apply" method="post">
                        <input type="hidden" name="petId" value="${pet.id}">

                        <!-- Pet Summary Preview Card -->
                        <div class="d-flex align-items-center gap-3 p-3 bg-light rounded-3 mb-4 border">
                            <c:choose>
                                <c:when test="${pet.photoPath.startsWith('http')}">
                                    <img src="${pet.photoPath}" class="rounded-circle pet-thumb-img border" style="width: 65px; height: 65px; object-fit: cover;" alt="${pet.name}" data-species="${pet.type}">
                                </c:when>
                                <c:otherwise>
                                    <c:set var="appFbUrl" value="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=150&q=80" />
                                    <c:if test="${pet.type == 'Cat'}"><c:set var="appFbUrl" value="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=150&q=80" /></c:if>
                                    <c:if test="${pet.type == 'Rabbit'}"><c:set var="appFbUrl" value="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=150&q=80" /></c:if>
                                    <c:if test="${pet.type == 'Bird'}"><c:set var="appFbUrl" value="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=150&q=80" /></c:if>
                                    <c:if test="${pet.type == 'Hamster'}"><c:set var="appFbUrl" value="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=150&q=80" /></c:if>
                                    <c:if test="${pet.type == 'Turtle'}"><c:set var="appFbUrl" value="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=150&q=80" /></c:if>
                                    <img src="${appFbUrl}" class="rounded-circle pet-thumb-img border" style="width: 65px; height: 65px; object-fit: cover;" alt="${pet.name}" data-species="${pet.type}">
                                </c:otherwise>
                            </c:choose>
                            <div>
                                <h5 class="fw-bold mb-0 text-dark"><c:out value="${pet.name}" /></h5>
                                <div class="small text-muted">
                                    <span class="badge bg-light text-dark border"><c:out value="${pet.type}" /></span>
                                    <c:out value="${pet.breed}" /> &bull; <c:out value="${pet.location}" />
                                    <c:if test="${not empty pet.gender and pet.gender != 'Unknown'}">
                                        &bull; <span class="fw-semibold">${pet.gender}</span>
                                    </c:if>
                                </div>
                            </div>
                            <div class="ms-auto">
                                <span class="badge badge-available px-3 py-2 rounded-pill">Available</span>
                            </div>
                        </div>

                        <!-- Applicant Contact Preview -->
                        <div class="mb-4">
                            <label class="form-label">Applicant Information</label>
                            <input type="text" class="form-control" value="${sessionScope.currentUser.name} (${sessionScope.currentUser.email})" disabled>
                            <div class="form-text">Your profile contact info will be securely shared with the shelter caretaker.</div>
                        </div>

                        <!-- Questionnaire Details -->
                        <div class="mb-4">
                            <label for="details" class="form-label">
                                Living Environment &amp; Adoption Reason <span class="text-danger">*</span>
                            </label>
                            <textarea class="form-control" id="details" name="details" rows="6" required minlength="10"
                                      placeholder="Describe your household setup (house/apartment, yard, presence of other animals or children, experience with ${pet.type}s, daily routine, and why you would be a great home for ${pet.name})."></textarea>
                            <div class="form-text">Minimum 10 characters. Detailed, thoughtful questionnaires help shelters evaluate match suitability faster.</div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mt-4 pt-3 border-top">
                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left"></i> Back to Pet Profile
                            </a>
                            <button type="submit" class="btn btn-paw-secondary px-4 py-2">
                                <i class="bi bi-send-fill me-1"></i> Send Adoption Application
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
