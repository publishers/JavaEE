<!--A Design by W3layouts
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page language="java" contentType="text/html;charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<title>Sweet Cakes A Ecommerce Category Flat Bootstarp Resposive Website Template | Register :: w3layouts</title>

	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="keywords" content="Luxury Watches Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
	<mytag:includScriptAndStyle/>
	<script type="application/javascript" src="<c:url value="/js/regex.js"/>"></script>
	<script type="application/javascript" src="<c:url value="/js/validationForm.js"/>"></script>
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
				<li><a href="index.html"><fmt:message key="home"/></a></li>
				<li class="active"><fmt:message key="register"/></li>
			</ol>
		</div>
	</div>
</div>
<!--end-breadcrumbs-->
<!--register-starts-->
<div class="register">
	<div class="container">
		<div class="register-top heading">
			<h2><fmt:message key="h2Register"/></h2>
		</div>

		<form class="form-horizontal" action="<c:url value="/registration"/>" method="post"
			  enctype="multipart/form-data" autocomplete="off">
			<div class="register-main">
				<div class="col-md-12 center">

					<input placeholder="<fmt:message key='placeHolderFirstName'/>" name="firstName" type="text"
						   tabindex="1"
					<c:if test="${not empty requestScope.comEpamMalykhinBeanForm}">
						   value="${requestScope.comEpamMalykhinBeanForm.beans.firstName}"
					</c:if> autocomplete="off">

					<p class="registerHideField" name="error"
							<c:if test="${
	                    	not empty requestScope.comEpamMalykhinValidFields
	                    	&&
	                   		!requestScope.comEpamMalykhinValidFields.beanFormValidation.firstName
	                    }">
								style="display:block"
							</c:if>
					>
						<fmt:message key="incorrectName"/>
					</p>

					<input placeholder="<fmt:message key='placeHolderLastName'/>" name="secondName" type="text"
						   tabindex="2"
					<c:if test="${not empty requestScope.comEpamMalykhinBeanForm}">

						   value="${requestScope.comEpamMalykhinBeanForm.beans.secondName}"
					</c:if>
						   autocomplete="off">

					<p class="registerHideField" name="error"
							<c:if test="${
                    	                    	not empty requestScope.comEpamMalykhinValidFields
                    	                    	&&
                    	                   		!requestScope.comEpamMalykhinValidFields.beanFormValidation.secondName
                    	                    }">
								style="display:block"
							</c:if>
					>
						<fmt:message key="incorrectName"/>
					</p>
					<input placeholder="<fmt:message key='placeHolderEmail'/>" name="email" type="text" tabindex="3"
					<c:if test="${not empty requestScope.comEpamMalykhinBeanForm}">
						   value="${requestScope.comEpamMalykhinBeanForm.beans.email}"
					</c:if>
						   autocomplete="off">
					<c:if test="${not empty requestScope.incorrectRegistration}">
						<p style="color:#FF0000">${requestScope.incorrectRegistration}</p>
					</c:if>

					<p class="registerHideField" name="error"
							<c:if test="${
                    	                    	not empty requestScope.comEpamMalykhinValidFields
                    	                    	&&
                    	                   		!requestScope.comEpamMalykhinValidFields.beanFormValidation.email
                    	                    }">
								style="display:block"
							</c:if>
					>
						<fmt:message key="incorrectEmail"/>
					</p>
					<input placeholder="<fmt:message key='placeHolderPassword'/>" name="password" type="password"
						   tabindex="4" autocomplete="off">

					<p class="registerHideField" name="error"
							<c:if test="${
                    	                    	not empty requestScope.comEpamMalykhinValidFields
                    	                    	&&
                    	                   		!requestScope.comEpamMalykhinValidFields.beanFormValidation.password
                    	                    }">
								style="display:block"
							</c:if>
					>
						<fmt:message key="incorrectPassword"/>
					</p>

					<input placeholder="<fmt:message key='placeHolderRetypePassword'/>" name="password2" type="password"
						   tabindex="4"
						   autocomplete="off">

					<p class="registerHideField" name="error"
							<c:if test="${
                    	                    	not empty requestScope.comEpamMalykhinValidFields
                    	                    	&&
                    	                   		!requestScope.comEpamMalykhinValidFields.beanFormValidation.password2
                    	                    }">
								style="display:block"
							</c:if>
					>
						<fmt:message key="incorrectPasswordAbove"/>
					</p>
					<mytag:insertcaptcha/>
					<span class="btn btn-default btn-file">
						<input type="file" name="file" accept="image/*"/>
					</span>
					<input type="checkbox" name="newsletter"/> <fmt:message key="newletter"/>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="address submit center">
				<input type="submit" value="<fmt:message key='submit'/>" onclick="return validationForm(this.form);">
			</div>
		</form>
	</div>
</div>
<!--register-end-->

<!--footer-starts-->
<mytag:footer/>
<!--footer-end-->
</body>
</html>