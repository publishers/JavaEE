<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<!--bottom-header-->

<div class="header-bottom">
	<div class="cart">
		<a href="<c:url value="/cart"/>">
			<div class="imageCart"></div>
		</a>
		<div class="count">
			<fmt:message key="count"/>:
			<span>
			<c:if test="${not empty sessionScope.cart}">
				${sessionScope.cart.countGoods()}
			</c:if>
			<c:if test="${empty sessionScope.cart}">
				0
			</c:if>
		</span>
		</div>
		<div class="sum">
			<fmt:message key="sum"/>:
			<span>
			<c:if test="${not empty sessionScope.cart}">
				${sessionScope.cart.getSumOfOrder()}
			</c:if>
			<c:if test="${empty sessionScope.cart}">
				0
			</c:if>
		</span>
		</div>
	</div>

	<div class="drop">
		<select id="lang">
			<option value="en" ${'en' == requestScope.lang ? 'selected' : ''}>English</option>
			<option value="ru" ${'ru' == requestScope.lang ? 'selected' : ''}>Russian</option>
		</select>
	</div>
	<c:if test="${not empty sessionScope.user}">
		<div class="userAvatar">
			<mytag:avatar/>
				${sessionScope.user.firstName}
		</div>
	</c:if>
	<div class="container">
		<div class="header">
			<div class="breadcrumbs">
				<div class="clearfix"></div>
				<div class="top-nav">
					<ul class="memenu skyblue">
						<li class="grid">
							<a href="<c:url value="/admin"/>"><fmt:message key="adminPage"/></a>
						</li>
						<li class="grid">
							<a href="<c:url value="/index"/>"><fmt:message key="home"/></a>
						</li>
						<li class="grid">
							<a href="<c:url value="/showcart"/>"><fmt:message key="yourCart"/></a>
						</li>
						<c:if test="${empty sessionScope.user}">
							<li class="grid"><a href="<c:url value="/registration"/>">
								<fmt:message key="registration"/></a></li>
							<li class="grid"><a href="<c:url value="/authorization"/>">
								<fmt:message key="login"/></a></li>
						</c:if>
						<c:if test="${not empty sessionScope.user}">
							<li class="grid"><a href="<c:url value="/logout"/>">
								<fmt:message key="logout"/></a></li>
						</c:if>
					</ul>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!--bottom-header-->