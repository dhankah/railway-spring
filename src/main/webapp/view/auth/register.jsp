
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Register">
<h1><fmt:message key="register"/></h1>
<br>
    <main class="col-lg-3 col-md-4 col-sm-6 form-signin m-3 m-sm-auto">
        <form name="edit" method="post" action="${pageContext.request.contextPath}/auth/register" onsubmit="return validateRegisterForm()">
            <fmt:message key="login"/>
            <br>
            <input type = "text" name = "login" class="form-control"/><br>
            <fmt:message key="password"/>
            <br>
            <input type = "password" name = "password" class="form-control"/><br>
            <fmt:message key="first_name"/>
            <br>
            <input type = "text" name = "first_name" class="form-control"/><br>
            <fmt:message key="last_name"/>
            <br>
            <input type = "text" name = "last_name" class="form-control"/><br>
            <fmt:message key="email"/>
            <br>
            <input type = "email" name = "email" class="form-control"/><br>
            <input class="btn btn-lg btn-primary mt-2 w-100 custom" type="submit" value = " <fmt:message key="register"/>" class="btn btn-primary custom">
        </form>
    </main>
    </z:layout>
