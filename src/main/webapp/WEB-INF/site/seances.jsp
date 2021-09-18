<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>

<div class="site-wrapper">
    <div class="page-title">
        <jsp:useBean id="now" class="java.util.Date"/>
        <fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="today"/>
        <h4><fmt:message key="on_screen"/></h4>
        <p>${today}</p>
    </div>
    <div class="m-20">
        <span style="margin-left: 5px;"><fmt:message key="search"/></span>
        <table id="seancesTable" class="table table-striped center-td">
            <thead>
                <tr>
                    <th class="no-sort" scope="col">Постер</th>
                    <th scope="col">Назва</th>
                    <th scope="col">Тривалість</th>
                    <th scope="col">Початок</th>
                    <th scope="col">Закінчення</th>
                    <th scope="col">Вартість</th>
                    <th scope="col">К-сть вільних місць</th>
                    <th class="no-sort" scope="col"></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${seances}" var="seance">
                <tr>
                    <td><img class="small-film-img" src="${seance.film.img}"></td>
                    <td>${seance.film.name}</td>
                    <td>${seance.film.formattedDuration}</td>
                    <td>${seance.formattedTime}</td>
                    <td>${seance.formattedTimeEnd}</td>
                    <td>${seance.price}</td>
                    <td>${seance.freeSeats}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/controller?command=order_page&id=${seance.id}">
                            Забронювати
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/footer.jsp" %>

