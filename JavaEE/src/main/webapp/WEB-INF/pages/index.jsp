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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<html>
	<head>
		<title>Sweet Cackes A Ecommerce Category Flat Bootstrap Resposive Website Template | Home :: w3layouts</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="keywords" content="Luxury Watches Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
    Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
		<mytag:includScriptAndStyle/>
		<link rel="stylesheet" href="css/slider/style.css">
		<link rel="stylesheet" href="css/slider/jquery-ui-1.8.19.custom.css">
		<script type="text/javascript" src="js/slider/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/slider/jquery-ui-1.8.19.custom.min.js"></script>

		<script>
            $(function () {
                $('#price').change(function () {
                    var val = $(this).val();
                    $('#slider_price').slider("values", 0, val);
                });

                $('#price2').change(function () {
                    var val2 = $(this).val();
                    $('#slider_price').slider("values", 1, val2);
                });

                $("#slider_price").slider({
                    range: true,
                    min: 0,
                    step: 1,
                    max: 70000,
                    values: [
                        ${param.priceFrom == null ? 100 : param.priceFrom},
                        ${param.priceTo == null ? 70000 : param.priceTo}
                    ],
                    slide: function (event, ui) {
                        $('#price').val(ui.values[0]);
                        $('#price2').val(ui.values[1]);
                    }
                });
                $('#price').val($('#slider_price').slider("values", 0));
                $('#price2').val($('#slider_price').slider("values", 1));
            });

		</script>
		<%-- work with cart start --%>
		<script type="text/javascript">

            function addToCart(idProduct) {
                var dataJson = {"action": "add", "idGoods": idProduct};
                alert(dataJson);
                jQuery
                    .ajax({
                        url: "<c:url value='/'/>cart",
                        type: "POST",
                        dataType: "html",
                        data: "action=add&idGoods=" + idProduct,
                        success: function (response) {
                            response = JSON.parse(response);
                            $(".cart > .count > span").html(response.orderCountGoods);
                            $(".cart > .sum > span").html(response.orderSum);
                        },
                        error: function (response) {
                            alert("Fail " + response[0]);
                        }
                    });
            }
		</script>
		<%-- work with cart end --%>
	</head>
<body>
<!--bottom-header-->
<mytag:menu/>
<!--bottom-header-->
<!--banner-starts-->
<div class="bnr" id="home">
	<div id="top" class="callbacks_container">
		<ul class="rslides" id="slider4">
			<li>
				<img src="images/bnr-1.jpg" alt=""/>
			</li>
			<li>
				<img src="images/bnr-2.jpg" alt=""/>
			</li>
			<li>
				<img src="images/bnr-3.jpg" alt=""/>
			</li>
		</ul>
	</div>
	<div class="clearfix"></div>
</div>
<!--banner-ends-->
<!--Slider-Starts-Here-->
<script src="js/responsiveslides.min.js"></script>
<script>
    $(function () {
        $("#slider4").responsiveSlides({
            auto: true,
            pager: true,
            nav: true,
            speed: 3000,
            namespace: "callbacks",
            before: function () {
                $('.events').append("<li>before event fired.</li>");
            },
            after: function () {
                $('.events').append("<li>after event fired.</li>");
            }
        });

    });

</script>
<!--End-slider-script-->
<!--about-starts-->
<div class="about">
	<div class="container">
		<div class="about-top grid-1">
			<div class="col-md-4 about-left">
				<figure class="effect-bubba">
					<img class="img-responsive" src="images/abt-1.jpg" alt=""/>
					<figcaption>
						<h2>Nulla maximus nunc</h2>
						<p>In sit amet sapien eros Integer dolore magna aliqua</p>
					</figcaption>
				</figure>
			</div>
			<div class="col-md-4 about-left">
				<figure class="effect-bubba">
					<img class="img-responsive" src="images/abt-2.jpg" alt=""/>
					<figcaption>
						<h4>Mauris erat augue</h4>
						<p>In sit amet sapien eros Integer dolore magna aliqua</p>
					</figcaption>
				</figure>
			</div>
			<div class="col-md-4 about-left">
				<figure class="effect-bubba">
					<img class="img-responsive" src="images/abt-3.jpg" alt=""/>
					<figcaption>
						<h4>Cras elit mauris</h4>
						<p>In sit amet sapien eros Integer dolore magna aliqua</p>
					</figcaption>
				</figure>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!--about-ends-->
<!--filters-starts-->
<mytag:filters/>
<!--filters-ends-->

<!-- pagination-starts-->
<mytag:pagination/>
<!-- pagination-ends-->

<!--product-starts-->
<div class="product">
	<div class="container">
		<div class="product-top">
			<div class="product-one">
				<c:if test="${requestScope.goods.size() == 0}">
					<h1>By your request find nothing!</h1>
				</c:if>
				<c:forEach items="${requestScope.goods}" var="product" varStatus="loopCounter">
					<div class="col-md-3 product-left">
						<div class="product-main simpleCart_shelfItem">
							<a href="#" class="mask">
								<img class="img-responsive zoom-img" src="${product.srcImg}" alt=""/>
							</a>
							<div class="product-bottom">
								<h3>${product.title}</h3>
								<p>Explore Now</p>
								<h4>
									<span class=" item_price">$ ${product.price}</span>
								</h4>
							</div>
							<div>
								<button onclick="addToCart(${product.id})">Add to cart</button>
							</div>
						</div>
					</div>
					<c:if test="${loopCounter.count%4 == 0}">
						<div class="clearfix"></div>
					</c:if>
				</c:forEach>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
<!--product-end-->
<!--footer-starts-->
<mytag:footer/>
<!--footer-end-->
</body>

</html>