<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="filmsList"/></li>
            </ol>
        </nav>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/controller?command=add_film">
            <i class="fa fa-plus" aria-hidden="true"></i> <fmt:message key="add"/></a>
    </div>

    <table class="table films-table hover-table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col" class="col-1"><fmt:message key="film.poster"/></th>

            <th scope="col" class="col-2"><fmt:message key="film.nameUa"/></th>
            <th scope="col" class="col-3"><fmt:message key="film.descUa"/></th>
            <th scope="col" class="col-2"><fmt:message key="film.nameEn"/></th>
            <th scope="col" class="col-3"><fmt:message key="film.descEn"/></th>

            <th scope="col" class="col-1"><fmt:message key="operations"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${films}" var="film">
            <tr class='clickable-row' data-id="${film.id}">
                <th scope="row">1</th>
                <td>
                    <c:choose>
                        <c:when test="${empty film.img}">
                                <img class="small-film-img" src="${pageContext.request.contextPath}/assets/img/no_img.jpeg">
                        </c:when>
                        <c:otherwise>
                            <img class="small-film-img" src="${pageContext.request.contextPath}/assets/img/posters/${film.img}">
                        </c:otherwise>
                    </c:choose>
                </td>

                <c:forEach items="${film.filmDescriptions}" var="item">
                    <td>${item.name}</td>
                    <td>${item.description.length() > 150 ? item.description.substring(0, 150) : item.description}</td>
                </c:forEach>

                <td class="align-middle operation-btn">
                    <button class="btn btn-danger delete-film"><i class="fa fa-trash" aria-hidden="true"></i></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


<%@ include file="/admin/footer.jsp" %>