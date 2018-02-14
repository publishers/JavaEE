<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.user}">
	<div class="userAvatar">
		<img class="avatar" alt="avatar image" src="avatar" placeholder="avatar"/>
	</div>
</c:if>