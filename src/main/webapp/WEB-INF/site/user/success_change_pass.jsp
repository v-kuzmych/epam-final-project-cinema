<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="film.name" var="title" scope="page"/>
<t:page title="${title}">
    <div style="text-align: center; margin: 30px;">
        <img src="/assets/img/success.png" style="height: 100px;">
        <div style="margin-top: 20px">
            <h5><fmt:message key="success.msg.changePswd"/></h5>
        </div>
    </div>
</t:page>
