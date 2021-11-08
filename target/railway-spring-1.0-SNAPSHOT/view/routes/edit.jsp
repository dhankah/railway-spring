<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 08.10.2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<z:layout pageTitle="Edit route">

    <form name ="edit" method="post" action="${pageContext.request.contextPath}/routes/${requestScope.route.id}" name="edit" onsubmit="return validateRouteForm()">
        <div class="mb-3 m-3">
            <input type="hidden" name="_method" value="put" />
            <h5><fmt:message key="from"/></h5>
            <select name="start_station" class="form-control custom_input">
            <c:forEach items="${requestScope.stations}" var="station">
                <h4><fmt:message key="depart_station"/></h4>
                <option value = ${station.name}
                <c:if test="${station.id == requestScope.route.startStation.id}"> selected </c:if> >${station.name}</option>
            </c:forEach>
        </select>
            <h5><fmt:message key="to"/></h5><select name="end_station" class="form-control custom_input">
            <c:forEach items="${requestScope.stations}" var="station">
                <h4><fmt:message key="arrival_station"/></h4>
                <option value = ${station.name}
                        <c:if test="${station.id == requestScope.route.endStation.id}"> selected </c:if> >${station.name}</option>
            </c:forEach>
        </select>
            <h5><fmt:message key="departure_time"/></h5><input type="time" name="depart_time" value="${requestScope.route.departTime}" class="form-control custom_input">
            <h5><fmt:message key="time"/></h5>

            <fmt:message key="days"/> <input type="number" name="days" value="${requestScope.route.day}" class="form-control custom_input">
            <fmt:message key="hours"/> <input type="number" name="hours" value="${requestScope.route.hour}" class="form-control custom_input">
            <fmt:message key="minutes"/> <input type="number" name="minutes" value="${requestScope.route.minute}" class="form-control custom_input">

            <h5><fmt:message key="price"/></h5><input type="number" name="price" value="${requestScope.route.price}" class="form-control custom_input">

            <input type="submit" value="<fmt:message key="save"/>" class="btn btn-primary custom my-1">
        </div>
    </form>
    </z:layout>
