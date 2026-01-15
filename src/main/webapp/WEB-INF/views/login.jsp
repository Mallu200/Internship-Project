<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex Enterprise | Secure Login"/>
<c:set var="pageCss" value="/resources/css/login.css"/>
<c:set var="pageJs" value="/resources/js/login.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

<header class="navbar navbar-expand-md navbar-light bg-white fixed-top border-bottom shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp">
            <span class="text-primary">PRODEX</span>&nbsp;<span class="text-secondary fw-light">ENTERPRISE</span>
        </a>
        <div class="ms-auto">
            <a href="index.jsp" class="btn btn-outline-secondary btn-sm px-3">
                <i class="bi bi-arrow-left me-1"></i>Back
            </a>
        </div>
    </div>
</header>

<main class="login-wrapper">
    <div class="container d-flex align-items-center justify-content-center min-vh-100">
        <div class="card shadow-sm border-0 login-card">
            <div class="card-body p-4 p-md-5">
                <div class="mb-4">
                    <h3 class="fw-bold text-dark mb-1">Sign In</h3>
                    <p class="text-muted small">Access the organizational portal</p>
                </div>

                <div id="jsError" class="alert alert-danger small py-2 mb-3" style="display:none;"></div>

                <form id="loginForm" method="post" action="loginUser" novalidate>

                    <div class="mb-3">
                        <label class="form-label small fw-semibold text-secondary">Email or Contact Number</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light border-end-0">
                                <i class="bi bi-person text-muted"></i>
                            </span>
                            <input type="text" name="emailOrContact" id="emailOrContact"
                                   class="form-control border-start-0 ps-0"
                                   placeholder="Enter registered ID"
                                   value="${emailPrefill}" required>
                        </div>
                        <div id="emailError" class="text-danger small mt-1"></div>
                    </div>

                    <div class="mb-4">
                        <label class="form-label small fw-semibold text-secondary">Password</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light border-end-0">
                                <i class="bi bi-lock text-muted"></i>
                            </span>
                            <input type="password" name="password" id="password"
                                   class="form-control border-start-0 border-end-0 ps-0"
                                   placeholder="••••••••" required>
                            <button class="btn btn-light border border-start-0 text-muted" type="button" id="togglePassword">
                                <i class="bi bi-eye"></i>
                            </button>
                        </div>
                        <div id="passwordError" class="text-danger small mt-1"></div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2 fw-bold mb-3" id="loginButton">
                        Authenticate
                    </button>

                    <div class="text-center">
                        <a href="forgotPasswordPage" class="text-decoration-none small text-primary">Trouble signing in?</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<footer class="fixed-bottom bg-white border-top py-3 text-center text-muted small">
    <div class="container">
        &copy; 2026 Prodex Systems Inc. | Authorized Personnel Only
    </div>
</footer>

<%@ include file="/WEB-INF/views/includes/footer.jspf" %>

</body>
</html>