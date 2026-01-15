document.addEventListener('DOMContentLoaded', function() {
    if (typeof axios === 'undefined') {
        console.error('Axios is missing!');
        return;
    }

    const pathArray = window.location.pathname.split('/');
    const contextPath = pathArray[1] === 'loginPage' ? '' : '/' + pathArray[1];

    const form = document.getElementById('loginForm');
    const emailOrContactInput = document.getElementById('emailOrContact');
    const passwordInput = document.getElementById('password');
    const loginButton = document.getElementById('loginButton');
    const emailOrContactErrorDiv = document.getElementById('emailError');
    const passwordErrorDiv = document.getElementById('passwordError');
    const jsErrorDiv = document.getElementById('jsError');

    function displayInlineError(inputElement, errorDiv, message) {
        inputElement.classList.add('is-invalid');
        if (errorDiv) {
            errorDiv.textContent = message;
            errorDiv.classList.add('d-block');
        }
    }

    function clearInlineError(inputElement, errorDiv) {
        inputElement.classList.remove('is-invalid');
        if (errorDiv) {
            errorDiv.textContent = '';
            errorDiv.classList.remove('d-block');
        }
    }

    function checkFormValidity() {
        const isEmailValid = emailOrContactInput.value.trim().length > 3;
        const isPasswordFilled = passwordInput.value.trim().length > 0;
        const hasError = emailOrContactInput.classList.contains('is-invalid');
        loginButton.disabled = !(isEmailValid && isPasswordFilled) || hasError;
    }

    document.getElementById('togglePassword').addEventListener('click', function() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        this.querySelector('i').classList.toggle('bi-eye');
        this.querySelector('i').classList.toggle('bi-eye-slash');
    });

    let userCheckTimeout;
    emailOrContactInput.addEventListener('input', function() {
        const value = this.value.trim();
        clearInlineError(emailOrContactInput, emailOrContactErrorDiv);

        clearTimeout(userCheckTimeout);
        if (value.length > 3) {
            userCheckTimeout = setTimeout(() => {
                axios.get(contextPath + '/checkUserExist', { params: { emailOrContact: value } })
                .then(response => {
                    if (response.data.status === 'NOT_FOUND') {
                        displayInlineError(emailOrContactInput, emailOrContactErrorDiv, 'Account not found.');
                    } else if (response.data.status === 'ACCOUNT_LOCKED') {
                        displayInlineError(emailOrContactInput, emailOrContactErrorDiv, 'Account locked. Reset password.');
                    }
                    checkFormValidity();
                }).catch(err => console.error("User check failed", err));
            }, 600);
        }
    });

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const emailVal = emailOrContactInput.value.trim();
        const passVal = passwordInput.value;

        loginButton.disabled = true;
        loginButton.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Verifying...';

        const params = new URLSearchParams();
        params.append('emailOrContact', emailVal);
        params.append('password', passVal);

        axios.post(contextPath + '/checkPassword', params)
        .then(response => {
            const status = response.data.status;
            if (status === 'SUCCESS') {
                form.submit(); 
            } else if (status === 'INVALID_PASSWORD') {
                displayInlineError(passwordInput, passwordErrorDiv, `Wrong password. ${response.data.remainingAttempts} attempts left.`);
                loginButton.innerHTML = 'Login';
                loginButton.disabled = false;
            } else if (status === 'ACCOUNT_LOCKED') {
                displayInlineError(emailOrContactInput, emailOrContactErrorDiv, 'Account locked.');
                loginButton.innerHTML = 'Login';
            } else {
                jsErrorDiv.textContent = "Login failed. Please try again.";
                jsErrorDiv.style.display = 'block';
                loginButton.innerHTML = 'Login';
                loginButton.disabled = false;
            }
        })
        .catch(err => {
            console.error(err);
            loginButton.innerHTML = 'Login';
            loginButton.disabled = false;
        });
    });

    passwordInput.addEventListener('input', () => {
        clearInlineError(passwordInput, passwordErrorDiv);
        checkFormValidity();
    });
});