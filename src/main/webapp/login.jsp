<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="login" var="title" scope="page"/>

<fmt:message key="validation.input.userEmail" var="validateUserEmail" scope="page"/>
<fmt:message key="validation.input.userPassword" var="validateUserPassword" scope="page"/>

<t:page title="${title}">
    <div class="wrapper">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="login">
                    <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="login"/></h5>
                        <a href="${pageContext.request.contextPath}/register.jsp" style="color: #8e8e8e; text-decoration: none;"><fmt:message key="signUp"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="inputEmail" class="form-label"><fmt:message key="email"/></label>
                            <input type="email" name="email" class="form-control" id="inputEmail"
                                   aria-describedby="emailHelp" placeholder="name@example.com"
                                   title="${validateUserEmail}" required
                                   oninvalid="setCustomValidity('${validateUserEmail}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                        <div class="mb-3">
                            <label for="inputPassword" class="form-label"><fmt:message key="password"/></label>
                            <input type="password" name="password" class="form-control" id="inputPassword"
                                   placeholder="<fmt:message key="placeholder.input.userPassword"/>" title="${validateUserPassword}"
                                   required pattern="([a-zA-Z\u0400-\u04ff0-9]{3,}"
                                   oninvalid="setCustomValidity('${validateUserPassword}')"
                                   oninput="setCustomValidity(''); checkValidity();">
                        </div>
                        <a href="" data-bs-toggle="modal" data-bs-target="#passwordRecoveryModal"><fmt:message key="forgotPassword"/></a><br>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="enter"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</t:page>