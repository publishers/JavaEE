var valid = function (element, regex) {
    return element.match(regex) !== null;
}
var showError = function (elem) {
    elem.css({"border-left-color": "red", "border-left-width": "10px"});
    return false;
}
var resetError = function (elem) {
    elem.css({"border-left-color": "black", "border-left-width": "1px"});
    return true;
}
var showErrorMessage = function (elem) {
    $("." + elem + "Error, p .registerHideField ." + elem + "Error").css("display", "block");
}
var hideErrorMessage = function (elem) {
    $("." + elem + "Error, p .registerHideField ." + elem + "Error").css("display", "none");
}
$(function () {
    $('form[name="registration"]').submit(function () {
        var markSend = true;
        var inputs = $("form[name='registration'] input");
        var i = 0;
        inputs.each(function () {
            if ($(this).attr("name") === "password2") {
                return false;
            }
            if (!valid($(this).val(), regexes[i])) {
                markSend = showError($("input[name='" + $(this).attr("name") + "']"))
                showErrorMessage($(this).attr("name"));
            } else {
                resetError($("input[name='" + $(this).attr("name") + "']"));
                hideErrorMessage($(this).attr("name"));
            }
            i++;
        });
        if ($("input[name='password']").val() !== null &&
            $("input[name='password']").val() === $("input[name='password2']").val()) {
            hideErrorMessage("password2");
            resetError($("input[name='password2']"));
        } else {
            markSend = showError($("input[name='password2']"))
            showErrorMessage("password2");
        }
        return markSend;
    });
});