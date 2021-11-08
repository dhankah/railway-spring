<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Edit profile">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/top_up" onsubmit="return validateTopUpForm()">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-sm-3">
                            <h6 class="mb-0"><fmt:message key="current_balance"/>: ${sessionScope.user.balance}</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="number" class="form-control" name="amount_to_add" min="1">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-9 text-secondary">
                            <input type="submit" class="btn btn-primary px-4 custom" value="<fmt:message key="confirm_btn"/>">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</z:layout>