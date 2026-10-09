<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Internal Server Error (500)" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />

<div class="container my-5 text-center">
    <div class="card card-paw p-5 mx-auto shadow-sm" style="max-width: 650px;">
        <div class="display-1 text-warning mb-3"><i class="bi bi-exclamation-triangle-fill"></i></div>
        <h2 class="fw-bold mb-2">500 - Internal Server Error</h2>
        <p class="text-muted mb-4">
            An unexpected error occurred while processing your request. The incident has been safely logged.
        </p>

        <c:if test="${not empty pageContext.exception}">
            <div class="alert alert-danger text-start small font-monospace mb-4">
                <strong>Exception:</strong> <c:out value="${pageContext.exception.message}" />
            </div>
        </c:if>

        <div class="d-flex justify-content-center gap-3">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-paw-primary">
                <i class="bi bi-house-door-fill me-1"></i> Return Home
            </a>
            <button onclick="history.back()" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left me-1"></i> Go Back
            </button>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
