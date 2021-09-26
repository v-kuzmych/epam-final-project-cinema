<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/controller?command=admin_users_page">
                        <fmt:message key="usersList"/>
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="user.info"/></li>
            </ol>
        </nav>
    </div>

    <div class="content__box">
        <div class="content__box-item">
            <h3 class="content__title"><fmt:message key="user.generalInfo"/></h3>
            <div class="info-block">
                <div>
                    <div class="item-block">
                        <label><fmt:message key="user.name"/></label>
                        <input type="text" name="nameUa" value="${user.name}" readonly disabled>
                    </div>
                    <div class="item-block">
                        <label><fmt:message key="user.email"/></label>
                        <input type="text" name="nameEn" value="${user.email}" readonly disabled>
                    </div>
                    <div class="item-block">
                        <label><fmt:message key="user.role"/></label>
                        <input type="text" name="nameEn" value="${user.role}" readonly disabled>
                    </div>
                </div>
            </div>
        </div>
        <div class="content__box-item">
            <h3 class="content__title"><fmt:message key="user.orderHistory"/></h3>
            <div class="container">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <c:choose>
                                <c:when test="${fn:length(user.orders) == 0}"><fmt:message key="user.order.noOrders"/></c:when>
                                <c:otherwise>
                                    <table class="table table-condensed table-striped">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th><fmt:message key="user.order.bookingDate"/></th>
                                            <th><fmt:message key="user.order.seanceDate"/></th>
                                            <th><fmt:message key="user.order.film"/></th>
                                            <th><fmt:message key="user.order.ticketsCount"/></th>
                                            <th><fmt:message key="user.order.price"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach items="${user.orders}" var="order">
                                        <tr data-bs-toggle="collapse" data-bs-target="#order${order.id}" class="accordion-toggle">
                                            <td><button class="btn btn-default btn-xs"><i class="fa fa-eye" aria-hidden="true"></i></button></td>
                                            <td>${order.formattedDateTime}</td>
                                            <td><span>${order.seance.formattedDate}</span>&nbsp;<span>${order.seance.formattedTime}</span></td>
                                            <td>${order.seance.film.name}</td>
                                            <td>${order.orderItems.size()}</td>
                                            <td>${order.price}</td>
                                        </tr>

                                        <tr>
                                            <td colspan="12" class="hiddenRow">
                                                <div class="accordian-body collapse" id="order${order.id}">
                                                    <table class="table table-striped">
                                                        <thead>
                                                        <tr class="info">
                                                            <th><fmt:message key="user.order.row"/></th>
                                                            <th><fmt:message key="user.order.seat"/></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${order.orderItems}" var="item">
                                                                <tr>
                                                                    <td>${item.rowNumber}</td>
                                                                    <td>${item.seatNumber}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                </div>
            </div>


        </div>
    </div>

</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>