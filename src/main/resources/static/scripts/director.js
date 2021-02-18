$(document).ready(function () {

	$.ajax({
		async: false,
		url: "http://localhost:8082/api/user/can-grade/",
        type: "GET",
        dataType:"json",
        crossDomain: true,
        withCredentials: true,
        headers: {  'Access-Control-Allow-Origin': '*' },
        success: function (data) {
        	console.log(data);

        	for(let user of data) {
        	    $('#container').append('<a href="/grade.html?userId='+ user.id + '">' + user.firstName + ' ' + user.lastName + '</a>');
            }

        },
        error: function (jqxhr, textStatus, errorThrown) {
        	toastr['error']('Ne radi');
        }
        });

})