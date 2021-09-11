<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark" aria-label="navbar">
    <div class="container">
        <a class="navbar-brand" href="#"><img src="${pageContext.request.contextPath}/assets/img/logo.png"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbars"
                aria-controls="navbars" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbars">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link <c:if test='${empty sitePage || sitePage == "home"}'>active</c:if>"
                       href="/index.jsp" aria-current="page"><fmt:message key="home"/></a></li>
                <li class="nav-item">
                    <a class="nav-link <c:if test='${sitePage == "films"}'>active</c:if>"
                       href="${pageContext.request.contextPath}/controller?command=user_films_page">
                        <fmt:message key="films"/></a></li>
                <li class="nav-item">
                    <a class="nav-link <c:if test='${sitePage == "schedule"}'>active</c:if>"
                       href="${pageContext.request.contextPath}/controller?command=user_schedule">
                        <fmt:message key="schedule"/></a></li>
                <li class="nav-item">
                    <a class="nav-link <c:if test='${sitePage == "uk_UA"}'>active</c:if>"
                       href="#">
                    <fmt:message key="schedule"/></a></li>
            </ul>
            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                <input class="form-control" type="text" placeholder="<fmt:message key="search"/>..."
                       aria-label="Search">
            </form>

            <c:choose>
                <c:when test="${sessionScope.loggedUser == null}">
                    <div class="text-end">
                        <a type="button" class="btn btn-outline-light me-2" href="login"><fmt:message key="login"/></a>
                        <a type="button" class="btn btn-warning" href="register"><fmt:message key="signUp"/></a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="dropdown">
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link text-muted dropdown-toggle" href="#" id="navbarDropdownMenuLink"
                                   role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <img src="https://s3.eu-central-1.amazonaws.com/bootstrapbaymisc/blog/24_days_bootstrap/fox.jpg"
                                         width="40" height="40" class="rounded-circle">
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile.jsp"><fmt:message key="profile"/></a>
                                    <c:if test="${sessionScope.loggedUser.role.equals('admin')}">
                                        <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/dashboard.jsp"><fmt:message
                                                key="adminPanel"/></a>
                                    </c:if>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message
                                            key="logout"/></a>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<main class="content">
