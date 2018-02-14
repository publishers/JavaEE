<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div id="pagination">
	<c:if test="${not empty param.numberGoods && param.numberGoods != 0}">
		<c:forEach var="i" begin="1" end="${Math.round((requestScope.fullNumberGoods/param.numberGoods)+0.5)}">
			<a href='<c:url value="/index?currentPage=${i}&${requestScope.queryString}"/>'>${i}</a>
		</c:forEach>
	</c:if>
	<c:if test="${empty param.numberGoods}">
		<c:forEach var="i" begin="1" end="${Math.round((requestScope.fullNumberGoods/8)+0.5)}">
			<a href='<c:url value="/index?currentPage=${i}&${requestScope.queryString}"/>'>${i}</a>
		</c:forEach>
	</c:if>
</div>


