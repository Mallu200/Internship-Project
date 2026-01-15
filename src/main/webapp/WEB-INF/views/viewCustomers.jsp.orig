<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" value="Prodex | Customer Directory"/>
<c:set var="pageCss" value="/resources/css/viewCustomers.css"/>
<%@ include file="/WEB-INF/views/includes/head.jspf" %>
<body class="bg-light">

    <header class="p-3 bg-dark text-white mb-4 shadow">
        <div class="container-fluid d-flex justify-content-between align-items-center">
            <span class="fs-4 fw-bold text-uppercase">Prodex <span class="text-primary">Flow</span></span>
            <div class="d-flex align-items-center gap-3">
                <span class="small text-white-50">Admin: ${userEmail}</span>
                <a href="administratorDashboardPage?email=${userEmail}" class="btn btn-outline-light btn-sm">
                    <i class="bi bi-speedometer2 me-1"></i> Dashboard
                </a>
            </div>
        </div>
    </header>

    <main class="container-fluid px-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold m-0">Customer Directory</h2>
                <p class="text-muted">Manage creditors and debtors</p>
            </div>
            <a href="addCustomerPage?userEmail=${userEmail}" class="btn btn-primary shadow-sm">
                <i class="bi bi-plus-circle me-2"></i>New Customer
            </a>
        </div>

        <%-- Search Filter --%>
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body bg-white rounded">
                <form action="searchCustomer" method="GET" class="row g-3">
                    <input type="hidden" name="userEmail" value="${userEmail}">

                    <div class="col-md-3">
                        <label class="form-label small fw-bold">Customer Name</label>
                        <input type="text" name="searchCustomerName" class="form-control" value="${param.searchCustomerName}" placeholder="Search name...">
                    </div>

                    <div class="col-md-2">
                        <label class="form-label small fw-bold">Type</label>
                        <select name="searchType" class="form-select">
                            <option value="">All Types</option>
                            <option value="CREDITOR" ${param.searchType == 'CREDITOR' ? 'selected' : ''}>Creditor</option>
                            <option value="DEBTOR" ${param.searchType == 'DEBTOR' ? 'selected' : ''}>Debtor</option>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <label class="form-label small fw-bold">Email</label>
                        <input type="text" name="searchEmail" class="form-control" value="${param.searchEmail}" placeholder="Email address">
                    </div>

                    <div class="col-md-2">
                        <label class="form-label small fw-bold">Contact</label>
                        <input type="text" name="searchContactNumber" class="form-control" value="${param.searchContactNumber}" placeholder="Phone number">
                    </div>

                    <div class="col-md-2 d-flex align-items-end gap-2">
                        <button type="submit" class="btn btn-dark w-100">Filter</button>
                        <a href="viewCustomerPage?userEmail=${userEmail}" class="btn btn-outline-secondary" title="Reset">
                            <i class="bi bi-arrow-clockwise"></i>
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <%-- Success/Error Alerts --%>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show border-0 shadow-sm" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <%-- Main Table --%>
        <div class="card border-0 shadow-sm rounded-3 overflow-hidden">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="bg-light border-bottom">
                        <tr>
                            <th class="ps-4 py-3">ID</th>
                            <th>Customer Name</th>
                            <th>Type</th>
                            <th>Contact Info</th>
                            <th>Address</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty allCustomers}">
                                <c:forEach items="${allCustomers}" var="customer">
                                    <tr>
                                        <td class="ps-4 text-muted small">#${customer.customerId}</td>
                                        <td>
                                            <div class="fw-bold">${customer.customerName}</div>
                                            <div class="text-muted x-small">Tax ID: ${customer.taxId}</div>
                                        </td>
                                        <td>
                                            <span class="badge rounded-pill ${customer.customerType == 'CREDITOR' ? 'bg-info-subtle' : 'bg-success-subtle'}">
                                                ${customer.customerType}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="small text-dark"><i class="bi bi-envelope-at me-1"></i>${customer.email}</div>
                                            <div class="small text-muted"><i class="bi bi-telephone me-1"></i>${customer.contact}</div>
                                        </td>
                                        <td>
                                            <div class="small">${customer.city}, ${customer.state}</div>
                                        </td>
                                        <td class="text-center">
                                            <div class="btn-group">
                                                <a href="viewCustomerDetails?id=${customer.customerId}&userEmail=${userEmail}"
                                                   class="btn btn-sm btn-outline-primary" title="View/Edit">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a>
                                                <form action="deleteCustomer" method="POST" class="d-inline" onsubmit="return confirm('Delete this customer permanently?')">
                                                    <input type="hidden" name="id" value="${customer.customerId}">
                                                    <input type="hidden" name="userEmail" value="${userEmail}">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6" class="text-center py-5">
                                        <i class="bi bi-search text-muted display-4 mb-3"></i>
                                        <p class="text-muted">No records found matching your search.</p>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <%-- Pagination Footer --%>
            <div class="card-footer bg-white py-3 border-top">
                <div class="d-flex justify-content-between align-items-center">
                    <span class="text-muted small">Showing Page <strong>${currentPage}</strong> of <strong>${totalPages}</strong></span>

                    <nav>
                        <ul class="pagination pagination-sm mb-0">
                            <c:set var="baseUrl" value="${searchMode ? 'searchCustomer' : 'viewCustomerPage'}" />

                            <%-- Previous Page --%>
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${baseUrl}?userEmail=${userEmail}&page=${currentPage - 1}&searchCustomerName=${param.searchCustomerName}&searchType=${param.searchType}&searchEmail=${param.searchEmail}&searchContactNumber=${param.searchContactNumber}">
                                    <i class="bi bi-chevron-left"></i>
                                </a>
                            </li>

                            <%-- Page Numbers --%>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${baseUrl}?userEmail=${userEmail}&page=${i}&searchCustomerName=${param.searchCustomerName}&searchType=${param.searchType}&searchEmail=${param.searchEmail}&searchContactNumber=${param.searchContactNumber}">${i}</a>
                                </li>
                            </c:forEach>

                            <%-- Next Page --%>
                            <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                                <a class="page-link" href="${baseUrl}?userEmail=${userEmail}&page=${currentPage + 1}&searchCustomerName=${param.searchCustomerName}&searchType=${param.searchType}&searchEmail=${param.searchEmail}&searchContactNumber=${param.searchContactNumber}">
                                    <i class="bi bi-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>