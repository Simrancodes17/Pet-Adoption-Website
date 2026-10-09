<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="isEdit" value="${not empty targetUser}" />
<c:set var="pageTitle" value="${isEdit ? 'Edit User' : 'Create New User'}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
            <div class="card card-paw shadow-sm">
                <div class="card-header bg-white py-3 border-bottom">
                    <h5 class="fw-bold mb-0">
                        <i class="bi bi-person-gear text-primary me-2"></i> ${isEdit ? 'Edit User Account' : 'Register New User'}
                    </h5>
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/admin/users/${isEdit ? 'edit' : 'create'}" method="post">
                        <c:if test="${isEdit}">
                            <input type="hidden" name="id" value="${targetUser.id}">
                        </c:if>

                        <div class="mb-3">
                            <label for="name" class="form-label fw-semibold">Full Name or Shelter Org Name</label>
                            <input type="text" class="form-control" id="name" name="name"
                                   value="<c:out value='${isEdit ? targetUser.name : ""}' />" required>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label fw-semibold">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="<c:out value='${isEdit ? targetUser.email : ""}' />" required>
                        </div>

                        <c:if test="${not isEdit}">
                            <div class="mb-3">
                                <label for="password" class="form-label fw-semibold">Initial Password</label>
                                <input type="password" class="form-control" id="password" name="password" required minlength="6"
                                       placeholder="Min 6 characters">
                            </div>
                        </c:if>

                        <div class="mb-3">
                            <label for="role" class="form-label fw-semibold">Role Authority</label>
                            <select class="form-select" id="role" name="role" required>
                                <option value="ADOPTER" ${isEdit and targetUser.role == 'ADOPTER' ? 'selected' : ''}>ADOPTER (Browse & Apply)</option>
                                <option value="SHELTER" ${isEdit and targetUser.role == 'SHELTER' ? 'selected' : ''}>SHELTER (List Pets & Review)</option>
                                <option value="ADMIN" ${isEdit and targetUser.role == 'ADMIN' ? 'selected' : ''}>ADMIN (Platform Management)</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="contactInfo" class="form-label fw-semibold">Contact Information</label>
                            <input type="text" class="form-control" id="contactInfo" name="contactInfo"
                                   value="<c:out value='${isEdit ? targetUser.contactInfo : ""}' />"
                                   placeholder="Address, phone, or notes">
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary">
                                Cancel
                            </a>
                            <button type="submit" class="btn btn-paw-primary">
                                <i class="bi bi-check-circle me-1"></i> ${isEdit ? 'Save Changes' : 'Create User'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
