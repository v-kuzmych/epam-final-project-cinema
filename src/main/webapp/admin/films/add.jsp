<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a
                        href="${pageContext.request.contextPath}/controller?command=admin_films_page"><fmt:message
                        key="filmsList"/></a></li>
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
                        <img src="${pageContext.request.contextPath}/assets/img/no_img.jpeg" class="choose_photo">
                        <input type="file" id="poster" name="filename" accept="image/gif, image/jpeg, image/png"
                               style="display: none">
                    </div>
                </div>

                <input type="hidden" name="lang_ids" value="1,2">

                <div class="item-block">
                    <label><fmt:message key="film.nameUa"/></label>
                    <input type="text" name="name.2" value="">
                </div>
                <div class="item-block">
                    <label><fmt:message key="film.descUa"/></label>
                    <textarea oninput="auto_grow(this)" name="desc.1"></textarea>
                </div>

                <div class="item-block">
                    <label><fmt:message key="film.nameEn"/></label>
                    <input type="text" name="name.1" value="">
                </div>
                <div class="item-block">
                    <label><fmt:message key="film.descEn"/></label>
                    <textarea oninput="auto_grow(this)" name="desc.2"></textarea>
                </div>

                <button class="btn btn-success submit-add-film" type="submit">
                    <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></button>
            </form>
        </div>
    </div>

</div>

<%@ include file="/admin/footer.jsp" %>