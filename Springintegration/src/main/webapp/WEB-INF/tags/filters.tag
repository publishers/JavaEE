<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<c:url value="/index"/>" method="get" autocomplete="off">
	<div class="filters">
		<div class="name">
			<label for="price">Name:</label>
			<input type="text" name="title" value="${param.title}"/>
		</div>
		<div class="type">
			<label for="price">Type:</label>
			<select name="idType">
				<option value=""></option>

				<c:forEach var="type" items="${requestScope.typeSelectAll}">
					<option value="${type.id}"
							<c:if test="${type.id == param.idType}">selected</c:if>>${type.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="manufacture">
			<label for="price">Manufacturer:</label>
			<select name="idManufacture">
				<option value=""></option>
				<c:forEach var="manufacturer" items="${requestScope.manufacturerSelectAll}">
					<option value="${manufacturer.id}"
							<c:if test="${manufacturer.id == param.idManufacture}">selected</c:if>>${manufacturer.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="price">
			<label for="price">Price:</label>

			<label for="price">From:
				<input type="text" name="priceFrom" id="price">
			</label>

			<label for="price2">To:
				<input type="text" name="priceTo" id="price2">
			</label>
			<div id="slider_price"></div>
		</div>
		<div class="numberGoods">
			<label for="price">Number Goods:</label>
			<select name="numberGoods">
				<c:forEach var="i" begin="8" end="48" step="8">
					<option value="${i}" <c:if test="${i == param.numberGoods}">selected</c:if>>${i}</option>
				</c:forEach>
			</select>
		</div>
		<div class="numberGoods">
			<label for="price">Sort:</label>
			<select name="sortBy">
				<option value="+">up</option>
				<option value="-" <c:if test="${not empty param.sortBy && param.sortBy eq '-'}"> selected</c:if>>down
				</option>
			</select>
		</div>
		<div class="submitFilter">
			<input type="submit" value="apply"/>
		</div>
	</div>
</form>
<div class="clearfix"></div>