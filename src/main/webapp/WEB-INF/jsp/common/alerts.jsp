<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container mt-3">
    <%-- Flash Success from Session --%>
    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm border d-flex align-items-center" role="alert" style="border-radius: var(--paw-radius-md);">
            <i class="bi bi-check-circle-fill me-2 fs-5 text-success"></i>
            <div><c:out value="${sessionScope.flashSuccess}" /></div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>

    <%-- Flash Error from Session --%>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm border d-flex align-items-center" role="alert" style="border-radius: var(--paw-radius-md);">
            <i class="bi bi-exclamation-triangle-fill me-2 fs-5 text-danger"></i>
            <div><c:out value="${sessionScope.flashError}" /></div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <%-- Request-Scoped Error Message --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm border d-flex align-items-center" role="alert" style="border-radius: var(--paw-radius-md);">
            <i class="bi bi-x-circle-fill me-2 fs-5 text-danger"></i>
            <div><c:out value="${errorMessage}" /></div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <%-- Request-Scoped Success Message --%>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm border d-flex align-items-center" role="alert" style="border-radius: var(--paw-radius-md);">
            <i class="bi bi-check-circle-fill me-2 fs-5 text-success"></i>
            <div><c:out value="${successMessage}" /></div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
</div>
