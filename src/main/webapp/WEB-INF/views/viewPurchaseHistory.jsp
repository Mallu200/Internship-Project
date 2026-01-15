<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | My Purchase History"/>
<c:set var="pageCss" value="/resources/css/viewPurchaseHistory.css"/>
<c:set var="pageJs" value="/resources/js/viewPurchaseHistory.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light" data-flash-success="${not empty successMessage ? successMessage : ''}">

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-dark"><i class="bi bi-clock-history me-2 text-primary"></i>My Purchase Requests</h3>
        <a href="memberDashboardPage?email=${userEmail}" class="btn btn-outline-secondary btn-sm">
            <i class="bi bi-house-door me-1"></i> Dashboard
        </a>
    </div>

    <div class="card shadow-sm border-0 table-card">
        <div class="card-body p-0">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th class="ps-4">Item Name</th>
                        <th>Product Group</th>
                        <th>Price (₹)</th>
                        <th>Quantity</th>
                        <th>Status</th>
                        <th>Due Date</th>
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${purchaseHistory}" var="item">
                        <tr>
                            <td class="ps-4">
                                <div class="fw-bold">${item.itemName}</div>
                                <small class="text-muted">Code: ${item.productCode}</small>
                            </td>
                            <td>${item.productGroupName}</td>
                            <td>₹${item.purchasePrice}</td>
                            <td>${item.stockInHand}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 'PENDING'}">
                                        <span class="badge bg-warning text-dark status-badge">PENDING</span>
                                    </c:when>
                                    <c:when test="${item.status == 'APPROVED'}">
                                        <span class="badge bg-success status-badge">APPROVED</span>
                                    </c:when>
                                    <c:when test="${item.status == 'REJECTED'}">
                                        <span class="badge bg-danger status-badge">REJECTED</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary status-badge">${item.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${item.orderDueDate}</td>
                            <td class="text-center">
                                <c:if test="${item.status == 'PENDING'}">
                                    <form action="${pageContext.request.contextPath}/purchase/cancelRequest" method="POST" style="display:inline;">
                                        <input type="hidden" name="purchaseId" value="${item.purchaseId}">
                                        <input type="hidden" name="userEmail" value="${userEmail}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Retract this request?')">
                                            Retract
                                        </button>
                                    </form>
                                </c:if>
                                <c:if test="${item.status != 'PENDING'}">
                                    <span class="text-muted small">No actions</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty purchaseHistory}">
                        <tr>
                            <td colspan="7" class="text-center py-5 text-muted">You haven't made any purchase requests yet.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>

</body>
</html>