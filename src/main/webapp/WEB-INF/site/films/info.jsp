<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>


<div class="container">
  <%@ include file="/WEB-INF/site/timetables/template.jsp" %>

  <div class="film-block row">
    <div class="col-lg-9">
      <div>
        ${film.description}
      </div>
    </div>
    <div class="col-lg-3">
      <img style="width: 100%;" src="${film.img}">
    </div>
  </div>
</div>


<%@ include file="/footer.jsp" %>
