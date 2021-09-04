<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>


<div class="container optimize">
    <div class="tile-list-wr">
        <c:forEach items="${films}" var="film">
            <div class="movie-block">
                <div class="movie-block__inner">
                    <img class="card-img-top" src="${pageContext.request.contextPath}/assets/img/posters/${film.img}"
                         alt="...">
                    <div class="card-body">
                        <h5 class="card-title">${"uk_UA".equals(sessionScope.locale) ? film.nameUa : film.nameEn}</h5>
                        <p class="card-text">${"uk_UA".equals(sessionScope.locale) ? film.descriptionUa : film.descriptionEn}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>


<%@ include file="/footer.jsp" %>
