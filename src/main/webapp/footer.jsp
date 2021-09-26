<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>

<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" pattern="yyyy" var="thisYear"/>

<div class="container">
    <footer class="d-flex justify-content-between py-4 my-4 border-top">
        <div class="footer-left">
            <p class="text-muted">&#169; ${thisYear} Company, Inc</p>

            <%@ include file="/WEB-INF/jspf/locales.jspf" %>

        </div>

        <ul class="nav justify-content-end">
            <li class="nav-item">
                <a class="nav-link px-2 footer-link <c:if test='${sitePage == "seances"}'>active</c:if>"
                   href="${pageContext.request.contextPath}/controller?command=seances">
                    <fmt:message key="on_screen"/></a></li>
            <li class="nav-item">
                <a class="nav-link px-2 footer-link <c:if test='${sitePage == "films"}'>active</c:if>"
                href="${pageContext.request.contextPath}/controller?command=user_films_page">
                    <fmt:message key="films"/></a></li>
            <li class="nav-item">
                <a class="nav-link px-2 footer-link <c:if test='${sitePage == "schedule"}'>active</c:if>"
                   href="${pageContext.request.contextPath}/controller?command=schedule">
                    <fmt:message key="schedule"/></a></li>
        </ul>
    </footer>
</div>

<!-- Password Recovery Modal -->
<fmt:message key="validation.input.userEmail" var="validateUserEmail" scope="page"/>

<div class="modal fade" id="passwordRecoveryModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="passwordRecoveryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="passwordRecoveryModalLabel"><fmt:message key="footer.modal.password.recovery.title"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="<fmt:message key="footer.modal.close"/>"></button>
            </div>
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="send_recovery_email">
                <div class="modal-body">
                    <fmt:message key="footer.modal.password.recovery.body"/>
                    <input type="email" name="recoveryEmail" class="form-control" id="inputRecoveryEmail"
                           aria-describedby="emailHelp" placeholder="name@example.com"
                           title="${validateUserEmail}" required
                           oninvalid="setCustomValidity('${validateUserEmail}')"
                           oninput="setCustomValidity(''); checkValidity();">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="footer.modal.close"/></button>
                    <button type="submit" class="btn btn-primary">
                        <fmt:message key="footer.modal.confirm"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
        crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.11.2/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
