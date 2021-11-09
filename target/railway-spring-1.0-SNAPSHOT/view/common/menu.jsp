<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/common_style.css">
<div class="container">

    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <a href="${pageContext.request.contextPath}/trips" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
            Rail<b>Way</b>
        </a>
        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${pageContext.request.contextPath}/trips" class="nav-link px-2 link-dark"><fmt:message key="trips"/></a></li>
            <c:if test="${sessionScope.user.isAdmin}">
                <li><a href="${pageContext.request.contextPath}/stations/1/page" class="nav-link px-2 link-dark"><fmt:message key="stations"/></a></li>
                <li><a href="${pageContext.request.contextPath}/routes/1/page" class="nav-link px-2 link-dark"><fmt:message key="routes"/></a></li>
            </c:if>
        </ul>

        <div class="align-items-center col-md-3 d-flex justify-content-end text-end">
            <form class="m-1" action="${pageContext.request.contextPath}/language">
                <button class="btn" type="submit" name="language" value="en">EN</button>
                <button class="btn" type="submit" name="language" value="ua">UA</button>
            </form>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/cabinet" class="btn btn-primary mx-2 custom" role="button" ><fmt:message key="cabinet"/></a>
                    <form method="post" action="${pageContext.request.contextPath}/auth/logout" class="m-0">
                        <button type="submit" class="btn btn-primary custom"><fmt:message key="logout"/></button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/auth/login" class="m-0">
                        <button type="submit" class="btn btn-primary custom"><fmt:message key="log_in"/></button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>
<div>

</div>