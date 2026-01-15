<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<c:set var="pageTitle" value="Prodex | Add Customer"/>
<c:set var="pageCss" value="/resources/css/addCustomer.css"/>
<c:set var="pageJs" value="/resources/js/addCustomer.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body>

    <header class="header-custom">
        <div class="navbar-left">
            <div class="brand-group">
                <span class="brand-title">PRODEX</span>
                <span class="brand-subtitle">FLOW</span>
            </div>
        </div>
        <div class="navbar-right">
            <a href="administratorDashboardPage?email=${userEmail}" class="btn btn-prodex-secondary">
                <i class="bi bi-clipboard-data-fill me-2"></i>Dashboard
            </a>
        </div>
    </header>

    <main class="container my-5">
        <div class="card-add-customer shadow-sm">
            <%-- Updated Header: Added the Customer List Button here --%>
            <div class="card-header-form py-4 px-4 d-flex justify-content-between align-items-center border-bottom">
                <div class="text-start">
                    <h2 class="mb-0 fw-800"><i class="bi bi-person-plus-fill me-2 text-primary"></i> Register New Customer</h2>
                    <p class="text-muted small mb-0 mt-1">Onboard a new client or vendor to the system.</p>
                </div>
                <div class="action-buttons">
                    <a href="viewCustomerPage?userEmail=${userEmail}" class="btn btn-outline-dark rounded-pill px-4">
                        <i class="bi bi-person-lines-fill me-2"></i>Customer List
                    </a>
                </div>
            </div>

            <div class="card-body p-4 p-md-5">
                <c:set var="errors" value="${requestScope['org.springframework.validation.BindingResult.customerDto']}" />

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger text-center fw-semibold rounded-3">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>${errorMessage}
                    </div>
                </c:if>

                <form id="customerForm" action="addCustomer" method="POST" novalidate>
                    <input type="hidden" name="userEmail" value="${userEmail}" />

                    <div class="row g-4">
                        <%-- Section: Identity --%>
                        <div class="col-12"><h5 class="section-divider"><span>Identity & Contact</span></h5></div>

                        <div class="col-md-6">
                            <label class="form-label">Customer Name *</label>
                            <input type="text" name="customerName" value="${customerDto.customerName}"
                                   class="form-control ${errors.hasFieldErrors('customerName') ? 'is-invalid' : ''}"
                                   placeholder="Full Name / Company Name">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Customer Type *</label>
                            <select name="customerType" class="form-select ${errors.hasFieldErrors('customerType') ? 'is-invalid' : ''}">
                                <option value="">-- Select Type --</option>
                                <option value="CREDITOR" ${customerDto.customerType=='CREDITOR' ? 'selected' : ''}>Creditor (Vendor)</option>
                                <option value="DEBTOR" ${customerDto.customerType=='DEBTOR' ? 'selected' : ''}>Debtor (Client)</option>
                            </select>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Email Address *</label>
                            <input type="email" name="email" id="email" value="${customerDto.email}"
                                   class="form-control ${errors.hasFieldErrors('email') ? 'is-invalid' : ''}"
                                   placeholder="customer@example.com">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Contact Number *</label>
                            <input type="text" name="contact" id="contact" value="${customerDto.contact}"
                                   class="form-control ${errors.hasFieldErrors('contact') ? 'is-invalid' : ''}"
                                   placeholder="Mobile or Office number">
                        </div>

                        <%-- Section: Financials --%>
                        <div class="col-12 mt-5"><h5 class="section-divider"><span>Financials & Taxation</span></h5></div>

                        <div class="col-md-6">
                            <label class="form-label">Tax ID / GST / VAT</label>
                            <input type="text" name="taxId" value="${customerDto.taxId}" class="form-control" placeholder="Optional (e.g., GSTIN)">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Payment Mode *</label>
                            <select name="paymentMode" class="form-select">
                                <option value="">-- Select Mode --</option>
                                <option value="UPI" ${customerDto.paymentMode=='UPI' ? 'selected' : ''}>UPI</option>
                                <option value="CASH" ${customerDto.paymentMode=='CASH' ? 'selected' : ''}>Cash</option>
                                <option value="CHEQUE" ${customerDto.paymentMode=='CHEQUE' ? 'selected' : ''}>Cheque</option>
                                <option value="NEFT" ${customerDto.paymentMode=='NEFT' ? 'selected' : ''}>NEFT/RTGS</option>
                            </select>
                        </div>

                        <%-- Section: Location --%>
                        <div class="col-12 mt-5"><h5 class="section-divider"><span>Location Details</span></h5></div>

                        <div class="col-md-3">
                            <label class="form-label">Country *</label>
                            <input type="text" name="country" id="country" class="form-control" value="${customerDto.country}">
                        </div>

                        <div class="col-md-3">
                            <label class="form-label">State *</label>
                            <input type="text" name="state" id="state" class="form-control" value="${customerDto.state}">
                        </div>

                        <div class="col-md-3">
                            <label class="form-label">City *</label>
                            <input type="text" name="city" id="city" class="form-control" value="${customerDto.city}">
                        </div>

                        <div class="col-md-3">
                            <label class="form-label">Pin Code *</label>
                            <input type="text" name="pinCode" value="${customerDto.pinCode}" class="form-control">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Billing Address *</label>
                            <textarea name="billingAddress" class="form-control" rows="3">${customerDto.billingAddress}</textarea>
                        </div>

                        <div class="col-md-6">
                            <div class="form-check form-switch mb-3">
                                <input class="form-check-input" type="checkbox" id="shippingToggle"
                                    ${customerDto.shippingSameAsBilling == null || customerDto.shippingSameAsBilling ? 'checked' : ''}>
                                <label class="form-check-label small fw-bold" for="shippingToggle">Shipping matches Billing address?</label>
                            </div>
                            <div id="shippingAddressContainer" style="${customerDto.shippingSameAsBilling == null || customerDto.shippingSameAsBilling ? 'display:none;' : ''}">
                                <textarea name="shippingAddress" id="shippingAddress" class="form-control" rows="3" placeholder="Enter Shipping Address">${customerDto.shippingAddress}</textarea>
                            </div>
                        </div>

                        <div class="col-12 text-center mt-5 border-top pt-4">
                            <button type="submit" id="saveCustomerBtn" class="btn btn-prodex-primary px-5 py-3 shadow-sm">
                                <i class="bi bi-person-check-fill me-2"></i>Save & Finalize Onboarding
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <footer class="footer-custom mt-auto">
        <div class="container text-center">
            <p class="mb-1">&copy; 2026 Prodex — Engineered for Flow</p>
            <div id="datetime" class="small opacity-50"></div>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>
</body>
</html>