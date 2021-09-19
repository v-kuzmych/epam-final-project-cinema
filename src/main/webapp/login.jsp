<%@ include file="/WEB-INF/jspf/pageHeader.jspf" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:message key="login" var="title" scope="page"/>
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
                            <input type="email" name="email" class="form-control" id="inputEmail" required
                                   aria-describedby="emailHelp" placeholder="name@example.com">
                            <%--                            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>--%>
                        </div>
                        <div class="mb-3">
                            <label for="inputPassword" class="form-label"><fmt:message key="password"/></label>
                            <input type="password" name="password" class="form-control" id="inputPassword" required>
                        </div>
                        <a href="hello-servlet"><fmt:message key="forgotPassword"/></a><br>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="enter"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</t:page>