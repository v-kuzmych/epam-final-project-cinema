<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a
                        href="${pageContext.request.contextPath}/controller?command=admin_users_page"><fmt:message
                        key="usersList"/></a></li>
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="user.info"/></li>
            </ol>
        </nav>
    </div>

    <div class="content__box">
        <div class="content__box-item">
            <h3 class="content__title"><fmt:message key="generalInfo"/></h3>
            <div class="info-block">
                <div class="item-photo">
                    <img src="${pageContext.request.contextPath}/assets/img/no_img.jpeg">

                    <input type="file" id="poster" name="filename" accept="image/gif, image/jpeg, image/png"
                           style="display: none">
                </div>
                <div class="block-right">
                    <div class="item-block">
                        <label><fmt:message key="user.name"/></label>
                        <input type="text" name="nameUa" value="${user.name}" readonly disabled>
                    </div>
                    <div class="item-block">
                        <label><fmt:message key="email"/></label>
                        <input type="text" name="nameEn" value="${user.email}" readonly disabled>
                    </div>
                    <div class="item-block">
                        <label><fmt:message key="user.role"/></label>
                        <input type="text" name="nameEn" value="${user.role}" readonly disabled>
                    </div>
                </div>
            </div>
        </div>
        <div class="content__box-item">
            <h3 class="content__title"><fmt:message key="user.orderHistory"/></h3>
            <div class="info-block">

            </div>
        </div>
    </div>

</div>

<%@ include file="/admin/footer.jsp" %>