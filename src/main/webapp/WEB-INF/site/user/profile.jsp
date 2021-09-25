<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="profile" var="title" scope="page"/>

<fmt:message key="validation.input.userName" var="validateUserName" scope="page"/>
<fmt:message key="validation.input.userEmail" var="validateUserEmail" scope="page"/>
<fmt:message key="validation.input.userPassword" var="validateUserPassword" scope="page"/>

<t:page title="${title}">
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
                            <input type="text" name="name" value="${user.name}" autocomplete="off"
                                   placeholder="<fmt:message key="placeholder.input.userName"/>" title="${validateUserName}"
                                   required pattern="[a-zA-Z\u0400-\u04ff\s]{2,}"
                                   oninvalid="setCustomValidity('${validateUserName}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                        <div class="item-block">
                            <label><fmt:message key="user.email"/></label>
                            <input type="email" name="email" value="${user.email}" class="form-control"
                                   aria-describedby="emailHelp" placeholder="name@example.com"
                                   title="${validateUserEmail}" required
                                   oninvalid="setCustomValidity('${validateUserEmail}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>

                        <small style="color:#909090">Залиште поле пустим, якшо Ви не бажаєте змінювати пароль</small>
                        <div class="item-block">
                            <label><fmt:message key="user.password"/></label>
                            <input type="password" name="password" class="form-control" autocomplete="off"
                                   placeholder="<fmt:message key="placeholder.input.userPassword"/>" title="${validateUserPassword}"
                                   pattern="[a-zA-Z\u0400-\u04ff0-9]{3,}"
                                   oninvalid="setCustomValidity('${validateUserPassword}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                        <button class="btn btn-success submit-update-user" type="submit" style="float: right;">
                            <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></button>
                    </form>
                </div>
            </div>
            <div class="content__box-item">
                <h3 class="content__title"><fmt:message key="user.yourTickets"/></h3>
                <div>
                <c:choose>
                    <c:when test="${empty user.orders}">
                        <h4><fmt:message key="user.ticketsNotFound"/></h4>
                    </c:when>
                    <c:otherwise>
                        <div class="tickets-box">
                            <c:forEach items="${user.orders}" var="order">
                                <div class="user-order">
                                    <div class="order-info">
                                        <b><fmt:message key="user.orderNumber"/> ${order.id},</b>
                                        <span>${order.formattedDateTime}</span>
                                        <p class="order-price"><fmt:message key="user.orderPrice"/> ${order.price}</p>
                                    </div>

                                    <div>
                                        <c:forEach items="${order.orderItems}" var="item">
                                            <article class="card fl-left">
                                                <section class="barcode" style="vertical-align: middle;">
                                                    <img src="${pageContext.request.contextPath}/assets/img/barcodes/FxCHC.png">
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
                                                            <span>${order.seance.formattedDate}</span>
                                                            <span>${order.seance.formattedTime} - ${order.seance.formattedTimeEnd}</span>
                                                        </time>
                                                    </div>
                                                    <div class="even-info">
                                                        <i class="fa fa-map-marker"></i>
                                                        <p>MULTIFLEX</p>
                                                    </div>
                                                    <a href="#">ticket</a>
                                                </section>
                                            </article>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </div>
    </div>
</t:page>
