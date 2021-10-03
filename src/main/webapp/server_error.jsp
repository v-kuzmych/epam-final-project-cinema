<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="error.title" var="title" scope="page"/>
<t:page title="${title}">
  <div style="text-align: center">
    <img src="/assets/img/500.png">
    <div style="margin-top: 20px">
      <p><fmt:message key="error.desc"/></p>
      <a href="/"><fmt:message key="site.error.returnOnMain"/></a>
    </div>
  </div>
</t:page>
