<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--  end div blocks class content and main-container  --%>
</div>
</div>

<!-- Delete Film Modal -->
<div class="modal fade" id="deleteFilmModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deleteFilmModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteFilmModalLabel">Видалення фільму</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрити"></button>
            </div>
            <div class="modal-body">
                Ви впевнені що хочете видалити фільм?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрити</button>
                <a type="button" class="btn btn-primary confilm-deleting-film" data-id="">Так</a>
            </div>
        </div>
    </div>
</div>

<!-- Delete Seance Modal -->
<div class="modal fade" id="deleteSeanceModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deleteSeanceModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteSeanceModalLabel">Видалення сеансу</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрити"></button>
            </div>
            <div class="modal-body">
                Ви впевнені що хочете видалити сеанс?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрити</button>
                <a type="button" class="btn btn-primary confilm-deleting-seance" data-id="">Так</a>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.inputmask.min.js"></script>
<script src="https://rawgit.com/RobinHerbots/Inputmask/5.x/dist/jquery.inputmask.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.2/b-2.0.0/b-html5-2.0.0/b-print-2.0.0/datatables.min.js"></script>
</body>
</html>
