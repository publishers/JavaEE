var regexPassword = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*){8,}";
var regexEmail = "^[\.a-zA-Z0-9_-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$";
var regexName = "^[A-Z]{1}[a-zA-Z-]*$";
var regexes = [regexName, regexName, regexEmail, regexPassword, regexPassword];