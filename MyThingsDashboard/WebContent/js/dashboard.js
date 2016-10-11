var formVisiblity = false;


function showLoader(){ 
	$('.blackWindow').fadeIn();
	$(".loader").css("display","block"); 
}
function hideLoader(){ 
	$('.blackWindow').fadeOut();
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
