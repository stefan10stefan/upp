$(document).ready(function () {

	var process = "Registration";
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
        		str+="<div class=\"form-group\">";
        		str+="<label class=\"sr-only\" >"+data.formFields[i].label+"</label>";
        			str+="<input type=\"" + tipPolja +"\" placeholder="+data.formFields[i].label+" class=\"form-first-name form-control\" id="+data.formFields[i].id+">";
        		
        		
        		str+="</div>";
        		divGlavni.append(str);
        		str="";
        	}
        	str+=" <button type=\"button\" onclick=\"buttonRegisterClick()\" class=\"btn\">Register</button>";
        	str+="<input type=\"hidden\" id=\"taskId\" name=\"taskId\" value=\""+data.taskId+"\">";
			str+="<input type=\"hidden\" id=\"processInstanceId\" name=\"processInstanceId\" value=\""+data.processInstanceId+"\">";
			
        	divGlavni.append(str);
        	
        },
        error: function (jqxhr, textStatus, errorThrown) {
        	toastr['error']('Ne radi');
        } 
        });	
	
})

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
				"fieldValue":city}]
				
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




