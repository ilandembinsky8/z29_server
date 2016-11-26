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
		$('.col-xs-12>h3').hide();
		$('#btnHideSelects').css('margin-top',"10px");
	}
	else{
		$('#btnHideSelects').text('Up');
		formVisiblity = true;
		$('.col-xs-12>h3').show();
		$('#btnHideSelects').css('margin-top',"0");
	}
});

function showTopMessage(msg){
	$('.top-message').html(msg);
	$(".top-message").slideToggle("slow");
	setTimeout(function(){ $(".top-message").slideToggle("slow"); }, 4000);
}