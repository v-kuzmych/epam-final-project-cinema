<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>


<div>
    <div class="order-content">
            <div class="content__left-block">
                <div>
                    <div>
                        <div class="left-block-row">
                            <div class="left-block-row__item">
                                <div class="movie-badge dv">
                                    <div class="movie-badge__poster">
                                        <img src="${pageContext.request.contextPath}/assets/img/posters/${seance.film.img}">
                                    </div>
                                    <div class="movie-badge__info">
                                        <div class="movie-badge__title">
                                            <span>${seance.film.filmDescriptions[0].name}</span>
                                        </div>
                                        <div class="info-badges">
                                            <div class="movie-info__text">
                                                <span>7 сентября, вторник</span>
                                            </div>
                                            <div class="movie-info__text">
                                                <span>21:40 - 23:53</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="user-order-block">
                            <div class="left-block-row">
                                <div class="left-block-row__item">
                                    <div class="ticket-info popup__ticket-info">
                                        <div class="ticket-info__seats">
                                            <div class="ticket-info__seat-info ticket-info__block">
                                                <div class="ticket-info__number">
                                                    <span>5</span>
                                                </div>
                                                <div class="ticket-info__description">
                                                    <span>ряд</span>
                                                </div>
                                            </div>
                                            <div class="ticket-info__seat-info ticket-info__block">
                                                <div class="ticket-info__number">
                                                    <span>15</span>
                                                </div>
                                                <div class="ticket-info__description">
                                                    <span>місце</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ticket-info__price-block ticket-info__block">
                                            <div class="ticket-info__price-number ticket-info__cash-number ticket-info__cash-number_hall">
                                                <span>400</span>
                                            </div>
                                            <div class="ticket-info__currency ticket-info__currency_cash">
                                                <span>грн</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="left-block-row__delete-item">
                                    <i class="fa fa-times" aria-hidden="true"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="left-block-row">
                        <div class="left-block-row__item">
                            <button type="submit" form="user-order" class="btn btn-primary add-to-cart">Primary</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="content__middle-block hall-ticket-info">
                <div class="hall-ticket-info__seat-type">
                    <div class="hall-info small">
                        <div class="hall-info__price_block">
                            <span class="hall-info__price">200 грн</span>
                        </div>
                    </div>
                </div>
                <div class="hall-scheme">
                    <div class="hall-scheme__screen">
                        <img alt="Screen" src="${pageContext.request.contextPath}/assets/img/screen.svg">
                    </div>
                    <div class="hall-scheme__title">
                        <span>ЕКРАН</span>
                    </div>
                    <div class="hall-scheme__hall">
                        <span>${seance.hall.numberOfRows}</span>
                        <span>${seance.hall.numberOfSeats}</span>
                        <form id="user-order" action="<%=request.getContextPath()%>/controller?command=add_order" method="post">
                            <input type="hidden" name="command" value="add_order">
                            <input type="hidden" name="seance" value="${seance.id}">
                            <table>
                                <tbody>
                                <c:forEach var="row" begin="1" end="${seance.hall.numberOfRows}" varStatus="loop">
                                    <tr>
                                        <c:forEach var="seat" begin="1" end="${seance.hall.numberOfSeats}" varStatus="loop">
                                            <td>
                                                <input type="checkbox" name="places" value="${row}_${seat}">
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


<%@ include file="/footer.jsp" %>
