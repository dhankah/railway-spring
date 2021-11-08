<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<z:layout pageTitle="Routes">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/routes" onsubmit="return validateRouteForm()">
        <div class="mb-3 m-3">
            <input name="add" style="display: none">
            <h5><fmt:message key="from"/></h5><select name="start_station" class="form-control custom_input">
                <c:forEach items="${requestScope.stations}" var="station">
                <h5><fmt:message key="depart_station"/></h5><option value = ${station.name}>${station.name}</option>
                </c:forEach>
            </select>
            <h5><fmt:message key="to"/></h5><select name="end_station" class="form-control custom_input">
                <c:forEach items="${requestScope.stations}" var="station">
                    <h5><fmt:message key="arrival_station"/></h5><option value = ${station.name}>${station.name}</option>
                </c:forEach>
            </select>
            <h5><fmt:message key="departure_time"/></h5>
            <input type="time" name="depart_time" value="00:00" class="form-control custom_input">        <h5><fmt:message key="time"/></h5>
            <fmt:message key="days"/> <input type="number" name="days" class="form-control custom_input">
            <fmt:message key="hours"/> <input type="number" name="hours" class="form-control custom_input">
            <fmt:message key="minutes"/> <input type="number" name="minutes" class="form-control custom_input">

            <h5><fmt:message key="price"/></h5><input type="number" name="price" class="form-control custom_input">
            <input type="submit" value="<fmt:message key="add"/>" class="btn btn-primary custom my-1">
        </div>
    </form>
    <ul class="list-group list-group-flush">
                <c:forEach items="${requestScope.routes}" var="route">
                    <li class="list-group-item">
                    ${route.startStation.name}-${route.endStation.name}
                    ${route.departTime}-${route.arrivalTime}
                    ${route.price}
                    <a href="${pageContext.request.contextPath}/routes/${route.id}/edit"><fmt:message key="edit"/></a>
                    <form action="${pageContext.request.contextPath}/routes/${route.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" value="<fmt:message key="delete"/>" onclick="return confirm('<fmt:message key="confirm"/>')" class="btn btn-primary danger">
                    </form>
                    </li>
            </c:forEach></td>
    </ul>
</z:layout>
<c:if test="${not empty requestScope.pages}">
<%for ( int pageNum = 1; pageNum <= (Integer)request.getAttribute("pages"); pageNum++){ %>
<a href="${pageContext.request.contextPath}/routes/<%=pageNum%>/page" class="btn btn-light m-3"><%=pageNum%></a>
<%}%>
</c:if>