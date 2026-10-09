<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Explore Pet Breeds & Companion Profiles" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <!-- Header -->
    <div class="mb-4">
        <span class="text-uppercase fw-bold text-muted small" style="letter-spacing: 0.05em;">Educational Directory</span>
        <h2 class="fw-bold mt-1 mb-2">Pet Breed &amp; Companion Reference</h2>
        <p class="text-secondary small mb-3" style="max-width: 680px; line-height: 1.65;">
            These profiles provide objective reference information on temperament, expected size, activity levels, and daily grooming needs.
            Review these guidelines to choose a companion whose care requirements suit your household and daily routine.
        </p>
        <div class="alert alert-light border small text-muted d-flex align-items-center gap-2 py-2 px-3" style="border-radius: var(--paw-radius-sm); max-width: 760px;">
            <i class="bi bi-info-circle-fill text-success"></i>
            <span>
                <strong>Note:</strong> Breed guides are educational references. Live pets currently awaiting adoption in shelters are cataloged separately in our browse directory.
            </span>
        </div>
    </div>

    <!-- Species Filter Tabs -->
    <div class="d-flex flex-wrap gap-2 mb-4 pb-2 border-bottom">
        <a href="${pageContext.request.contextPath}/breeds?species=ALL"
           class="btn btn-sm ${selectedSpecies == 'ALL' ? 'btn-dark' : 'btn-outline-secondary'}">
            All Animals
        </a>
        <c:forEach var="sp" items="${allSpeciesList}">
            <a href="${pageContext.request.contextPath}/breeds?species=${sp}"
               class="btn btn-sm ${selectedSpecies == sp ? 'btn-dark' : 'btn-outline-secondary'}">
                ${sp}s
            </a>
        </c:forEach>
    </div>

    <!-- Breeds Grid -->
    <div class="row g-4">
        <c:forEach var="b" items="${breeds}">
            <div class="col-lg-4 col-md-6">
                <div class="breed-guide-card scroll-reveal">
                    <div class="breed-img-wrapper">
                        <img src="${b.photoUrl}" alt="${b.name}" class="breed-img">
                        <span class="position-absolute top-0 end-0 m-2 badge bg-white text-dark border rounded-pill">
                            <c:out value="${b.species}" />
                        </span>
                    </div>

                    <div class="p-3 d-flex flex-column flex-grow-1">
                        <h4 class="fw-bold text-dark mb-1"><c:out value="${b.name}" /></h4>
                        <p class="small text-secondary mb-3">
                            <c:out value="${b.description}" />
                        </p>

                        <!-- Key Spec Attributes -->
                        <div class="small mb-3">
                            <div class="mb-1">
                                <span class="fw-bold text-dark">Typical Size:</span>
                                <span class="text-secondary"><c:out value="${b.size}" /></span>
                            </div>
                            <div class="mb-1">
                                <span class="fw-bold text-dark">Temperament:</span>
                                <span class="text-secondary"><c:out value="${b.temperament}" /></span>
                            </div>
                            <div class="mb-1">
                                <span class="fw-bold text-dark">Activity Level:</span>
                                <span class="text-secondary"><c:out value="${b.activityLevel}" /></span>
                            </div>
                            <div class="mb-1">
                                <span class="fw-bold text-dark">Care &amp; Grooming:</span>
                                <span class="text-secondary"><c:out value="${b.groomingCare}" /></span>
                            </div>
                            <div>
                                <span class="fw-bold text-dark">Best Suited For:</span>
                                <span class="text-secondary"><c:out value="${b.bestSuitedFor}" /></span>
                            </div>
                        </div>

                        <!-- Direct Search CTA -->
                        <div class="mt-auto pt-3 border-top">
                            <a href="${pageContext.request.contextPath}/search?type=${b.species}&breed=${b.name}"
                               class="btn btn-outline-secondary btn-sm w-100">
                                <i class="bi bi-search me-1"></i> Search Available ${b.name}s
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
