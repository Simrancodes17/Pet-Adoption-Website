<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Online Pet Adoption Platform" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<!-- Hero Section -->
<section class="hero-section">
    <!-- Ambient 3D floating background particles -->
    <div class="hero-3d-bg" aria-hidden="true">
        <div class="ambient-particle particle-1"><i class="bi bi-heart-fill"></i></div>
        <div class="ambient-particle particle-2"><i class="bi bi-heart-pulse-fill"></i></div>
        <div class="ambient-particle particle-3"><i class="bi bi-star-fill"></i></div>
        <div class="ambient-particle particle-4"><i class="bi bi-heart-fill"></i></div>
        <div class="ambient-particle particle-5"><i class="bi bi-emoji-smile-fill"></i></div>
        <div class="ambient-particle particle-6"><i class="bi bi-heart-fill"></i></div>
    </div>

    <div class="container position-relative" style="z-index: 1;">
        <div class="row align-items-center gy-5">
            <div class="col-lg-6">
                <div class="hero-tagline">
                    <i class="bi bi-heart-pulse-fill"></i> Responsible Pet Adoption
                </div>
                <h1 class="hero-title">
                    Find a companion that needs a <span>caring home</span>
                </h1>
                <p class="hero-subtitle">
                    Connect directly with local rescue shelters to adopt dogs, cats, rabbits, birds, hamsters, and turtles. Review temperament, space requirements, and daily care needs before applying.
                </p>
                <div class="d-flex flex-wrap gap-2 mb-3">
                    <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-primary">
                        <i class="bi bi-search me-1"></i> Browse Available Pets
                    </a>
                    <a href="${pageContext.request.contextPath}/breeds" class="btn btn-paw-outline">
                        <i class="bi bi-journal-bookmark me-1"></i> Explore Breed Guides
                    </a>
                    <a href="${pageContext.request.contextPath}/how-it-works" class="btn btn-link text-decoration-none text-dark fw-semibold px-2 py-2">
                        How Adoption Works &rarr;
                    </a>
                </div>
            </div>

            <!-- 3D Interactive Hero Pet Element Stage -->
            <div class="col-lg-6">
                <div class="hero-3d-stage-wrapper">
                    <div class="hero-3d-stage" id="hero3dStage">
                        <!-- Floating 3D Badge: Health Verified -->
                        <div class="hero-3d-layer hero-floating-badge-top">
                            <div class="hero-float-chip">
                                <span class="badge bg-success rounded-circle p-2"><i class="bi bi-shield-check text-white"></i></span>
                                <div>
                                    <div class="text-dark fw-bold" style="font-size: 0.8rem;">Health Verified</div>
                                    <div class="text-muted" style="font-size: 0.72rem;">Shelter Vet Checked</div>
                                </div>
                            </div>
                        </div>

                        <!-- 3D Pet Card Element with Interactive Parallax -->
                        <div class="hero-3d-card" id="hero3dCard">
                            <div class="hero-3d-glare" id="hero3dGlare"></div>
                            <div class="hero-3d-media-wrap">
                                <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=700&q=80"
                                     id="hero3dImg"
                                     class="hero-3d-pet-img"
                                     alt="Friendly adoptable companion">
                                <div class="hero-3d-badge-status">
                                    <span class="pulse-dot"></span>
                                    <span>Ready for Loving Home</span>
                                </div>
                            </div>
                        </div>

                        <!-- Floating 3D Badge: Shelter Companion -->
                        <div class="hero-3d-layer hero-floating-badge-bottom">
                            <div class="hero-float-chip">
                                <span class="badge bg-warning text-dark rounded-circle p-2"><i class="bi bi-heart-fill text-danger"></i></span>
                                <div>
                                    <div class="text-dark fw-bold" style="font-size: 0.8rem;">Loving Companion</div>
                                    <div class="text-muted" style="font-size: 0.72rem;">Meet &amp; Greet Ready</div>
                                </div>
                            </div>
                        </div>

                        <!-- Floating 3D Paw prints in stage -->
                        <div class="hero-3d-layer hero-floating-paw-1 text-primary opacity-50">
                            <i class="bi bi-paw-fill"></i>
                        </div>
                        <div class="hero-3d-layer hero-floating-paw-2 text-warning opacity-50">
                            <i class="bi bi-paw-fill"></i>
                        </div>

                        <!-- Dynamic 3D Ground Shadow -->
                        <div class="hero-3d-ground-shadow" id="hero3dShadow"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Search Form Card Banner -->
        <div class="hero-search-card mt-4 scroll-reveal">
            <h5 class="fw-bold mb-3 text-dark">
                <i class="bi bi-funnel-fill text-success me-2"></i> Find Adoptable Animals
            </h5>
            <form action="${pageContext.request.contextPath}/search" method="get">
                <div class="row g-3 align-items-end">
                    <div class="col-lg-4 col-md-6">
                        <label class="form-label small fw-bold text-muted mb-1">Search Keywords</label>
                        <input type="text" name="q" class="form-control" placeholder="Search by name, breed, traits...">
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <label class="form-label small fw-bold text-muted mb-1">Animal Species</label>
                        <select name="type" class="form-select">
                            <option value="ALL">All Animals</option>
                            <option value="Dog">Dogs</option>
                            <option value="Cat">Cats</option>
                            <option value="Rabbit">Rabbits</option>
                            <option value="Bird">Birds</option>
                            <option value="Hamster">Hamsters</option>
                            <option value="Turtle">Turtles</option>
                        </select>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <label class="form-label small fw-bold text-muted mb-1">Location / City</label>
                        <input type="text" name="location" class="form-control" placeholder="e.g. Austin, Denver">
                    </div>
                    <div class="col-lg-2 col-md-6">
                        <button type="submit" class="btn btn-paw-secondary w-100 py-2">
                            <i class="bi bi-search me-1"></i> Search Pets
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- How Adoption Works Process Section -->
<section class="container my-5 py-3" id="how-it-works-preview">
    <div class="text-center mb-5 scroll-reveal">
        <span class="text-uppercase fw-bold text-muted small" style="letter-spacing: 0.05em;">Step-by-Step Guidance</span>
        <h2 class="fw-bold mt-1 mb-2">How Adoption Works on PawHaven</h2>
        <p class="text-muted small mx-auto" style="max-width: 520px;">
            We keep the adoption process straightforward, transparent, and focused on animal welfare.
        </p>
    </div>

    <div class="row g-4">
        <div class="col-lg-3 col-sm-6">
            <div class="step-card scroll-reveal">
                <div class="step-number">1</div>
                <h5 class="step-title">Browse &amp; Filter</h5>
                <p class="step-desc">
                    Search through adoptable pets across six species. Filter by age, breed, gender, and location to find a good match for your home.
                </p>
            </div>
        </div>
        <div class="col-lg-3 col-sm-6">
            <div class="step-card scroll-reveal">
                <div class="step-number">2</div>
                <h5 class="step-title">Review Care Needs</h5>
                <p class="step-desc">
                    Check the pet's background, personality description, daily exercise needs, and shelter notes before deciding to apply.
                </p>
            </div>
        </div>
        <div class="col-lg-3 col-sm-6">
            <div class="step-card scroll-reveal">
                <div class="step-number">3</div>
                <h5 class="step-title">Submit Application</h5>
                <p class="step-desc">
                    Complete a thoughtful questionnaire about your living environment, schedule, and previous experience with animals.
                </p>
            </div>
        </div>
        <div class="col-lg-3 col-sm-6">
            <div class="step-card scroll-reveal">
                <div class="step-number">4</div>
                <h5 class="step-title">Connect with Shelter</h5>
                <p class="step-desc">
                    Communicate directly with the shelter caretaker via platform messaging to arrange meet &amp; greets and finalize placement.
                </p>
            </div>
        </div>
    </div>
</section>

<!-- Animal Species Directory -->
<section class="container my-5">
    <div class="d-flex flex-wrap justify-content-between align-items-end mb-4 scroll-reveal">
        <div>
            <span class="text-uppercase fw-bold text-muted small" style="letter-spacing: 0.05em;">Companion Categories</span>
            <h2 class="fw-bold mt-1 mb-1">Browse by Animal Type</h2>
            <p class="text-muted small mb-0">Explore adoptable animals and learn about each companion group</p>
        </div>
        <a href="${pageContext.request.contextPath}/breeds" class="btn btn-outline-secondary btn-sm mt-2 mt-sm-0">
            View All Breed Guides &rarr;
        </a>
    </div>

    <div class="row g-3 justify-content-center">
        <!-- Dogs -->
        <div class="col-lg-2 col-md-4 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Dog" class="category-card scroll-reveal">
                <img src="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=300&q=80"
                     class="category-img-avatar" alt="Dogs">
                <div class="category-name">Dogs</div>
                <span class="category-badge-pill">8 Breeds</span>
            </a>
        </div>

        <!-- Cats -->
        <div class="col-lg-2 col-md-4 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Cat" class="category-card scroll-reveal">
                <img src="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=300&q=80"
                     class="category-img-avatar" alt="Cats">
                <div class="category-name">Cats</div>
                <span class="category-badge-pill">6 Breeds</span>
            </a>
        </div>

        <!-- Rabbits -->
        <div class="col-lg-2 col-md-4 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Rabbit" class="category-card scroll-reveal">
                <img src="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=300&q=80"
                     class="category-img-avatar" alt="Rabbits">
                <div class="category-name">Rabbits</div>
                <span class="category-badge-pill">4 Breeds</span>
            </a>
        </div>

        <!-- Birds -->
        <div class="col-lg-2 col-md-4 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Bird" class="category-card scroll-reveal">
                <img src="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=300&q=80"
                     class="category-img-avatar" alt="Birds">
                <div class="category-name">Birds</div>
                <span class="category-badge-pill">4 Breeds</span>
            </a>
        </div>

        <!-- Hamsters -->
        <div class="col-lg-2 col-md-4 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Hamster" class="category-card scroll-reveal">
                <img src="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=300&q=80"
                     class="category-img-avatar" alt="Hamsters">
                <div class="category-name">Hamsters</div>
                <span class="category-badge-pill">3 Breeds</span>
            </a>
        </div>

        <!-- Turtles -->
        <div class="col-lg-2 col-md-4 col-6">
            <a href="${pageContext.request.contextPath}/search?type=Turtle" class="category-card scroll-reveal">
                <img src="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=300&q=80"
                     class="category-img-avatar" alt="Turtles">
                <div class="category-name">Turtles</div>
                <span class="category-badge-pill">3 Breeds</span>
            </a>
        </div>
    </div>
</section>

<!-- Featured Adoptable Pets -->
<section class="container my-5">
    <div class="d-flex flex-wrap justify-content-between align-items-end mb-4 scroll-reveal">
        <div>
            <span class="text-uppercase fw-bold text-muted small" style="letter-spacing: 0.05em;">Current Listings</span>
            <h2 class="fw-bold mt-1 mb-1">Available Pets Awaiting Adoption</h2>
            <p class="text-muted small mb-0">Real pets registered by local shelters ready for loving families</p>
        </div>
        <a href="${pageContext.request.contextPath}/search" class="btn btn-paw-outline btn-sm mt-2 mt-sm-0">
            Browse All Pets <i class="bi bi-arrow-right"></i>
        </a>
    </div>

    <div class="row g-4">
        <c:forEach var="pet" items="${featuredPets}">
            <div class="col-lg-4 col-md-6">
                <div class="card card-paw h-100 d-flex flex-column scroll-reveal">
                    <div class="pet-card-img-wrapper">
                        <c:choose>
                            <c:when test="${pet.photoPath.startsWith('http')}">
                                <img src="${pet.photoPath}" class="pet-card-img" alt="${pet.name}" data-species="${pet.type}">
                            </c:when>
                            <c:otherwise>
                                <c:set var="fbUrl" value="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80" />
                                <c:if test="${pet.type == 'Cat'}"><c:set var="fbUrl" value="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=600&q=80" /></c:if>
                                <c:if test="${pet.type == 'Rabbit'}"><c:set var="fbUrl" value="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80" /></c:if>
                                <c:if test="${pet.type == 'Bird'}"><c:set var="fbUrl" value="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=600&q=80" /></c:if>
                                <c:if test="${pet.type == 'Hamster'}"><c:set var="fbUrl" value="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80" /></c:if>
                                <c:if test="${pet.type == 'Turtle'}"><c:set var="fbUrl" value="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=600&q=80" /></c:if>
                                <img src="${fbUrl}" class="pet-card-img" alt="${pet.name}" data-species="${pet.type}">
                            </c:otherwise>
                        </c:choose>

                        <span class="pet-card-species-badge">
                            <c:out value="${pet.type}" />
                        </span>

                        <span class="pet-card-status-badge badge-available">
                            Available
                        </span>
                    </div>

                    <div class="pet-card-body d-flex flex-column flex-grow-1">
                        <div class="d-flex justify-content-between align-items-center mb-1">
                            <h4 class="pet-name-title mb-0"><c:out value="${pet.name}" /></h4>
                            <c:if test="${not empty pet.gender and pet.gender != 'Unknown'}">
                                <span class="pet-gender-pill">
                                    <i class="bi ${pet.gender == 'Male' ? 'bi-gender-male' : 'bi-gender-female'}"></i>
                                    <c:out value="${pet.gender}" />
                                </span>
                            </c:if>
                        </div>

                        <div class="pet-meta-row">
                            <span class="fw-semibold text-dark"><c:out value="${pet.breed}" /></span>
                            <span>&bull;</span>
                            <span>${pet.age} ${pet.age == 1 ? 'year' : 'years'} old</span>
                        </div>

                        <div class="small text-muted mb-2">
                            <i class="bi bi-geo-alt-fill text-danger me-1"></i> <c:out value="${pet.location}" />
                        </div>

                        <p class="pet-card-desc flex-grow-1">
                            <c:out value="${pet.description}" />
                        </p>

                        <div class="pet-card-footer mt-auto">
                            <span class="small text-muted text-truncate" style="max-width: 140px;">
                                <i class="bi bi-building me-1"></i> <c:out value="${pet.shelterName}" />
                            </span>
                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="btn btn-paw-primary btn-sm">
                                View Profile &rarr;
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<!-- Responsible Adoption Guidance Callout -->
<section class="container my-5 py-4">
    <div class="p-4 p-md-5 rounded-3 border scroll-reveal" style="background-color: var(--paw-bg-subtle);">
        <div class="row align-items-center gy-3">
            <div class="col-lg-8">
                <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Welfare Commitment</span>
                <h3 class="fw-bold mt-1 mb-2 text-dark">Adoption is a Lifetime Promise</h3>
                <p class="text-secondary small mb-0" style="line-height: 1.7;">
                    Pets bring immense joy, but they also rely on you for proper nutrition, veterinary care, patience, and consistent daily companionship. Take time to research the temperament and space needs of each species before completing an application.
                </p>
            </div>
            <div class="col-lg-4 text-lg-end">
                <a href="${pageContext.request.contextPath}/how-it-works" class="btn btn-paw-primary">
                    Read Adoption Guide
                </a>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
