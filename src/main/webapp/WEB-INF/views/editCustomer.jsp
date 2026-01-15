<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<c:set var="pageTitle" value="Prodex - Edit Customer: ${customerDto.customerName}"/>
<c:set var="pageCss" value="/resources/css/editCustomer.css"/>
<c:set var="pageJs" value="/resources/js/editCustomer.js"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>

<body>

    <header class="header-custom">
            <div class="navbar-left">
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" onerror="this.style.display='none'"
                    alt="Prodex Flow Icon" class="navbar-logo">
            </div>
            <div class="navbar-center">
                <span class="brand-title">Prodex</span>
                <span class="brand-subtitle">Engineered for Flow</span>
            </div>
            <div class="navbar-right">
                        <a href="administratorDashboardPage?email=<c:out value='${userEmail}'/>" class="btn btn-prodex-secondary">
                            <i class="bi bi-clipboard-data-fill me-2"></i>Dashboard
                        </a>
            </div>
        </header>

    <main class="container py-5">
        <div class="card-custom">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="bi bi-pencil-square me-2"></i> Edit Customer:
                    <c:out value="${customerDto.customerName}" />
                </h2>
                <a href="viewCustomerPage?userEmail=<c:out value='${userEmail}'/>"
                    class="btn btn-prodex-primary mt-4">
                    <i class="bi bi-list-stars me-1"></i> Back to Customer List
                </a>
            </div>

            <c:set var="errors" value="${requestScope['org.springframework.validation.BindingResult.customerDto']}" />

            <c:if test="${not empty errorMessage and empty errors}">
                <div class="alert alert-danger text-center fw-semibold mb-3">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <c:out value="${errorMessage}" />
                </div>
            </c:if>

            <c:if test="${not empty successMessage and empty errors}">
                <div class="alert alert-success text-center fw-semibold mb-3">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    <c:out value="${successMessage}" />
                </div>
            </c:if>

            <form id="customerForm" action="updateCustomer" method="POST" novalidate>

                <input type="hidden" name="userEmail" value="<c:out value='${userEmail}'/>" />
                <input type="hidden" name="customerId" value="<c:out value='${customerDto.customerId}'/>" />

                <div class="row g-3">

                    <div class="col-md-6 col-12">
                        <label for="customerName" class="form-label">
                            <i class="bi bi-person-badge me-1"></i> Customer Name <span class="required-star">*</span>
                        </label>
                        <input type="text" name="customerName" value="<c:out value="${customerDto.customerName}" />"
                        class="form-control ${errors.hasFieldErrors('customerName') ? 'input-error-highlight' : ''}"
                        id="customerName" placeholder="Enter Full Name (Max 100 characters)"/>
                        <span class="error-message" id="customerNameError">Customer name is required and cannot exceed 100 characters.</span>
                        <c:if test="${errors.hasFieldErrors('customerName')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('customerName').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="customerType" class="form-label">
                            <i class="bi bi-tag-fill me-1"></i> Customer Type <span class="required-star">*</span>
                        </label>
                        <select name="customerType"
                            class="form-select ${errors.hasFieldErrors('customerType') ? 'input-error-highlight' : ''}"
                            id="customerType">
                            <option value="" ${customerDto.customerType==null ? 'selected' : '' }>-- Select Type --
                            </option>
                            <option value="CREDITOR" ${customerDto.customerType=='CREDITOR' ? 'selected' : '' }>Creditor
                            </option>
                            <option value="DEBTOR" ${customerDto.customerType=='DEBTOR' ? 'selected' : '' }>Debtor
                            </option>
                        </select>
                        <span class="error-message" id="customerTypeError">Customer type is required.</span>
                        <c:if test="${errors.hasFieldErrors('customerType')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('customerType').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="email" class="form-label">
                            <i class="bi bi-envelope-at-fill me-1"></i> Email <span class="required-star">*</span>
                        </label>
                        <div class="input-group">
                            <input type="email" name="email" id="email" value="<c:out value='${customerDto.email}'/>"
                                data-original="<c:out value='${customerDto.email}'/>"
                                data-id="<c:out value='${customerDto.customerId}'/>"
                                class="form-control ${errors.hasFieldErrors('email') ? 'input-error-highlight' : ''}"
                                placeholder="name@example.com" />
                            <span id="emailStatus" class="input-group-text p-0 border-0 bg-transparent"
                                style="width: 25px;"></span>
                        </div>
                        <span class="error-message" id="emailError">Please enter a valid email address.</span>
                        <c:if test="${errors.hasFieldErrors('email')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('email').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="contact" class="form-label">
                            <i class="bi bi-phone-fill me-1"></i> Phone/Contact Number <span class="required-star">*</span>
                        </label>
                        <div class="input-group">
                            <input type="text" name="contact" id="contact"
                                value="<c:out value='${customerDto.contact}'/>"
                                data-original="<c:out value='${customerDto.contact}'/>"
                                data-id="<c:out value='${customerDto.customerId}'/>"
                                class="form-control ${errors.hasFieldErrors('contact') ? 'input-error-highlight' : ''}"
                                placeholder="Enter contact number (5-20 characters, includes +,-,(), space)" />
                            <span id="contactStatus" class="input-group-text p-0 border-0 bg-transparent"
                                style="width: 25px;"></span>
                        </div>
                        <span class="error-message" id="contactError">Invalid contact number format.</span>
                        <c:if test="${errors.hasFieldErrors('contact')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('contact').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="taxId" class="form-label">
                            <i class="bi bi-journal-text me-1"></i> Tax ID / VAT / GST Number
                        </label>
                        <div class="input-group">
                            <input type="text" name="taxId" id="taxId"
                                value="<c:out value='${customerDto.taxId}'/>"
                                data-original="<c:out value='${customerDto.taxId}'/>"
                                data-id="<c:out value='${customerDto.customerId}'/>"
                                class="form-control ${errors.hasFieldErrors('taxId') ? 'input-error-highlight' : ''}"
                                placeholder="Optional (Max 20 characters)" />
                            <span id="taxIdStatus" class="input-group-text p-0 border-0 bg-transparent"
                                style="width: 25px;"></span>
                        </div>
                        <span class="error-message" id="taxIdError">Tax ID cannot exceed 20 characters.</span>
                        <c:if test="${errors.hasFieldErrors('taxId')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('taxId').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="paymentMode" class="form-label">
                            <i class="bi bi-cash-coin me-1"></i> Preferred Payment Mode <span class="required-star">*</span>
                        </label>
                        <select name="paymentMode"
                            class="form-select ${errors.hasFieldErrors('paymentMode') ? 'input-error-highlight' : ''}"
                            id="paymentMode">
                            <option value="" ${customerDto.paymentMode==null ? 'selected' : '' }>-- Select Payment Mode
                                --</option>
                            <option value="UPI" ${customerDto.paymentMode=='UPI' ? 'selected' : '' }>UPI</option>
                            <option value="CASH" ${customerDto.paymentMode=='CASH' ? 'selected' : '' }>CASH</option>
                            <option value="CHEQUE" ${customerDto.paymentMode=='CHEQUE' ? 'selected' : '' }>CHEQUE
                            </option>
                        </select>
                        <span class="error-message" id="paymentModeError">Payment mode is required.</span>
                        <c:if test="${errors.hasFieldErrors('paymentMode')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('paymentMode').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12 position-relative">
                        <label for="country" class="form-label">
                            <i class="bi bi-globe me-1"></i> Country <span class="required-star">*</span>
                        </label>
                        <input type="text" name="country" id="country"
                            class="form-control ${errors.hasFieldErrors('country') ? 'input-error-highlight' : ''}"
                            placeholder="Enter Country" autocomplete="off"
                            value="<c:out value='${customerDto.country}'/>"
                            data-initial-value="<c:out value='${customerDto.country}'/>">
                        <div class="dropdown-menu w-100" id="countryDropdown"></div>
                        <span class="error-message" id="countryError">Country is required.</span>
                        <c:if test="${errors.hasFieldErrors('country')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('country').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12 position-relative">
                        <label for="state" class="form-label">
                            <i class="bi bi-map-fill me-1"></i> State/Province/Region <span class="required-star">*</span>
                        </label>
                        <input type="text" name="state" id="state"
                            class="form-control ${errors.hasFieldErrors('state') ? 'input-error-highlight' : ''}"
                            placeholder="Enter State/Province/Region" autocomplete="off"
                            value="<c:out value='${customerDto.state}'/>"
                            data-initial-value="<c:out value='${customerDto.state}'/>">
                        <div class="dropdown-menu w-100" id="stateDropdown"></div>
                        <span class="error-message" id="stateError">State/Province/Region is required.</span>
                        <c:if test="${errors.hasFieldErrors('state')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('state').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12 position-relative">
                        <label for="city" class="form-label">
                            <i class="bi bi-building me-1"></i> City/Town <span class="required-star">*</span>
                        </label>
                        <input type="text" name="city" id="city"
                            class="form-control ${errors.hasFieldErrors('city') ? 'input-error-highlight' : ''}"
                            placeholder="Enter City/Town" autocomplete="off"
                            value="<c:out value='${customerDto.city}'/>"
                            data-initial-value="<c:out value='${customerDto.city}'/>">
                        <div class="dropdown-menu w-100" id="cityDropdown"></div>
                        <span class="error-message" id="cityError">City/Town is required.</span>
                        <c:if test="${errors.hasFieldErrors('city')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('city').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="pinCode" class="form-label">
                            <i class="bi bi-hash me-1"></i> Pin Code <span class="required-star">*</span>
                        </label>
                        <input type="text" name="pinCode" value="<c:out value="${customerDto.pinCode}" />"
                        class="form-control ${errors.hasFieldErrors('pinCode') ? 'input-error-highlight' : ''}"
                        id="pinCode" placeholder="Postal/Zip Code (3-10 alphanumeric characters)"/>
                        <span class="error-message" id="pinCodeError">Pin code must be a valid postal/zip code.</span>
                        <c:if test="${errors.hasFieldErrors('pinCode')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('pinCode').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <div class="col-md-6 col-12">
                        <label for="billingAddress" class="form-label">
                            <i class="bi bi-house-door-fill me-1"></i> Billing Address <span class="required-star">*</span>
                        </label>
                        <textarea name="billingAddress" id="billingAddress"
                            class="form-control ${errors.hasFieldErrors('billingAddress') ? 'input-error-highlight' : ''}"
                            placeholder="Enter Full Billing Address"><c:out value="${customerDto.billingAddress}"/></textarea>
                        <span class="error-message" id="billingAddressError">Billing address is required.</span>
                        <c:if test="${errors.hasFieldErrors('billingAddress')}">
                            <span class="error-message d-block">
                                <c:out value="${errors.getFieldError('billingAddress').getDefaultMessage()}" />
                            </span>
                        </c:if>
                    </div>

                    <input type="hidden" name="shippingSameAsBilling" id="shippingSameAsBilling" value="<c:out value='${customerDto.shippingSameAsBilling}'/>" />

                    <div class="col-md-6 col-12">
                        <div class="form-check form-switch mb-3">
                            <c:set var="isShippingSame" value="${customerDto.shippingSameAsBilling != false}" />
                            <input class="form-check-input" type="checkbox" role="switch" id="shippingToggle"
                                ${isShippingSame ? 'checked' : '' }>
                            <label class="form-check-label" for="shippingToggle">Shipping address same as
                                billing?</label>
                        </div>

                        <div id="shippingAddressContainer" style="display:${isShippingSame ? 'none' : 'block'};">
                            <label for="shippingAddress" class="form-label">
                                <i class="bi bi-truck-flatbed me-1"></i> Shipping Address <span class="required-star shipping-required-star">*</span>
                            </label>
                            <textarea name="shippingAddress" id="shippingAddress"
                                class="form-control ${errors.hasFieldErrors('shippingAddress') ? 'input-error-highlight' : ''}"
                                placeholder="Enter Shipping Address (Max 255 characters)"><c:out value="${customerDto.shippingAddress}"/></textarea>
                            <span class="error-message" id="shippingAddressError">Shipping address is required if
                                different from billing, and cannot exceed 255 characters.</span>
                            <c:if test="${errors.hasFieldErrors('shippingAddress')}">
                                <span class="error-message d-block">
                                    <c:out value="${errors.getFieldError('shippingAddress').getDefaultMessage()}" />
                                </span>
                            </c:if>
                        </div>
                    </div>

                    <div class="col-12 d-flex justify-content-center mt-4 mb-4">
                      <button type="submit" id="saveCustomerBtn" class="btn btn-update-customer">
                          <i class="bi bi-save me-2"></i> Update Customer
                      </button>
                    </div>

                </div>
            </form>
        </div>
    </main>

    <footer class="footer-custom fixed-bottom">
        <div class="container text-center">
            &copy; 2025 Prodex — Engineered for Flow
            <div id="datetime"></div>
        </div>
    </footer>

    <%@ include file="/WEB-INF/views/includes/footer.jspf" %>

</body>

</html>