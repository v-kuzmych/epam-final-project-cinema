<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="film.name" var="title" scope="page"/>
<t:page title="${title}">
    <div style="text-align: center">
        <img src="/assets/img/404.png">
        <div style="margin-top: 20px">
            <a href="/">Повернутись на головну</a>
        </div>
    </div>
</t:page>
