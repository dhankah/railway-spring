<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Select seat">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/select.css">
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-danger d-flex align-items-center" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                 aria-label="Warning:">
                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div>
                    ${sessionScope.message}
                <c:remove var="message" scope="session"/>
            </div>
        </div>
    </c:if>
    <div class="m-3">
        <form method="post" action="${pageContext.request.contextPath}/tickets" onsubmit="return validateSeatForm()">
            <table>
                <tr>
                    <c:forEach items = "${requestScope.seats}" var="seat" begin="0" end="8">
                        <td>
                          <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                          <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                        </td>
                    </c:forEach>
                </tr>
                <tr>
                <c:forEach items = "${requestScope.seats}" var="seat" begin="9" end="17">
                    <td>
                        <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                        <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                    </td>
                </c:forEach>
                </tr>
                <tr>
                    <td><p><br></p></td>
                </tr>
                <tr>
                    <c:forEach items = "${requestScope.seats}" var="seat" begin="18" end="26">
                    <td>
                        <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                        <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                    </td>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach items = "${requestScope.seats}" var="seat" begin="27">
                        <td>
                            <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                            <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                        </td>
                    </c:forEach>
                </tr>
            </table>
            <input type="hidden" name="trip" value="${requestScope.trip.id}">
            <input type="submit" class="m-1 btn btn-primary custom" value="<fmt:message key="purchase"/>">
        </form>
    </div>
</z:layout>