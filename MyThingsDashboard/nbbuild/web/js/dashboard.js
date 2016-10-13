var formVisiblity = false;


function showLoader(){ 
	$('.blackWindow').fadeIn(200);
	$(".loader").css("display","block"); 
}
function hideLoader(){ 
	$('.blackWindow').fadeOut(200);
	$(".loader").css("display","none"); 
}

$.ajaxSetup({
    beforeSend: showLoader,
    complete: hideLoader
});

$("#btn_logOut").click(function(){
	$.post("logout");
});

$('#btnHideSelects').click(function(){
	$('form').toggle("slow");
	if(formVisiblity) {
		$('#btnHideSelects').text('Down');
		formVisiblity = false;
	}
	else{
		$('#btnHideSelects').text('Up');
		formVisiblity = true;
	}
});

function showTopMessage(msg){
	$('.top-message').html(msg);
	$(".top-message").slideToggle("slow");
	setTimeout(function(){ $(".top-message").slideToggle("slow"); }, 4000);
}