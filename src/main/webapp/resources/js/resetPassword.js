document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('resetPasswordForm');
    const password = document.getElementById('password');
    const confirm = document.getElementById('confirmPassword');
    const submitBtn = document.getElementById('resetPasswordButton');
    const toggleBtn = document.getElementById('togglePassword');

    const policies = {
        length: { el: document.getElementById('p_length'), reg: /.{8,20}/ },
        upper:  { el: document.getElementById('p_upper'),  reg: /[A-Z]/ },
        lower:  { el: document.getElementById('p_lower'),  reg: /[a-z]/ },
        num:    { el: document.getElementById('p_number'), reg: /[0-9]/ },
        spec:   { el: document.getElementById('p_special'), reg: /[@#$%^&+=!]/ }
    };

    function validate() {
        const val = password.value;
        let allPassed = true;

        for (const key in policies) {
            const passed = policies[key].reg.test(val);
            const el = policies[key].el;

            if (passed) {
                el.classList.replace('text-muted', 'text-success');
                el.querySelector('i').classList.replace('bi-circle', 'bi-check-circle-fill');
            } else {
                el.classList.replace('text-success', 'text-muted');
                el.querySelector('i').classList.replace('bi-check-circle-fill', 'bi-circle');
                allPassed = false;
            }
        }

        const match = confirm.value.length > 0 && val === confirm.value;
        confirm.classList.toggle('is-invalid', confirm.value.length > 0 && !match);

        submitBtn.disabled = !(allPassed && match);
    }

    password.addEventListener('input', validate);
    confirm.addEventListener('input', validate);

    toggleBtn.addEventListener('click', function() {
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        this.querySelector('i').classList.toggle('bi-eye');
        this.querySelector('i').classList.toggle('bi-eye-slash');
    });

    form.addEventListener('submit', function() {
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Updating...';
    });
});