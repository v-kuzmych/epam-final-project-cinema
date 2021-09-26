<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="film.name" var="title" scope="page"/>
<t:page title="${title}">
    <div style="text-align: center; margin: 30px;">
        <img src="/assets/img/error.png" style="height: 400px;">

        <h3 class="error">
            The following error occurred
        </h3>

        <%-- this way we get the error information (error 404)--%>
        <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
        <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

        <%-- this way we get the exception --%>
        <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

        <c:if test="${not empty code}">
            <h4>Error code: ${code}</h4>
        </c:if>

        <c:if test="${not empty message}">
            <h4>Message: ${message}</h4>
        </c:if>

        <%-- if get this page using forward --%>
        <c:if test="${not empty errorMessage and empty exception and empty code}">
            <h4>Error message: ${errorMessage}</h4>
        </c:if>

        <%-- this way we print exception stack trace --%>
        <c:if test="${not empty exception}">
            <hr/>
            <h4>Stack trace:</h4>
            <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
                ${stackTraceElement}
            </c:forEach>
        </c:if>

        <c:if test="${not empty prevPage}">
            <br>
            <h6><a href="${prevPage}"><fmt:message key="error.msg.returnToPrev"/></a></h6>
        </c:if>
    </div>
</t:page>