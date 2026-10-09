<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Pet Feed | Discover Adoptable Companions" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="feed-wrapper">

        <!-- Top Header & Search -->
        <div class="text-center mb-4">
            <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Community Discovery</span>
            <h1 class="fw-bold mt-1 mb-2 text-dark" style="font-size: 2rem;">Pet Discovery Feed</h1>
            <p class="text-secondary small mb-3">
                Explore real rescue pets waiting for loving families. Discover, favorite, share, and apply to adopt.
            </p>

            <!-- Search Bar -->
            <form action="${pageContext.request.contextPath}/feed" method="get" class="mb-3">
                <input type="hidden" name="filter" value="${currentFilter}">
                <input type="hidden" name="species" value="${currentSpecies}">
                <div class="input-group shadow-sm" style="border-radius: var(--paw-radius-pill); overflow: hidden;">
                    <span class="input-group-text bg-white border-end-0 ps-3">
                        <i class="bi bi-search text-muted"></i>
                    </span>
                    <input type="text" name="q" class="form-control border-start-0 border-end-0 py-2"
                           placeholder="Search by name, breed, location, or trait..."
                           value="<c:out value='${searchQuery}' />">
                    <button class="btn btn-paw-primary px-4" type="submit">
                        Find Pets
                    </button>
                </div>
            </form>
        </div>

        <!-- Horizontal Story-Style Discovery Bar -->
        <div class="card card-paw border mb-4 p-2">
            <div class="feed-stories-container">
                <!-- All Stories -->
                <a href="${pageContext.request.contextPath}/feed?filter=all&species=${currentSpecies}"
                   class="feed-story-chip ${currentFilter == 'all' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-grid-fill"></i>
                        </div>
                    </div>
                    <span>All Pets</span>
                </a>

                <!-- New Pets -->
                <a href="${pageContext.request.contextPath}/feed?filter=new&species=${currentSpecies}"
                   class="feed-story-chip ${currentFilter == 'new' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-stars"></i>
                        </div>
                    </div>
                    <span>New Pets</span>
                </a>

                <!-- Urgent Adoption -->
                <a href="${pageContext.request.contextPath}/feed?filter=urgent&species=${currentSpecies}"
                   class="feed-story-chip ${currentFilter == 'urgent' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-heart-pulse-fill"></i>
                        </div>
                    </div>
                    <span>Urgent</span>
                </a>

                <!-- Puppies -->
                <a href="${pageContext.request.contextPath}/feed?filter=puppies&species=Dog"
                   class="feed-story-chip ${currentFilter == 'puppies' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-balloon-heart-fill"></i>
                        </div>
                    </div>
                    <span>Puppies</span>
                </a>

                <!-- Kittens -->
                <a href="${pageContext.request.contextPath}/feed?filter=kittens&species=Cat"
                   class="feed-story-chip ${currentFilter == 'kittens' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-feather"></i>
                        </div>
                    </div>
                    <span>Kittens</span>
                </a>

                <!-- Senior Pets -->
                <a href="${pageContext.request.contextPath}/feed?filter=seniors&species=${currentSpecies}"
                   class="feed-story-chip ${currentFilter == 'seniors' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-calendar-heart-fill"></i>
                        </div>
                    </div>
                    <span>Seniors</span>
                </a>

                <!-- Recently Adopted -->
                <a href="${pageContext.request.contextPath}/feed?filter=adopted&species=${currentSpecies}"
                   class="feed-story-chip ${currentFilter == 'adopted' ? 'active' : ''}">
                    <div class="feed-story-ring">
                        <div class="feed-story-inner">
                            <i class="bi bi-check-circle-fill"></i>
                        </div>
                    </div>
                    <span>Adopted</span>
                </a>
            </div>

            <!-- Species Filter Pills -->
            <div class="d-flex flex-wrap gap-2 pt-2 border-top justify-content-center">
                <a href="${pageContext.request.contextPath}/feed?filter=${currentFilter}&species=ALL"
                   class="badge px-3 py-2 rounded-pill text-decoration-none ${currentSpecies == 'ALL' ? 'bg-success text-white' : 'bg-light text-dark border'}">
                    All Animals
                </a>
                <a href="${pageContext.request.contextPath}/feed?filter=${currentFilter}&species=Dog"
                   class="badge px-3 py-2 rounded-pill text-decoration-none ${currentSpecies == 'Dog' ? 'bg-success text-white' : 'bg-light text-dark border'}">
                    Dogs
                </a>
                <a href="${pageContext.request.contextPath}/feed?filter=${currentFilter}&species=Cat"
                   class="badge px-3 py-2 rounded-pill text-decoration-none ${currentSpecies == 'Cat' ? 'bg-success text-white' : 'bg-light text-dark border'}">
                    Cats
                </a>
                <a href="${pageContext.request.contextPath}/feed?filter=${currentFilter}&species=Other"
                   class="badge px-3 py-2 rounded-pill text-decoration-none ${currentSpecies == 'Other' ? 'bg-success text-white' : 'bg-light text-dark border'}">
                    Other Pets
                </a>
                <c:if test="${not empty searchQuery or currentFilter != 'all' or currentSpecies != 'ALL'}">
                    <a href="${pageContext.request.contextPath}/feed" class="badge px-2 py-2 rounded-pill text-muted text-decoration-none">
                        <i class="bi bi-x-circle me-1"></i> Reset
                    </a>
                </c:if>
            </div>
        </div>

        <!-- Feed Posts Stream -->
        <c:choose>
            <c:when test="${empty feedPets}">
                <!-- Empty State -->
                <div class="card card-paw p-5 text-center my-4 border">
                    <div class="mb-3 text-muted display-4"><i class="bi bi-search"></i></div>
                    <h4 class="fw-bold text-dark mb-2">No Pets Found in this Feed</h4>
                    <p class="text-secondary small mb-4">
                        There are currently no adoptable pets matching your selected filter. Try selecting "All Pets" or resetting your search.
                    </p>
                    <div>
                        <a href="${pageContext.request.contextPath}/feed" class="btn btn-paw-primary btn-sm px-4">
                            <i class="bi bi-arrow-repeat me-1"></i> View Full Feed
                        </a>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <c:forEach var="pet" items="${feedPets}">
                    <article class="feed-post-card scroll-reveal" id="post-pet-${pet.id}">
                        <!-- Post Header -->
                        <header class="feed-post-header">
                            <div class="d-flex align-items-center gap-3">
                                <c:choose>
                                    <c:when test="${pet.photoPath.startsWith('http')}">
                                        <img src="${pet.photoPath}" class="feed-avatar" alt="${pet.name}" data-species="${pet.type}">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="thumbFb" value="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=120&q=80" />
                                        <c:if test="${pet.type == 'Cat'}"><c:set var="thumbFb" value="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=120&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Rabbit'}"><c:set var="thumbFb" value="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=120&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Bird'}"><c:set var="thumbFb" value="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=120&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Hamster'}"><c:set var="thumbFb" value="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=120&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Turtle'}"><c:set var="thumbFb" value="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=120&q=80" /></c:if>
                                        <img src="${thumbFb}" class="feed-avatar" alt="${pet.name}" data-species="${pet.type}">
                                    </c:otherwise>
                                </c:choose>
                                <div>
                                    <h5 class="fw-bold mb-0">
                                        <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}" class="text-dark text-decoration-none">
                                            <c:out value="${pet.name}" />
                                        </a>
                                    </h5>
                                    <div class="small text-muted">
                                        <span><c:out value="${pet.breed}" /></span> &bull;
                                        <span>${pet.age} ${pet.age == 1 ? 'yr' : 'yrs'}</span> &bull;
                                        <span><i class="bi bi-geo-alt text-danger"></i> <c:out value="${pet.location}" /></span>
                                    </div>
                                </div>
                            </div>

                            <div>
                                <span class="badge ${pet.adoptionStatus == 'AVAILABLE' ? 'badge-available' : (pet.adoptionStatus == 'PENDING' ? 'badge-pending' : 'badge-adopted')} rounded-pill px-3 py-1">
                                    <c:out value="${pet.adoptionStatus}" />
                                </span>
                            </div>
                        </header>

                        <!-- Large Pet Media -->
                        <div class="feed-media-container" ondblclick="toggleLike(${pet.id})">
                            <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}">
                                <c:choose>
                                    <c:when test="${pet.photoPath.startsWith('http')}">
                                        <img src="${pet.photoPath}" class="feed-media-img" alt="${pet.name}" data-species="${pet.type}">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="feedImgFb" value="https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=700&q=80" />
                                        <c:if test="${pet.type == 'Cat'}"><c:set var="feedImgFb" value="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=700&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Rabbit'}"><c:set var="feedImgFb" value="https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=700&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Bird'}"><c:set var="feedImgFb" value="https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=700&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Hamster'}"><c:set var="feedImgFb" value="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=700&q=80" /></c:if>
                                        <c:if test="${pet.type == 'Turtle'}"><c:set var="feedImgFb" value="https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=700&q=80" /></c:if>
                                        <img src="${feedImgFb}" class="feed-media-img" alt="${pet.name}" data-species="${pet.type}">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </div>

                        <!-- Action Bar -->
                        <div class="feed-actions-bar">
                            <div class="d-flex align-items-center gap-1">
                                <!-- Interactive Like Button -->
                                <button type="button" class="feed-action-btn like-btn" id="like-btn-${pet.id}"
                                        onclick="toggleLike(${pet.id})" title="Favorite this pet">
                                    <i class="bi bi-heart" id="like-icon-${pet.id}"></i>
                                    <span class="small fw-semibold" id="like-text-${pet.id}">Favorite</span>
                                </button>

                                <!-- Inquire / Details Button -->
                                <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}"
                                   class="feed-action-btn" title="Learn more about ${pet.name}">
                                    <i class="bi bi-chat-left-dots"></i>
                                    <span class="small fw-semibold">Inquire</span>
                                </a>

                                <!-- Share Button -->
                                <button type="button" class="feed-action-btn"
                                        onclick="sharePet(${pet.id}, '<c:out value="${pet.name}" />', '${pageContext.request.contextPath}/pets/details?id=${pet.id}')"
                                        title="Share pet profile">
                                    <i class="bi bi-share"></i>
                                    <span class="small fw-semibold">Share</span>
                                </button>
                            </div>

                            <div>
                                <span class="small text-muted">
                                    <i class="bi bi-building me-1"></i> <c:out value="${pet.shelterName}" />
                                </span>
                            </div>
                        </div>

                        <!-- Content & Description -->
                        <div class="feed-post-content">
                            <p class="feed-post-desc">
                                <strong><c:out value="${pet.name}" /></strong>: <c:out value="${pet.description}" />
                            </p>

                            <div class="d-flex flex-wrap gap-2 mb-3">
                                <span class="badge bg-light text-dark border">
                                    <i class="bi bi-tag-fill text-success me-1"></i> <c:out value="${pet.type}" />
                                </span>
                                <span class="badge bg-light text-dark border">
                                    <c:out value="${pet.breed}" />
                                </span>
                                <c:if test="${not empty pet.gender and pet.gender != 'Unknown'}">
                                    <span class="badge bg-light text-dark border">
                                        <c:out value="${pet.gender}" />
                                    </span>
                                </c:if>
                            </div>

                            <!-- Prominent Adopt Action Button -->
                            <c:choose>
                                <c:when test="${pet.adoptionStatus == 'AVAILABLE'}">
                                    <a href="${pageContext.request.contextPath}/applications/apply?petId=${pet.id}"
                                       class="btn btn-paw-secondary btn-adopt-cta w-100 py-2 fw-bold shadow-sm">
                                        <i class="bi bi-heart-fill me-1"></i> Adopt ${pet.name}
                                    </a>
                                </c:when>
                                <c:when test="${pet.adoptionStatus == 'PENDING'}">
                                    <div class="d-flex gap-2">
                                        <a href="${pageContext.request.contextPath}/pets/details?id=${pet.id}"
                                           class="btn btn-outline-secondary w-100 py-2 fw-semibold">
                                            Application Under Review &bull; View Profile
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-secondary w-100 py-2 disabled" disabled>
                                        <i class="bi bi-house-heart-fill me-1"></i> Happy in Forever Home
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<!-- Toast Feedback for Copy/Share -->
<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 1100">
    <div id="shareToast" class="toast align-items-center text-white bg-dark border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body" id="toastMessage">
                <i class="bi bi-link-45deg me-1"></i> Link copied to clipboard!
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
</div>

<script>
    // Real Client-Side Favorites Persistence (Zero fake likes)
    const LIKES_KEY = 'pawhaven_user_liked_pets';

    function getLikedPets() {
        try {
            return JSON.parse(localStorage.getItem(LIKES_KEY)) || [];
        } catch (e) {
            return [];
        }
    }

    function saveLikedPets(likes) {
        localStorage.setItem(LIKES_KEY, JSON.stringify(likes));
    }

    function updateLikeUI(petId, isLiked) {
        const btn = document.getElementById('like-btn-' + petId);
        const icon = document.getElementById('like-icon-' + petId);
        const text = document.getElementById('like-text-' + petId);
        if (!btn || !icon || !text) return;

        if (isLiked) {
            btn.classList.add('liked');
            icon.className = 'bi bi-heart-fill text-danger';
            text.textContent = 'Favorited';
        } else {
            btn.classList.remove('liked');
            icon.className = 'bi bi-heart';
            text.textContent = 'Favorite';
        }
    }

    function toggleLike(petId) {
        let likes = getLikedPets();
        const index = likes.indexOf(petId);
        if (index >= 0) {
            likes.splice(index, 1);
            saveLikedPets(likes);
            updateLikeUI(petId, false);
        } else {
            likes.push(petId);
            saveLikedPets(likes);
            updateLikeUI(petId, true);
        }
    }

    // Share Pet link helper
    function sharePet(petId, petName, relativeUrl) {
        const fullUrl = window.location.origin + relativeUrl;
        if (navigator.clipboard) {
            navigator.clipboard.writeText(fullUrl).then(() => {
                showToast('Link for ' + petName + ' copied to clipboard!');
            }).catch(() => {
                prompt('Copy adoption link for ' + petName + ':', fullUrl);
            });
        } else {
            prompt('Copy adoption link for ' + petName + ':', fullUrl);
        }
    }

    function showToast(message) {
        const toastEl = document.getElementById('shareToast');
        const toastMsg = document.getElementById('toastMessage');
        if (toastEl && toastMsg) {
            toastMsg.textContent = message;
            const toast = new bootstrap.Toast(toastEl, { delay: 2500 });
            toast.show();
        }
    }

    // Initialize saved likes on page load
    document.addEventListener('DOMContentLoaded', () => {
        const likes = getLikedPets();
        likes.forEach(petId => updateLikeUI(petId, true));
    });
</script>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
