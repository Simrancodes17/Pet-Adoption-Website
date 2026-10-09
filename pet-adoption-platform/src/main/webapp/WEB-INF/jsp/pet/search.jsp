<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Browse Adoptable Pets" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="row g-4">
        <!-- Sidebar Filters -->
        <div class="col-lg-3">
            <div class="card card-paw sticky-top" style="top: 80px;">
                <div class="card-body p-3 p-md-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h5 class="fw-bold mb-0"><i class="bi bi-funnel-fill text-danger"></i> Filters</h5>
                        <a href="${pageContext.request.contextPath}/search" class="small text-danger text-decoration-none">Reset</a>
                    </div>

                    <form action="${pageContext.request.contextPath}/search" method="get">
                        <div class="mb-3">
                            <label class="form-label small fw-semibold">Keywords</label>
                            <input type="text" name="q" class="form-control form-control-sm" placeholder="Search name, bio..."
                                   value="<c:out value='${query}' />">
                        </div>

                        <div class="mb-3">
                            <label class="form-label small fw-semibold">Animal Species</label>
                            <select name="type" class="form-select form-select-sm">
                                <option value="ALL" ${selectedType == 'ALL' ? 'selected' : ''}>All Animals</option>
                                <option value="Dog" ${selectedType == 'Dog' ? 'selected' : ''}>Dog</option>
                                <option value="Cat" ${selectedType == 'Cat' ? 'selected' : ''}>Cat</option>
                                <option value="Rabbit" ${selectedType == 'Rabbit' ? 'selected' : ''}>Rabbit</option>
                                <option value="Bird" ${selectedType == 'Bird' ? 'selected' : ''}>Bird</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label small fw-semibold">Breed</label>
                            <input type="text" name="breed" class="form-control form-control-sm" placeholder="e.g. Retriever, Siamese"
                                   value="<c:out value='${breed}' />">
                        </div>

                        <div class="mb-3">
                            <label class="form-label small fw-semibold">Location / City</label>
                            <input type="text" name="location" class="form-control form-control-sm" placeholder="e.g. Austin, Denver"
                                   value="<c:out value='${location}' />">
                        </div>

                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label class="form-label small fw-semibold">Min Age (yrs)</label>
                                <input type="number" name="minAge" class="form-control form-control-sm" min="0" max="25"
                                       value="${minAge}">
                            </div>
                            <div class="col-6">
                                <label class="form-label small fw-semibold">Max Age (yrs)</label>
                                <input type="number" name="maxAge" class="form-control form-control-sm" min="0" max="25"
                                       value="${maxAge}">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label small fw-semibold">Sort By</label>
                            <select name="sortBy" class="form-select form-select-sm">
                                <option value="newest" ${sortBy == 'newest' ? 'selected' : ''}>Newest First</option>
                                <option value="name" ${sortBy == 'name' ? 'selected' : ''}>Name (A-Z)</option>
                                <option value="age_asc" ${sortBy == 'age_asc' ? 'selected' : ''}>Age: Youngest First</option>
                                <option value="age_desc" ${sortBy == 'age_desc' ? 'selected' : ''}>Age: Oldest First</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-paw-primary btn-sm w-100 py-2">
                            <i class="bi bi-funnel"></i> Apply Filters
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Pets Results Grid -->
        <div class="col-lg-9">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="fw-bold mb-0">
                    Adoptable Companions
                    <span class="badge bg-secondary rounded-pill fs-6 ms-2">${totalResults} Available</span>
                </h4>
            </div>

            <c:choose>
                <c:when test="${empty pets}">
                    <div class="card card-paw p-5 text-center">
                        <div class="display-3 text-muted mb-3"><i class="bi bi-search"></i></div>
                        <h5>No pets found matching your criteria</h5>
                        <p class="text-muted small">Try broadening your search keywords or resetting filters.</p>
                        <div>
                            <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-outline btn-sm">Clear All Filters</a>
                        </div>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="row g-4">
                        <c:forEach var="pet" items="${pets}">
                            <div class="col-md-6 col-xl-4">
                                <div class="card card-paw h-100">
                                    <div class="pet-card-img-wrapper">
                                        <c:choose>
                                            <c:when test="${pet.photoPath.startsWith('http')}">
                                                <img src="${pet.photoPath}" class="pet-card-img" alt="${pet.name}">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80"
                                                     class="pet-card-img" alt="${pet.name}">
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="badge badge-available position-absolute top-0 end-0 m-2 px-2 py-1 rounded-pill small">
                                            Available
                                        </span>
                                    </div>
                                    <div class="card-body d-flex flex-column p-3">
                                        <div class="d-flex justify-content-between align-items-start mb-1">
                                            <h5 class="fw-bold text-dark mb-0"><c:out value="${pet.name}" /></h5>
                                            <span class="badge bg-light text-dark border small"><c:out value="${pet.type}" /></span>
                                        </div>
                                        <div class="text-muted small mb-2">
                                            <i class="bi bi-tag-fill text-danger me-1"></i> <c:out value="${pet.breed}" /> &bull; ${pet.age} yrs
                                        </div>
                                        <div class="text-muted small mb-2">
                                            <i class="bi bi-geo-alt-fill text-primary me-1"></i> <c:out value="${pet.location}" />
                                        </div>
                                        <p class="small text-secondary flex-grow-1 mb-3">
                                            <c:out value="${pet.description.length() > 80 ? pet.description.substring(0, 80).concat('...') : pet.description}" />
                                        </p>
                                        <div class="pt-2 border-top d-flex justify-content-between align-items-center mt-auto">
                                            <span class="small text-muted text-truncate" style="max-width: 130px;">
                                                <i class="bi bi-building"></i> <c:out value="${pet.shelterName}" />
                                            </span>
                                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-paw-primary btn-sm">
                                                Meet ${pet.name}
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
