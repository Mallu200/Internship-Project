document.addEventListener('DOMContentLoaded', function () {
    const timerDisplay = document.getElementById('timerDisplay');
    const timeHolder = document.getElementById('remainingTimeHolder');
    const verifyButton = document.getElementById('verifyOtpButton');
    const resendButton = document.getElementById('jsResendButton');
    const otpInput = document.getElementById('otp');
    const feedback = document.getElementById('otpFeedback');

    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

    let rawTime = parseInt(timeHolder.value) || 0;
    let remainingTime = (rawTime > 10000) ? Math.floor(rawTime / 1000) : rawTime;

    function formatTime(seconds) {
        const m = Math.floor(seconds / 60);
        const s = seconds % 60;
        return `${String(m).padStart(2, '0')}:${String(s).padStart(2, '0')}`;
    }

    function updateState() {
        const val = otpInput.value.trim();
        const isReady = /^\d{4}$/.test(val);
        verifyButton.disabled = !isReady || remainingTime <= 0;
        resendButton.disabled = remainingTime > 0;

        if(val.length < 4) {
            otpInput.classList.remove('is-invalid');
        }
    }

    function startTimer() {
        const tick = () => {
            if (remainingTime <= 0) {
                clearInterval(timerInterval);
                timerDisplay.textContent = "00:00";
                timerDisplay.classList.add('text-danger');
            } else {
                timerDisplay.textContent = formatTime(remainingTime);
                timerDisplay.classList.remove('text-danger');
                remainingTime--;
            }
            updateState();
        };
        tick();
        const timerInterval = setInterval(tick, 1000);
    }

    otpInput.addEventListener('input', updateState);

    resendButton.addEventListener('click', function() {
        resendButton.disabled = true;
        resendButton.innerHTML = '<span class="spinner-border spinner-border-sm"></span>';

        const email = document.getElementById('emailPrefill').value;
        axios.post(contextPath + '/resendOtp', new URLSearchParams({ email: email }))
            .then(() => {
                remainingTime = 120; 
                feedback.style.display = 'none';
                resendButton.innerHTML = '<i class="bi bi-arrow-clockwise me-1"></i>Resend OTP';
                startTimer();
            })
            .catch(() => {
                alert("Failed to resend. Please try again.");
                resendButton.disabled = false;
            });
    });

    startTimer();
});