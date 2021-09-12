<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>

    <div class="site-wrapper">
        <div class="page-title">
            <h4><fmt:message key="profile"/></h4>
        </div>

        <div class="content__box">
            <div class="content__box-item">
                <h3 class="content__title"><fmt:message key="generalInfo"/></h3>
                <div class="info-block">
                    <form id="save-film" method="post" action="${pageContext.request.contextPath}/controller?command=update_user">
                        <input type="hidden" name="id" value="${user.id}">
                        <div class="item-block">
                            <label><fmt:message key="user.name"/></label>
                            <input type="text" name="nameUa" value="${user.name}">
                        </div>
                        <div class="item-block">
                            <label><fmt:message key="email"/></label>
                            <input type="text" name="nameEn" value="${user.email}">
                        </div>
                        <div class="item-block">
                            <label><fmt:message key="password"/></label>
                            <input type="text" name="nameEn" value="">
                        </div>
                        <button class="btn btn-success submit-update-user" type="submit" style="float: right;">
                            <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></button>
                    </form>
                </div>
            </div>
            <div class="content__box-item">
                <h3 class="content__title">Ваші квитки</h3>
                <div>
                <c:choose>
                    <c:when test="${empty user.orders}">
                        <h4>У Вас немає придбаних квитків</h4>
                    </c:when>
                    <c:otherwise>
                        <div class="tickets-box">
                            <c:forEach items="${user.orders}" var="order">
                                <div class="user-order">
                                    <div class="order-info">
                                        <b>Замовлення № ${order.id},</b>
                                        <span>${order.formatedDateTime}</span>
                                        <p class="order-price">Вартість: ${order.price}</p>
                                    </div>

                                    <c:forEach items="${order.orderItems}" var="item">
                                            <article class="card fl-left">
                                                <section class="barcode" style="vertical-align: middle;">
                                                    <img src="${pageContext.request.contextPath}/assets/img/barcodes/FxCHC.png" style="height: 200px;">
                                                </section>
                                                <section class="card-cont">
                                                    <h3>${order.seance.film.name}</h3>
                                                    <div style="font-weight: 600;">
                                                        <span>Ряд: ${item.rowNumber}</span>
                                                        <p>Місце: ${item.seatNumber}</p>
                                                    </div>
                                                    <div class="even-date">
                                                        <i class="fa fa-calendar"></i>
                                                        <time>
                                                            <span>${order.seance.formatedDate}</span>
                                                            <span>${order.seance.formatedTime}</span>
                                                        </time>
                                                    </div>
                                                    <div class="even-info">
                                                        <i class="fa fa-map-marker"></i>
                                                        <p>MULTIFLEX</p>
                                                    </div>
                                                    <a href="#">tickets</a>
                                                </section>
                                            </article>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </div>
    </div>

<%@ include file="/footer.jsp" %>
