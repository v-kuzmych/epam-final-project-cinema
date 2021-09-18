<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="user.orderHistory"/></li>
            </ol>
        </nav>
    </div>

    <table id="ordersTable" class="table hover-table center-td">
        <thead>
            <tr>
                <th scope="col" class="col-1">№</th>
                <th scope="col" class="col-2">Дата замовлення</th>
                <th scope="col" class="col-2">Користувач</th>
                <th scope="col" class="col-3">Фільм</th>
                <th scope="col" class="col-2">Дата сеансу</th>
                <th scope="col" class="col-1">К-сть квитків</th>
                <th scope="col" class="col-1">Сума</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.formattedDateTime}</td>
                    <td>${order.user.name} (${order.user.email})</td>
                    <td>${order.seance.film.name}</td>
                    <td>${order.seance.formattedDate} ${order.seance.formattedTime}</td>
                    <td>${order.ticketsCount}</td>
                    <td>${order.price}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

<%@ include file="/WEB-INF/admin/footer.jsp" %>