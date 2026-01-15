document.addEventListener("DOMContentLoaded", function () {
    if (typeof axios === 'undefined') {
        console.error('axios is not available. Aborting AJAX features for editMember.js.');
        return;
    }
    const form = document.getElementById("memberForm");
    const saveBtn = document.getElementById("saveMemberBtn");

    const userName = document.getElementById("userName");
    const role = document.getElementById("role");
    const email = document.getElementById("email");
    const contact = document.getElementById("contact");
    const accountLocked = document.getElementById("accountLocked");

    const togglePasswordBtn = document.getElementById('togglePasswordBtn');
    const passwordFieldsSection = document.getElementById('passwordFieldsSection');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    const errors = {
        userName: document.getElementById("userNameError"),
        role: document.getElementById("roleError"),
        email: document.getElementById("emailError"),
        contact: document.getElementById("contactError"),
        status: document.getElementById("statusError"),
        newPassword: document.getElementById("newPasswordError"),
        confirmPassword: document.getElementById("confirmPasswordError")
    };

    const originalSaveButtonHtml = saveBtn.innerHTML;
    const originalToggleButtonHtml = togglePasswordBtn.innerHTML; 



    function updateDateTime() {
        const now = new Date();
        const options = {
            year: 'numeric', month: 'short', day: 'numeric',
            hour: '2-digit', minute: '2-digit', hour12: true
        };
        const datetimeElement = document.getElementById('datetime');
        if (datetimeElement) {
            const dateStr = now.toLocaleDateString('en-US', options);
            datetimeElement.textContent = `| ${dateStr}`;
        }
    }

    function showError(field, message) {
        field.classList.add("is-invalid");
        field.classList.remove("is-valid");

        const errorSpanId = field.id === 'newPassword' ? 'newPassword' :
                            field.id === 'confirmPassword' ? 'confirmPassword' :
                            field.id;
        const errorSpan = errors[errorSpanId];

        if (errorSpan) {
            errorSpan.textContent = message;
            errorSpan.style.display = "block";
        }
        updateSubmitButton();
    }

    function clearError(field) {
        field.classList.remove("is-invalid");
        field.classList.add("is-valid");

        const errorSpanId = field.id === 'newPassword' ? 'newPassword' :
                            field.id === 'confirmPassword' ? 'confirmPassword' :
                            field.id;
        const errorSpan = errors[errorSpanId];

        if (errorSpan) {
            errorSpan.textContent = '';
            errorSpan.style.display = "none";
        }
        updateSubmitButton();
    }

    function isPasswordSectionVisible() {
        return passwordFieldsSection.style.display !== 'none';
    }

    function updateSubmitButton() {
        const allMainFieldsValid =
            !document.querySelector("#userName.is-invalid, #role.is-invalid, #email.is-invalid, #contact.is-invalid, #accountLocked.is-invalid");

        const allMainFieldsFilled =
            userName.value.trim() !== "" &&
            role.value.trim() !== "" &&
            email.value.trim() !== "" &&
            contact.value.trim() !== "" &&
            accountLocked.value.trim() !== "";

        let passwordFieldsValid = true;
        if (isPasswordSectionVisible()) {
            passwordFieldsValid = !document.querySelector("#newPassword.is-invalid, #confirmPassword.is-invalid");
        }

        saveBtn.disabled = !(allMainFieldsValid && allMainFieldsFilled && passwordFieldsValid);
    }


    function validateUserName() {
        const value = userName.value.trim();
        if (value.length < 3 || value.length > 50) {
            showError(userName, "User name must be between 3 and 50 characters.");
            return false;
        }
        clearError(userName);
        return true;
    }

    function validateRole() {
        if (role.value === "") {
            showError(role, "Member role is required.");
            return false;
        }
        clearError(role);
        return true;
    }

    function validateStatus() {
        if (accountLocked.value === "") {
            showError(accountLocked, "Account status is required.");
            return false;
        }
        clearError(accountLocked);
        return true;
    }

    async function validateEmail() {
        const emailVal = email.value.trim();
        const originalEmail = email.getAttribute("data-original");

        if (emailVal === "") {
            showError(email, "Email cannot be blank.");
            return false;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(emailVal)) {
            showError(email, "Enter a valid email address.");
            return false;
        }

        if (emailVal === originalEmail) {
            clearError(email);
            return true;
        }

        saveBtn.disabled = true;
        saveBtn.innerHTML = `<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span> Checking Email...`;

        let isValid = false;
        try {
            const response = await axios.get("checkMemberEmail", { params: { email: emailVal } });
            const data = response.data;

            if (data.status === "AVAILABLE") {
                clearError(email);
                isValid = true;
            } else {
                showError(email, data.message || "Email already exists.");
            }
        } catch {
            showError(email, "Error checking email. Please try again.");
        } finally {
            saveBtn.innerHTML = originalSaveButtonHtml;
            updateSubmitButton();
        }
        return isValid;
    }

    async function validateContact() {
        const contactVal = contact.value.trim();
        const originalContact = contact.getAttribute("data-original");

        if (contactVal === "") {
            showError(contact, "Contact cannot be blank.");
            return false;
        }

        const contactRegex = /^(\+\d{1,3}[- ]?)?\d{10}$/; 
        if (!contactRegex.test(contactVal)) {
            showError(contact, "Enter a valid 10-digit phone number.");
            return false;
        }

        if (contactVal === originalContact) {
            clearError(contact);
            return true;
        }

        saveBtn.disabled = true;
        saveBtn.innerHTML = `<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span> Checking Contact...`;

        let isValid = false;
        try {
            const response = await axios.get("checkMemberContact", { params: { contact: contactVal } });
            const data = response.data;

            if (data.status === "AVAILABLE") {
                clearError(contact);
                isValid = true;
            } else {
                showError(contact, data.message || "Contact already exists.");
            }
        } catch {
            showError(contact, "Error checking contact. Please try again.");
        } finally {
            saveBtn.innerHTML = originalSaveButtonHtml;
            updateSubmitButton();
        }
        return isValid;
    }

    function validatePasswords() {
        if (!isPasswordSectionVisible()) {
            return true;
        }

        let isValid = true;
        const newPassword = newPasswordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        const minLength = 8;

        if (newPassword === '' && confirmPassword === '') {
            clearError(newPasswordInput);
            clearError(confirmPasswordInput);
            return true;
        }

        const strongPasswordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if (newPassword.length > 0 && newPassword.length < minLength) {
            showError(newPasswordInput, `Password must be at least ${minLength} characters.`);
            isValid = false;
        } else if (newPassword.length > 0 && !strongPasswordRegex.test(newPassword)) {
             showError(newPasswordInput, `Password must contain 8+ chars, including lower, upper, digit, and special char.`);
             isValid = false;
        } else if (newPassword !== '' && confirmPassword === '') {
            showError(confirmPasswordInput, 'Confirmation password is required.');
            isValid = false;
        } else if (newPassword === '' && confirmPassword !== '') {
            showError(newPasswordInput, 'New password is required.');
            isValid = false;
        } else {
            clearError(newPasswordInput);
        }

        if (newPassword.length >= minLength && newPassword !== confirmPassword) {
            showError(confirmPasswordInput, 'Passwords do not match.');
            isValid = false;
        } else if (newPassword.length >= minLength && newPassword === confirmPassword) {
            clearError(confirmPasswordInput);
        }

        return isValid;
    }


    if (togglePasswordBtn && passwordFieldsSection) {
        passwordFieldsSection.style.display = 'none';

        togglePasswordBtn.addEventListener('click', function() {
            const isHidden = passwordFieldsSection.style.display === 'none';

            if (isHidden) {
                passwordFieldsSection.style.display = 'block';
                togglePasswordBtn.innerHTML = '<i class="bi bi-x-circle me-2"></i> Cancel Password Reset';
                togglePasswordBtn.classList.replace('btn-prodex-secondary', 'btn-danger');
                newPasswordInput.focus();
            } else {
                newPasswordInput.value = '';
                confirmPasswordInput.value = '';
                clearError(newPasswordInput);
                clearError(confirmPasswordInput);

                passwordFieldsSection.style.display = 'none';
                togglePasswordBtn.classList.replace('btn-danger', 'btn-prodex-secondary');
                togglePasswordBtn.innerHTML = originalToggleButtonHtml;
            }
            validatePasswords(); 
            updateSubmitButton(); 
        });
    }


    userName.addEventListener("input", validateUserName);
    role.addEventListener("change", validateRole);
    accountLocked.addEventListener("change", validateStatus);

    email.addEventListener("blur", validateEmail);
    contact.addEventListener("blur", validateContact);

    if (newPasswordInput) newPasswordInput.addEventListener('input', validatePasswords);
    if (confirmPasswordInput) confirmPasswordInput.addEventListener('input', validatePasswords);


    function validateAllOnLoad() {
        validateUserName();
        validateRole();
        validateStatus();
        if (email.value.trim() !== "") clearError(email);
        if (contact.value.trim() !== "") clearError(contact);

        updateSubmitButton();
    }
    validateAllOnLoad();

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const validUser = validateUserName();
        const validRole = validateRole();
        const validStatus = validateStatus();
        const validPassword = validatePasswords(); 

        const validEmail = await validateEmail();
        const validContact = await validateContact();

        if (validUser && validRole && validEmail && validContact && validStatus && validPassword) {
            saveBtn.disabled = true;
            saveBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span> Updating...';

            form.submit();
        } else {
            window.scrollTo({ top: 0, behavior: "smooth" });

            saveBtn.innerHTML = originalSaveButtonHtml;
            updateSubmitButton();
        }
    });

    updateDateTime();
    setInterval(updateDateTime, 60000); 
});