
var reviewsTableDiv = $('#reviews-table-div');

function deleteReview(reviewId) {
    console.log('deleteReview with id: '+ reviewId);
    $.ajax({
        type: 'DELETE',
        url: reviewsURL + '/' + reviewId,
        success: function(data, textStatus, jqXHR) {
            console.log("Book deleted successfully: " + textStatus);
            window.alert("Book deleted successfully: " + textStatus);
            setTimeout(function(){location.reload();}, 750);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("delete Book with error: " + textStatus);
            window.alert("delete Book with error: " + textStatus);
        }
    })
}

function setTriggers() {
    $("button[id^='btnDeleteReview']").each(function (i, el) {
        console.log(el.id);
        $('#' + el.id).click(function() {
            deleteReview(el.name);
            return false
        })
    })
}

function appendReviewsTableHeader(body) {
    body.append(
        '<thead><tr>'
        + '  <td>Рецензии</td>'
        + '  <td>'
        + '    <form id="book-edit-form" class="inline">'
        + '      <button form="book-edit-form" class="link-button-disabled" onclick="return false">Edit</button>'
        + '    </form>'
        + '  </td>'
        + '  <td>'
        + '    <form id="book-delete-form" class="inline">'
        + '      <button form="book-delete-form" class="link-button-disabled" onclick="return false">Delete</button>'
        + '    </form>'
        + '  </td>'
        + '</tr></thead>'
    );
}

function addReviewRow(body, review) {
    btnDeleteAuthorId = 'btnDeleteReview-' + review.id;
    bookAuthorDeleteForm = 'review-delete-form-' + review.id;
    body.append(
        '<tr>'
        + '  <td>' + review.review + '</td>'
        + '  <td>'
        + '    <a href="/review-edit?reviewId=' + review.id + '&bookId=' + bookId + '">Edit</a>'
        + '  </td>'
        + '  <td>'
        + '    <form id="' + bookAuthorDeleteForm + '" class="inline">'
        + '      <button form="' + bookAuthorDeleteForm + '" id="' + btnDeleteAuthorId + '" name="' + review.id + '" '
        + 'class="link-button">'
        + 'Delete</button>'
        + '    </form>'
        + '  </td>'
        + '</tr>'
    );
}

function appendReviewsTableFooter(body) {
    body.append(
        '<tr>'
        + '  <td colspan="3">'
        + '    <form id="button-form" method="get" action="/review-create">'
        + '      <div class="dtb0">'
        + '        <div class="dtb0Body">'
        + '          <div class="dtb0Row">'
        + '            <div class="dtb0Cell">'
        + '              <button type="reset" onclick="location.href = \'/\';">Home</button>'
        + '            </div>'
        + '            <div class="dtb0Cell">'
        + '              <input hidden type="hidden" name="bookId" value="' + bookId + '"/>'
        + '              <button type="submit" >Create new</button>'
        + '            </div>'
        + '          </div>'
        + '        </div>'
        + '      </div>'
        + '    </form>'
        + '  </td>'
        + '</tr>'
    );
}

function renderReviewsList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    reviewsTableDiv.empty();
    reviewsTableDiv.append('<table id="reviews-table" class="tg0"></table>');

    var reviewsTable = $('#reviews-table');

    appendReviewsTableHeader(reviewsTable);
    reviewsTable.append('<tbody id="reviews-table-tbody"></tbody>');

    var reviewsTableTbody = $('#reviews-table-tbody');

    $.each(list, function(index, entry) { addReviewRow(reviewsTableTbody, entry) });
    appendReviewsTableFooter(reviewsTableTbody);
    setTriggers()
}

function findAllReviews() {
    console.log('findAllReviews');
    $.ajax({
        type: 'GET',
        url: reviewsURL + '/' + bookId,
        dataType: "json",
        success: renderReviewsList
    })
}

function main() {
    findAllReviews()
}

jQuery(document).ready(main());
