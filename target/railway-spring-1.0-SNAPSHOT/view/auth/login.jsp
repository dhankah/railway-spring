<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<z:layout pageTitle="Login">

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger d-flex align-items-center" role="alert">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
             class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
             aria-label="Warning:">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </svg>
        <div>
        <fmt:message key="wrong_login_password"/>
        </div>
        </div>
    </c:if>

    <main class="col-lg-3 col-md-4 col-sm-6 form-signin m-3 m-sm-auto">
        <form method="post" action="${pageContext.request.contextPath}/auth/login">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="login_please"/></h1>

            <div class="form-floating">
                <input type="text" class="form-control" id="input" name="username">
                <fmt:message key="login"/>
            </div>
            <div class="form-floating mt-2">
                <input type="password" class="form-control" id="password" name="password">
                <fmt:message key="password"/>
            </div>

            <button class="btn btn-lg btn-primary mt-2 w-100 custom" type="submit"><fmt:message key="log_in"/> </button>

        </form>
        <a href="${pageContext.request.contextPath}/auth/register"> <fmt:message key="register"/></a>
    </main>

</z:layout>