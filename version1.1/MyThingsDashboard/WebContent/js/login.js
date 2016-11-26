$(document).ready(function(){
	$('#adminId').focus();
});

function logIn(){
	var id1 = $("#adminId").val();
	var pass1 = $("#adminPass").val();
	
	if( id1 ==''){
		$('.message').css("display","block");
		$('.message').html("Please enter ID");
	}else if(pass1 ==''){
		$('.message').css("display","block");
		$('.message').html("Please enter Password");
	}else {
		$.post("login",
				{ 
					id: id1, 
					pass:pass1
				},
		function(data) {
			if (!data.success){
				$('.message').css("display","block");
				$('.message').html(data.message);
			}else 
				window.location = data.redirectPath;
		});
	}
}

$('#adminPass').on('keypress', function (e) {
	if(e.which === 13)
		logIn();	
});

$("#btn_login").click(function(){
	logIn();
});

