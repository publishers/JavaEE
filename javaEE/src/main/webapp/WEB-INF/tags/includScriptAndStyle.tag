<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet" type="text/css" media="all"/>
<!--jQuery(necessary for Bootstrap's JavaScript plugins)-->
<script src="<c:url value="/js/jquery-1.11.0.min.js"/>"></script>
<!--Custom-Theme-files-->
<!--theme-style-->
<link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css" media="all"/>
<!--//theme-style-->
<script type="application/x-javascript">
    addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);
    function hideURLbar() {
        window.scrollTo(0, 1);
    }
</script>
<!--start-menu-->
<script src="<c:url value="/js/simpleCart.min.js"/>"></script>
<link href="<c:url value="/css/memenu.css"/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" src="<c:url value="/js/memenu.js"/>"></script>
<script>$(document).ready(function () {

    $(".drop > #lang").change(function () {
        var query = window.location.href;
        if (query.indexOf("lang=") != -1) {
            query = query.substr(0, query.indexOf("lang=") - 1);
        }
        window.location = query + (query.indexOf('?') != -1 ? "&lang=" + $(this).val() : "?lang=" + $(this).val());

    });
    $(".memenu").memenu();
});
</script>
<!--dropdown-->
<script src="<c:url value="/js/jquery.easydropdown.js"/>"></script>
<link href="css/order.css" rel="stylesheet" type="text/css">