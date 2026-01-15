<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Pending Requests"/>
<c:set var="pageCss" value="/resources/css/adminDashboard.css"/>
<c:set var="pageJs" value="/resources/js/viewPendingRequests.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light" data-flash-success="${not empty successMessage ? successMessage : ''}" data-flash-error="${not empty errorMessage ? errorMessage : ''}">

<div class="container-fluid py-5">
    <div class="container">
        <div class="card shadow-sm border-0 rounded-3 overflow-hidden">
            <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center border-bottom">
                <h5 class="mb-0 fw-bold text-dark">
                    <i class="bi bi-clock-history text-primary me-2"></i>Pending Purchase Requests
                </h5>
                <a href="administratorDashboardPage?email=${userEmail}" class="btn btn-sm btn-outline-dark rounded-pill px-3">
                    <i class="bi bi-arrow-left me-1"></i> Back to Dashboard
                </a>
            </div>

            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr class="small text-uppercase">
                                <th class="ps-4">Req ID</th>
                                <th>Customer / Email</th>
                                <th>Product Details</th>
                                <th>Price (₹)</th>
                                <th>Qty</th>
                                <th>Delivery Due</th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${pendingRequests}" var="req">
                                <tr>
                                    <td class="ps-4">
                                        <span class="badge bg-secondary-subtle text-secondary badge-id">#${req.purchaseId}</span>
                                    </td>
                                    <td>
                                        <div class="fw-bold text-dark">${req.customerName}</div>
                                        <div class="small text-muted">${req.memberEmail}</div>
                                    </td>
                                    <td>
                                        <div class="fw-bold">${req.itemName}</div>
                                        <div class="small text-primary">${req.productGroupName}</div>
                                    </td>
                                    <td>₹${req.purchasePrice}</td>
                                    <td><span class="badge bg-light text-dark border">${req.stockInHand}</span></td>
                                    <td><i class="bi bi-calendar-event me-1 text-muted"></i>${req.orderDueDate}</td>
                                    <td class="text-center">
                                        <div class="btn-group gap-2">
                                            <button class="action-btn btn btn-sm btn-success rounded-pill px-3" data-id="${req.purchaseId}" data-action="APPROVED">
                                                Approve
                                            </button>
                                            <button class="action-btn btn btn-sm btn-outline-danger rounded-pill px-3" data-id="${req.purchaseId}" data-action="REJECTED">
                                                Reject
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty pendingRequests}">
                                <tr>
                                    <td colspan="7" class="text-center py-5 text-muted">
                                        <i class="bi bi-inbox fs-1 d-block mb-2 opacity-50"></i>
                                        No pending requests found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Hidden Form for Processing Actions --%>
<form id="actionForm" action="updatePurchaseStatus" method="POST">
    <input type="hidden" name="purchaseId" id="formId">
    <input type="hidden" name="status" id="formStatus">
    <input type="hidden" name="adminEmail" value="${userEmail}">
    <%-- Note: The controller method expects "adminEmail" as the request param --%>
</form>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
function handleAction(id, status) {
    const color = status === 'APPROVED' ? '#198754' : '#dc3545';

    Swal.fire({
        title: 'Confirm ' + status,
        text: "Are you sure you want to " + status.toLowerCase() + " this purchase request?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: color,
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Yes, ' + status + ' it!'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById('formId').value = id;
            document.getElementById('formStatus').value = status;
            document.getElementById('actionForm').submit();
        }
    })
}

// Handle Flash Attributes (Success/Error Messages)
document.addEventListener('DOMContentLoaded', function() {
    <c:if test="${not empty successMessage}">
        Swal.fire({ icon: 'success', title: 'Success', text: '${successMessage}', timer: 3000 });
    </c:if>
    <c:if test="${not empty errorMessage}">
        Swal.fire({ icon: 'error', title: 'Oops...', text: '${errorMessage}' });
    </c:if>
});
</script>

</body>
</html>