<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-dark navbar-paw sticky-top">
    <div class="container">
        <a class="navbar-brand navbar-brand-paw" href="${pageContext.request.contextPath}/home">
            <i class="bi bi-heart-pulse-fill"></i> PawHaven
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#pawNavbar" aria-controls="pawNavbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="pawNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/search">
                        <i class="bi bi-search"></i> Browse Pets
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
                                    <i class="bi bi-people"></i> Users
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/pets">
                                    <i class="bi bi-check2-circle"></i> Approvals
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/analytics">
                                    <i class="bi bi-bar-chart-line"></i> Analytics
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/settings">
                                    <i class="bi bi-gear"></i> Settings
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
                                    <i class="bi bi-card-list"></i> My Pets
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/shelter/pets/create">
                                    <i class="bi bi-plus-circle"></i> List Pet
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/shelter/applications">
                                    <i class="bi bi-journal-text"></i> Applications
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/messages">
                                    <i class="bi bi-chat-dots"></i> Messages
                                </a>
                            </li>
                        </c:when>

                        <%-- Adopter Navigation --%>
                        <c:when test="${sessionScope.currentUser.role == 'ADOPTER'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/adopter/dashboard">
                                    <i class="bi bi-speedometer2"></i> Dashboard
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/adopter/applications">
                                    <i class="bi bi-file-earmark-check"></i> My Applications
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/messages">
                                    <i class="bi bi-chat-dots"></i> Messages
                                </a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:if>
            </ul>

            <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
                <c:choose>
                    <c:when test="${empty sessionScope.currentUser}">
                        <li class="nav-item me-2">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                <i class="bi bi-box-arrow-in-right"></i> Sign In
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-paw-primary btn-sm" href="${pageContext.request.contextPath}/register">
                                <i class="bi bi-person-plus"></i> Join Platform
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-white d-flex align-items-center gap-2" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="badge bg-danger rounded-pill">${sessionScope.currentUser.role}</span>
                                <strong><c:out value="${sessionScope.currentUser.name}" /></strong>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow">
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}${sessionScope.currentUser.getDashboardPath()}">
                                        <i class="bi bi-grid-fill me-2 text-primary"></i> My Dashboard
                                    </a>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                        <i class="bi bi-box-arrow-right me-2"></i> Sign Out
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
