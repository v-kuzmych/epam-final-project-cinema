<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<fmt:message key="film.add" var="title" scope="page"/>
<t:page title="${title}">

    <c:set var="occupiedSeats" value="${occupiedSeats}" />
    <div>
        <div class="order-content">
            <div class="content__left-block">
                <div>
                    <div>
                        <div class="left-block-row">
                            <div class="left-block-row__item">
                                <div class="movie-badge dv">
                                    <div class="movie-badge__poster">
                                        <img src="${seance.film.img}">
                                    </div>
                                    <div class="movie-badge__info">
                                        <div class="movie-badge__title">
                                            <span>${seance.film.name}</span>
                                        </div>
                                        <div class="info-badges">
                                            <div class="movie-info__text">
                                                <span>${seance.formattedDate}</span>
                                            </div>
                                            <div class="movie-info__text">
                                                <span>${seance.formattedTime} - ${seance.formattedTimeEnd}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="user-orders">
                            <%--   user tickets block   --%>
                        </div>
                    </div>
                    <div class="reservation">
                            <%--  reservation tickets block   --%>
                    </div>
                </div>
            </div>
            <div class="content__middle-block hall-ticket-info">
                <div class="hall-ticket-info__seat-type">
                    <div class="hall-info small">
                        <div class="hall-info__price_block">
                            <span class="hall-info__price">${seance.price} <fmt:message key="site.order.currency"/></span>
                        </div>
                    </div>
                </div>
                <div class="hall-scheme">
                    <div class="hall-scheme__screen">
                        <img alt="Screen" src="${pageContext.request.contextPath}/assets/img/screen.svg">
                    </div>
                    <div class="hall-scheme__title">
                        <span><fmt:message key="site.order.screen"/></span>
                    </div>
                    <div class="hall-scheme__hall">
                        <input type="hidden" name="price" value="${seance.price}">
                        <form id="user-order" action="${pageContext.request.contextPath}/controller?command=add_order" method="post">
                            <input type="hidden" name="seance_id" value="${seance.id}">
                            <table class="hall-area">
                                <tbody>
                                    <c:forEach var="row" begin="1" end="${seance.hall.numberOfRows}" varStatus="loop">
                                        <tr>
                                            <c:forEach var="seat" begin="1" end="${seance.hall.numberOfSeats}" varStatus="loop">
                                                <c:set var="thisSeat" value="${row}_${seat}" />
                                                <td>
                                                    <input class="seat" type="checkbox" name="places" value="${thisSeat}"
                                                           <c:if test='${fn:contains(occupiedSeats, thisSeat)}'>checked="checked" disabled="disabled"</c:if> >
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:page>
