<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="user.recoveryPassword" var="title" scope="page"/>
<fmt:message key="validation.input.userPassword" var="validateUserPassword" scope="page"/>

<t:page title="${title}">
    <div class="wrapper">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="change_password">
                    <input type="hidden" name="token" value="${token}">
                    <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="user.recoveryPassword"/></h5>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="inputPassword" class="form-label"><fmt:message key="user.enterNewPassword"/></label>
                            <input type="password" name="password" class="form-control" id="inputPassword"
                                   placeholder="<fmt:message key="placeholder.input.userPassword"/>" title="${validateUserPassword}"
                                   required pattern="[a-zA-Z\u0400-\u04ff0-9]{3,}"
                                   oninvalid="setCustomValidity('${validateUserPassword}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</t:page>