<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="title" required="true" type="java.lang.String" %>
<t:template>

    <jsp:attribute name="css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.11.2/css/jquery.dataTables.min.css" rel="stylesheet">
        <script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>${title}</title>
    </jsp:attribute>

    <jsp:attribute name="navbar">
        <jsp:include page="${pageContext.request.contextPath}/navbar.jsp"/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>

</t:template>