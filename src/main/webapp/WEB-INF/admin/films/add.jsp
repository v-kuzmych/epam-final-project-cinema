<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<fmt:message key="validation.input.filmImg" var="validateFilmImg" scope="page"/>
<fmt:message key="validation.input.filmName" var="validateFilmName" scope="page"/>
<fmt:message key="validation.input.filmDuration" var="validateFilmDuration" scope="page"/>
<fmt:message key="validation.input.filmDescription" var="validateFilmDescription" scope="page"/>

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
                        <input type="url" name="img_url" value=""
                               placeholder="<fmt:message key="placeholder.filmImg"/> (https://example.com)"
                               pattern="[Hh][Tt][Tt][Pp][Ss]?:\/\/(?:(?:[a-zA-Z\u00a1-\uffff0-9]+-?)*[a-zA-Z\u00a1-\uffff0-9]+)(?:\.(?:[a-zA-Z\u00a1-\uffff0-9]+-?)*[a-zA-Z\u00a1-\uffff0-9]+)*(?:\.(?:[a-zA-Z\u00a1-\uffff]{2,}))(?::\d{2,5})?(?:\/[^\s]*)?"
                               required title="${validateFilmImg}"
                               oninvalid="setCustomValidity('${validateFilmImg}')"
                               oninput="setCustomValidity(''); checkValidity();">
                    </div>
                </div>
                <div class="item-block">
                    <label><fmt:message key="film.durationInMinutes"/></label>
                    <input type="number" name="duration" value=""
                           placeholder="<fmt:message key="placeholder.filmDuration"/>"
                           required min="2" pattern="\\d+" title="${validateFilmDuration}"
                           oninvalid="setCustomValidity('${validateFilmDuration}')"
                           oninput="setCustomValidity(''); checkValidity();">
                </div>

                <c:forEach items="${languages}" var="language">
                    <input type="hidden" name="languageIds" value="${language.id}">
                    <div class="item-block">
                        <label><fmt:message key="film.name${language.id}"/></label>
                        <input type="text" name="name${language.id}" value=""
                               placeholder="<fmt:message key="placeholder.filmName"/>"
                               required minlength="2" maxlength="100" title="${validateFilmName}"
                               oninvalid="setCustomValidity('${validateFilmName}')"
                               oninput="setCustomValidity(''); checkValidity();">
                    </div>
                    <div class="item-block">
                        <label><fmt:message key="film.desc${language.id}"/></label>
                        <textarea oninput="auto_grow(this)" name="desc${language.id}"
                                  placeholder="<fmt:message key="placeholder.filmDescription"/>"
                                  required minlength="10" maxlength="2000" title="${validateFilmDescription}"
                                  oninvalid="setCustomValidity('${validateFilmDescription}')"
                                  oninput="setCustomValidity(''); checkValidity();"></textarea>
                    </div>
                </c:forEach>

                <button class="btn btn-success submit-add-film" type="submit">
                    <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></button>
            </form>
        </div>
    </div>

</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>