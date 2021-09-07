<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

</main>

<div class="container">
    <footer class="d-flex justify-content-between py-4 my-4 border-top">
        <div class="footer-left">
            <p class="text-muted">© 2021 Company, Inc</p>
            <div class="language-switcher">
                <a class="link-dark" style="text-decoration: none" href="/controller?command=switch_language&param=ua">UA</a>
                <a class="link-secondary" style="text-decoration: none"
                   href="/controller?command=switch_language&param=en">EN</a>
            </div>
        </div>

        <ul class="nav justify-content-end">
            <li class="nav-item"><a href="#" class="nav-link px-2 text-muted">Home</a></li>
            <li class="nav-item"><a href="#" class="nav-link px-2 text-muted">Features</a></li>
            <li class="nav-item"><a href="#" class="nav-link px-2 text-muted">Pricing</a></li>
            <li class="nav-item"><a href="#" class="nav-link px-2 text-muted">FAQs</a></li>
            <li class="nav-item"><a href="#" class="nav-link px-2 text-muted">About</a></li>
        </ul>
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

<%--    <script type="text/javascript" src="js/main.js"></script>--%>

<%-- LOGIN MODAL START --%>

<%-- LOGIN MODAL END --%>

</body>
</html>