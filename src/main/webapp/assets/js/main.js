$('.btn-show-sidebar').click(function () {
    $(this).toggleClass("click");
    $('.sidebar').toggleClass("show");
    $('.main-container').toggleClass("show-sidebar");
});

$('.sidebar ul li').click(function () {
    $(this).addClass("active").siblings().removeClass("active");
});

$('.users-table .clickable-row').click(function () {
    let id = $(this).data("id")
    location.href = "/controller?command=user_info&id=" + id;
});

$('.films-table .clickable-row').click(function () {
    if (!$(this).hasClass('delete-film')) {
        let id = $(this).data("id")
        location.href = "/controller?command=film_edit&id=" + id;
    }
});

$('.films-table .delete-film').click(function (e) {
    e.preventDefault();
    if (confirm('dsadsadsada')) {
        let id = $(this).data("id")
        location.href = "/controller?command=film_delete&id=" + id;
    }
});

function auto_grow(element) {
    if (element.scrollHeight > 65) {
        element.style.height = (element.scrollHeight) + "px";
    }
}

$(document).find('textarea').each(function () {
    $(this).css('height', ($(this).get(0).scrollHeight + 5) + "px")
})

$('.choose_photo').click(function () {
    $(this).parent().find('input[type="file"]').trigger('click')
})

$('.nav-link').click(function () {
    let tabId = $(this).attr('aria-controls')
    $(this).closest('.nav-tabs').find('.nav-link').removeClass('active')
    $('.tab-content').find('.tab-pane').removeClass('show active')
    $(this).addClass('active')
    $('.tab-content').find('[id="' + tabId + '"]').addClass('show active')

    var url = new URL(window.location.href);
    url.searchParams.set('tab', tabId);
    window.history.pushState({}, "", url.search);
})

$('.open_add_seance_block').click(function () {
    $('.add-seance').toggleClass('active')
})

$('.choose_poster').on('click', function () {
    $('#poster').trigger('click')
})

$('#poster').on('change', function () {
    var that = $(this)
    var formData = new FormData();
    var filename = $(this).get(0).files[0].name;
    console.log(filename)
    formData.append('file[]', $(this).get(0).files[0], filename);
    $.ajax({
        type: "POST",
        url: "/controller?command=save_poster",
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            result = JSON.parse(data);
            if (result.status == 'ok') {
                that.val(filename)
                $('.choose_image').attr('src', '/assets/img/posters/' + filename)
            } else {
                alert('Error');
            }
        },
        error: function (data, error) {
            alert('Error');
        }
    })
})

$('.filterScheduleByDate').on('click', function () {
    var filter = $(this).val()
    location.href = "/controller?command=user_schedule&dateFilter=" + filter;
})

$('.films_pagination .page-link').on('click', function () {
    var page = $(this).data('value')
    if (page == 'next' || page == 'prev') {
        var activePage = parseInt($('.films_pagination .page-item.active').find('.page-link').data('value'));
        var pages = parseInt($('.films_pagination .pagination').data('pages'));
        if (page == 'next') {
            if (activePage < pages)
                page = activePage + 1;
            else return
        }
        if (page == 'prev') {
            if (activePage > 1)
                page = activePage - 1;
            else return
        }
    }

    location.href = "/controller?command=user_films_page&page=" + page;
})


var addingOrder = () => {


    // $(document).on('click', '.add-to-cart', function (e) {
    //     var places = [];
    //     $('.choosedPlace:checked').each(function () {
    //         places.push($(this).val())
    //     })
    //
    //     var form = $('form#user-order')
    //     var formData = new FormData(form[0]);
    //     formData.append('places', places)
    //
    //     $.ajax({
    //         processData: false,
    //         contentType: false,
    //         type: 'POST',
    //         data:formData,
    //         url: '/controller?command=add_order',
    //         success: function (data) {
    //             console.log(111111)
    //             // location.href = "/admin/films/edit.jsp"
    //         }
    //     })
    // })
}


$(document).ready(function () {
    addingOrder();
});


