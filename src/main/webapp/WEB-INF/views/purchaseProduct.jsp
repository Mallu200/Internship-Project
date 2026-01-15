<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Submit Purchase Request"/>
<c:set var="pageCss" value="/resources/css/purchaseProduct.css"/>
<c:set var="pageJs" value="/resources/js/purchaseProduct.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

<div class="container form-container">
    <div class="card shadow-lg">
        <div class="card-header bg-white py-4 border-0">
            <h3 class="mb-0 fw-bold text-dark d-flex align-items-center">
                <i class="bi bi-cart-plus-fill text-primary me-3"></i>Submit Purchase Request
            </h3>
            <p class="text-muted small mb-0 mt-1">Fill in the details below to request a new product stock purchase.</p>
        </div>

        <div class="card-body p-4 p-md-5">
            <form action="savePurchaseRequest" method="POST" id="purchaseForm">
                <%-- Context Data --%>
                <input type="hidden" name="memberEmail" value="${userEmail}">

                <div class="row g-4 mb-4">
                    <div class="col-md-6">
                        <label class="form-label fw-bold small">Debit Customer *</label>
                        <select name="customerName" class="form-select shadow-sm" required>
                            <option value="">-- Select Customer --</option>
                            <c:forEach items="${debitCustomers}" var="name">
                                <option value="${name}">${name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-bold small">Product Group *</label>
                        <select name="productGroupName" class="form-select shadow-sm" required>
                            <option value="">-- Select Product Group --</option>
                            <c:forEach items="${productGroups}" var="group">
                                <option value="${group}">${group}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label fw-bold small">Manufacturer (Make) *</label>
                        <input type="text" id="make" name="make" class="form-control" placeholder="e.g. Philips" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold small">Model Number *</label>
                        <input type="text" id="model" name="model" class="form-control" placeholder="e.g. LED-100" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold small">Product Code *</label>
                        <input type="text" name="productCode" class="form-control" placeholder="SKU Code" required>
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-bold small">Item Display Name (Auto-generated)</label>
                        <input type="text" id="itemName" name="itemName" class="form-control bg-light border-dashed"
                               placeholder="Full item name will appear here..." readonly>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-bold small">Initial Price (₹) *</label>
                        <div class="input-group">
                            <span class="input-group-text">₹</span>
                            <input type="number" step="0.01" name="initialPrice" class="form-control" required>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold small">Purchase Price (₹) *</label>
                        <div class="input-group">
                            <span class="input-group-text">₹</span>
                            <input type="number" step="0.01" name="purchasePrice" class="form-control" required>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold small">Quantity *</label>
                        <input type="number" name="stockInHand" class="form-control" placeholder="Qty" min="1" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-bold small">Target Delivery Date *</label>
                        <input type="date" name="orderDueDate" id="deliveryDate" class="form-control" required>
                    </div>
                </div>

                <div class="mt-5 pt-3 d-flex justify-content-end gap-3 border-top">
                    <a href="administratorDashboardPage?email=${userEmail}" class="btn btn-outline-secondary px-4 rounded-3">
                        Cancel
                    </a>
                    <button type="submit" class="btn btn-primary btn-submit fw-bold shadow-sm">
                        Submit Purchase Request
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>

</body>
</html>