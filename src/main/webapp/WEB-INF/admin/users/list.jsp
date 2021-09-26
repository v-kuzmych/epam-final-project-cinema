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
            <th scope="col" class="col-2"><fmt:message key="user.date"/></th>
        </tr>
        </thead>
        <tbody>
        <c:set var="k" value="0"/>
        <c:forEach items="${users}" var="user">
            <c:set var="k" value="${k+1}"/>
            <tr class='clickable-row' data-id="${user.id}">
                <th scope="row">${k}</th>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td><fmt:formatDate type="date" dateStyle="medium" value="${user.date}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>