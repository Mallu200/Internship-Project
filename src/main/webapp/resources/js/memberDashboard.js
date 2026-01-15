document.addEventListener('DOMContentLoaded', function () {
    function updateDateTime() {
        const now = new Date();
        const datetimeElement = document.getElementById("datetime");
        if (datetimeElement) {
            const options = {
                year: 'numeric', month: 'short', day: 'numeric',
                hour: '2-digit', minute: '2-digit', second: '2-digit'
            };
            datetimeElement.innerText = now.toLocaleString("en-US", options);
        }
    }

    setInterval(updateDateTime, 1000);

    updateDateTime();
});