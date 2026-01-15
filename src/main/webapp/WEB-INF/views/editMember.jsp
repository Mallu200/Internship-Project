<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<c:set var="pageTitle" value="Prodex | Edit Member: ${memberDto.userName}"/>
<c:set var="pageCss" value="/resources/css/editMember.css"/>
<c:set var="pageJs" value="/resources/js/editMember.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body class="d-flex flex-column min-vh-100 bg-light">

    <header class="header-custom bg-white border-bottom shadow-sm py-2">
        <div class="container d-flex justify-content-between align-items-center">
            <div class="brand-group d-flex align-items-baseline">
                <span class="brand-title fw-bold fs-4 text-primary">PRODEX</span>
                <span class="brand-subtitle ms-1 text-muted fw-semibold">FLOW</span>
            </div>
            <div class="navbar-right">
                <a href="${pageContext.request.contextPath}/administrator/administratorDashboardPage?email=${userEmail}" class="btn btn-outline-primary btn-sm">
                    <i class="bi bi-speedometer2 me-2"></i>Dashboard
                </a>
            </div>
        </div>
    </header>

    <main class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                    <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                        <h5 class="mb-0 fw-bold">
                            <i class="bi bi-person-gear me-2 text-primary"></i>Update Member Record
                        </h5>
                        <a href="viewMemberPage?userEmail=${userEmail}" class="btn btn-sm btn-light">
                            <i class="bi bi-arrow-left me-1"></i>Back to List
                        </a>
                    </div>

                    <div class="card-body p-4 p-md-5 bg-white">
                        <%-- Display Messages --%>
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <i class="bi bi-check-circle-fill me-2"></i>${successMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>

                        <%-- Update Form --%>
                        <form id="memberForm" action="${pageContext.request.contextPath}/administrator/updateMember" method="POST">
                            <%-- Context Hidden Fields --%>
                            <input type="hidden" name="userEmail" value="${userEmail}" />
                            <input type="hidden" name="enrollmentId" value="${memberDto.enrollmentId}" />

                            <div class="row g-4">
                                <div class="col-12">
                                    <div class="d-flex align-items-center text-muted small bg-light p-2 rounded">
                                        <span class="me-3">ID Reference: <span class="fw-bold text-dark">#${memberDto.enrollmentId}</span></span>
                                        <span class="me-3">|</span>
                                        <span>Current Role: <span class="badge bg-secondary">${memberDto.role}</span></span>
                                    </div>
                                </div>

                                <%-- Name --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Full Name</label>
                                    <input type="text" name="userName" value="${memberDto.userName}"
                                           class="form-control" required placeholder="Full Name">
                                </div>

                                <%-- Email --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Email Address</label>
                                    <input type="email" name="email" value="${memberDto.email}"
                                           class="form-control" required placeholder="name@example.com">
                                </div>

                                <%-- Contact --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Contact Number</label>
                                    <input type="text" name="contact" value="${memberDto.contact}"
                                           class="form-control" required maxlength="10">
                                </div>

                                <%-- Status --%>
                                <div class="col-md-3">
                                    <label class="form-label fw-semibold">Account Status</label>
                                    <select name="accountLocked" class="form-select">
                                        <option value="false" ${not memberDto.accountLocked ? 'selected' : ''}>Active</option>
                                        <option value="true" ${memberDto.accountLocked ? 'selected' : ''}>Locked</option>
                                    </select>
                                </div>

                                <%-- Role --%>
                                <div class="col-md-3">
                                    <label class="form-label fw-semibold">Access Level</label>
                                    <select name="role" class="form-select">
                                        <option value="MEMBER" ${memberDto.role == 'MEMBER' ? 'selected' : ''}>Member</option>
                                        <option value="ADMINISTRATOR" ${memberDto.role == 'ADMINISTRATOR' ? 'selected' : ''}>Administrator</option>
                                    </select>
                                </div>

                                <%-- Security Section --%>
                                <div class="col-12 mt-4">
                                    <div class="border rounded-4 p-4">
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <h6 class="mb-0 fw-bold text-dark"><i class="bi bi-shield-lock me-2"></i>Change Password</h6>
                                            <button type="button" id="togglePasswordBtn" class="btn btn-sm btn-outline-dark">
                                                <i class="bi bi-pencil-square me-1"></i>Enable Reset
                                            </button>
                                        </div>

                                        <div id="passwordFieldsSection" style="display: none;" class="row g-3 pt-2">
                                            <div class="col-md-6">
                                                <label class="form-label small">New Password</label>
                                                <input type="password" id="newPassword" name="password" class="form-control" placeholder="New Secret Password">
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label small">Confirm Password</label>
                                                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Re-type Password">
                                            </div>
                                            <div class="col-12 mt-3">
                                                <div class="alert alert-light border small text-muted mb-0">
                                                    <i class="bi bi-info-circle me-2"></i>Passwords must match. Leave these fields blank to keep the current password.
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%-- Form Submission --%>
                                <div class="col-12 text-end mt-4">
                                    <hr class="mb-4">
                                    <button type="submit" class="btn btn-primary btn-lg px-5">
                                        <i class="bi bi-save me-2"></i>Update Member
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="bg-white border-top py-4 mt-auto">
        <div class="container text-center text-muted small">
            <p class="mb-0">&copy; 2026 Prodex Flow Management System</p>
            <div id="datetime" class="mt-1 opacity-75"></div>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>