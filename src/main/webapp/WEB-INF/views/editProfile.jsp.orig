<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Edit Profile"/>
<c:set var="pageCss" value="/resources/css/editProfile.css"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

    <%-- Navigation Bar --%>
    <nav class="navbar navbar-dark bg-dark shadow-sm fixed-top">
        <div class="container">
            <span class="navbar-brand fw-bold text-primary">PRODEX <span class="text-white fw-light">FLOW</span></span>
            <div class="ms-auto">
                <a href="<c:url value='/member/memberDashboardPage?email=${userEmail}'/>" class="btn btn-outline-light btn-sm px-3 rounded-pill">
                    <i class="bi bi-arrow-left me-1"></i> Dashboard
                </a>
            </div>
        </div>
    </nav>

    <main class="container py-5 mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                    <div class="card-header bg-white border-bottom py-3">
                        <h4 class="mb-0 fw-bold text-dark">
                            <i class="bi bi-person-gear text-primary me-2"></i> Account Settings
                        </h4>
                    </div>

                    <div class="card-body p-4 p-md-5">
                        <%-- Success/Error Messages from RedirectAttributes --%>
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success border-0 shadow-sm mb-4">
                                <i class="bi bi-check-circle me-2"></i> ${successMessage}
                            </div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger border-0 shadow-sm mb-4">
                                <i class="bi bi-exclamation-triangle me-2"></i> ${errorMessage}
                            </div>
                        </c:if>

                        <%--
                             FORM ACTION: Must match @PostMapping("/updateProfile")
                             FIELD NAMES: Must match EnrollmentDto properties
                        --%>
                        <form action="<c:url value='/member/updateProfile'/>" method="POST">

                            <%-- Hidden field to pass the email back to the controller --%>
                            <input type="hidden" name="email" value="${user.email}">

                            <div class="mb-4">
                                <label class="form-label fw-semibold text-muted">Email Address</label>
                                <input type="text" class="form-control bg-light border-0 shadow-none" value="${user.email}" disabled>
                                <div class="form-text">Primary identifier (cannot be modified).</div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-semibold text-dark">Full Name *</label>
                                <input type="text" name="userName" class="form-control" value="${user.userName}" required placeholder="Enter your full name">
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-semibold text-dark">Mobile Number *</label>
                                <%--
                                    CRITICAL FIX: Changed name="mobile" to name="contact"
                                    to match your EnrollmentDto property
                                --%>
                                <input type="tel" name="contact" class="form-control" value="${user.contact}" required placeholder="e.g. 9876543210">
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-semibold text-dark">Update Password</label>
                                <input type="password" name="password" class="form-control" placeholder="Leave blank to keep current password">
                                <div class="form-text text-warning">Only fill this if you want to change your password.</div>
                            </div>

                            <div class="pt-3 d-grid">
                                <button type="submit" class="btn btn-primary py-2 fw-bold rounded-pill">
                                    <i class="bi bi-save me-2"></i> Save Profile Changes
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="mt-auto py-3 bg-white border-top">
        <div class="container text-center">
            <p class="text-muted mb-0 small">
                &copy; 2026 <strong>PRODEX FLOW</strong> | <span id="datetime"></span>
            </p>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>