<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Edit Admin Profile"/>
<c:set var="pageCss" value="/resources/css/adminDashboard.css"/>
<c:set var="pageJs" value="/resources/js/adminDashboard.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body class="d-flex flex-column min-vh-100">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top shadow">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold" href="adminDashboard?userEmail=${email}">
                <span class="text-primary">PRODEX</span><span class="text-white fw-light">FLOW</span>
            </a>
            <div class="ms-auto d-flex align-items-center">
                <a href="adminDashboard?userEmail=${email}" class="btn btn-outline-light btn-sm rounded-pill px-3">
                    <i class="bi bi-arrow-left me-1"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </nav>

    <main class="flex-grow-1" style="margin-top: 100px;">
        <div class="container">
            <%-- Breadcrumb for easy navigation --%>
            <nav aria-label="breadcrumb" class="mb-4">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="adminDashboard?userEmail=${email}">Dashboard</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Edit Profile</li>
                </ol>
            </nav>

            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="profile-card p-4 p-md-5">
                        <div class="text-center mb-5">
                            <div class="display-6 fw-800 text-dark mb-2">Account Settings</div>
                            <p class="text-muted">Update your administrative credentials and personal information.</p>
                        </div>

                        <form action="updateAdminProfile" method="POST" class="needs-validation" novalidate>
                            <input type="hidden" name="oldEmail" value="${email}">

                            <div class="row g-4">
                                <%-- Full Name --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold small text-muted">Full Name</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0"><i class="bi bi-person"></i></span>
                                        <input type="text" name="fullName" class="form-control border-start-0" value="${adminName}" required>
                                    </div>
                                </div>

                                <%-- Email Address (Primary ID) --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold small text-muted">Email Address</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0"><i class="bi bi-envelope"></i></span>
                                        <input type="email" name="newEmail" class="form-control border-start-0" value="${email}" required>
                                    </div>
                                </div>

                                <%-- Contact Number --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold small text-muted">Contact Number</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0"><i class="bi bi-telephone"></i></span>
                                        <input type="text" name="contact" class="form-control border-start-0" value="${adminPhone}" required>
                                    </div>
                                </div>

                                <%-- New Password (Optional) --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold small text-muted">New Password (Leave blank to keep current)</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0"><i class="bi bi-shield-lock"></i></span>
                                        <input type="password" name="password" class="form-control border-start-0" placeholder="••••••••">
                                    </div>
                                </div>

                                <div class="col-12 mt-5">
                                    <hr class="opacity-10 mb-4">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <button type="reset" class="btn btn-light rounded-pill px-4">Discard Changes</button>
                                        <button type="submit" class="btn btn-primary rounded-pill px-5 fw-bold">Save Profile</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="admin-footer mt-auto py-3 bg-white border-top">
        <div class="container text-center">
            <span class="small text-muted">&copy; 2026 <strong>PRODEX FLOW</strong> — Security & Privacy Guaranteed</span>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>