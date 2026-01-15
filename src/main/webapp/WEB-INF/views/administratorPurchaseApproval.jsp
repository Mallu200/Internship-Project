<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Approval Center"/>
<c:set var="pageCss" value="/resources/css/adminDashboard.css"/>
<c:set var="pageJs" value="/resources/js/adminDashboard.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body>

    <header class="header-custom p-3 mb-5 shadow-sm">
        <div class="container d-flex justify-content-between align-items-center">
            <h4 class="mb-0 fw-800 text-primary">PRODEX FLOW <span class="text-muted fs-6 fw-normal">| Admin Approval</span></h4>
            <a href="administratorDashboardPage?email=${param.userEmail}" class="btn btn-outline-dark btn-sm rounded-pill">
                <i class="bi bi-house-door me-1"></i> Dashboard
            </a>
        </div>
    </header>

    <main class="container">
        <div class="mb-4">
            <h2 class="fw-bold">Pending Purchase Requests</h2>
            <p class="text-muted">Review incoming stock requests and provide approval or rejection notes.</p>
        </div>

        <%-- Notification Alerts --%>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show border-0 shadow-sm" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show border-0 shadow-sm" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty pendingRequests}">
                <div class="card card-prodex p-5 text-center">
                    <div class="display-1 text-muted opacity-25"><i class="bi bi-clipboard-x"></i></div>
                    <h4 class="text-secondary mt-3">All requests processed!</h4>
                    <p class="text-muted">There are currently no pending items in the queue.</p>
                </div>
            </c:when>

            <c:otherwise>
                <div class="card card-prodex overflow-hidden">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-header-prodex">
                                <tr>
                                    <th class="ps-4">Req ID</th>
                                    <th>Item Details</th>
                                    <th>Requestor</th>
                                    <th class="text-center">Quantity</th>
                                    <th>Total Price</th>
                                    <th class="text-center pe-4">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="req" items="${pendingRequests}">
                                    <tr>
                                        <td class="ps-4 fw-bold">#${req.purchaseId}</td>
                                        <td>
                                            <div class="fw-bold">${req.itemName}</div>
                                            <div class="small text-muted">${req.productCode} | ${req.make}</div>
                                        </td>
                                        <td>
                                            <div class="fw-semibold">${req.customerName}</div>
                                            <div class="small text-secondary">${req.memberEmail}</div>
                                        </td>
                                        <td class="text-center">
                                            <span class="badge badge-qty rounded-pill">${req.stockInHand}</span>
                                        </td>
                                        <td class="fw-bold text-success">₹ ${req.purchasePrice}</td>
                                        <td class="text-center pe-4">
                                            <div class="btn-group">
                                                <button class="btn btn-approve btn-sm px-3"
                                                        data-bs-toggle="modal" data-bs-target="#actionModal"
                                                        data-id="${req.purchaseId}" data-type="APPROVE">Approve</button>
                                                <button class="btn btn-reject btn-sm px-3"
                                                        data-bs-toggle="modal" data-bs-target="#actionModal"
                                                        data-id="${req.purchaseId}" data-type="REJECT">Reject</button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <%-- The Approval/Rejection Modal --%>
    <div class="modal fade" id="actionModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow-lg" style="border-radius: 20px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title fw-bold" id="modalTitle">Process Request</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <%-- Using purchaseActionDto to match the PurchaseActionDto.java --%>
                <form:form action="processRequest?userEmail=${param.userEmail}" method="POST" modelAttribute="purchaseActionDto">
                    <div class="modal-body py-4">
                        <p id="modalMsg" class="mb-4"></p>

                        <form:hidden path="purchaseId" id="hiddenId" />
                        <form:hidden path="status" id="hiddenStatus" />

                        <div class="mb-3">
                            <label class="form-label fw-bold">Admin Notes / Comments</label>
                            <form:textarea path="adminComment" class="form-control" rows="3"
                                           placeholder="Enter reason for this action..."/>
                        </div>

                        <div id="stockNote" class="alert alert-warning border-0 small d-none">
                            <i class="bi bi-info-circle me-2"></i> Confirming will deduct items from the main inventory.
                        </div>
                    </div>
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-light rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn rounded-pill px-4" id="submitBtn">Confirm</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>