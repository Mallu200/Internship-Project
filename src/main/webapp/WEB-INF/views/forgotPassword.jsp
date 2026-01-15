<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex Enterprise | Password Recovery"/>
<c:set var="pageCss" value="/resources/css/forgotPassword.css"/>
<c:set var="pageJs" value="/resources/js/forgotPassword.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

<header class="navbar navbar-expand-md navbar-light bg-white fixed-top border-bottom shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp">
            <span class="text-primary">PRODEX</span>&nbsp;<span class="text-secondary fw-light">ENTERPRISE</span>
        </a>
        <div class="ms-auto">
            <a href="loginPage" class="btn btn-outline-secondary btn-sm px-3">
                <i class="bi bi-box-arrow-in-right me-1"></i>Sign In
            </a>
        </div>
    </div>
</header>

<main class="recovery-wrapper">
    <div class="container d-flex align-items-center justify-content-center min-vh-100">
        <div class="card shadow-sm border-0 recovery-card">
            <div class="card-body p-4 p-md-5">
                <div class="mb-4">
                    <h3 class="fw-bold text-dark mb-1">Recover Password</h3>
                    <p class="text-muted small">Enter your email to receive a verification OTP.</p>
                </div>

                <div id="jsError" class="alert alert-danger small py-2 mb-3" style="display:none;"></div>

                <form id="forgotPasswordForm" method="post" action="sendOtp" novalidate>

                    <div class="mb-4">
                        <label class="form-label small fw-semibold text-secondary">Registered Email Address</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light border-end-0">
                                <i class="bi bi-envelope text-muted"></i>
                            </span>
                            <input type="email" name="email" id="email"
                                   class="form-control border-start-0 ps-0"
                                   placeholder="e.g. employee@company.com"
                                   value="<c:out value='${emailPrefill}'/>" required>
                        </div>
                        <div id="emailError" class="text-danger small mt-1">
                            <c:if test="${errorField eq 'email'}">${errorMessage}</c:if>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2 fw-bold mb-3" id="sendOtpButton">
                        Request OTP
                    </button>

                    <div class="text-center">
                        <a href="loginPage" class="text-decoration-none small text-secondary">
                            <i class="bi bi-arrow-left me-1"></i>Back to Authentication
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<footer class="fixed-bottom bg-white border-top py-3 text-center text-muted small">
    <div class="container">
        &copy; 2026 Prodex Systems Inc. | Recovery Portal
    </div>
</footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>

</body>
</html>