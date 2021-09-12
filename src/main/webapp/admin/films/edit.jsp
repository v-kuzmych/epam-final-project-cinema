<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/admin/header.jsp" %>
<% DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");%>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a
                        href="${pageContext.request.contextPath}/controller?command=admin_films_page"><fmt:message
                        key="filmsList"/></a></li>
                <li class="breadcrumb-item active" aria-current="page"><fmt:message key="film.edit"/></li>
            </ol>
        </nav>
    </div>

    <div>
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link <%=request.getParameter("tab") == null || request.getParameter("tab").equals("home") ? "active": ""%> "
                        id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button"
                        role="tab" aria-controls="home" aria-selected="true"><fmt:message key="generalInfo"/>
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link  <%=request.getParameter("tab") != null && request.getParameter("tab").equals("schedule") ? "active": ""%> "
                        id="schedule-tab" data-bs-toggle="tab" data-bs-target="#schedule" type="button"
                        role="tab" aria-controls="schedule" aria-selected="false"><fmt:message key="schedule"/>
                </button>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">
            <%--   general info tab content   --%>
            <div class="tab-pane fade <%=request.getParameter("tab") == null || request.getParameter("tab").equals("home") ? "show active": ""%>"
                 id="home" role="tabpanel" aria-labelledby="home-tab">
                <div class="m-20">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/controller?command=save_film"
                       style="float: right;">
                        <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></a>

                    <div class="item-block">
                        <label><fmt:message key="film.poster"/></label>
                        <div class="item-photo">
                            <c:choose>
                                <c:when test="${empty film.img}">
                                    <img src="${pageContext.request.contextPath}/assets/img/no_img.jpeg" class="choose_poster">
                                </c:when>
                                <c:otherwise>
                                    <img src="${film.img}" class="img_url">
                                </c:otherwise>
                            </c:choose>

                            <input type="text" name="img_url" value="${film.img}" placeholder="Введіть url зображення">
                        </div>
                    </div>

                    <div class="item-block">
                        <label>Тривалість у хв</label>
                        <input type="text" name="duration" value="${film.duration}">
                    </div>

                    <c:forEach items="${descriptions}" var="item">
                        <div class="item-block">
                            <label><fmt:message key="film.nameUa"/></label>
                            <input type="text" name="nameUa" value="${item.name}">
                        </div>
                        <div class="item-block">
                            <label><fmt:message key="film.descUa"/></label>
                            <textarea oninput="auto_grow(this)" name="descEn">${item.description}</textarea>
                        </div>
                    </c:forEach>

                </div>
            </div>

            <%--   schedule tab content  --%>
            <div class="tab-pane fade <%=request.getParameter("tab") != null && request.getParameter("tab").equals("schedule") ? "show active": ""%>"
                 id="schedule" role="tabpanel" aria-labelledby="schedule-tab">
                <div class="m-20">
                    <div class="mb-20" style="display: inline-block;">
                        <button class="btn btn-primary open_add_seance_block">Додати сеанс</button>
                        <div class="add-seance m-20">
                            <div>
                                <form id="add-seance" method="post"
                                      action="${pageContext.request.contextPath}/controller?command=add_seance&id=${film.id}">
                                    <div class="item-block">
                                        <label>Оберіть дату</label>
                                        <input type="date" class="min-input" name="date" value="2021-09-01"
                                               min="2021-09-01" max="2022-12-31" required>
                                    </div>
                                    <div class="item-block">
                                        <label>Оберіть час</label>
                                        <input type="time" class="min-input" name="time" value="09:00" min="09:00"
                                               max="22:00" required>
                                    </div>
                                    <div class="item-block">
                                        <label>Введіть вартість</label>
                                        <input type="text" class="min-input" value="" name="price">
                                    </div>
                                    <button class="btn btn-success" type="submit" style="float:right">
                                        <fmt:message key="save"/>
                                    </button>
                                </form>
                            </div>

                        </div>
                    </div>
                    <div class="list-block">
                        <c:forEach items="${seances}" var="seance">
                            <div class="date mb-20">
                                <div class="showtime-date mb-10">${seance.getKey()}</div>
                                <div class="tech">
                                    <div class="seances">
<%--                                        <button class="chips" disabled="">11:45</button>--%>
                                        <c:forEach items="${seance.getValue()}" var="item">
                                            <button class="chips">${item.formatedTime }</button>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<%@ include file="/admin/footer.jsp" %>