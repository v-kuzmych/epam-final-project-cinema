<%@tag pageEncoding="UTF-8" %>
<%@attribute name="tags" fragment="true" %>
<%@attribute name="css" fragment="true" %>
<%@attribute name="navbar" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<jsp:invoke fragment="tags"/>
<html>
    <head>
        <jsp:invoke fragment="css"/>
    </head>
    <body>
        <jsp:invoke fragment="navbar"/>

        <main class="content">
            <jsp:doBody/>
        </main>

        <jsp:invoke fragment="footer"/>
    </body>
</html>