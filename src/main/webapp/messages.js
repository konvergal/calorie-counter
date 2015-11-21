function errorMsg(msg) {
    $(".alert").hide();
    $("#alert-danger").show();
    $("#alert-danger").text(msg);
}

function successMsg(msg) {
    $(".alert").hide();
    $("#alert-success").show();
    $("#alert-success").text(msg);
}