


document.addEventListener("DOMContentLoaded", () => {
    if (typeof axios === 'undefined') {
        console.error('axios is not available. Aborting AJAX features for purchaseProduct.js.');
        return;
    }
    const form = document.getElementById("purchaseForm");
    const submitRequestBtn = document.getElementById("savePurchaseBtn");

    const requestConfirmModalElement = document.getElementById('orderConfirmModal');
    let modal;
    if (requestConfirmModalElement) {
        modal = new bootstrap.Modal(requestConfirmModalElement, {});
    }
    const confirmSubmissionBtn = document.getElementById('confirmSubmissionBtn');

    let path = window.location.pathname;
    let contextIndex = path.indexOf('/purchaseProduct');
    const BASE_URL = contextIndex !== -1
        ? path.substring(0, contextIndex)
        : '';

    const validationStatus = {
        customerName: false, productGroupName: false, make: false, model: false,
        productCode: false, itemName: false, initialPrice: false, purchasePrice: false,
        stockInHand: false,
        orderDueDate: false
    };
    const touchedStatus = {};

    const REQUIRED_FIELDS = Object.keys(validationStatus);

    function setError(field, message) {
        field.classList.add("is-invalid", "input-error-highlight");
        field.classList.remove("is-valid");
        const errorEl = document.getElementById(field.id + "Error");
        if (errorEl) { errorEl.textContent = message; errorEl.style.display = 'block'; }
    }

    function clearError(field) {
        field.classList.remove("is-invalid", "input-error-highlight");
        field.classList.remove("is-valid");
        const errorEl = document.getElementById(field.id + "Error");
        if (errorEl) { errorEl.style.display = 'none'; }
    }

    function setValid(field) {
        clearError(field);
        field.classList.add("is-valid");
    }

    function toggleSubmitRequestButton() {
        let allRequiredValid = true;

        console.log("--- Validation Status Check (Toggle Submit Request Button) ---");
        REQUIRED_FIELDS.forEach(k => {
            const isFieldValidAndTouched = validationStatus[k] && touchedStatus[k];
            console.log(`Field: ${k}, Valid: ${validationStatus[k]}, Touched: ${touchedStatus[k]}, Status: ${isFieldValidAndTouched ? 'PASS' : 'FAIL'}`);
            if (!isFieldValidAndTouched) {
                allRequiredValid = false;
            }
        });
        console.log(`Final All Required Valid: ${allRequiredValid}`);

        submitRequestBtn.disabled = !allRequiredValid;
    }

    const debounce = (fn, delay = 600) => {
        let timer;
        return (...args) => { clearTimeout(timer); timer = setTimeout(() => fn(...args), delay); };
    };

    const validators = {
        customerName: v => v.length > 0 ? true : "Customer Name is required.",
        productGroupName: v => v.length > 0 ? true : "Product Group Name is required.",
        make: v => v.length > 0 && v.length <= 100 ? true : "Manufacturer (Make) is required (max 100 chars).",
        model: v => v.length > 0 && v.length <= 100 ? true : "Model is required (max 100 chars).",
        productCode: v => v.length > 0 && v.length <= 50 ? true : "Product Code is required (max 50 chars).",
        itemName: v => v.length > 0 ? true : "Item Name could not be generated. Check Make, Model, and Product Code.",
        orderDueDate: v => v.length > 0 ? true : "Fulfillment Target Date is required.",

        initialPrice: v => {
            if (!v.length) return "Initial Price is required.";
            const num = parseFloat(v);
            if (isNaN(num) || num <= 0) return "Initial Price must be a number greater than 0.00.";
            return true;
        },
        purchasePrice: v => {
            if (!v.length) return "Purchase Price is required.";
            const num = parseFloat(v);
            if (isNaN(num) || num <= 0) return "Purchase Price must be a number greater than 0.00.";
            return true;
        },
        stockInHand: v => {
            if (!v.length) return "Quantity Requested is required.";
            const num = parseInt(v, 10);
            if (isNaN(num) || num < 0 || !Number.isInteger(num)) return "Quantity must be a whole number (0 or greater).";
            return true;
        }
    };

    function validateField(field, isInitial = false) {
        const name = field.name;
        const val = field.value.trim();

        if (!(name in validators)) return;

        if (!touchedStatus[name] && !isInitial) {
             return;
        }

        const result = validators[name](val);

        if (result !== true) {
            setError(field, result);
            validationStatus[name] = false;
            toggleSubmitRequestButton();
            return false;
        }

        setValid(field);
        validationStatus[name] = true;
        toggleSubmitRequestButton();
        return true;
    }

    function updateItemName(triggerField) {
        const make = document.getElementById('make').value.trim();
        const model = document.getElementById('model').value.trim();
        const productCode = document.getElementById('productCode').value.trim();
        const itemNameField = document.getElementById('itemName');
        const name = 'itemName';

        let parts = [make, model, productCode].filter(part => part.length > 0);
        itemNameField.value = parts.join(' - ');

        const isSourceTouched = touchedStatus['make'] || touchedStatus['model'] || touchedStatus['productCode'];

        if (isSourceTouched || touchedStatus[name]) {
            touchedStatus[name] = true;
            validateField(itemNameField);
        } else if (itemNameField.value.length > 0) {
            touchedStatus[name] = true;
            validateField(itemNameField, true);
        }
    }

    function validateAndConfirmRequestSubmission(e) {
        e.preventDefault();

        let isFormValid = true;

        REQUIRED_FIELDS.forEach(name => {
            const field = document.getElementById(name);
            if (field) {
                touchedStatus[name] = true;
                if (!validateField(field, true)) {
                    isFormValid = false;
                }
            }
        });

        if (!isFormValid) {
            const firstInvalid = form.querySelector('.is-invalid');
            if (firstInvalid) {
                firstInvalid.scrollIntoView({ behavior: 'smooth', block: 'center' });
                firstInvalid.focus();
            }
            return;
        }

        if (!modal) {
             if (confirm("Validation passed, but the confirmation dialog failed to load. Proceed with request submission?")) {
                 form.submit();
             }
             return;
        }


        const stockInHand = parseInt(document.getElementById('stockInHand').value);
        const purchasePrice = parseFloat(document.getElementById('purchasePrice').value);
        const itemName = document.getElementById('itemName').value;

        const totalOrderValue = stockInHand * purchasePrice;

        const formattedTotalValue = new Intl.NumberFormat('en-IN', {
            style: 'currency',
            currency: 'INR'
        }).format(totalOrderValue);

        document.getElementById('modalItemName').textContent = itemName;
        document.getElementById('modalQuantity').textContent = stockInHand;
        document.getElementById('modalTotalValue').textContent = formattedTotalValue;

        modal.show();
    }

    // 4. Handle the Modal's custom "Confirm Submission" button click
    if (confirmSubmissionBtn) {
        confirmSubmissionBtn.addEventListener('click', () => {
            if (modal) modal.hide();
            form.submit();
        });
    }


    async function populateDropdown(selectId, endpoint, initialText, preSelectedValue) {
        const select = document.getElementById(selectId);
        if (!select) return;

        select.innerHTML = `<option value="" selected disabled>${initialText}...</option>`;

        try {
            const res = await axios.get(`${BASE_URL}${endpoint}`);
            if (res.data && res.data.status === 'SUCCESS' && Array.isArray(res.data.data)) {

                select.innerHTML = `<option value="" ${!preSelectedValue ? 'selected' : ''} disabled>${initialText}</option>`;

                res.data.data.forEach(item => {
                    const option = document.createElement('option');
                    const value = item;
                    option.value = value;
                    option.textContent = value;

                    if (preSelectedValue && value === preSelectedValue) {
                        option.selected = true;
                        validationStatus[selectId] = true;
                        touchedStatus[selectId] = true;
                        setValid(select);
                    }
                    select.appendChild(option);
                });
            } else {
                select.innerHTML = `<option value="" selected disabled>Error Loading Data</option>`;
            }
        } catch (e) {
            console.error(`Error loading dropdown data for ${selectId}:`, e);
            select.innerHTML = `<option value="" selected disabled>Service Unavailable</option>`;
        }

        if (!select.value || select.value === "") {
             validationStatus[selectId] = false;
        } else {
             validationStatus[selectId] = true;
             touchedStatus[selectId] = true;
        }

        toggleSubmitRequestButton();
    }


    function initialize() {
        REQUIRED_FIELDS.forEach(name => {
            const field = document.getElementById(name);
            if (!field) return;

            const isPreFilled = field.value.trim().length > 0;

            if (isPreFilled) {
                 validateField(field, true);
                 touchedStatus[name] = true;
            } else {
                validationStatus[name] = false;
                touchedStatus[name] = false;
            }

            field.addEventListener('blur', () => {
                touchedStatus[name] = true;
                validateField(field);
            });

            const eventType = (field.tagName === 'SELECT' || field.type === 'date') ? 'change' : 'input';

            if (eventType === 'input') {
                field.addEventListener('input', debounce(() => {
                    if (touchedStatus[name]) validateField(field);
                }, 400));
            } else {
                 field.addEventListener('change', () => {
                    touchedStatus[name] = true;
                    validateField(field);
                });
            }

            if (field.type === 'number') {
                field.addEventListener('input', () => {
                    touchedStatus[name] = true;
                    validateField(field);
                });
            }
        });

        document.getElementById('make').addEventListener('input', updateItemName);
        document.getElementById('model').addEventListener('input', updateItemName);
        document.getElementById('productCode').addEventListener('input', updateItemName);

        updateItemName();

        const preSelected = {
            customerName: document.getElementById('customerName').value,
            productGroupName: document.getElementById('productGroupName').value
        };

        populateDropdown('customerName', '/getDebitCustomerNames', '-- Select Debit Customer', preSelected.customerName);
        populateDropdown('productGroupName', '/getProductGroupNames', '-- Select Product Group', preSelected.productGroupName);

        form.addEventListener('submit', validateAndConfirmRequestSubmission);
        toggleSubmitRequestButton();
    }

    initialize();
});


function updateDateTime() {
    const now = new Date();
    const datetimeElement = document.getElementById("datetime");
    if (datetimeElement) {
        const options = {
            year: 'numeric', month: 'short', day: 'numeric',
            hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: true
        };
        datetimeElement.innerText = now.toLocaleString("en-IN", options);
    }
}
setInterval(updateDateTime, 1000);
updateDateTime();