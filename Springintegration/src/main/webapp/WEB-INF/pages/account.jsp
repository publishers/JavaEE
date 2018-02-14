<!--A Design by W3layouts
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page language="java" contentType="text/html;charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE html>
<html>
<head>
	<title>Sweet Cackes A Ecommerce Category Flat Bootstrap Resposive Website Template | Account :: w3layouts</title>

	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="keywords" content="Luxury Watches Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
    Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
	<mytag:includScriptAndStyle/>
</head>
<body>
<!--bottom-header-->
<mytag:menu/>
<!--bottom-header-->
<!--start-breadcrumbs-->
<div class="breadcrumbs">
	<div class="container">
		<div class="breadcrumbs-main">
			<ol class="breadcrumb">
				<li><a href="index.html">Home</a></li>
				<li class="active">Account</li>
			</ol>
		</div>
	</div>
</div>
<!--end-breadcrumbs-->
<!--account-starts-->
<div class="account">
	<div class="container">
		<div class="account-top heading">
			<h2>ACCOUNT</h2>
		</div>
		<div class="account-main">
			<div class="col-md-6 account-left">
				<h3>Existing User</h3>
				<form action="<c:url value="/authorization"/>" method="post">
					<div class="account-bottom">
						<input placeholder="Email" name="email" type="text" tabindex="3" required>
						<input placeholder="Password" name="password" type="password" tabindex="4" required>
						<div class="address">
							<a class="forgot" href="#">Forgot Your Password?</a>
							<input type="submit" value="Login">
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-6 account-right account-left">
				<h3>New User? Create an Account</h3>
				<p>By creating an account with our store, you will be able to move through the checkout process faster,
					store multiple shipping addresses, view and track your orders in your account and more.</p>
				<a href="register.html">Create an Account</a>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!--account-end-->
<!--footer-starts-->
<mytag:footer/>
<!--footer-end-->
</body>
</html>