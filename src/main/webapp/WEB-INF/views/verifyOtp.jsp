<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex Enterprise | OTP Verification"/>
<c:set var="pageCss" value="/resources/css/verifyOtp.css"/>
<c:set var="pageJs" value="/resources/js/verifyOtp.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

<header class="navbar navbar-expand-md navbar-light bg-white fixed-top border-bottom shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp">
            <span class="text-primary">PRODEX</span>&nbsp;<span class="text-secondary fw-light">ENTERPRISE</span>
        </a>
    </div>
</header>

<main class="verify-wrapper">
    <div class="container d-flex align-items-center justify-content-center min-vh-100">
        <div class="card shadow-sm border-0 verify-card">
            <div class="card-body p-4 p-md-5">
                <div class="text-center mb-4">
                    <h3 class="fw-bold text-dark mb-1">Verify OTP</h3>
                    <p class="text-muted small">Enter the code sent to your email.</p>
                </div>

                <input type="hidden" id="emailPrefill" value="${emailPrefill}">
                <input type="hidden" id="remainingTimeHolder" value="${remainingTime}">

                <c:set var="errorMsg" value="${not empty errorMessage ? errorMessage : param.errorMessage}" />
                <div id="otpFeedback" class="alert alert-danger small py-2 mb-3"
                     style="${not empty errorMsg ? 'display:block;' : 'display:none;'}">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <span>${errorMsg}</span>
                </div>

                <form id="verifyOtpForm" method="post" action="verifyOtp" novalidate>
                    <input type="hidden" name="email" value="${emailPrefill}">

                    <div class="mb-3 text-center">
                        <label class="form-label small fw-semibold text-secondary">Security Code</label>
                        <input type="text" name="otp" id="otp"
                               class="form-control otp-input text-center fw-bold fs-4 ${not empty errorMsg ? 'is-invalid' : ''}"
                               placeholder="0000" maxlength="4" autocomplete="off" required>
                    </div>

                    <div class="text-center mb-4">
                        <div class="timer-bubble small fw-medium text-muted" id="timerContainer">
                            Expires in: <span id="timerDisplay" class="text-primary fw-bold">--:--</span>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2 fw-bold mb-3" id="verifyOtpButton" disabled>
                        Confirm Code
                    </button>
                </form>

                <div class="text-center">
                    <button type="button" class="btn btn-link text-decoration-none small fw-semibold" id="jsResendButton" disabled>
                        <i class="bi bi-arrow-clockwise me-1"></i>Resend OTP
                    </button>
                </div>
            </div>
        </div>
    </div>
</main>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>

</body>
</html>