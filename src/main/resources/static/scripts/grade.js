$(document).ready(function () {

    let userId = getParameterByName('userId');

	$.ajax({
		async: false,
		url: "http://localhost:8082/api/book/get-user/" + userId,
        type: "GET",
        dataType:"json",
        crossDomain: true,
        withCredentials: true,
        headers: {  'Access-Control-Allow-Origin': '*' },
        success: function (data) {
        	console.log(data);

        	$("#button").append('<a class="btn" href="/api/grade/accept/' + userId + '">Accept</a>');
        	$("#button").append('<a class="btn" href="/api/grade/decline/' + userId + '">Decline</a>');
        	$("#button").append('<a class="btn" href="/api/grade/additional/' + userId + '">Need additional documents</a>');

        	for(let book of data) {
        	    $('#container').append('<div><a href="'+ book.link + '">' + book.name + '</a></div>');
            }

        },
        error: function (jqxhr, textStatus, errorThrown) {
        	toastr['error']('Ne radi');
        }
        });

});

function getParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}