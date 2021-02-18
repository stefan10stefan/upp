$(document).ready(function () {

	var process = "RegistrationUser";
	$.ajax({
		async: false,
		url: "http://localhost:8082/api/user/startProcess/"+process,
        type: "GET",
        dataType:"json",
        crossDomain: true,
        withCredentials: true,
        headers: {  'Access-Control-Allow-Origin': '*' },
        success: function (data) {
        	divGlavni = $('#formaRegistracija');
        	var str="";

        	console.log(data);

        	for(i=0;i<data.formFields.length;i++){
        		var tipPolja = "text";
        		if(data.formFields[i].label === "Password"){
        			tipPolja = "password";
        		}
        		if(data.formFields[i].label === "Beta"){

        		    str += '<label>Beta</label>'
                    str += '<select id="beta" name="isBeta">'
                    str += '<option>Yes</option>'
                    str += '<option selected>No</option>'
                    str += '</select>'

                    str+="</div>";
                    divGlavni.append(str);
                    str="";
                    continue;
                }
        		str+="<div class=\"form-group\">";
        		str+="<label class=\"sr-only\" >"+data.formFields[i].label+"</label>";
        			str+="<input type=\"" + tipPolja +"\" placeholder="+data.formFields[i].label+" class=\"form-first-name form-control\" id="+data.formFields[i].id+">";



        	}
        	str+='<div id="categories" style="display: none;"> ';
        	str+='<label>Comedy<input type="checkbox" id="Comedy"></label>';
        	str+='<label>Horror<input type="checkbox" id="Horror"></label>';
        	str+='<label>Triler<input type="checkbox" id="Triler"></label>';
        	str+='<label>Drama<input type="checkbox" id="Drama"></label>';
        	str+='<label>History<input type="checkbox" id="History"></label>';
        	str+='<label>Fantasy<input type="checkbox" id="Fantasy"></label>';
        	str+='</div>';

        	str+=" <button type=\"button\" onclick=\"buttonRegisterClick()\" class=\"btn\">Register</button>";
        	str+="<input type=\"hidden\" id=\"taskId\" name=\"taskId\" value=\""+data.taskId+"\">";
			str+="<input type=\"hidden\" id=\"processInstanceId\" name=\"processInstanceId\" value=\""+data.processInstanceId+"\">";

        	divGlavni.append(str);

        },
        error: function (jqxhr, textStatus, errorThrown) {
        	toastr['error']('Ne radi');
        }
        });

        listenerAdd();

})

function listenerAdd() {

    $('#beta').on('change', function(){
        beta = $('#beta').val();
    	beta = beta == 'Yes' ? true : false;

        if(beta) {
            $('#categories').css('display', 'block');
        }
        else {
            $('#categories').css('display', 'none');
        }

    });

}

function buttonRegisterClick(){
	taskId = $("#taskId").val();
	processInstanceId = $('#processInstanceId').val();
	email = $('#email').val();
	password = $('#password').val();
	//var res = email.match("^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$");
	var emailCheck = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
	var res = email.match(emailCheck);
	city = $('#city').val();
	country = $('#country').val();
	firstName = $('#firstName').val();
	lastName = $('#lastName').val();
	beta = $('#beta').val();

	beta = beta == 'Yes' ? true : false;

	var categories = ''

	if($('#Comedy').is(":checked")) {
	    categories += " Comedy";
	}

	if($('#Horror').is(":checked")) {
    	    categories += " Horror";
    	}

    if($('#Triler').is(":checked")) {
            categories += " Triler";
        }

    if($('#Drama').is(":checked")) {
    	    categories += " Drama";
    	}

    if($('#History').is(":checked")) {
    	    categories += " History";
    	}
    if($('#Fantasy').is(":checked")) {
        	    categories += " Fantasy";
        	}


	if(!firstName || !lastName || !email || !password || !country || !city) {
		toastr["error"]('Popunite sva polja!');
		return;
	}

	var data = JSON.stringify([
			{"fieldId":"firstName",
			"fieldValue":firstName},
			{"fieldId":"lastName",
				"fieldValue":lastName},
			{"fieldId":"email",
				"fieldValue":email},
			{"fieldId":"password",
				"fieldValue":password},
			{"fieldId":"country",
				"fieldValue":country},
			{"fieldId":"city",
				"fieldValue":city},
            {"fieldId":"isBeta",
                            "fieldValue":beta},
            {"fieldId":"categories",
                "fieldValue":categories}]
	);

	$.ajax({
		async: false,
		url: "http://localhost:8082/api/user/registerUser/"+taskId,
        type: "POST",
        contentType:"application/json",
        data : data,
        crossDomain: true,
        withCredentials: true,
        headers: {  'Access-Control-Allow-Origin': '*' },
        success: function (data1) {
        	top.location.href = "logIn.html";
        },
        error: function (jqxhr, textStatus, errorThrown) {
        	toastr['error']('Ne radi');
        }
        });
}

