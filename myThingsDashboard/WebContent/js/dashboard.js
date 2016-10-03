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
