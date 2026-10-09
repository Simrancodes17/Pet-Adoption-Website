<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Page Not Found (404)" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />

<div class="container my-5 text-center">
    <div class="card card-paw p-5 mx-auto shadow-sm border-0" style="max-width: 600px;">
        <div class="display-1 text-primary mb-3"><i class="bi bi-question-circle"></i></div>
        <h2 class="fw-bold mb-2 text-dark">404 - Page Not Found</h2>
        <p class="text-muted mb-4">
            The page, pet profile, or resource you were looking for does not exist or has been relocated.
        </p>
        <div class="d-flex justify-content-center gap-3">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-paw-primary">
                <i class="bi bi-house-door-fill me-1"></i> Return Home
            </a>
            <a href="${pageContext.request.contextPath}/search" class="btn btn-outline-secondary">
                <i class="bi bi-search me-1"></i> Browse Pets
            </a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
