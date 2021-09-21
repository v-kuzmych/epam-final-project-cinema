<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="usersList"/></li>
            </ol>
        </nav>
    </div>

    <table class="table users-table hover-table">
        <thead>
        <tr>
            <th scope="col" class="col-1">#</th>
            <th scope="col" class="col-2"><fmt:message key="user.name"/></th>
            <th scope="col" class="col-2"><fmt:message key="user.email"/></th>
            <th scope="col" class="col-2"><fmt:message key="user.role"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr class='clickable-row' data-id="${user.id}">
                <th scope="row">1</th>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>