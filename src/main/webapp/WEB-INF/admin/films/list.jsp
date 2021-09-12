<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

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
            <th scope="col" class="col-1"><fmt:message key="film.poster"/></th>
            <th scope="col" class="col-2"><fmt:message key="film.name"/></th>
            <th scope="col" class="col-5"><fmt:message key="film.desc"/></th>
            <th scope="col" class="col-1"><fmt:message key="operations"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${films}" var="film">
            <tr class='clickable-row' data-id="${film.id}">
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
                <td>${film.name}</td>
                <td>${film.description.length() > 300 ? film.description.substring(0, 300) : film.description}</td>

                <td class="align-middle operation-btn" style="text-align: center;">
                    <button class="btn btn-danger delete-film"><i class="fa fa-trash" aria-hidden="true"></i></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="films_pagination">
        <nav aria-label="navigation">
            <ul class="pagination justify-content-center" data-pages="${pages}">
                <li class="page-item <c:if test='${currentPage == "1" || pages == "1"}'>disabled</c:if>">
                    <span class="page-link" data-value="prev">&laquo;</span>
                </li>
                <c:forEach var="page" begin="1" end="${pages}" varStatus="loop">
                    <li class="page-item <c:if test='${currentPage == page}'>active</c:if>">
                        <span class="page-link" data-value="${page}">${page}</span>
                    </li>
                </c:forEach>
                <li class="page-item <c:if test='${currentPage == pages || pages == "1"}'>disabled</c:if>">
                    <span class="page-link" data-value="next">&raquo;</span>
                </li>
            </ul>
        </nav>
    </div>

</div>


<%@ include file="/WEB-INF/admin/footer.jsp" %>