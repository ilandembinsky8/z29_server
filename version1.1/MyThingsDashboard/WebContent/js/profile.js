var id,pass;

$(document).ready(function (){
	id = $('#id').val();
	var pass = $('#pass').val();
	
	$.post('profile?func=getAdminData&id='+id+"&pass="+pass,function(data){
		$('#email').val(data);
	});
});

$('button').click(function(){
	var pass = $('#pass').val();
	var email = $('#email').val();
	$.post('profile?func=updateAdminData&pass='+pass+'&email='+email,function(data){
		showTopMessage(data);
	});
});