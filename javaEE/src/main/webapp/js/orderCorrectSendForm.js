correctForm();
function correctForm() {
    var form = $("form").serialize();
    $("form").css({"float": "left"});
}
$(".payment").change(function () {
    var value = $(this).val();
    if (value === "2") {
        $("form input[name='card']").parent().fadeToggle();
    } else {
        $("form input[name='card']").parent().fadeIn();
    }
});
