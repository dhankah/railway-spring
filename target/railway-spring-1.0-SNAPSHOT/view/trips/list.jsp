<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 03.10.2021
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Trips">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js" integrity="sha256-+C0A5Ilqmu4QcSPxrlGpaZxJ04VjsRjKu+G82kl5UJk=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.bootstrap3.min.css" integrity="sha256-ze/OEYGcFbPRmvCnrSeKbRTtjG4vGLHXgOqsyLFTRjg=" crossorigin="anonymous" />
    <script>
        $(document).ready(function () {
            $('select').selectize({
                sortField: 'text'
            });
        });
    </script>
    <div class="form-group m-5">
    <form action="${pageContext.request.contextPath}/trips/1/page" name="edit" onsubmit="validateSearchForm()">
        <div class="col-sm-3"><fmt:message key="from"/></div>
        <select name="depart_station" class="custom_input">
            <c:forEach items="${requestScope.stations}" var="station">
               <option value = ${station.name}
                       <c:if test="${station.id == sessionScope.depart_station}"> selected </c:if>
                >${station.name}</option>
            </c:forEach>
        </select>
        <div class="col-sm-3"><fmt:message key="to"/></div>
        <select name="arrival_station" class="custom_input">
            <c:forEach items="${requestScope.stations}" var="station">
               <option value = ${station.name}
                       <c:if test="${station.id == sessionScope.arrival_station}"> selected </c:if>
               >${station.name}</option>
            </c:forEach>
        </select>
        <div class="col-sm-3"><fmt:message key="departure_date"/></div>
        <input type="date" name="depart_date" value="${sessionScope.date}" min="${requestScope.min_date}" max="${requestScope.max_date}">
        <input type="submit" value="<fmt:message key="find"/>" class="btn btn-primary custom">
    </form>
    </div>

    <c:if test="${null != sessionScope.trips}">
        <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col"><fmt:message key="train_number"/></th>
                <th scope="col"><fmt:message key="depart_station"/></th>
                <th scope="col"><fmt:message key="departure_date"/></th>
                <th scope="col"><fmt:message key="departure_time"/></th>
                <th scope="col"><fmt:message key="arrival_station"/></th>
                <th scope="col"><fmt:message key="arrival_date"/></th>
                <th scope="col"><fmt:message key="arrival_time"/></th>
                <th scope="col"><fmt:message key="time"/></th>
                <th scope="col"><fmt:message key="price"/></th>
                <th scope="col"><fmt:message key="places"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${sessionScope.trips}" var="trip">
                    <tr>
                        <td>${trip.route.id}</td>
                        <td>${trip.route.startStation.name}</td>
                        <td>${trip.departDate}</td>
                        <td>${trip.route.departTime}</td>
                        <td>${trip.route.endStation.name}</td>
                        <td>${trip.arrivalDate}</td>
                        <td>${trip.route.arrivalTime}</td>
                        <td> ${trip.route.day} days ${trip.route.hour} hours ${trip.route.minute} minutes
                        <td>${trip.route.price}</td>
                        <td>${trip.availablePlaces}</td>
                        <td><a href="${pageContext.request.contextPath}/trips/${trip.id}/route_info"><fmt:message key="route_info"/></a>
                            <c:if test="${not empty sessionScope.user && !sessionScope.user.isAdmin}">
                                <a href="${pageContext.request.contextPath}/trips/${trip.id}/choose"><fmt:message key="choose_seat"/></a>
                            </c:if>
                        </td>
                    </tr>
            </c:forEach>
        </tbody>
        </table>
    </c:if>
</z:layout>
