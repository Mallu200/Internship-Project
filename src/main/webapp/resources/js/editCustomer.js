


document.addEventListener("DOMContentLoaded", () => {
    if (typeof axios === 'undefined') {
        console.error('axios is not available. Aborting AJAX features for editCustomer.js.');
        return;
    }
    const form = document.getElementById("customerForm");
    const saveBtn = document.getElementById("saveCustomerBtn");
    const customerId = document.querySelector('input[name="customerId"]')?.value;

    let path = window.location.pathname;
    let prodexIndex = path.indexOf('/prodex');
    const BASE_URL = prodexIndex !== -1
        ? path.substring(0, prodexIndex + '/prodex'.length)
        : '';
    const ADMIN_BASE_URL = BASE_URL + '/administrator';
    const GEO_API_BASE = BASE_URL;

    const validationStatus = {
        customerName: true, customerType: true, email: true, contact: true,
        country: true, state: true, city: true, pinCode: true, billingAddress: true,
        paymentMode: true, taxId: true, shippingAddress: true,
    };
    const touchedStatus = {};

    let isGeoSelecting = false;
    let countryList = [];
    let stateList = [];
    let cityList = [];


    function setError(field, message) {
        field.classList.add("is-invalid", "input-error-highlight");
        field.classList.remove("is-valid");
        const errorEl = document.getElementById(field.id + "Error");
        if (errorEl) { errorEl.textContent = message; errorEl.style.display = 'block'; }

        const statusEl = field.parentElement.querySelector('.input-group-text');
        if (statusEl) { statusEl.innerHTML = ''; }
    }

    function clearError(field) {
        field.classList.remove("is-invalid", "input-error-highlight");
        field.classList.remove("is-valid");
        const errorEl = document.getElementById(field.id + "Error");
        if (errorEl) { errorEl.style.display = 'none'; }

        const statusEl = field.parentElement.querySelector('.input-group-text');
        if (statusEl) { statusEl.innerHTML = ''; }
    }

    function setValid(field) {
        clearError(field);
        field.classList.remove("is-valid");
    }

    function toggleSaveButton() {
        const shippingToggle = document.getElementById('shippingToggle');
        const isShippingToggleChecked = shippingToggle?.checked || false;

        let isShippingValid = isShippingToggleChecked || validationStatus['shippingAddress'];

        const requiredFields = Object.keys(validationStatus).filter(k =>
            k !== 'taxId' && k !== 'shippingAddress'
        );

        const allRequiredValid = requiredFields.every(k => validationStatus[k]);

        const isTaxIdValid = validationStatus.taxId;

        saveBtn.disabled = !(allRequiredValid && isShippingValid && isTaxIdValid);
    }

    const debounce = (fn, delay = 600) => {
        let timer;
        return (...args) => { clearTimeout(timer); timer = setTimeout(() => fn(...args), delay); };
    };

    const validators = {
        customerName: v => v.length > 0 && v.length <= 100 ? true : "Customer name is required (max 100 characters).",
        customerType: v => v.length > 0 ? true : "Customer type is required.",
        email: v => {
            if (!v.length) return "Email is required.";
            const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return re.test(v) ? true : "Invalid email format.";
        },
        contact: v => {
            if (!v.length) return "Contact number is required.";
            const re = /^[0-9+()\s\-]{5,20}$/;
            return re.test(v) ? true : "Invalid contact number format (5-20 characters, allowing digits, +, -, (), and spaces).";
        },
        taxId: v => {
            if (!v.length) return true; 
            if (v.length > 20) return "Tax ID cannot exceed 20 characters.";
            return true;
        },
        country: v => v.length > 0 ? true : "Country is required.",
        state: v => v.length > 0 ? true : "State/Province/Region is required.",
        city: v => v.length > 0 ? true : "City/Town is required.",
        pinCode: v => {
            if (!v.length) return "Pin code is required.";
            const re = /^[a-zA-Z0-9\s\-]{3,10}$/;
            return re.test(v) ? true : "Pin code must be a valid postal/zip code (3-10 alphanumeric characters).";
        },
        billingAddress: v => v.length > 0 ? true : "Billing address is required.",
        paymentMode: v => v.length > 0 ? true : "Payment mode is required.",
        shippingAddress: v => {
            const isToggleChecked = document.getElementById('shippingToggle')?.checked || false;
            if (!isToggleChecked) {
                if (v.length === 0) return "Shipping address is required if different from billing.";
                if (v.length > 255) return "Shipping address cannot exceed 255 characters.";
            }
            return true;
        }
    };

    async function checkUniqueness(field, type) {
        const val = field.value.trim();
        const originalVal = field.dataset.original;
        const statusEl = document.getElementById(type + "Status");
        if (statusEl) statusEl.innerHTML = ''; 

        if (val === originalVal) {
            setValid(field);
            validationStatus[type] = true;
            toggleSaveButton();
            return;
        }

        if (!val && type === 'taxId') {
            setValid(field);
            validationStatus[type] = true;
            toggleSaveButton();
            return;
        }

        const urlMap = {
            email: '/checkCustomerEmail',
            contact: '/checkCustomerContact',
            taxId: '/checkCustomerTaxId'
        };

        if(validators[type](val) !== true) return;

        let url = `${ADMIN_BASE_URL}${urlMap[type]}?${type}=${encodeURIComponent(val)}&customerId=${encodeURIComponent(customerId)}`;

        try {
            statusEl.innerHTML = '<i class="bi bi-arrow-repeat spin"></i>';
            const res = await axios.get(url);
            const { status, message } = res.data;

            if (status === 'EXISTS') {
                setError(field, message);
                validationStatus[type] = false;
                statusEl.innerHTML = '<i class="bi bi-x-circle-fill text-danger"></i>'; 
            } else if (status === 'AVAILABLE' || status === 'NOT_REQUIRED') {
                setValid(field);
                validationStatus[type] = true;
                // REMOVED GREEN CHECKMARK: statusEl.innerHTML = '<i class="bi bi-check-circle-fill text-success"></i>';
            } else {
                 setError(field, message || "Validation failed.");
                 validationStatus[type] = false;
                 statusEl.innerHTML = '<i class="bi bi-exclamation-triangle-fill text-warning"></i>';
            }
        } catch (e) {
            setError(field, "Validation service error.");
            validationStatus[type] = false;
            statusEl.innerHTML = '<i class="bi bi-x-circle-fill text-danger"></i>';
        }
        toggleSaveButton();
    }

    async function validateField(field) {
        const name = field.name;
        if (isGeoSelecting && (name === 'state' || name === 'city' || name === 'country')) return;

        const val = field.value.trim();
        if (!(name in validators)) return;

        const result = validators[name](val);

        if (result !== true) {
            setError(field, result);
            validationStatus[name] = false;
            toggleSaveButton();
            return;
        }

        setValid(field);
        validationStatus[name] = true;
        toggleSaveButton();

        if (['email', 'contact', 'taxId'].includes(name)) {
            await checkUniqueness(field, name);
        }
    }

    function showDropdown(inputField, dropdownId, items) {
        const dropdown = document.getElementById(dropdownId);
        dropdown.innerHTML = '';
        items.forEach(i => {
            const btn = document.createElement('button');
            btn.type = 'button';
            btn.className = 'dropdown-item';
            btn.textContent = i;
            btn.addEventListener('click', () => {
                inputField.value = i;
                dropdown.classList.remove('show');

                inputField.dispatchEvent(new Event('blur'));

                if(inputField.id === 'country' || inputField.id === 'state') {
                    inputField.dispatchEvent(new Event('change'));
                }
            });
            dropdown.appendChild(btn);
        });
        if (items.length > 0) dropdown.classList.add('show');
        else dropdown.classList.remove('show');
    }

    document.addEventListener('click', e => {
        ['countryDropdown', 'stateDropdown', 'cityDropdown'].forEach(id => {
            const dropdown = document.getElementById(id);
            const input = dropdown ? document.getElementById(id.replace('Dropdown', '')) : null;
            if (dropdown && (!dropdown.contains(e.target) && e.target !== input)) {
                dropdown.classList.remove('show');
            }
        });
    });

    function getCountryCode(countryName) {
        return countryName.toUpperCase() === 'INDIA' ? 'IN' : countryName;
    }

    function loadAddressData() {
        const countryInput = document.getElementById('countryInput');
        const stateInput = document.getElementById('stateInput');
        const cityInput = document.getElementById('cityInput');

        if (!countryInput || !stateInput || !cityInput) return;

        const initialCountry = countryInput.dataset.initialValue;
        const initialState = stateInput.dataset.initialValue;
        const initialCity = cityInput.dataset.initialValue;

        countryInput.addEventListener('input', debounce(() => {
            isGeoSelecting = true;
            const val = countryInput.value.trim().toLowerCase();

            if (countryList.length === 0) {
                axios.get(`${GEO_API_BASE}/getCountries`).then(res => {
                    isGeoSelecting = false;
                    if (res.data.status === 'SUCCESS' && Array.isArray(res.data.data)) {
                        countryList = res.data.data;
                        const filtered = countryList.filter(c => c.toLowerCase().includes(val));
                        showDropdown(countryInput, 'countryDropdown', filtered);
                    }
                }).catch(e => {
                    isGeoSelecting = false;
                    setError(countryInput, "Error fetching countries from service.");
                });
            } else {
                isGeoSelecting = false;
                const filtered = countryList.filter(c => c.toLowerCase().includes(val));
                showDropdown(countryInput, 'countryDropdown', filtered);
            }
        }, 300));

        countryInput.addEventListener('blur', () => validateField(countryInput));

        countryInput.addEventListener('change', () => {
            const countryVal = countryInput.value.trim();

            if (stateInput.value.trim() !== initialState || countryVal !== initialCountry) {
                stateInput.value = '';
                cityInput.value = '';
                cityInput.disabled = true;
                validationStatus.state = false;
                validationStatus.city = false;
                clearError(stateInput);
                clearError(cityInput);
                stateInput.oninput = null;
            }

            if (validationStatus.country) {
                stateInput.disabled = false;
                const countryCode = getCountryCode(countryVal);

                const setupStateAutocomplete = (states) => {
                    stateList = states;
                    stateInput.oninput = debounce(() => {
                        isGeoSelecting = true;
                        const val = stateInput.value.trim().toLowerCase();
                        const filtered = stateList.filter(s => s.toLowerCase().includes(val));
                        showDropdown(stateInput, 'stateDropdown', filtered);
                        isGeoSelecting = false;
                    }, 300);
                };

                axios.get(`${GEO_API_BASE}/getStates?countryCode=${countryCode}`).then(res => {
                    if (res.data.status === 'SUCCESS' && Array.isArray(res.data.data)) {
                        setupStateAutocomplete(res.data.data);
                        if (countryVal === initialCountry && initialState && initialCity) {
                            stateInput.dispatchEvent(new Event('change'));
                        }
                    } else {
                        setError(stateInput, "Error fetching states or no states found.");
                        stateInput.disabled = true;
                    }
                }).catch(e => {
                    setError(stateInput, "Error fetching states.");
                    stateInput.disabled = true;
                });
            } else {
                stateInput.disabled = true;
            }
            toggleSaveButton();
        });

        stateInput.addEventListener('blur', () => validateField(stateInput));

        stateInput.addEventListener('change', () => {
            const stateVal = stateInput.value.trim();
            validateField(stateInput);

            if (stateVal !== initialState || cityInput.value.trim() !== initialCity) {
                cityInput.value = '';
                validationStatus.city = false;
                clearError(cityInput);
                cityInput.oninput = null;
            }

            if (validationStatus.state) {
                cityInput.disabled = false;

                axios.get(`${GEO_API_BASE}/getCities?stateName=${encodeURIComponent(stateVal)}`)
                    .then(res => {
                        if (res.data.status === 'SUCCESS' && Array.isArray(res.data.data)) {
                            cityList = res.data.data;

                            cityInput.oninput = debounce(() => {
                                const val = cityInput.value.trim().toLowerCase();
                                const filtered = cityList.filter(c => c.toLowerCase().includes(val));
                                showDropdown(cityInput, 'cityDropdown', filtered);
                            }, 300);

                            if (stateVal === initialState && initialCity) {
                                validateField(cityInput);
                            }
                        } else {
                            setError(cityInput, "No cities found or service error.");
                            cityInput.disabled = true;
                        }
                    })
                    .catch(e => {
                        setError(cityInput, "Error fetching cities from service.");
                        cityInput.disabled = true;
                    });
            } else {
                cityInput.disabled = true;
            }
            toggleSaveButton();
        });

        cityInput.addEventListener('blur', () => validateField(cityInput));

        if (initialCountry) {
            countryInput.dispatchEvent(new Event('change'));
        } else {
            stateInput.disabled = true;
            cityInput.disabled = true;
            validationStatus.country = false;
            validationStatus.state = false;
            validationStatus.city = false;
        }
    }

    function setupShippingToggle() {
        const toggle = document.getElementById('shippingToggle');
        const container = document.getElementById('shippingAddressContainer');
        const shippingField = document.getElementById('shippingAddressInput'); 
        const billingField = document.getElementById('billingAddressInput');   
        const hiddenField = document.getElementById('shippingSameAsBilling');
        const name = 'shippingAddress';

        if (!toggle || !shippingField || !billingField) return;

        const updateShippingAddress = () => {
            if (toggle.checked) {
                container.style.display = 'none';
                shippingField.value = billingField.value;
                setValid(shippingField);
                validationStatus[name] = true;
                hiddenField.value = 'true';
            } else {
                container.style.display = 'block';
                if (shippingField.value.trim() === billingField.value.trim() && !touchedStatus[name]) {
                    shippingField.value = '';
                }
                hiddenField.value = 'false';

                validateField(shippingField);
            }
            toggleSaveButton();
        };

        toggle.addEventListener('change', updateShippingAddress);

        billingField.addEventListener('input', () => {
            if (toggle.checked) {
                shippingField.value = billingField.value;
                setValid(shippingField);
                validationStatus[name] = true;
            } else {
                if(touchedStatus[name] || shippingField.value.trim().length > 0) {
                     validateField(shippingField);
                }
            }
        });

        shippingField.addEventListener('blur', () => {
            touchedStatus[name] = true;
            validateField(shippingField);
        });

        shippingField.addEventListener('input', debounce(() => {
            if (touchedStatus[name]) {
                 validateField(shippingField);
            }
        }, 400));

        updateShippingAddress();
    }

    function initialize() {
        Object.keys(validators).forEach(name => {
            const field = form.querySelector(`[name=${name}]`);
            if (!field) return;

            if(field.value.trim().length > 0) {
                 setValid(field);
                 validationStatus[name] = true;
                 touchedStatus[name] = true;
            } else {
                 validationStatus[name] = false;
                 touchedStatus[name] = false;
            }

            if (['country', 'state', 'city', 'shippingAddress'].includes(name)) return;

            field.addEventListener('blur', () => {
                touchedStatus[name] = true;
                validateField(field);
            });

            if (field.tagName === 'SELECT') {
                field.addEventListener('change', () => {
                    touchedStatus[name] = true;
                    validateField(field);
                });
            } else {
                 const delay = ['email', 'contact', 'taxId'].includes(name) ? 800 : 400;
                 field.addEventListener('input', debounce(() => {
                    if (touchedStatus[name]) validateField(field);
                }, delay));
            }
        });

        loadAddressData();
        setupShippingToggle();
        toggleSaveButton();
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
        datetimeElement.innerText = now.toLocaleString("en-US", options);
    }
}
