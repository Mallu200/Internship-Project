<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Dashboard"/>
<c:set var="pageCss" value="/resources/css/memberDashboard.css"/>
<c:set var="pageJs" value="/resources/js/memberDashboard.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body>

    <nav class="navbar navbar-dark bg-dark shadow-sm fixed-top">
        <div class="container">
            <span class="navbar-brand fw-bold text-primary">PRODEX <span class="text-white fw-light">FLOW</span></span>
            <%-- Updated Logout Link --%>
            <a href="<c:url value='/member/logout'/>" class="btn btn-outline-danger btn-sm rounded-pill px-4">Logout</a>
        </div>
    </nav>

    <main class="container py-5 mt-5">
        <div class="hero-section p-5 mb-5 rounded-4 shadow-sm bg-white border">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h1 class="display-6 fw-bold text-dark">Welcome back,</h1>
                    <p class="fs-4 text-primary fw-medium mb-0">${email}</p>
                </div>
                <div class="col-md-4 text-md-end">
                    <div class="d-inline-flex gap-4 bg-light p-3 rounded-3 border">
                        <div class="text-center px-2">
                            <h3 class="fw-bold mb-0">${totalRequests}</h3>
                            <small class="text-muted small">TOTAL</small>
                        </div>
                        <div class="vr"></div>
                        <div class="text-center px-2">
                            <h3 class="fw-bold text-warning mb-0">${pendingCount}</h3>
                            <small class="text-muted small">PENDING</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-4">
            <%-- New Request Card --%>
            <div class="col-md-4">
                <div class="card h-100 border-0 shadow-sm p-4 text-center action-card">
                    <i class="bi bi-cart-plus-fill fs-1 text-primary mb-3"></i>
                    <h5 class="fw-bold">Procurement</h5>
                    <p class="text-muted small">Submit new purchase requests.</p>
                    <%-- Path points to ProductPurchaseController --%>
                    <a href="<c:url value='/purchase/showForm?userEmail=${email}'/>" class="btn btn-primary rounded-pill mt-auto">New Request</a>
                </div>
            </div>

            <%-- Audit Log / History Card --%>
            <div class="col-md-4">
                <div class="card h-100 border-0 shadow-sm p-4 text-center action-card position-relative">
                    <c:if test="${pendingCount > 0}">
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">${pendingCount}</span>
                    </c:if>
                    <i class="bi bi-clock-history fs-1 text-success mb-3"></i>
                    <h5 class="fw-bold">Audit Log</h5>
                    <p class="text-muted small">Track your request history.</p>
                    <%-- Path points to MemberController --%>
                    <a href="<c:url value='/member/viewPurchaseHistoryPage?userEmail=${email}'/>" class="btn btn-success rounded-pill mt-auto">View History</a>
                </div>
            </div>

            <%-- Account Card --%>
            <div class="col-md-4">
                <div class="card h-100 border-0 shadow-sm p-4 text-center action-card">
                    <i class="bi bi-person-circle fs-1 text-secondary mb-3"></i>
                    <h5 class="fw-bold">Account</h5>
                    <p class="text-muted small">Manage your profile settings.</p>
                    <%-- Path points to MemberController --%>
                    <a href="<c:url value='/member/editProfile?userEmail=${email}'/>" class="btn btn-secondary rounded-pill mt-auto">Edit Profile</a>
                </div>
            </div>
        </div>
    </main>

    <footer class="footer-custom mt-auto py-3 bg-white border-top">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <div class="footer-brand text-muted small">
                    &copy; 2026 <strong>PRODEX FLOW</strong> — Engineered for Excellence
                </div>
                <div id="datetime" class="text-muted small"></div>
            </div>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>