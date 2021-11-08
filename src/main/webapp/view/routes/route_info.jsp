<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Route info"><div class="col-md-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/routeinfo.css">
    <div class="container py-5">
        <div class="row">
            <div class="col-md-12">
                <div id="content">
                    <ul class="timeline-1 text-black">
                        <li class="station" data-date="${requestScope.route.departTime}">
                            <h4 class="mb-3">${requestScope.route.startStation.name}</h4>
                            <br><br><br><br><br><br><br><br>
                        </li>
                        <li class="station" data-date="${requestScope.route.arrivalTime}">
                            <h4 class="mb-3 pt-3" >${requestScope.route.endStation.name}</h4>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <a href="${pageContext.request.contextPath}/trips" class="btn btn-primary custom m-5"><fmt:message key="back"/></a>
</z:layout>