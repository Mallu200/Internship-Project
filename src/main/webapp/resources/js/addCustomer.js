


document.addEventListener("DOMContentLoaded", () => {
    if (typeof axios === 'undefined') {
        console.error('axios is not available. Aborting AJAX features for addCustomer.js.');
        return;
    }
    const form = document.getElementById("customerForm");
    const saveBtn = document.getElementById("saveCustomerBtn");

    let path = window.location.pathname;
    let prodexIndex = path.indexOf('/prodex');
    const BASE_URL = prodexIndex !== -1
        ? path.substring(0, prodexIndex + '/prodex'.length)
        : '';
    const ADMIN_BASE_URL = BASE_URL + '/administrator';
    const GEO_API_BASE = BASE_URL;

    const validationStatus = {
        customerName: false, customerType: false, email: false, contact: false,
        country: false, state: false, city: false, pinCode: false, billingAddress: false,
        paymentMode: false,
        taxId: true,
        shippingAddress: true, 
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

    function toggleSaveButton() {
        const shippingToggle = document.getElementById('shippingToggle');
        const isShippingToggleChecked = shippingToggle ? shippingToggle.checked : true;

        let isShippingValid = isShippingToggleChecked;

        if (!isShippingToggleChecked) {
             if (touchedStatus['shippingAddress']) {
                isShippingValid = validationStatus['shippingAddress'];
             } else {
                isShippingValid = true;
             }
        }

        const requiredFields = Object.keys(validationStatus).filter(k =>
            k !== 'shippingAddress' && k !== 'taxId'
        );

        const allRequiredValid = requiredFields.every(k => validationStatus[k] && touchedStatus[k]);

        const taxIdField = document.getElementById('taxId');
        const isTaxIdValid = taxIdField.value.trim().length === 0 || (taxIdField.value.trim().length > 0 && validationStatus.taxId);

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
        state: v => v.length > 0 ? true : "State is required.",
        city: v => v.length > 0 ? true : "City is required.",
        pinCode: v => {
            if (!v.length) return "Pin code is required.";
            const re = /^[a-zA-Z0-9\s\-]{3,10}$/;
            return re.test(v) ? true : "Pin code must be a valid postal/zip code (3-10 alphanumeric characters).";
        },
        billingAddress: v => v.length > 0 ? true : "Billing address is required.",
        paymentMode: v => v.length > 0 ? true : "Payment mode is required.",
        shippingAddress: v => {
            const isToggleChecked = document.getElementById('shippingToggle').checked;
            if (!isToggleChecked) {
                if (touchedStatus['shippingAddress']) {
                    if (v.length === 0) return "Shipping address is required if different from billing.";
                    if (v.length > 255) return "Shipping address cannot exceed 255 characters.";
                }
            }
            return true;
        }
    };

    async function checkUniqueness(field, type) {
        const val = field.value.trim();

        if (type === 'taxId' && !val) {
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

        let url = `${ADMIN_BASE_URL}${urlMap[type]}?${type}=${encodeURIComponent(val)}`;
        const statusEl = document.getElementById(type + "Status");

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
                statusEl.innerHTML = '<i class="bi bi-check-circle-fill text-success"></i>';
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

    async function validateField(field, isInitial = false) {
        const name = field.name;
        if (isGeoSelecting && (name === 'state' || name === 'city' || name === 'country')) return;

        const val = field.value.trim();
        if (!(name in validators)) return;

        if (!touchedStatus[name] && !isInitial) {
             return;
        }

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

        if (['email', 'contact', 'taxId'].includes(name) && !isInitial) {
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

                isGeoSelecting = false;
                inputField.dispatchEvent(new Event('blur'));
                if (inputField.id === 'country' || inputField.id === 'state') {
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
        const countryInput = document.getElementById('country');
        const stateInput = document.getElementById('state');
        const cityInput = document.getElementById('city');
        const initialCountry = countryInput.getAttribute('data-initial-value');
        const initialState = stateInput.getAttribute('data-initial-value');

        countryInput.addEventListener('input', debounce(() => {
            isGeoSelecting = true;
            const val = countryInput.value.trim().toLowerCase();
            axios.get(`${GEO_API_BASE}/getCountries`).then(res => {
                if (res.data.status === 'SUCCESS' && Array.isArray(res.data.data)) {
                    countryList = res.data.data;
                    const filtered = countryList.filter(c => c.toLowerCase().includes(val));
                    showDropdown(countryInput, 'countryDropdown', filtered);
                }
            }).catch(e => console.error("Error fetching countries:", e));
        }, 300));

        countryInput.addEventListener('blur', () => {
            if (!document.getElementById('countryDropdown').classList.contains('show')) {
                isGeoSelecting = false;
                touchedStatus['country'] = true; 
                validateField(countryInput);
            }
        });

        countryInput.addEventListener('change', () => {
            isGeoSelecting = true;
            const countryVal = countryInput.value.trim();

            stateInput.value = '';
            cityInput.value = '';
            stateList = [];
            cityList = [];
            cityInput.disabled = true;

            validationStatus.state = false;
            validationStatus.city = false;
            touchedStatus.state = false; 
            touchedStatus.city = false;

            clearError(stateInput);
            clearError(cityInput);

            if (validationStatus.country) {
                stateInput.disabled = false;
                const countryCode = getCountryCode(countryVal);

                axios.get(`${GEO_API_BASE}/getStates?countryCode=${countryCode}`).then(res => {
                    if (res.data.status === 'SUCCESS' && Array.isArray(res.data.data)) {
                        stateList = res.data.data;

                        stateInput.oninput = debounce(() => {
                            const val = stateInput.value.trim().toLowerCase();
                            const filtered = stateList.filter(s => s.toLowerCase().includes(val));
                            showDropdown(stateInput, 'stateDropdown', filtered);
                        }, 300);

                        if (initialState.length > 0 && countryVal === initialCountry) {
                            validateField(stateInput, true);
                            if (validationStatus.state) {
                                stateInput.dispatchEvent(new Event('change'));
                            }
                        }

                    } else {
                        setError(stateInput, "Error fetching states or no states found.");
                        stateInput.disabled = true;
                    }
                }).catch(e => {
                    setError(stateInput, "Error fetching states.");
                    stateInput.disabled = true;
                }).finally(() => {
                    isGeoSelecting = false;
                    toggleSaveButton();
                });
            } else {
                stateInput.disabled = true;
                isGeoSelecting = false;
                toggleSaveButton();
            }
        });

        stateInput.addEventListener('blur', () => {
            if (!document.getElementById('stateDropdown').classList.contains('show')) {
                isGeoSelecting = false;
                touchedStatus['state'] = true; 
                validateField(stateInput);
            }
        });

        stateInput.addEventListener('change', () => {
            isGeoSelecting = true;
            const stateVal = stateInput.value.trim();

            cityInput.value = '';
            cityList = [];
            validationStatus.city = false;
            touchedStatus.city = false; 
            clearError(cityInput);

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
                        } else {
                            setError(cityInput, "No cities found or service error.");
                            cityInput.disabled = true;
                        }
                    })
                    .catch(e => {
                        setError(cityInput, "Error fetching cities from service.");
                        cityInput.disabled = true;
                    })
                    .finally(() => {
                        isGeoSelecting = false;
                        toggleSaveButton();
                    });
            } else {
                isGeoSelecting = false;
                cityInput.disabled = true;
                toggleSaveButton();
            }
        });

        cityInput.addEventListener('blur', () => {
            if (!document.getElementById('cityDropdown').classList.contains('show')) {
                isGeoSelecting = false;
                touchedStatus['city'] = true; 
                validateField(cityInput);
            }
        });

        if (initialCountry.length > 0) {
            validateField(countryInput, true);
            if (validationStatus.country) {
                countryInput.dispatchEvent(new Event('change'));
            }
        }
    }

    function setupShippingToggle() {
        const toggle = document.getElementById('shippingToggle');
        const container = document.getElementById('shippingAddressContainer');
        const shippingField = document.getElementById('shippingAddress');
        const billingField = document.getElementById('billingAddress');
        const hiddenField = document.getElementById('shippingSameAsBilling');
        const name = 'shippingAddress';

        let billingAddressSnapshot = billingField.value.trim();

        const updateShippingAddress = () => {
            if (toggle.checked) {
                container.style.display = 'none';
                shippingField.value = billingField.value;
                billingAddressSnapshot = billingField.value.trim();

                setValid(shippingField);
                validationStatus[name] = true;
                hiddenField.value = 'true';
            } else {
                container.style.display = 'block';

                if (shippingField.value.trim() === billingAddressSnapshot) {
                    shippingField.value = '';
                }

                hiddenField.value = 'false';

                if (touchedStatus[name]) {
                    validateField(shippingField);
                } else {
                    clearError(shippingField);
                    shippingField.classList.remove('is-valid');
                    validationStatus[name] = true;
                }
            }
            toggleSaveButton();
        };

        toggle.addEventListener('change', updateShippingAddress);

        billingField.addEventListener('input', () => {
            if (toggle.checked) {
                shippingField.value = billingField.value;
                billingAddressSnapshot = billingField.value.trim();
            }
            validateField(shippingField);
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

        return { updateShippingAddress };
    }

    function initialize() {
        const shippingToggle = document.getElementById('shippingToggle');
        const shippingField = document.getElementById('shippingAddress');
        const isInitialLoad = shippingField.value.trim() === '';

        if (isInitialLoad && !shippingToggle.checked) {
             shippingToggle.checked = true;
             document.getElementById('shippingSameAsBilling').value = 'true';
        }

        const shippingHandlers = setupShippingToggle();

        Object.keys(validators).forEach(name => {
            const field = form.querySelector(`[name=${name}]`);
            if (!field || name === 'shippingAddress') return; 

            if (field.value.trim().length > 0) {
                 validateField(field, true);
                 touchedStatus[name] = true; 
            } else {
                touchedStatus[name] = false;
            }

            field.addEventListener('blur', () => {
                touchedStatus[name] = true;
                validateField(field);
            });

            if (!['country', 'state', 'city', 'email', 'contact', 'taxId'].includes(name)) {
                field.addEventListener('input', debounce(() => {
                    if (touchedStatus[name]) validateField(field);
                }, 400));
            }
            if (['email', 'contact', 'taxId'].includes(name)) {
                 field.addEventListener('input', debounce(() => {
                    if (touchedStatus[name]) validateField(field);
                }, 800));
            }
            if (field.tagName === 'SELECT') {
                field.addEventListener('change', () => {
                    touchedStatus[name] = true;
                    validateField(field);
                });
            }
        });

        touchedStatus['shippingAddress'] = !isInitialLoad;

        loadAddressData();
        shippingHandlers.updateShippingAddress();

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
setInterval(updateDateTime, 1000);
updateDateTime();