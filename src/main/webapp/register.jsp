<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="signUp" var="title" scope="page"/>

<fmt:message key="validation.input.userName" var="validateUserName" scope="page"/>
<fmt:message key="validation.input.userEmail" var="validateUserEmail" scope="page"/>
<fmt:message key="validation.input.userPassword" var="validateUserPassword" scope="page"/>

<t:page title="${title}">
    <div class="wrapper">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="register">
                    <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="signUp"/></h5>
                        <a href="${pageContext.request.contextPath}/login.jsp" style="color: #8e8e8e; text-decoration: none;"><fmt:message key="login"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="inputName" class="form-label"><fmt:message key="user.name"/></label>
                            <input type="text" name="name" class="form-control" id="inputName" autocomplete="off"
                                   placeholder="<fmt:message key="placeholder.input.userName"/>" title="${validateUserName}"
                                   required pattern="[a-zA-Z\u0400-\u04ff\s]{2,}"
                                   oninvalid="setCustomValidity('${validateUserName}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                        <div class="mb-3">
                            <label for="inputEmail" class="form-label"><fmt:message key="user.email"/></label>
                            <input type="email" name="email" class="form-control" id="inputEmail"
                                   aria-describedby="emailHelp" placeholder="name@example.com"
                                   title="${validateUserEmail}" required
                                   oninvalid="setCustomValidity('${validateUserEmail}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                        <div class="mb-3">
                            <label for="inputPassword" class="form-label"><fmt:message key="user.password"/></label>
                            <input type="password" name="password" class="form-control" id="inputPassword"
                                   placeholder="<fmt:message key="placeholder.input.userPassword"/>" title="${validateUserPassword}"
                                   required pattern="[a-zA-Z\u0400-\u04ff0-9]{3,}"
                                   oninvalid="setCustomValidity('${validateUserPassword}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="register"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</t:page>
