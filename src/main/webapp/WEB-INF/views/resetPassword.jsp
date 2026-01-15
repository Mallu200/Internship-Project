<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex Enterprise | Secure Password Reset"/>
<c:set var="pageCss" value="/resources/css/resetPassword.css"/>
<c:set var="pageJs" value="/resources/js/resetPassword.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

<header class="navbar navbar-expand-md navbar-light bg-white fixed-top border-bottom shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp">
            <span class="text-primary">PRODEX</span>&nbsp;<span class="text-secondary fw-light">ENTERPRISE</span>
        </a>
    </div>
</header>

<main class="reset-wrapper">
    <div class="container d-flex align-items-center justify-content-center min-vh-100">
        <div class="card shadow-sm border-0 reset-card">
            <div class="card-body p-4 p-md-5">
                <div class="text-center mb-4">
                    <h3 class="fw-bold text-dark mb-1">Set New Password</h3>
                    <p class="text-muted small">Establish a strong password to secure your account.</p>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger small py-2 mb-3">
                        <i class="bi bi-exclamation-circle me-2"></i>${errorMessage}
                    </div>
                </c:if>

                <form id="resetPasswordForm" method="post" action="updatePassword" novalidate>
                    <div class="mb-3">
                        <label class="form-label small fw-semibold text-secondary">Account Email</label>
                        <input type="text" class="form-control bg-light border-0 small py-2" value="${emailPrefill}" readonly>
                        <input type="hidden" name="email" value="${emailPrefill}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-semibold text-secondary">New Password</label>
                        <div class="input-group">
                            <span class="input-group-text bg-white border-end-0"><i class="bi bi-lock"></i></span>
                            <input type="password" name="password" id="password"
                                   class="form-control border-start-0 py-2" placeholder="••••••••" required>
                            <button type="button" class="btn btn-outline-light border border-start-0 text-secondary" id="togglePassword">
                                <i class="bi bi-eye"></i>
                            </button>
                        </div>

                        <div class="password-policy-box mt-2 p-2 rounded border bg-light">
                            <ul class="list-unstyled mb-0 small" id="policyList">
                                <li id="p_length" class="text-muted"><i class="bi bi-circle me-1"></i> 8-20 characters</li>
                                <li id="p_upper" class="text-muted"><i class="bi bi-circle me-1"></i> One uppercase letter</li>
                                <li id="p_lower" class="text-muted"><i class="bi bi-circle me-1"></i> One lowercase letter</li>
                                <li id="p_number" class="text-muted"><i class="bi bi-circle me-1"></i> One numeric digit</li>
                                <li id="p_special" class="text-muted"><i class="bi bi-circle me-1"></i> One special character</li>
                            </ul>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label class="form-label small fw-semibold text-secondary">Confirm Password</label>
                        <div class="input-group">
                            <span class="input-group-text bg-white border-end-0"><i class="bi bi-shield-lock"></i></span>
                            <input type="password" name="confirmPassword" id="confirmPassword"
                                   class="form-control border-start-0 py-2" placeholder="••••••••" required>
                        </div>
                        <div id="confirmError" class="invalid-feedback">Passwords do not match.</div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2 fw-bold" id="resetPasswordButton" disabled>
                        Update Password
                    </button>
                </form>

                <div class="text-center mt-4">
                    <a href="loginPage" class="text-decoration-none small text-secondary">
                        <i class="bi bi-arrow-left me-1"></i>Return to Login
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="fixed-bottom bg-white border-top py-3 text-center text-muted small">
    <div class="container">
        &copy; 2026 Prodex Systems Inc. | Password Management
    </div>
</footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>