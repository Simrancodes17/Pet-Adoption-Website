<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="User Management" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-0">Platform User Directory</h3>
            <p class="text-muted small mb-0">Manage system administrators, shelter accounts, and prospective adopters</p>
        </div>
        <div class="d-flex gap-2 mt-2 mt-md-0">
            <!-- Filter by Role -->
            <form action="${pageContext.request.contextPath}/admin/users" method="get" class="d-flex gap-2">
                <select name="role" class="form-select form-select-sm" onchange="this.form.submit()">
                    <option value="ALL" ${roleFilter == 'ALL' ? 'selected' : ''}>All Roles</option>
                    <option value="ADMIN" ${roleFilter == 'ADMIN' ? 'selected' : ''}>Admins</option>
                    <option value="SHELTER" ${roleFilter == 'SHELTER' ? 'selected' : ''}>Shelters</option>
                    <option value="ADOPTER" ${roleFilter == 'ADOPTER' ? 'selected' : ''}>Adopters</option>
                </select>
            </form>
            <a href="${pageContext.request.contextPath}/admin/users/create" class="btn btn-paw-primary btn-sm">
                <i class="bi bi-person-plus-fill me-1"></i> Add New User
            </a>
        </div>
    </div>

    <div class="card card-paw shadow-sm border-0">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-paw table-hover align-middle mb-0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Contact Details</th>
                            <th>Registered</th>
                            <th class="text-end">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${users}">
                            <tr>
                                <td>#${u.id}</td>
                                <td><strong class="text-dark"><c:out value="${u.name}" /></strong></td>
                                <td><c:out value="${u.email}" /></td>
                                <td>
                                    <span class="badge ${u.role == 'ADMIN' ? 'bg-danger' : (u.role == 'SHELTER' ? 'bg-primary' : 'bg-success')} rounded-pill">
                                        <c:out value="${u.role}" />
                                    </span>
                                </td>
                                <td class="small text-muted"><c:out value="${u.contactInfo}" /></td>
                                <td class="small text-muted"><c:out value="${u.createdAt}" /></td>
                                <td class="text-end">
                                    <a href="${pageContext.request.contextPath}/admin/users/edit?id=${u.id}"
                                       class="btn btn-sm btn-outline-secondary py-1 px-2" title="Edit User">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <form action="${pageContext.request.contextPath}/admin/users/delete" method="post" class="d-inline">
                                        <input type="hidden" name="id" value="${u.id}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger py-1 px-2 confirm-action"
                                                data-confirm="Are you sure you want to delete user account '${u.name}'?" title="Delete User">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
