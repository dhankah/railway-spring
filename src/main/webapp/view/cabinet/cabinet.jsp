<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Cabinet">
<div class="col-md-8">
    <div class="card mb-3">
        <div class="card-body">
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"> <fmt:message key="login"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.login}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"> <fmt:message key="first_name"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.details.firstName}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"> <fmt:message key="last_name"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.details.lastName}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"> <fmt:message key="email"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.details.email}
                </div>
            </div>
            <hr>
            <c:if test="${!sessionScope.user.isAdmin}">
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0"> <fmt:message key="balance"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                            ${sessionScope.user.balance} <a href="${pageContext.request.contextPath}/top_up"><fmt:message key="top_up"/></a>
                    </div>
                </div>
                <hr>
            </c:if>

            <div class="row">
                <div class="col-sm-12">
                    <a class="btn btn-info custom" href="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}/edit"> <fmt:message key="edit"/></a>
                    <a class="btn btn-info custom" href="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}/change_password"> <fmt:message key="change_password"/></a>
                    <form action="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" value=" <fmt:message key="delete_profile"/>" class="btn btn-danger my-1 danger" onclick="return confirm('<fmt:message key="confirm"/>')">
                    </form>
            </div>
        </div>
    </div>
    <c:if test="${not empty requestScope.upcoming_tickets}">
        <h4 class="m-3"><fmt:message key="upcoming_tickets"/></h4>
        <div class="card-group">
            <c:forEach items="${requestScope.upcoming_tickets}" var="ticket">
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"> <fmt:message key="passenger_name"/>: ${ticket.user.details.firstName} ${ticket.user.details.lastName}</li>
                            <li class="list-group-item"> <fmt:message key="train_number"/>: ${ticket.trip.route.id}</li>
                            <li class="list-group-item"> <fmt:message key="depart_station"/>: ${ticket.trip.route.startStation.name}</li>
                            <li class="list-group-item"> <fmt:message key="arrival_station"/>: ${ticket.trip.route.endStation.name}</li>
                            <li class="list-group-item"> <fmt:message key="departure"/>: ${ticket.trip.departDate} ${ticket.trip.route.departTime}</li>
                            <li class="list-group-item"> <fmt:message key="arrival"/>: ${ticket.trip.arrivalDate} ${ticket.trip.route.arrivalTime}</li>
                        </ul>
                        <form class="m-3" action="${pageContext.request.contextPath}/tickets/${ticket.id}" method="post">
                            <input type="hidden" name="_method" value="delete" />
                            <input type="submit" class="btn btn-primary custom" value="<fmt:message key="cancel_purchase"/>" onclick="return confirm('<fmt:message key="confirm"/>')">
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.old_tickets}">
        <h4 class="m-3"><fmt:message key="old_tickets"/></h4>
        <div class="card-group">
            <c:forEach items="${requestScope.old_tickets}" var="ticket">
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"> <fmt:message key="passenger_name"/>: ${ticket.user.details.firstName} ${ticket.user.details.lastName}</li>
                            <li class="list-group-item"> <fmt:message key="train_number"/>: ${ticket.trip.route.id}</li>
                            <li class="list-group-item"> <fmt:message key="depart_station"/>: ${ticket.trip.route.startStation.name}</li>
                            <li class="list-group-item"> <fmt:message key="arrival_station"/>: ${ticket.trip.route.endStation.name}</li>
                            <li class="list-group-item"> <fmt:message key="departure"/>: ${ticket.trip.departDate} ${ticket.trip.route.departTime}</li>
                            <li class="list-group-item"> <fmt:message key="arrival"/>: ${ticket.trip.arrivalDate} ${ticket.trip.route.arrivalTime}</li>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</z:layout>
