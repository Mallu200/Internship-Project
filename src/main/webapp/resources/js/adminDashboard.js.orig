document.addEventListener('DOMContentLoaded', function () {

    // --- 1. Date and Time Update Logic ---

    // Function to update the datetime element.
    function updateDateTime() {
        const now = new Date();
        const datetimeElement = document.getElementById("datetime");

        if (datetimeElement) {
            // Using a standard, clean format for consistency.
            const options = {
                year: 'numeric', month: 'short', day: 'numeric',
                hour: '2-digit', minute: '2-digit', second: '2-digit',
                hour12: true
            };
            datetimeElement.textContent = now.toLocaleTimeString("en-US", options);
        }
    }

    // Call immediately upon load
    updateDateTime();

    // Call every second (1000ms) to keep the time updated.
    setInterval(updateDateTime, 1000);


    // --- 2. Notification Modal Display Logic (REVISED FOR RELIABILITY) ---

    // The modal relies on Bootstrap being fully loaded.
    // We use a short delay to ensure the DOM is ready AND the Bootstrap JS has initialized.

    // Check if the modal element exists (it only exists if pendingRequestCount > 0 in JSP)
    const notificationModalElement = document.getElementById('notificationModal');

    if (notificationModalElement) {

        // Use a guaranteed slight delay (500ms)
        setTimeout(() => {
            // Check if the Bootstrap object is available before attempting to instantiate the modal
            if (typeof bootstrap !== 'undefined' && bootstrap.Modal) {

                // 1. Instantiate the modal object
                const notificationModal = new bootstrap.Modal(notificationModalElement);

                // 2. Show the modal
                console.log("Prodex: Showing pending requests notification modal.");
                notificationModal.show();

            } else {
                console.error("Prodex: Bootstrap Modal class is not defined. Check bootstrap.bundle.min.js loading.");
            }
        }, 500); // 500ms delay to ensure Bootstrap is fully initialized
    }
});

// --- Approval modal wiring (moved from JSP inline script) ---
document.addEventListener('DOMContentLoaded', function () {
    const actionModal = document.getElementById('actionModal');
    if (!actionModal) return;
    actionModal.addEventListener('show.bs.modal', function (event) {
        const btn = event.relatedTarget;
        const id = btn.getAttribute('data-id');
        const type = btn.getAttribute('data-type');

        const hiddenId = document.getElementById('hiddenId');
        const hiddenStatus = document.getElementById('hiddenStatus');
        if (hiddenId) hiddenId.value = id;
        if (hiddenStatus) hiddenStatus.value = type;

        const title = document.getElementById('modalTitle');
        const msg = document.getElementById('modalMsg');
        const submit = document.getElementById('submitBtn');
        const note = document.getElementById('stockNote');

        if (type === 'APPROVE') {
            if (title) { title.className = 'modal-title fw-bold text-success'; title.innerText = 'Approve Request #' + id; }
            if (msg) { msg.innerHTML = 'Are you sure you want to <strong>APPROVE</strong> this request?'; }
            if (submit) { submit.className = 'btn btn-success rounded-pill px-4'; }
            if (note) note.classList.remove('d-none');
        } else {
            if (title) { title.className = 'modal-title fw-bold text-danger'; title.innerText = 'Reject Request #' + id; }
            if (msg) { msg.innerHTML = 'Are you sure you want to <strong>REJECT</strong> this request?'; }
            if (submit) { submit.className = 'btn btn-danger rounded-pill px-4'; }
            if (note) note.classList.add('d-none');
        }
    });
});