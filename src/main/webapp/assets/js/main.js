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

    let url = new URL(window.location.href);
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
    let that = $(this)
    let formData = new FormData();
    let filename = $(this).get(0).files[0].name;
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
    let filter = $(this).val()
    location.href = "/controller?command=user_schedule&dateFilter=" + filter;
})

$('.films_pagination .page-link').on('click', function () {
    let page = $(this).data('value')
    if (page == 'next' || page == 'prev') {
        let activePage = parseInt($('.films_pagination .page-item.active').find('.page-link').data('value'));
        let pages = parseInt($('.films_pagination .pagination').data('pages'));
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

$('.hall-area .seat').on('change', function () {
    let checked = $(this).is(':checked')
    let value = $(this).val()
    let price = $('.hall-scheme__hall [name="price"]').val()
    let ordersCount = $('.hall-area .seat:checked:not(:disabled)').length
    if (checked) {
        $('.user-orders').append(getUserOrderBlock(value, price))
        if (ordersCount == 1) {
            $('.reservation').append(getAddToCartBlock(price));
            return;
        }
    } else {
        if (ordersCount == 0) {
            $('.user-orders').html("");
            $('.reservation').html("");
            return;
        } else {
            $('.user-orders').find('.user-order-block[data-id="'+ value +'"]').remove()
        }
    }

    $('.add-to-cart__price').html(ordersCount * price)
})

$(document).on('click', '.left-block-row__delete-item', function (){
    let value = $(this).closest('.user-order-block').data('id')
    $('.hall-area .seat[value="'+ value +'"]').prop('checked', false)
    $(this).closest('.user-order-block').remove();
    let ordersCount = $('.hall-area .seat:checked:not(:disabled)').length
    if (ordersCount == 0) {
        $('.user-orders').html("");
        $('.reservation').html("");
        return;
    } else {
        let price = $('.hall-scheme__hall [name="price"]').val()
        $('.add-to-cart__price').html(ordersCount * price)
    }
})

let getUserOrderBlock = (value, price) => {
    let places = value.split('_');

    let block = `<div class="user-order-block" data-id="${value}">
                    <div class="left-block-row">
                        <div class="left-block-row__item">
                            <div class="ticket-info popup__ticket-info">
                                <div class="ticket-info__seats">
                                    <div class="ticket-info__seat-info ticket-info__block">
                                        <div class="ticket-info__number">
                                            <span>${places[0]}</span>
                                        </div>
                                        <div class="ticket-info__description">
                                            <span>ряд</span>
                                        </div>
                                    </div>
                                    <div class="ticket-info__seat-info ticket-info__block">
                                        <div class="ticket-info__number">
                                            <span>${places[1]}</span>
                                        </div>
                                        <div class="ticket-info__description">
                                            <span>місце</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="ticket-info__price-block ticket-info__block">
                                    <div class="ticket-info__price-number ticket-info__cash-number ticket-info__cash-number_hall">
                                        <span>${price}</span>
                                    </div>
                                    <div class="ticket-info__currency ticket-info__currency_cash">
                                        <span>грн</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="left-block-row__delete-item">
                            <i class="fa fa-times" aria-hidden="true"></i>
                        </div>
                    </div>
                </div>`

    return block;
}

let getAddToCartBlock = (price) => {
    let block = `<div class="left-block-row">
                        <div class="left-block-row__item">
                            <button type="submit" form="user-order" class="btn btn-primary add-to-cart">
                                <div class="add-to-cart__content-left">
                                    Забронювати
                                </div>
                                <div class="add-to-cart__content-right">
                                    <span class="add-to-cart__price">${price}</span>
                                    <span class="add-to-cart__currency">&nbsp;грн</span>
                                </div>
                            </button>
                        </div>
                    </div>`
    return block;
}


let addingOrder = () => {


    // $(document).on('click', '.add-to-cart', function (e) {
    //     let places = [];
    //     $('.choosedPlace:checked').each(function () {
    //         places.push($(this).val())
    //     })
    //
    //     let form = $('form#user-order')
    //     let formData = new FormData(form[0]);
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


