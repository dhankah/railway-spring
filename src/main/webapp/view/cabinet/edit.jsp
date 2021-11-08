<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 16.10.2021
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Edit profile">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}" onsubmit="return validateUserEditForm()">
    <input type="hidden" name="_method" value="put" />
    <div class="col-lg-8">
        <div class="card">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="login"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <input type="text" class="form-control" value="${sessionScope.user.login}" name="login">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="first_name"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <input type="text" class="form-control" value="${sessionScope.user.details.firstName}" name="first_name">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="last_name"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <input type="text" class="form-control" value="${sessionScope.user.details.lastName}" name="last_name">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="email"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <input type="text" class="form-control" value="${sessionScope.user.details.email}" name="email">
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-9 text-secondary">
                        <input type="submit" class="btn btn-primary px-4 custom" value="<fmt:message key="save"/>">
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form>
</z:layout>