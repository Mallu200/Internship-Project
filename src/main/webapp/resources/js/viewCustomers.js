document.addEventListener("DOMContentLoaded", function () {

  function updateDateTime() {
    const now = new Date();
    const datetimeElement = document.getElementById("datetime");
    if (datetimeElement) {
      datetimeElement.textContent = now.toLocaleString();
    }
  }

  updateDateTime();
  setInterval(updateDateTime, 1000);
});