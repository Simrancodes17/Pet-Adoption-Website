<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Application Error" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />

<div class="container my-5 text-center">
    <div class="card card-paw p-5 mx-auto shadow-sm border-0" style="max-width: 650px;">
        <div class="display-1 text-danger mb-3"><i class="bi bi-shield-x"></i></div>
        <h2 class="fw-bold mb-2 text-dark">Something Went Wrong</h2>
        <p class="text-muted mb-4">
            We encountered an unexpected situation. Please return to the homepage or try your request again.
        </p>

        <c:if test="${not empty pageContext.exception}">
            <div class="alert alert-secondary text-start small font-monospace mb-4">
                <strong>Details:</strong> <c:out value="${pageContext.exception.message}" />
            </div>
        </c:if>

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
