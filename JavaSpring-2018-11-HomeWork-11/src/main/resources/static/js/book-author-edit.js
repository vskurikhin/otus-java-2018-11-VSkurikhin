
function bookAuthorFormToJSON() {
    var authorId = $('#authorId').val();
    var bookId = $('#bookId').val();
    var firstName = $('#firstName').val();
    var lastName = $('#lastName').val();

    return JSON.stringify({
        "id": authorId === "" ? null : authorId,
        "bookId": bookId,
        "firstName": firstName,
        "lastName": lastName
    })
}

function addAuthor() {
    console.log('addAuthor');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: authorsURL,
        dataType: "json",
        data: bookAuthorFormToJSON(),
        statusCode: {
            201: function(data, textStatus, jqXHR) {
                console.log("Author created successfully: " + textStatus);
                window.alert("Author created successfully: " + textStatus);
            },
            406: function(data, textStatus, jqXHR) {
                console.log("create Author with error: " + textStatus);
                window.alert("create Author with error: " + textStatus);
            },
            500: function(data, textStatus, jqXHR) {
                console.log("create Author with server error: " + textStatus);
                window.alert("create Author with server error: " + textStatus);
            }
        }
    })
}

function updateAuthor() {
    console.log('updateAuthor');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: authorsURL,
        dataType: "json",
        data: bookAuthorFormToJSON(),
        success: function(data, textStatus, jqXHR) {
            console.log("Author updated successfully: " + textStatus);
            window.alert("Author updated successfully: " + textStatus);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("update Author with error: " + textStatus);
            window.alert("update Author with error: " + textStatus);
        }
    })
}

function setTriggers() {
    var authorId = $('#authorId').val();
    $('#btnSave').click(function() {
        if (authorId === '' || authorId === '0' || authorId === "null" )
            addAuthor();
        else
            updateAuthor();
        return false
    })
}

function main() {
    setTriggers()
}

jQuery(document).ready(main());