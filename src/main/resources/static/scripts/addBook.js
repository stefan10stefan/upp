
function addBook(){

	name = $('#name').val();
	link = $('#link').val();
	if(!name || !link) {
		toastr["error"]('Popunite sva polja!');
		return;
	}

	var data = JSON.stringify(
        {
            "name": name,
            "link": link
        }
	);

	$.ajax({
		async: false,
		url: "http://localhost:8082/api/book/add",
        type: "POST",
        contentType:"application/json",
        data : data,
        crossDomain: true,
        withCredentials: true,
        headers: {  'Access-Control-Allow-Origin': '*' },
        success: function (data1) {
            name = $('#name').val("");
            link = $('#link').val("");
        },
        error: function (jqxhr, textStatus, errorThrown) {
        	toastr['error']('Ne radi');
        }
        });
}




