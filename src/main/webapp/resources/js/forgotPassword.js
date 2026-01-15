document.addEventListener('DOMContentLoaded', function() {
    if (typeof axios === 'undefined') {
        console.error('Axios missing. Password recovery features disabled.');
        return;
    }

    const form = document.getElementById("forgotPasswordForm");
    const emailInput = document.getElementById("email");
    const sendOtpButton = document.getElementById("sendOtpButton");
    const emailErrorDiv = document.getElementById("emailError");
    const jsErrorDiv = document.getElementById("jsError");

    const pathArray = window.location.pathname.split('/');
    const contextPath = pathArray[1] === 'forgotPasswordPage' ? '' : '/' + pathArray[1];

    function setFeedback(isValid, message = "") {
        if (isValid) {
            emailInput.classList.remove("is-invalid");
            emailInput.classList.add("is-valid");
            emailErrorDiv.textContent = "";
            sendOtpButton.disabled = false;
        } else {
            emailInput.classList.remove("is-valid");
            emailInput.classList.add("is-invalid");
            emailErrorDiv.textContent = message;
            sendOtpButton.disabled = true;
        }
    }

    function resetUI() {
        emailInput.classList.remove("is-invalid", "is-valid");
        emailErrorDiv.textContent = "";
        jsErrorDiv.style.display = "none";
        sendOtpButton.disabled = true;
    }

    let debounceTimer;
    async function checkEmailExistence() {
        const email = emailInput.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!email) {
            resetUI();
            return;
        }

        if (!emailRegex.test(email)) {
            setFeedback(false, "Invalid enterprise email format.");
            return;
        }

        try {
            const res = await axios.get(contextPath + "/checkRegisteredEmail", {
                params: { email: email }
            });

            if (res.data.status === "FOUND") {
                setFeedback(true);
            } else if (res.data.status === "NOT_FOUND") {
                setFeedback(false, "This email is not registered in our system.");
            } else {
                setFeedback(false, "Account status issue. Contact administrator.");
            }
        } catch (err) {
            console.error("Email verification failed", err);
            jsErrorDiv.style.display = "block";
            jsErrorDiv.textContent = "Unable to reach security server. Please try again later.";
        }
    }

    emailInput.addEventListener("input", function() {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(checkEmailExistence, 700);
    });

    emailInput.addEventListener("blur", checkEmailExistence);

    form.addEventListener("submit", async function (e) {
        // We let the form submit naturally to the action="sendOtp"

        if (!emailInput.classList.contains("is-valid")) {
            e.preventDefault();
            return;
        }

        sendOtpButton.disabled = true;
        sendOtpButton.innerHTML = `
            <span class="spinner-border spinner-border-sm me-2"></span>
            Verifying Identity...
        `;
    });
});