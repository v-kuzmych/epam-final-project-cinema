<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/header.jsp" %>

<div class="wrapper">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="<%=request.getContextPath()%>/controller" method="post">
                <input type="hidden" name="command" value="register">
                <div class="modal-header">
                    <h5 class="modal-title"><fmt:message key="signUp"/></h5>
                    <a href="${pageContext.request.contextPath}/login.jsp" style="color: #8e8e8e; text-decoration: none;"><fmt:message key="login"/></a>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="inputEmail" class="form-label"><fmt:message key="email"/></label>
                        <input type="email" name="email" class="form-control" id="inputEmail"
                               aria-describedby="emailHelp" placeholder="name@example.com">
                        <%--                        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>--%>
                    </div>
                    <div class="mb-3">
                        <label for="inputPassword" class="form-label"><fmt:message key="password"/></label>
                        <input type="password" name="password" class="form-control" id="inputPassword">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary"><fmt:message key="register"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/footer.jsp" %>
