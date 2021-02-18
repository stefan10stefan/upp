
function buttonLoginClick(){
	//taskId = $("#taskId").val();
	email = $('#formLogin-email').val();
	lozinka = $('#formLogin-password').val();
	//processInstanceId = $('#processInstanceId').val();
	if(!email || !lozinka) {
		toastr["error"]('Fill in all required entry fields!');
		return;
	}
	var data = JSON.stringify([
	             {"fieldId":"email",
	            "fieldValue":email},
	            {"fieldId":"password",
		            "fieldValue":lozinka},
		            
	             ]);
	
	var emailCheck = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
	var res = email.match(emailCheck);          
	
	if(res !=null){
			$.ajax({
				async: false,
				url: "http://localhost:8082/api/user/loginUser",
		        type: "POST",
		        contentType:"application/json",
		        data : data,
		        crossDomain: true,
		        withCredentials: true,
		        headers: {  'Access-Control-Allow-Origin': '*' },
		        success: function (data2) {
	         		if(data2!=null){
	         			if(data2.userType=="WRITER"){
	         				top.location.href= "writer.html";
	         			}
	         			else if(data2.userType=="DIRECTOR"){
	         				top.location.href = "director.html";
	         			}
	         			else {
	         			top.location.href = "home.html";
	         			}
	         		}else{
	         			top.location.href="registracija.html";
	         		}
                },
                error: function (jqxhr, textStatus, errorThrown) {
                	toastr['error']('Ne radi');
                } 
            });	
	}else{
		toastr['error']('Invalid email');
	}

  }
	

