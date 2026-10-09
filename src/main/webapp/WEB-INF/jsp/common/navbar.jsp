<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-paw sticky-top">
    <div class="container">
        <a class="navbar-brand navbar-brand-paw" href="${pageContext.request.contextPath}/home">
            <span class="brand-icon"><i class="bi bi-heart-pulse-fill"></i></span>
            <span>PawHaven</span>
        </a>
        <button class="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse" data-bs-target="#pawNavbar" aria-controls="pawNavbar" aria-expanded="false" aria-label="Toggle navigation">
            <i class="bi bi-list fs-2 text-dark"></i>
        </button>

        <div class="collapse navbar-collapse" id="pawNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-3 gap-1">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/search">
                        Browse Pets
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/feed">
                        <i class="bi bi-collection-play text-success me-1"></i> Pet Feed
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/breeds">
                        Explore Breeds
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/how-it-works">
                        How It Works
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/about">
                        About
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/contact">
                        Contact
                    </a>
                </li>

                <c:if test="${not empty sessionScope.currentUser}">
                    <c:choose>
                        <%-- Admin Navigation --%>
                        <c:when test="${sessionScope.currentUser.role == 'ADMIN'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">
                                    <i class="bi bi-speedometer2"></i> Dashboard
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
                                    Users
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/pets">
                                    Approvals
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">
                                    Profile
                                </a>
                            </li>
                        </c:when>

                        <%-- Shelter Navigation --%>
                        <c:when test="${sessionScope.currentUser.role == 'SHELTER'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/shelter/dashboard">
                                    <i class="bi bi-speedometer2"></i> Dashboard
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/shelter/pets">
                                    My Listings
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/shelter/applications">
                                    Applications
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">
                                    Profile
                                </a>
                            </li>
                        </c:when>

                        <%-- Adopter Navigation --%>
                        <c:when test="${sessionScope.currentUser.role == 'ADOPTER'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/adopter/applications">
                                    My Applications
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">
                                    Profile
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/messages">
                                    Messages
                                </a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:if>
            </ul>

            <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-lg-center gap-2">
                <c:choose>
                    <c:when test="${empty sessionScope.currentUser}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                Login
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-paw-primary btn-sm px-3" href="${pageContext.request.contextPath}/register">
                                Sign Up
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle user-menu-pill d-flex align-items-center gap-2" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="badge ${sessionScope.currentUser.role == 'ADMIN' ? 'bg-dark' : (sessionScope.currentUser.role == 'SHELTER' ? 'bg-secondary' : 'bg-success')} rounded-pill px-2 py-1">${sessionScope.currentUser.role}</span>
                                <span class="fw-semibold small"><c:out value="${sessionScope.currentUser.name}" /></span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow-sm border mt-2" style="border-radius: var(--paw-radius-md);">
                                <li>
                                    <a class="dropdown-item py-2" href="${pageContext.request.contextPath}/profile">
                                        <i class="bi bi-person-circle me-2 text-primary"></i> Profile &amp; Account
                                    </a>
                                </li>
                                <c:if test="${sessionScope.currentUser.role == 'ADOPTER'}">
                                    <li>
                                        <a class="dropdown-item py-2" href="${pageContext.request.contextPath}/adopter/applications">
                                            <i class="bi bi-journal-check me-2 text-success"></i> My Applications
                                        </a>
                                    </li>
                                </c:if>
                                <li>
                                    <a class="dropdown-item py-2" href="${pageContext.request.contextPath}${sessionScope.currentUser.getDashboardPath()}">
                                        <i class="bi bi-grid-fill me-2 text-success"></i> Dashboard
                                    </a>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item py-2 text-danger" href="${pageContext.request.contextPath}/logout">
                                        <i class="bi bi-box-arrow-right me-2"></i> Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
