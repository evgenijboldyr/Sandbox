function createRequisitionTable() {
    jQuery('#workspace')
        .empty()
        .append('<table id="requisitions">')
        .append('<div class="nav_pnl"><label>Amount</label><input id="amount" type="text">' +
                                     '<input type="button" value="Prev" onclick="prevRequisitions()">' +
                                     '<input type="button" value="Next" onclick="nextRequisitions()"></div>')
        .append('<div id="forms"></div>');
    jQuery('#amount').val('10');

    jQuery('#requisitions')
        .append('<caption>Requisitions</caption>')
        .append('<thead><tr id="header"></tr></thead>');

    jQuery('#header')
        .append('<th>ID</th>')
        .append('<th>STATUS</th>')
        .append('<th>NAME</th>')
        .append('<th>PHONE</th>')
        .append('<th>EMAIL</th>')
        .append('<th>COMMENT</th>')
        .append('<th>CREATION_DATE</th>')
        .append('<th>ACTIONS</th>');
}

function createRequisitionProcessForm(id, name, phone, email, comment) {
    jQuery('#forms')
        .empty()
        .append('<form id="requisition_form" class="form_view"></form>');

    jQuery('#requisition_form')
        .append('<p id="r_id">' + id + '</p>')
        .append('<p>Name </p>')
        .append('<label>' + name + '</label><br/>')
        .append('<p>Phone</p>')
        .append('<label>' + phone + '</label><br/>')
        .append('<p>Email</p>')
        .append('<label>' + email + '</label><br/>')
        .append('<p>Comment</p>')
        .append('<label>' + comment + '</label><br/>');
}

function createClientCreationForm() {
    jQuery('#forms')
        .append('<form id="client_form" class="form_client"></form>');

    jQuery('#client_form')
        .append('<div id="ns"></div>')
        .append('<br/><br/><br/><br/><br/><br/>') /*Костыли :(*/
        .append('<p>Phone<input id="phone" type="text"></p>')
        .append('<p>Email<input id="email" type="text"></p>')
        .append('<input type="button" value="Create Client" onclick="return createClient()">');

    jQuery('#ns')
        .append('<div class="ns_left"><p>Name:<input id="name" type="text"></p></div>')
        .append('<div class="ns_right"><p>Surname:<input id="surname" type="text"></p></div>');
}
/*********************************************************************************************************************/
function processRequisition(btn) {
    var requisition = btn.parentNode.parentNode;
    createRequisitionProcessForm(
        jQuery(requisition).find("#req_id").html(),
        jQuery(requisition).find("#req_name").html(),
        jQuery(requisition).find("#req_phone").html(),
        jQuery(requisition).find("#req_email").html(),
        jQuery(requisition).find("#req_comment").html()
    );
    /*Меняем статус заявки в случае с откытием к обработке*/
    requisitionStatusUpdate(jQuery(requisition).find("#req_id").html(), "PROCESS");
    createClientCreationForm();
}
/*********************************************************************************************************************/
function closeRequisition(btn) {
    var requisition = btn.parentNode.parentNode;
    requisitionStatusUpdate(jQuery(requisition).find("#req_id").html(), "DONE");
}
/*********************************************************************************************************************/
function processRequisitions(jsonResult) {
    var result = jQuery.parseJSON(jsonResult);
    jQuery('#requisitions').append('<tbody id="tblBody"></tbody>');

    jQuery('#tblBody').empty();

    for (var i = 0; i < result.length; i++) {
        jQuery('#tblBody').append('<tr id="cur_row'+ i +'"></tr>')
        jQuery('#cur_row' + i)
            .append('<td id="req_id">' + result[i].id + '</td>')
            .append('<td id="req_status">' + result[i].status + '</td>')
            .append('<td id="req_name">' + result[i].name + '</td>')
            .append('<td id="req_phone">' + result[i].phone + '</td>')
            .append('<td id="req_email">' + result[i].email + '</td>')
            .append('<td id="req_comment">' + result[i].comment + '</td>')
            .append('<td id="req_date">' + result[i].date + '</td>');

        /*Опция действия(-ий) выбирается в зависимости от статуса заявки*/
        if (result[i].status == 'NEW') {
            jQuery('#cur_row' + i)
                .append('<td><input class="process" type="button" value="Process" onclick="processRequisition(this)"></td>')
        } else if (result[i].status == 'PROCESS') {
            jQuery('#cur_row' + i).append('<td></td>');
        } else if (result[i].status == 'COMPLETE') {
            jQuery('#cur_row' + i)
                .append('<td><input class="close" type="button" value="Close" onclick="closeRequisition(this)"></td>');
        }
    }
}
/*********************************************************************************************************************/

