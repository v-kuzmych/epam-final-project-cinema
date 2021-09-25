<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/admin/header.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today"/>

<fmt:message key="validation.input.filmImg" var="validateFilmImg" scope="page"/>
<fmt:message key="validation.input.filmName" var="validateFilmName" scope="page"/>
<fmt:message key="validation.input.filmDuration" var="validateFilmDuration" scope="page"/>
<fmt:message key="validation.input.filmDescription" var="validateFilmDescription" scope="page"/>

<fmt:message key="validation.input.seanceDate" var="validateSeanceDate" scope="page"/>
<fmt:message key="validation.input.seanceTime" var="validateSeanceTime" scope="page"/>
<fmt:message key="validation.input.seancePrice" var="validateSeancePrice" scope="page"/>

<div class="wrapper">
    <div class="page-title">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/controller?command=admin_films_page">
                        <fmt:message key="filmsList"/>
                    </a>
                </li>
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

                    <form id="save-film" method="post" action="${pageContext.request.contextPath}/controller?command=save_film">
                        <input type="hidden" name="id" value="${film.id}">
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

                                <input type="url" name="img_url" value="${film.img}"
                                       placeholder="<fmt:message key="placeholder.filmImg"/> (https://example.com)"
                                       pattern="[Hh][Tt][Tt][Pp][Ss]?:\/\/(?:(?:[a-zA-Z\u00a1-\uffff0-9]+-?)*[a-zA-Z\u00a1-\uffff0-9]+)(?:\.(?:[a-zA-Z\u00a1-\uffff0-9]+-?)*[a-zA-Z\u00a1-\uffff0-9]+)*(?:\.(?:[a-zA-Z\u00a1-\uffff]{2,}))(?::\d{2,5})?(?:\/[^\s]*)?"
                                       required title="${validateFilmImg}"
                                       oninvalid="setCustomValidity('${validateFilmImg}')"
                                       oninput="setCustomValidity(''); checkValidity();">
                            </div>
                        </div>

                        <div class="item-block">
                            <label><fmt:message key="film.durationInMinutes"/></label>
                            <input type="number" name="duration" value="${film.duration}"
                                   placeholder="<fmt:message key="placeholder.filmDuration"/>"
                                   required min="2" pattern="\\d+" title="${validateFilmDuration}"
                                   oninvalid="setCustomValidity('${validateFilmDuration}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>

                        <c:forEach items="${descriptions}" var="item">
                            <input type="hidden" name="languageIds" value="${item.languageId}">
                            <div class="item-block">
                                <label><fmt:message key="film.name${item.languageId}"/></label>
                                <input type="text" name="name${item.languageId}" value="${item.name}"
                                       placeholder="<fmt:message key="placeholder.filmName"/>"
                                       required minlength="2" maxlength="100" title="${validateFilmName}"
                                       oninvalid="setCustomValidity('${validateFilmName}')"
                                       oninput="setCustomValidity(''); checkValidity();">
                            </div>
                            <div class="item-block">
                                <label><fmt:message key="film.desc${item.languageId}"/></label>
                                <textarea oninput="auto_grow(this)" name="desc${item.languageId}"
                                          placeholder="<fmt:message key="placeholder.filmDescription"/>"
                                          required minlength="10" maxlength="2000" title="${validateFilmDescription}"
                                          oninvalid="setCustomValidity('${validateFilmDescription}')">
                                        ${item.description}
                                </textarea>
                            </div>
                        </c:forEach>
                        <button class="btn btn-success submit-add-film" type="submit">
                            <i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="save"/></button>
                    </form>
                </div>
            </div>

            <%--   schedule tab content  --%>
            <div class="tab-pane fade <%=request.getParameter("tab") != null && request.getParameter("tab").equals("schedule") ? "show active": ""%>"
                 id="schedule" role="tabpanel" aria-labelledby="schedule-tab">
                <div class="m-20">
                    <div class="mb-20" style="display: inline-block;">
                        <button class="btn btn-primary open_add_seance_block"><fmt:message key="seances.add"/></button>
                        <div class="add-seance m-20">
                            <div>
                                <form id="add-seance" method="post"
                                      action="${pageContext.request.contextPath}/controller?command=add_seance&id=${film.id}">
                                    <div class="item-block">
                                        <label><fmt:message key="seances.chooseDate"/></label>
                                        <input type="date" class="min-input" name="date" value="${today}"
                                               min="${today}" required title="${validateSeanceDate}"
                                               oninvalid="setCustomValidity('${validateSeanceDate}')"
                                               oninput="setCustomValidity(''); checkValidity();">
                                    </div>
                                    <div class="item-block">
                                        <label><fmt:message key="seances.chooseTime"/></label>
                                        <input type="time" class="min-input" name="time"
                                               value="09:00" min="09:00" max="22:00"
                                               title="${validateSeanceTime}"
                                               oninvalid="setCustomValidity('${validateSeanceTime}')"
                                               oninput="setCustomValidity(''); checkValidity();">
                                    </div>
                                    <div class="item-block">
                                        <label><fmt:message key="seances.insertPrice"/></label>
                                        <input type="number" class="min-input" value="" name="price"
                                               placeholder="<fmt:message key="placeholder.seancePrice"/>"
                                               required min="1" pattern="\\d+" title="${validateSeancePrice}"
                                               oninvalid="setCustomValidity('${validateSeancePrice}')"
                                               oninput="setCustomValidity(''); checkValidity();">
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
                                        <c:forEach items="${seance.getValue()}" var="item">
                                            <a class="delete-seance none-text-decoration" data-id="${item.id}">
                                                <div class="chips"  >${item.formattedTime }</div>
                                                <i class="fa fa-trash remove-btn" aria-hidden="true"></i>
                                            </a>
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

<%@ include file="/WEB-INF/admin/footer.jsp" %>