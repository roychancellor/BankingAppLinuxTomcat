/**
 * Causes a Bootstrap alert message to fade after 4500 milliseconds
 */
window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove(); 
    });
}, 4500);