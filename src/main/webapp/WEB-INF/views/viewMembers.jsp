<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Team Directory"/>
<c:set var="pageCss" value="/resources/css/viewMembers.css"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body class="bg-light">
    <header class="header-custom bg-white border-bottom shadow-sm py-2">
        <div class="container d-flex justify-content-between align-items-center">
            <div class="brand-group">
                <span class="brand-title fw-bold text-primary fs-4">PRODEX</span>
                <span class="brand-subtitle text-muted fw-semibold">FLOW</span>
            </div>
            <div class="navbar-right">
                <a href="${pageContext.request.contextPath}/administrator/administratorDashboardPage?email=${userEmail}" class="btn btn-outline-primary btn-sm">
                    <i class="bi bi-speedometer2 me-2"></i>Dashboard
                </a>
            </div>
        </div>
    </header>

    <div class="container my-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="fw-bold text-dark"><i class="bi bi-people-fill me-2 text-primary"></i> Managed Members
                <span class="badge bg-secondary rounded-pill ms-2 fs-6">${totalMembers}</span>
            </h2>
        </div>

        <%-- Notification Alerts --%>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show shadow-sm border-0 border-start border-4 border-success">
                <i class="bi bi-check-circle-fill me-2"></i>${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show shadow-sm border-0 border-start border-4 border-danger">
                <i class="bi bi-x-circle-fill me-2"></i>${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <%-- Search Filter Card --%>
        <div class="card border-0 shadow-sm rounded-4 mb-4">
            <div class="card-header bg-white fw-bold py-3">
                <i class="bi bi-funnel me-2 text-primary"></i>Search Directory
            </div>
            <div class="card-body p-4">
                <form action="${pageContext.request.contextPath}/administrator/searchMember" method="GET" class="row g-3">
                    <input type="hidden" name="userEmail" value="${userEmail}">

                    <div class="col-md-3">
                        <label class="form-label small fw-bold text-muted text-uppercase">User Name</label>
                        <input type="text" name="searchUserName" class="form-control" placeholder="Name..." value="${param.searchUserName}">
                    </div>

                    <div class="col-md-3">
                        <label class="form-label small fw-bold text-muted text-uppercase">Email</label>
                        <input type="email" name="searchEmail" class="form-control" placeholder="Email..." value="${param.searchEmail}">
                    </div>

                    <div class="col-md-3">
                        <label class="form-label small fw-bold text-muted text-uppercase">Contact</label>
                        <input type="text" name="searchContactNumber" class="form-control" placeholder="Phone..." value="${param.searchContactNumber}">
                    </div>

                    <div class="col-md-3">
                        <label class="form-label small fw-bold text-muted text-uppercase">Account Status</label>
                        <select name="searchAccountLocked" class="form-select">
                            <option value="">All Statuses</option>
                            <option value="false" ${param.searchAccountLocked == 'false' ? 'selected' : ''}>Active</option>
                            <option value="true" ${param.searchAccountLocked == 'true' ? 'selected' : ''}>Locked</option>
                        </select>
                    </div>

                    <div class="col-12 text-end mt-4">
                        <c:if test="${searchMode}">
                            <a href="${pageContext.request.contextPath}/administrator/viewMemberPage?userEmail=${userEmail}" class="btn btn-light me-2 rounded-pill">
                                <i class="bi bi-x-circle me-1"></i>Reset
                            </a>
                        </c:if>
                        <button class="btn btn-primary px-4 rounded-pill shadow-sm" type="submit">
                            <i class="bi bi-search me-2"></i>Apply Filters
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <%-- Members Table --%>
        <div class="table-responsive bg-white rounded-4 shadow-sm border overflow-hidden">
            <table class="table table-hover align-middle mb-0">
                <thead class="bg-light border-bottom">
                    <tr>
                        <th class="ps-4 py-3 text-muted small text-uppercase fw-bold">ID</th>
                        <th class="py-3 text-muted small text-uppercase fw-bold">User Details</th>
                        <th class="py-3 text-muted small text-uppercase fw-bold">Status</th>
                        <th class="py-3 text-muted small text-uppercase fw-bold">Contact Info</th>
                        <th class="text-end pe-4 py-3 text-muted small text-uppercase fw-bold">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty allMembers}">
                            <tr>
                                <td colspan="5" class="text-center py-5">
                                    <i class="bi bi-person-x fs-1 opacity-25 d-block mb-2"></i>
                                    <span class="text-muted">No members found matching your search.</span>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="member" items="${allMembers}">
                                <tr>
                                    <td class="ps-4 text-secondary fw-bold">#${member.enrollmentId}</td>
                                    <td>
                                        <div class="fw-bold text-dark">${member.userName}</div>
                                        <div class="small text-muted">${member.email}</div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not member.accountLocked}">
                                                <span class="badge rounded-pill bg-success-subtle text-success border border-success-subtle px-3">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge rounded-pill bg-danger-subtle text-danger border border-danger-subtle px-3">Locked</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-muted small">
                                        <i class="bi bi-telephone me-1"></i> ${member.contact}
                                    </td>
                                    <td class="text-end pe-4">
                                        <div class="btn-group shadow-sm rounded-pill">
                                            <a href="${pageContext.request.contextPath}/administrator/editMemberPage?id=${member.enrollmentId}&userEmail=${userEmail}"
                                                class="btn btn-sm btn-white border-end px-3">
                                                <i class="bi bi-pencil-square text-primary"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/administrator/deleteMember?id=${member.enrollmentId}&userEmail=${userEmail}"
                                                class="btn btn-sm btn-white px-3"
                                                onclick="return confirm('Confirm deletion of: ${member.userName}?');">
                                                <i class="bi bi-trash3 text-danger"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <%-- Pagination Logic --%>
        <c:if test="${totalMembers > 10}">
            <fmt:parseNumber var="totalPages" integerOnly="true" value="${(totalMembers + 9) / 10}" />
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <c:set var="basePath" value="${pageContext.request.contextPath}/administrator/${searchMode ? 'searchMember' : 'viewMemberPage'}" />

                    <%-- Keep filters in URL while paginating --%>
                    <c:set var="queryParams" value="&userEmail=${userEmail}&searchUserName=${param.searchUserName}&searchEmail=${param.searchEmail}&searchContactNumber=${param.searchContactNumber}&searchAccountLocked=${param.searchAccountLocked}" />

                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link rounded-circle mx-1" href="${basePath}?page=${currentPage - 1}${queryParams}">&laquo;</a>
                    </li>

                    <c:forEach begin="1" end="${totalPages}" var="p">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link rounded-circle mx-1" href="${basePath}?page=${p}${queryParams}">${p}</a>
                        </li>
                    </c:forEach>

                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link rounded-circle mx-1" href="${basePath}?page=${currentPage + 1}${queryParams}">&raquo;</a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>

    <footer class="bg-white border-top py-4 mt-auto">
        <div class="container text-center text-muted small">
            <p class="mb-1">&copy; 2026 Prodex Flow — Management Console</p>
            <div id="datetime" class="opacity-75"></div>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>