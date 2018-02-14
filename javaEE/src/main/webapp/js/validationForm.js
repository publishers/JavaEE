function validationForm(form) {
    var submit = true;
    var elems = form.elements;
    var errors = document.getElementsByName("error");
    resetHiddenFields();
    for (i = 0; i < elems.length - 2; i++) {
        if (!valid(elems[i], regexes[i])) {
            showErrorMessage(errors[i]);
            submit = false;
        }
    }
    resetError(elems.password2);
    if (elems.password.value !== elems.password2.value) {
        showError(elems.password2);
        showErrorMessage(errors[4]);
        submit = false;
    }
    return submit;
}

function resetHiddenFields() {
    var elems = document.getElementsByClassName("registerHideField");
    for (var i = 0; i < elems.length; i++) {
        elems[i].style.display = "none";
    }
}

function showErrorMessage(elem) {
    elem.style.display = "block";
}

function valid(name, regex) {
    var result = false;
    if (name.value.match(regex) === null) {
        showError(name);
    } else {
        resetError(name);
        result = true;
    }
    return result;
}

function showError(element) {
    element.style.borderLeftColor = "red";
    element.style.borderLeftWidth = "10px";
}

function resetError(element) {
    element.style.borderLeftColor = "black";
    element.style.borderLeftWidth = "1px";
}