<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="films" var="title" scope="page"/>
<t:page title="${title}">
  <div class="container">

    <%@ include file="/WEB-INF/jspf/seancesSchedule.jspf" %>

    <div class="film-block row">
      <div class="col-lg-9">
        <h5>${film.name}</h5>
        <div>
          ${film.description}
        </div>
      </div>
      <div class="col-lg-3">
        <img style="width: 100%;" src="${film.img}">
      </div>
    </div>
  </div>
</t:page>