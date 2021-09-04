<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="sidebar show">
    <div class="text"><a href="/"><img src="${pageContext.request.contextPath}/assets/img/logo.png"/></a></div>
    <ul class="main_side">
        <li class="active"><a href="#"><fmt:message key="home"/></a></li>
        <li><a href="${pageContext.request.contextPath}/controller?command=admin_films_page">
            <fmt:message key="films"/></a></li>
        <li><a href="${pageContext.request.contextPath}/controller?command=schedule_page"><fmt:message key="schedule"/></a></li>
        <li><a href="${pageContext.request.contextPath}/controller?command=admin_users_page">
            <fmt:message key="users"/></a></li>
    </ul>
</nav>