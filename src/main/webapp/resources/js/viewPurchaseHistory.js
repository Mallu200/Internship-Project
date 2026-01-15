document.addEventListener('DOMContentLoaded', function(){
    const msg = document.body.dataset.flashSuccess;
    if(msg && msg.trim().length>0){
        if(typeof Swal !== 'undefined'){
            Swal.fire('Success', msg, 'success');
        } else {
            alert(msg);
        }
    }
});
