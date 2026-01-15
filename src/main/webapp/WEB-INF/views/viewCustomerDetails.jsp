<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Customer Profile"/>
<c:set var="pageCss" value="/resources/css/viewCustomerDetail.css"/>
<c:set var="pageJs" value="/resources/js/viewCustomerDetail.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

    <header class="header-custom">
        <div class="navbar-left">
            <div class="brand-group">
                <span class="brand-title">PRODEX</span><span class="brand-subtitle">FLOW</span>
            </div>
        </div>
        <div class="navbar-right">
            <a href="administratorDashboardPage?email=${userEmail}" class="btn btn-outline-light btn-sm px-3 rounded-pill">
                <i class="bi bi-speedometer2 me-1"></i> Dashboard
            </a>
        </div>
    </header>

    <main class="container my-5">
        <div class="card border-0 shadow-lg rounded-4 overflow-hidden">
            <div class="card-header bg-white py-4 px-4 d-flex justify-content-between align-items-center border-bottom">
                <div>
                    <h2 class="mb-0 fw-bold fs-4 text-dark text-uppercase">
                        <i class="bi bi-person-badge me-2 text-primary"></i>Customer Profile
                    </h2>
                    <%-- Fixed Line 69: Removed createdAt to prevent 500 error --%>
                    <p class="text-muted small mb-0">
                        <i class="bi bi-hash"></i> Customer ID: ${customerDetails.customerId} | Status: <span class="text-success fw-bold">Active</span>
                    </p>
                </div>
                <div class="d-flex gap-2">
                    <a href="viewCustomerPage?userEmail=${userEmail}" class="btn btn-light border rounded-pill px-4">
                        <i class="bi bi-arrow-left me-1"></i> Back
                    </a>
                    <button id="editToggleBtn" class="btn btn-primary rounded-pill px-4" type="button">
                        <i class="bi bi-pencil me-1"></i> Edit Details
                    </button>
                    <button id="saveChangesBtn" class="btn btn-success rounded-pill px-4 d-none" type="submit" form="customerDetailForm">
                        <i class="bi bi-check-lg me-1"></i> Save Changes
                    </button>
                </div>
            </div>

            <div class="card-body p-4 p-md-5">
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger border-0 shadow-sm">${errorMessage}</div>
                </c:if>

                <form id="customerDetailForm" action="updateCustomer" method="post">
                    <input type="hidden" name="customerId" value="${customerDetails.customerId}" />
                    <input type="hidden" name="userEmail" value="${userEmail}" />
                    <input type="hidden" name="country" value="${customerDetails.country}" />
                    <input type="hidden" name="taxId" value="${customerDetails.taxId}" />
                    <input type="hidden" name="shippingSameAsBilling" value="${customerDetails.shippingSameAsBilling}">
                    <input type="hidden" name="shippingAddress" value="${customerDetails.shippingAddress}">

                    <h6 class="section-label">General Information</h6>
                    <div class="row g-4 mb-5">
                        <div class="col-md-6">
                            <label class="form-label fw-bold small">Customer Name</label>
                            <input type="text" name="customerName" class="form-control detail-input" value="${customerDetails.customerName}" required disabled>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">Customer Type</label>
                            <select name="customerType" class="form-select detail-input" disabled>
                                <option value="CREDITOR" ${customerDetails.customerType == 'CREDITOR' ? 'selected' : ''}>CREDITOR</option>
                                <option value="DEBTOR" ${customerDetails.customerType == 'DEBTOR' ? 'selected' : ''}>DEBTOR</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">Payment Mode</label>
                            <select name="paymentMode" class="form-select detail-input" disabled>
                                <option value="UPI" ${customerDetails.paymentMode == 'UPI' ? 'selected' : ''}>UPI</option>
                                <option value="CASH" ${customerDetails.paymentMode == 'CASH' ? 'selected' : ''}>CASH</option>
                                <option value="CARD" ${customerDetails.paymentMode == 'CARD' ? 'selected' : ''}>CARD</option>
                            </select>
                        </div>
                    </div>

                    <h6 class="section-label">Contact Details</h6>
                    <div class="row g-4 mb-5">
                        <div class="col-md-6">
                            <label class="form-label fw-bold small">Email Address</label>
                            <%-- Note: Email is readonly because it is usually a unique key --%>
                            <input type="email" name="email" class="form-control detail-input" value="${customerDetails.email}" readonly disabled>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold small">Phone/Contact</label>
                            <input type="text" name="contact" class="form-control detail-input" value="${customerDetails.contact}" required disabled>
                        </div>
                    </div>

                    <h6 class="section-label">Location Details</h6>
                    <div class="row g-4">
                        <div class="col-md-4">
                            <label class="form-label fw-bold small">State</label>
                            <input type="text" name="state" class="form-control detail-input" value="${customerDetails.state}" required disabled>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-bold small">City</label>
                            <input type="text" name="city" class="form-control detail-input" value="${customerDetails.city}" required disabled>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-bold small">Pin Code</label>
                            <input type="text" name="pinCode" class="form-control detail-input" value="${customerDetails.pinCode}" required disabled>
                        </div>
                        <div class="col-12 mt-4">
                            <label class="form-label fw-bold small">Billing Address</label>
                            <textarea name="billingAddress" class="form-control detail-input" rows="3" required disabled>${customerDetails.billingAddress}</textarea>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>