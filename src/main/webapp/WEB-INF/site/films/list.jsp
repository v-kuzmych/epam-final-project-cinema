<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>


<div class="container optimize">
    <div class="tile-list-wr">
        <c:forEach items="${films}" var="film">
            <div class="movie-block">
                <div class="movie-block__inner">
                    <a href="${pageContext.request.contextPath}/controller?command=film_page&id=${film.id}">
                        <img class="card-img-top" src="${film.img}" alt="...">
                        <span class="card-title">${film.name}</span>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

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


<%@ include file="/footer.jsp" %>
