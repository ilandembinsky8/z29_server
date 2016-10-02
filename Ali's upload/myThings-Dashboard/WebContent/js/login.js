
$("#btn_login").click(function(){
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
			var jsonData= JSON.parse(data);
			if (!jsonData.success){
				$('.message').css("display","block");
				$('.message').html(jsonData.message);
			}else 
				window.location = jsonData.redirectPath;
		});
	}
});