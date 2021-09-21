<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/controller?command=admin_films_page">
                        <fmt:message key="filmsList"/>
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="film.add"/></li>
            </ol>
        </nav>

    </div>

    <div class="content__box">
        <div class="content__box-item">
            <h3 class="content__title"><fmt:message key="generalInfo"/></h3>
            <form id="save-film" method="post" action="${pageContext.request.contextPath}/controller?command=save_film">
<%--                <input type="hidden" name="command" value="save_film">--%>
                <div class="item-block">
                    <label><fmt:message key="film.poster"/></label>
                    <div class="item-photo">
                        <img src="${pageContext.request.contextPath}/assets/img/no_img.jpeg" class="img_url">
                        <input type="text" name="img_url" value="" placeholder="<fmt:message key="film.imgPlaceholder"/>">
                    </div>
                </div>
                <div class="item-block">
                    <label><fmt:message key="film.durationInMinutes"/></label>
                    <input type="text" name="duration" value="">
                </div>

                <c:forEach items="${languages}" var="language">
                    <input type="hidden" name="languageIds" value="${language.id}">
                    <div class="item-block">
                        <label><fmt:message key="film.name${language.id}"/></label>
                        <input type="text" name="name${language.id}" value="">
                    </div>
                    <div class="item-block">
                        <label><fmt:message key="film.desc${language.id}"/></label>
                        <textarea oninput="auto_grow(this)" name="desc${language.id}"></textarea>
                    </div>
                </c:forEach>

                <button class="btn btn-success submit-add-film" type="submit">
                    <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></button>
            </form>
        </div>
    </div>

</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>