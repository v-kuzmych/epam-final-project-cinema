<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="schedule"/></li>
            </ol>
        </nav>
    </div>

    <div class="timetable-wrapper">
        <div class="desktop-filters">
            <div class="filter-panel">
                <div class="filter-title filter-section flex-center">
                    <img class="mr-15" src="${pageContext.request.contextPath}/assets/img/filter.svg">
                    <span class="small-text">Фільтр</span>
                </div>
                <div class="date-filter filter-section">
                    <span class="filter-section__title small-grey-text">Період:</span>
                    <div class="filter-group">
                        <div class="group-item">
                            <input type="radio" id="contactChoice1" name="contact" value="email">
                            <label for="contactChoice1">сьогодні</label>
                        </div>

                        <div class="group-item">
                            <input type="radio" id="contactChoice2" name="contact" value="phone">
                            <label for="contactChoice2">завтра</label>
                        </div>

                        <div class="group-item">
                            <input type="radio" id="contactChoice3" name="contact" value="mail">
                            <label for="contactChoice3">тиждень</label>
                        </div>

                        <div class="group-item">
                            <input type="radio" id="contactChoice4" name="contact" value="mail">
                            <label for="contactChoice4">місяць</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="movies">
            <c:forEach items="${schedule}" var="item">
                <div class="movie">
                    <a class="tablet-movie-name link-text" href="${pageContext.request.contextPath}/controller?command=film_edit&id=${item.getKey().id}">
                         ${item.getKey().filmDescriptions[0].name}</a>
                    <div class="box-for-img">
                        <div class="box-for-img__inner">
                            <div>
                                <img alt="Movie poster" class="poster" srcset="${pageContext.request.contextPath}/assets/img/posters/${item.getKey().img}">
                            </div>
                        </div>
                    </div>

                    <div class="movie-info flex-column">
                        <a class="movie-name link-text mb-15" href="${pageContext.request.contextPath}/controller?command=film_edit&id=${item.getKey().id}">
                                ${item.getKey().filmDescriptions[0].name}
                        </a>
                        <div class="date mb-20">
                            <c:forEach items="${item.getValue()}" var="date">
                                <div class="showtime-date mb-10"><span>${ date.getKey() }</span>
                                    <div class="tech">
                                        <div class="seances">
                                            <c:forEach items="${ date.getValue() }" var="seance">
                                                <button class="chips">${ seance.formatedTime }</button>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </c:forEach>
        </div>

    </div>


</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>