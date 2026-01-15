<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Admin Control Center"/>
<c:set var="pageCss" value="/resources/css/adminDashboard.css"/>
<c:set var="pageJs" value="/resources/js/adminDashboard.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body class="d-flex flex-column min-vh-100 bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark fixed-top shadow-sm">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold" href="administratorDashboardPage?email=${email}">
                <span class="text-primary">PRODEX</span><span class="text-white fw-light">FLOW</span>
            </a>

            <div class="ms-auto d-flex align-items-center">
                <%-- Notification Bell --%>
                <c:if test="${pendingRequestCount > 0}">
                    <a href="viewPendingRequests?userEmail=${email}" class="position-relative me-4">
                        <i class="bi bi-bell-fill fs-4 text-warning"></i>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                            ${pendingRequestCount}
                        </span>
                    </a>
                </c:if>

                <%-- Profile Dropdown --%>
                <div class="dropdown">
                    <button class="btn btn-link text-white text-decoration-none dropdown-toggle d-flex align-items-center" type="button" data-bs-toggle="dropdown">
                        <div class="text-end me-2 d-none d-lg-block">
                            <small class="d-block opacity-75" style="font-size: 10px;">ADMINISTRATOR</small>
                            <span class="small fw-bold">${email}</span>
                        </div>
                        <i class="bi bi-person-circle fs-3 text-primary"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end shadow border-0 mt-2">
                        <li><h6 class="dropdown-header">Management</h6></li>
                        <li><a class="dropdown-item" href="editAdminProfile?email=${email}"><i class="bi bi-pencil-square me-2"></i> Edit Profile</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-danger" href="logout"><i class="bi bi-box-arrow-right me-2"></i> Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <main class="flex-grow-1">
        <header class="hero-section text-center py-5 text-white" style="margin-top: 56px;">
            <div class="container">
                <h1 class="display-5 fw-800">Welcome Back, ${adminDto.userName != null ? adminDto.userName : 'Admin'}</h1>
                <p class="lead opacity-75">Centralized operations and system administration hub.</p>
            </div>
        </header>

        <div class="container py-5">
            <div class="row g-4 mb-5">
                <div class="col-md-4">
                    <div class="stats-card p-4 shadow-sm bg-white border-start border-primary border-5">
                        <small class="text-muted fw-bold text-uppercase small">Team Members</small>
                        <h2 class="mb-0 fw-800 text-dark">${totalMembers != null ? totalMembers : '0'}</h2>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stats-card p-4 shadow-sm bg-white border-start border-danger border-5">
                        <small class="text-muted fw-bold text-uppercase small">Pending Approvals</small>
                        <h2 class="mb-0 fw-800 text-danger">${pendingRequestCount != null ? pendingRequestCount : '0'}</h2>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stats-card p-4 shadow-sm bg-white border-start border-success border-5">
                        <small class="text-muted fw-bold text-uppercase small">System Health</small>
                        <div class="d-flex align-items-center">
                            <div class="spinner-grow spinner-grow-sm text-success me-2"></div>
                            <h4 class="mb-0 fw-bold text-success">ONLINE</h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row g-4 mb-5">
                <div class="col-12"><h3 class="fw-bold mb-4">Control Modules</h3></div>

                <div class="col-md-4">
                    <div class="card h-100 shadow-sm border-0 admin-card p-3">
                        <div class="card-body text-center d-flex flex-column">
                            <div class="icon-box bg-primary-subtle text-primary mx-auto">
                                <i class="bi bi-people-fill fs-2"></i>
                            </div>
                            <h5 class="fw-bold">Staff Directory</h5>
                            <p class="small text-muted mb-4">Manage internal users, verify accounts, and handle access control.</p>
                            <a href="viewMemberPage?userEmail=${email}" class="btn btn-primary w-100 rounded-pill mt-auto">View Members</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card h-100 shadow-sm border-0 admin-card p-3">
                        <div class="card-body text-center d-flex flex-column">
                            <div class="icon-box bg-danger-subtle text-danger mx-auto">
                                <i class="bi bi-shield-check fs-2"></i>
                            </div>
                            <h5 class="fw-bold">Approval Queue</h5>
                            <p class="small text-muted mb-4">Review requests and verify transactions awaiting administrative consent.</p>
                            <a href="viewPendingRequests?userEmail=${email}" class="btn btn-danger w-100 rounded-pill mt-auto">
                                Check Requests (${pendingRequestCount != null ? pendingRequestCount : '0'})
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card h-100 shadow-sm border-0 admin-card p-3">
                        <div class="card-body text-center d-flex flex-column">
                            <div class="icon-box bg-success-subtle text-success mx-auto">
                                <i class="bi bi-building-fill-check fs-2"></i>
                            </div>
                            <h5 class="fw-bold">Customer Hub</h5>
                            <p class="small text-muted mb-4">Manage the global list of Creditors and Debtors and their financial status.</p>
                            <a href="viewCustomerPage?userEmail=${email}" class="btn btn-success w-100 rounded-pill mt-auto">Open Hub</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row g-4">
                <div class="col-12">
                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                        <div class="card-header bg-white py-3 border-bottom border-light">
                            <h6 class="fw-bold mb-0 text-uppercase small text-muted">
                                <i class="bi bi-broadcast me-2 text-primary"></i>System Activity Log
                            </h6>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0 small">
                                <tbody>
                                    <tr>
                                        <td class="ps-4" style="width: 50px;"><i class="bi bi-check-circle-fill text-success"></i></td>
                                        <td>Secure connection established for session <strong>${email}</strong>.</td>
                                        <td class="text-end pe-4 text-muted">Current Session</td>
                                    </tr>
                                    <c:if test="${pendingRequestCount > 0}">
                                        <tr>
                                            <td class="ps-4"><i class="bi bi-exclamation-circle-fill text-warning"></i></td>
                                            <td>There are <strong>${pendingRequestCount}</strong> pending items that require your immediate review.</td>
                                            <td class="text-end pe-4 text-muted">Awaiting Action</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="mt-auto py-3 bg-white border-top shadow-sm">
        <div class="container d-flex justify-content-between align-items-center">
            <span class="small text-muted">&copy; 2026 <strong>PRODEX FLOW</strong></span>
            <span id="datetime" class="small fw-bold text-primary"></span>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>