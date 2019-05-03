
function reviewFormToJSON() {
    var reviewId = $('#reviewId').val();
    var bookId = $('#bookId').val();
    var review = $('#review').val();

    return JSON.stringify({
        "id": reviewId === "" ? "0" : reviewId,
        "bookId": bookId,
        "review": review
    })
}

function addReview() {
    console.log('addReview');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: reviewsURL,
        dataType: "json",
        data: reviewFormToJSON(),
        statusCode: {
            201: function(data, textStatus, jqXHR) {
                console.log("Review created successfully: " + textStatus);
                window.alert("Review created successfully: " + textStatus);
            },
            406: function(data, textStatus, jqXHR) {
                console.log("create Review with error: " + textStatus);
                window.alert("create Review with error: " + textStatus);
            },
            500: function(data, textStatus, jqXHR) {
                console.log("create Review with server error: " + textStatus);
                window.alert("create Review with server error: " + textStatus);
            }
        }
    })
}

function updateReview() {
    console.log('updateReview');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: reviewsURL,
        dataType: "json",
        data: reviewFormToJSON(),
        success: function(data, textStatus, jqXHR) {
            console.log("Review updated successfully: " + textStatus);
            window.alert("Review updated successfully: " + textStatus);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("update Review with error: " + textStatus);
            window.alert("update Review with error: " + textStatus);
        }
    })
}

function setTriggers() {
    $('#btnSave').click(function() {
        if ($('#reviewId').val() === '0')
            addReview();
        else
            updateReview();
        return false
    })
}

function main() {
    setTriggers()
}

jQuery(document).ready(main());