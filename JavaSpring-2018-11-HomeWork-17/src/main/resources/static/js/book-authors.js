
var bookAuthorsTableDiv = $('#book-author-table-div');

function deleteBookAuthor(authorId) {
    console.log('deleteReview with id: '+ authorId);
    $.ajax({
        type: 'DELETE',
        url: authorsURL + '/' + authorId + '/from-book/' + bookId,
        success: function(data, textStatus, jqXHR) {
            console.log("Author deleted successfully: " + textStatus);
            window.alert("Author deleted successfully: " + textStatus);
            setTimeout(function(){location.reload();}, 750);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("delete Author with error: " + textStatus);
            window.alert("delete Author with error: " + textStatus);
        }
    })
}

function setTriggers() {
    $("button[id^='btnDeleteAuthor']").each(function (i, el) {
        console.log(el.id);
        $('#' + el.id).click(function() {
            deleteBookAuthor(el.name);
            return false
        })
    })
}

function appendBookAuthorsTableHeader(body) {
    body.append(
        '<thead><tr>'
        + '  <td>Имя</td>'
        + '  <td>Фамилия</td>'
        + '  <td>'
        + '    <form id="book-authors-edit-form" class="inline">'
        + '      <button form="book-edit-form" class="link-button-disabled" onclick="return false">Edit</button>'
        + '    </form>'
        + '  </td>'
        + '  <td>'
        + '    <form id="book-authors-delete-form" class="inline">'
        + '      <button form="book-delete-form" class="link-button-disabled" onclick="return false">Delete</button>'
        + '    </form>'
        + '  </td>'
        + '</tr></thead>'
    );
}

function addBookAuthorRow(body, author) {
    btnDeleteAuthorId = 'btnDeleteAuthor-' + author.id;
    bookAuthorDeleteForm = 'book-author-delete-form-' + author.id;
    body.append(
        '<tr>'
        + '  <td>' + author.firstName + '</td>'
        + '  <td>' + author.lastName + '</td>'
        + '  <td>'
        + '    <a href="/author-edit?authorId=' + author.id + '&bookId=' + bookId + '">Edit</a>'
        + '  </td>'
        + '  <td>'
        + '    <form id="' + bookAuthorDeleteForm + '" class="inline">'
        + '      <button form="' + bookAuthorDeleteForm + '" id="' + btnDeleteAuthorId + '" name="' + author.id + '" '
        + 'class="link-button">'
        + 'Delete</button>'
        + '    </form>'
        + '  </td>'
        + '</tr>'
    );
}

function appendBookAuthorsTableFooter(body) {
    body.append(
        '<tr>'
        + '  <td colspan="4">'
        + '    <form id="button-form" method="get" action="/author-create">'
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

function renderBookAuthorsList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    bookAuthorsTableDiv.empty();
    bookAuthorsTableDiv.append('<table id="book-authors-table" class="tg0"></table>');

    var reviewsTable = $('#book-authors-table');

    appendBookAuthorsTableHeader(reviewsTable);
    reviewsTable.append('<tbody id="book-authors-table-tbody"></tbody>');

    var reviewsTableTbody = $('#book-authors-table-tbody');

    $.each(list, function(index, entry) { addBookAuthorRow(reviewsTableTbody, entry) });
    appendBookAuthorsTableFooter(reviewsTableTbody);
    setTriggers()
}

function findAllBookAuthors() {
    console.log('findAllBookAuthors');
    $.ajax({
        type: 'GET',
        url: authorsURL + '/' + bookId,
        dataType: "json",
        success: renderBookAuthorsList
    })
}

function main() {
    findAllBookAuthors()
}

jQuery(document).ready(main());
