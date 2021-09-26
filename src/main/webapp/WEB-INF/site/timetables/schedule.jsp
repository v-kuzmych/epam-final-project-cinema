<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="schedule" var="title" scope="page"/>
<t:page title="${title}">
    <div class="site-wrapper">
        <div class="page-title">
            <h4><fmt:message key="schedule"/></h4>
        </div>

        <%@ include file="/WEB-INF/jspf/seancesSchedule.jspf" %>

    </div>
</t:page>