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
	<%-- work with cart start --%>
	<script type="text/javascript">
        var sendPostQuery = {};

        sendPostQuery.send = function (goods, idProduct, action) {
            jQuery
                .ajax({
                    url: "<c:url value='/'/>cart",
                    type: "POST",
                    dataType: "html",
                    data: "action=" + action + "&idGoods=" + idProduct,
                    success: function (response) {
                        updateCart(response);
                        updateDataGoods(goods, idProduct);
                    },
                    error: function (response) {
                        alert("Fail " + response[0]);
                    }
                });
        }

        function addGoods(goods, idProduct) {
            sendPostQuery.send(goods, idProduct, "add");
        }

        function removeGoods(goods, idProduct) {
            sendPostQuery.send(goods, idProduct, "remove");
        }

        function clearItemsGoods(goods, idProduct) {
            sendPostQuery.send(goods, idProduct, "clear");
        }

        function updateDataGoods(goods, idProduct) {
            jQuery
                .ajax({
                    url: "<c:url value='/'/>cart",
                    type: "POST",
                    dataType: "html",
                    data: "action=updateGoodsData&idGoods=" + idProduct,
                    success: function (response) {
                        response = JSON.parse(response);
                        currentGoods = $(goods).parent().parent();
                        if (response.count >= 1) {
                            currentGoods.find(".value").html(response.count);
                            currentGoods.find(".fullPrice").html(response.count * response.priceGoods);
                        } else {
                            $(currentGoods).fadeOut('slow', function () {
                                $(currentGoods).remove();
                            });
                            if ($(".Goods").length == 0) {
                                $(".mainFormGoods").html("<h3>Your cart is empty!</h3>");
                            }
                        }
                    },
                    error: function (response) {
                        alert("Fail update dataGoods " + response);
                    }
                });
        }

        function updateCart(response) {
            response = JSON.parse(response);
            $(".cart > .count > span").html(response.orderCountGoods);
            $(".cart > .sum > span").html(response.orderSum);
        }
	</script>
	<%-- work with cart end --%>
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
							<div class="actions">
								<div class="button plus" onclick="addGoods(this, ${product.key.id})">+</div>
								<div class="button minus" onclick="removeGoods(this, ${product.key.id})">-</div>
								<div id="cancel" onclick="clearItemsGoods(this, ${product.key.id})"></div>
							</div>
							<div class="clearfix"></div>
						</ul>
					</c:forEach>
					<c:if test="${not empty requestScope.goods}">
						<div><a href="<c:url value="/order?step=1"/>">NEXT</a></div>
					</c:if>
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
</html>