<!--A Design by W3layouts
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page language="java" contentType="text/html;charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<li><a href="<c:out value="/index"/>">Home</a></li>
				<li class="active">Order</li>
			</ol>
		</div>
	</div>
</div>
<!--end-breadcrumbs-->

<!--start-ckeckout-->
<div class="ckeckout">
	<div class="container">
		<div class="ckeck-top heading">
			<h2>CHECKOUT</h2>
		</div>
		<div class="ckeckout-top">
			<div class="cart-items">
				<h3>My Shopping Bag</h3>
				<div class="in-check">
					<ul class="unit">
						<li><span>Item</span></li>
						<li><span>Product Name</span></li>
						<li><span>Unit Price</span></li>
						<li><span>Count</span></li>
						<li><span>Sum</span></li>
						<li></li>
						<div class="clearfix"></div>
					</ul>
					<c:if test="${empty requestScope.goods}">
						<h3>Your cart is empty!</h3>
					</c:if>
					<c:forEach var="product" items="${requestScope.goods}">
						<ul class="cart-header">
							<li class="ring-in">
								<img src="<c:url value="${product.key.srcImg}"/>" class="img-responsive" alt="">
							</li>
							<li>
								<span class="name">${product.key.title}</span>
							</li>
							<li>
								<span class="cost">$ ${product.key.price}</span>
							</li>
							<li>
                                <span class="value">
										${product.value}
								</span>
							</li>
							<li>
                                <span class="fullPrice">
										${product.value * product.key.price}
								</span>
							</li>
							<div class="clearfix"></div>
						</ul>
					</c:forEach>

					<a href="<c:url value="/finalOrderPage?action=cancel&orderId=${requestScope.orderId}"/>">
						<button>Cancel</button>
					</a>
					<a href="<c:url value="/finalOrderPage?action=confirm&orderId=${requestScope.orderId}"/>">
						<button>Confirm</button>
					</a>
				</div>

			</div>
		</div>
	</div>
</div>
<!--end-ckeckout-->


<!--footer-starts-->
<mytag:footer/>
<!--footer-end-->
</body>

<script src="<c:url value="/js/orderCorrectSendForm.js"/>"></script>
</html>