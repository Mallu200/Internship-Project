document.addEventListener('DOMContentLoaded', function(){
    // Attach click handlers to action buttons
    document.querySelectorAll('.action-btn').forEach(function(btn){
        btn.addEventListener('click', function(){
            const id = this.dataset.id;
            const status = this.dataset.action;
            const color = status === 'APPROVED' ? '#198754' : '#dc3545';

            Swal.fire({
                title: 'Confirm ' + status,
                text: 'Are you sure you want to ' + status.toLowerCase() + ' this purchase request?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: color,
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Yes, ' + status + ' it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    const formId = document.getElementById('formId');
                    const formStatus = document.getElementById('formStatus');
                    if(formId) formId.value = id;
                    if(formStatus) formStatus.value = status;
                    const actionForm = document.getElementById('actionForm');
                    if(actionForm) actionForm.submit();
                }
            });
        });
    });

    // Flash messages
    const success = document.body.dataset.flashSuccess;
    const error = document.body.dataset.flashError;
    if(success && success.trim().length>0){
        Swal.fire({ icon: 'success', title: 'Success', text: success, timer: 3000 });
    }
    if(error && error.trim().length>0){
        Swal.fire({ icon: 'error', title: 'Oops...', text: error });
    }
});
