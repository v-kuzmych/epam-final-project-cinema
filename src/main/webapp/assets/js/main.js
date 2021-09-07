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
    $('.tab-content').find('[id="'+tabId+'"]').addClass('show active')

    var url = new URL(window.location.href);
    url.searchParams.set('tab', tabId);
    window.history.pushState({},"", url.search);
})

$('.open_add_seance_block').click(function () {
    $('.add-seance').toggleClass('active')
})

var openFilmTab = () => {
    // var url = new URL(parent.window.location);
    // var tab = url.searchParams.get("tab");
    // if (tab != null) {
    //     $('.nav-link[data-bs-target="'+tab+'"]').trigger('click')
    // }
}


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



$( document ).ready(function() {
    openFilmTab();
    addingOrder();
});


