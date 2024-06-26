<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="film.name" var="title" scope="page"/>
<t:page title="${title}">
    <div style="text-align: center; margin: 30px;">
        <img src="/assets/img/success.png" style="height: 100px;">
        <div style="margin-top: 20px">
            <p><fmt:message key="success.msg.order"/></p>
            <a href="/controller?command=profile"><fmt:message key="success.msg.viewOrder"/></a>
        </div>
    </div>
</t:page>
