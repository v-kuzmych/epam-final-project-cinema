<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="timetable-wrapper">
    <div class="desktop-filters">
        <div class="filter-panel">
            <div class="filter-title filter-section flex-center">
                <img class="mr-15" src="${pageContext.request.contextPath}/assets/img/filter.svg">
                <span class="small-text"><fmt:message key="site.template.filter"/></span>
            </div>
            <div class="date-filter filter-section">
                <span class="filter-section__title small-grey-text"><fmt:message key="site.template.period"/></span>
                <div class="filter-group">
                    <div class="group-item">
                        <input type="radio" class="filterScheduleByDate" id="todayFilter" name="date_filter" value="today"
                               <c:if test='${dateFilter == "today" || dateFilter == null}'>checked="checked"</c:if>>
                        <label for="todayFilter"><fmt:message key="site.template.today"/></label>
                    </div>

                    <div class="group-item">
                        <input type="radio" class="filterScheduleByDate" id="tomorrowFilter" name="date_filter" value="tomorrow"
                               <c:if test='${dateFilter == "tomorrow"}'>checked="checked"</c:if>>
                        <label for="tomorrowFilter"><fmt:message key="site.template.tomorrow"/></label>
                    </div>

                    <div class="group-item">
                        <input type="radio" class="filterScheduleByDate" id="weekFilter" name="date_filter" value="week"
                               <c:if test='${dateFilter == "week"}'>checked="checked"</c:if>>
                        <label for="weekFilter"><fmt:message key="site.template.week"/></label>
                    </div>

                    <div class="group-item">
                        <input type="radio" class="filterScheduleByDate" id="monthFilter" name="date_filter" value="month"
                               <c:if test='${dateFilter == "month"}'>checked="checked"</c:if>>
                        <label for="monthFilter"><fmt:message key="site.template.month"/></label>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="movies">
        <c:if test="${schedule.size() == 0}">
            <div class="movie">
                <p><fmt:message key="site.seances.notFound"/></p>
            </div>
        </c:if>
        <c:forEach items="${schedule}" var="item">
            <div class="movie">
                <a class="tablet-movie-name link-text" href="${pageContext.request.contextPath}/controller?command=film_page&id=${item.getKey().id}">
                        ${item.getKey().name}</a>
                <div class="box-for-img">
                    <div class="box-for-img__inner">
                        <div>
                            <a href="${pageContext.request.contextPath}/controller?command=film_page&id=${item.getKey().id}">
                                <img alt="Movie poster" class="poster" srcset="${item.getKey().img}">
                            </a>
                        </div>
                    </div>
                </div>

                <div class="movie-info flex-column">
                    <a class="movie-name link-text mb-15" href="${pageContext.request.contextPath}/controller?command=film_page&id=${item.getKey().id}">
                            ${item.getKey().name}
                    </a>
                    <div class="date mb-20">
                        <c:forEach items="${item.getValue()}" var="date">
                            <div class="showtime-date mb-10"><span>${ date.getKey() }</span>
                                <div class="tech">
                                    <div class="seances">
                                        <c:forEach items="${ date.getValue() }" var="seance">
                                            <a class="chips none-text-decoration" href="${pageContext.request.contextPath}/controller?command=order_page&id=${seance.id}">${ seance.formattedTime }</a>
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