// Common utilities for Prodex views
(function(){
    function togglePassword(toggleId, inputId){
        const toggle = document.getElementById(toggleId);
        const input = document.getElementById(inputId);
        if(!toggle || !input) return;
        toggle.addEventListener('click', function(){
            const icon = this.querySelector('i');
            if(input.type === 'password'){
                input.type = 'text';
                if(icon) icon.className = 'bi bi-eye-slash';
            } else {
                input.type = 'password';
                if(icon) icon.className = 'bi bi-eye';
            }
        });
    }

    function updateClockId(elId){
        const el = document.getElementById(elId);
        if(!el) return;
        function update(){
            const now = new Date();
            const options = { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: true };
            el.innerText = now.toLocaleString('en-IN', options);
        }
        update();
        setInterval(update, 1000);
    }

    document.addEventListener('DOMContentLoaded', function(){
        togglePassword('togglePassword','password');
        updateClockId('datetime');
    });

    // Bootstrap-like form validation for forms with .needs-validation
    document.addEventListener('DOMContentLoaded', function(){
        var forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    });

    window.prodex = {
        togglePassword: togglePassword,
        updateClockId: updateClockId
    };
})();
