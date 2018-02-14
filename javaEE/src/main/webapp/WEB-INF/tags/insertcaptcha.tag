<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input name="captcha" type="text" autocomplete="off" placeholder="Data Captcha"/>
<c:if test="${not empty requestScope.idTokenCaptcha}">
	<input name="idTokenCaptcha" type="hidden" value="${requestScope.idTokenCaptcha}"/>
	<img alt="captcha image" src="captcha?idTokenCaptcha=${requestScope.idTokenCaptcha}" placeholder="Captcha"/>
</c:if>

<c:if test="${empty requestScope.idTokenCaptcha}">
	<img alt="captcha image" src="captcha" placeholder="Captcha"/>
</c:if>
<p style="color: red;">${requestScope.captchaValid}</p>

