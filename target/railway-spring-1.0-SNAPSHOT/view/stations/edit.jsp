
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Edit station">
    <form name = "edit" action="${pageContext.request.contextPath}/stations/${requestScope.station.id}" method="post" onsubmit="return validateStationForm()">
        <div class="mb-3 m-3">
            <input type="hidden" name="_method" value="put" />
            <h4><fmt:message key="name"/></h4>
            <input class="form-control custom_input" type="text" name = "name" value="${requestScope.station.name}">

            <input type="submit" value="<fmt:message key="save"/>" class="btn btn-primary custom my-1">
        </div>
    </form>

</z:layout>