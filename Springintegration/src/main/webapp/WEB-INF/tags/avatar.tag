<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="isAuthenticated()" >
    <sec:authentication var="principal" property="principal" />
	<div class="userAvatar">
		<img class="avatar" alt="avatar image" src="avatar" placeholder="avatar"/>
	</div>
</security:authorize>