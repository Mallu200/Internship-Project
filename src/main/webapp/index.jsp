<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prodex Enterprise | Management Portal</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        body { font-family: 'Inter', sans-serif; background-color: #f4f7f9; }
        .navbar { border-bottom: 1px solid #dee2e6; }
        .hero-banner { background: #ffffff; border-bottom: 1px solid #dee2e6; padding: 80px 0; }
        .org-card { transition: transform 0.2s; border: none; border-radius: 10px; }
        .org-card:hover { transform: translateY(-5px); }
        .fixed-footer { height: 60px; background: #ffffff; border-top: 1px solid #dee2e6; }
        main { padding-bottom: 100px; }
    </style>
</head>
<body>

<header class="navbar navbar-expand-md navbar-light bg-white fixed-top">
    <div class="container">
        <a class="navbar-brand fw-bold d-flex align-items-center" href="#">
            <span class="text-primary">PRODEX</span>&nbsp;<span class="text-secondary fw-light">ENTERPRISE</span>
        </a>
        <div class="ms-auto d-flex align-items-center">
            <span class="text-muted small me-3 d-none d-sm-inline">System Status: <span class="text-success">● Operational</span></span>
            <a href="loginPage" class="btn btn-primary btn-sm px-4 fw-semibold">Sign In</a>
        </div>
    </div>
</header>

<main>
    <section class="hero-banner mt-5">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-7">
                    <h1 class="display-5 fw-bold text-dark">Organization Resource Planning</h1>
                    <p class="lead text-secondary mt-3">
                        Centralized management for infrastructure, personnel, and flow optimization.
                        Access protected by enterprise-grade encryption.
                    </p>
                    <div class="mt-4">
                        <a href="loginPage" class="btn btn-dark btn-lg px-4 me-2">Access Portal</a>
                        <a href="#" class="btn btn-outline-secondary btn-lg px-4">Internal Documentation</a>
                    </div>
                </div>
                <div class="col-lg-5 d-none d-lg-block">
                    <div class="p-4 border rounded shadow-sm bg-light">
                        <div class="d-flex justify-content-between mb-2">
                            <span class="small fw-bold text-uppercase text-muted">Active Nodes</span>
                            <span class="badge bg-success">Online</span>
                        </div>
                        <div class="progress mb-3" style="height: 8px;">
                            <div class="progress-bar bg-primary" style="width: 85%"></div>
                        </div>
                        <div class="small text-muted">Last sync: <span id="sync-time">Just now</span></div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="container mt-5">
        <div class="row g-4 text-center">
            <div class="col-md-4">
                <div class="card h-100 shadow-sm org-card p-4">
                    <i class="bi bi-shield-lock text-primary h1"></i>
                    <h5 class="fw-bold mt-3">Security Ops</h5>
                    <p class="text-muted small">Monitor access logs and manage authentication protocols.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card h-100 shadow-sm org-card p-4">
                    <i class="bi bi-people text-primary h1"></i>
                    <h5 class="fw-bold mt-3">Staff Management</h5>
                    <p class="text-muted small">Directory services and organizational hierarchy control.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card h-100 shadow-sm org-card p-4">
                    <i class="bi bi-graph-up-arrow text-primary h1"></i>
                    <h5 class="fw-bold mt-3">Analytics</h5>
                    <p class="text-muted small">Real-time performance metrics and throughput reporting.</p>
                </div>
            </div>
        </div>
    </section>
</main>

<footer class="fixed-bottom fixed-footer d-flex align-items-center">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-md-6 text-center text-md-start">
                <span class="text-muted small">&copy; 2026 Prodex Systems Inc. All rights reserved.</span>
            </div>
            <div class="col-md-6 text-center text-md-end">
                <a href="#" class="text-muted small text-decoration-none me-3">Privacy Policy</a>
                <a href="#" class="text-muted small text-decoration-none me-3">Legal</a>
                <a href="#" class="text-muted small text-decoration-none">Support</a>
            </div>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('sync-time').textContent = new Date().toLocaleTimeString();
</script>
</body>
</html>