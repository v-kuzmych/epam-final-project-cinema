<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="sidebar show">
    <div class="text"><a href="/"><img src="${pageContext.request.contextPath}/assets/img/logo.png"/></a></div>
    <ul class="main_side">
        <li <c:if test='${adminPage == null}'>class="active"</c:if>><a href="${pageContext.request.contextPath}/controller?command=admin_dashboard"><fmt:message key="home"/></a></li>
        <li <c:if test='${adminPage == "films"}'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/controller?command=admin_films_page"><fmt:message key="films"/></a>
        </li>
        <li  <c:if test='${adminPage == "users"}'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/controller?command=admin_users_page"><fmt:message key="users"/></a>
        </li>
        <li  <c:if test='${adminPage == "orders"}'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/controller?command=admin_orders_page"><fmt:message key="user.orderHistory"/></a>
        </li>
    </ul>
</nav>