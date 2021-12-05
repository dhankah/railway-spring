<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<z:layout pageTitle="Stations">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/stations" onsubmit="return validateStationForm()">
        <div class="mb-3 m-3">
        <input type="text" name="name" class="form-control custom_input">
        <input type="submit" value="<fmt:message key="add"/>" class="btn btn-primary custom my-1">
        </div>
    </form>
    <ul class="list-group list-group-flush">
            <c:forEach items="${requestScope.stations}" var="station">
                <li class="list-group-item">
                    <h6 class="form-label"> ${station.name} </h6>
                    <a href="${pageContext.request.contextPath}/stations/${station.id}/edit"><fmt:message key="edit"/></a>
                    <form action="${pageContext.request.contextPath}/stations/${station.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" value="Delete" onclick="return confirm('<fmt:message key="delete"/>')" class = "btn btn-primary danger">
                    </form>
                </li>
            </c:forEach></t>
    </ul>
</z:layout>
<div class="m-3">
    <c:if test="${not empty requestScope.pages}">
    <%for ( int pageNum = 1; pageNum <= (Integer)request.getAttribute("pages"); pageNum++){ %>
    <a href="${pageContext.request.contextPath}/stations/<%=pageNum%>/page" class="btn btn-light"><%=pageNum%></a>
    <%}%>
    </c:if>
</div>