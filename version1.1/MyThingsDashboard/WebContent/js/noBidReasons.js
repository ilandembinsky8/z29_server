var advVisible, reasonsArr;

$(document).ready(function() {	
	for(var i=0; i<24; i++){
		$('#referTime').append($("<option></option>").attr("value",i).text(i)); 
		$('#compareTime').append($("<option></option>").attr("value",i).text(i)); 
	}
	
	$.get("getdata?func=getEx", function(data){
        for(var i = 0; i < data.length; i++) 
        	$('#selectExch').append($("<option></option>").attr("value",data[i].id).text(data[i].name)); 
    });
});

$('#referDate').datepicker(); 
$('#compareDate').datepicker(); 

$('#compareTime').blur(function(){
	var refDate = new Date($("#referDate").val());
    var refHour = $("#referTime").val();
    var compDate = new Date($("#compareDate").val());
    var compHour = $("#compareTime").val();
    
    if(refDate.valueOf() === compDate.valueOf())
    	if(parseInt(refHour) === parseInt(compHour)){
    		showTopMessage("Have to change the compare date or hour");
        	$('#compareTime').focus();
    	}
});

$('#selectReasons').focus(function(){
	$(this).css("height","80px");
});

$('#selectReasons').blur(function(){
	$(this).css("height","32px");
});


$("#selectExch").change(function() {
    
	if(advVisible)
		$('#selectAdv').empty();
	$('#btn_send').css("display","none");
	
	var exchSelectedId = $("#selectExch option:selected").val();
    var refDate = $("#referDate").val();
    var referHour = $("#referTime").val();
    var compDate = $("#compareDate").val();
    var compareHour = $("#compareTime").val();
    
    if(refDate == "" || compDate == ""){
    	showTopMessage("Fill the date please !");
    	if(refDate == "") $("#referDate").focus();
    	else $("#compareDate").focus();
    	$("#selectExch").val($("#selectExch option:first").val());
    	return;
    }
	
    if($(this).val() === 'select')
    	return;
    
    $.get("getdata?func=getReasons&exchId="+exchSelectedId+"&refDate="+refDate+"&refHour="+referHour+"&compDate="+compDate+"&compHour="+compareHour, function(data) {
    	$('#selectReasons').empty();
    	for(var i = 0; i < data.length; i++) 
        	$('#selectReasons').append($("<option></option>").attr("value",data[i].id).text(data[i].name)); 
    });
});


$('#selectReasons').blur(function(){
	reasonsArr = [];
	var selectedOptions = $('#selectReasons option:selected');
	if(selectedOptions.length > 0){
		selectedOptions.each(function(){
			reasonsArr.push($(this).val());
		});
		
		if(advVisible){
			$("#selectAdv").change(function(){
				$('#btn_send').css("display","block");
			});
			$('#btn_send').css("display","none");
			$('#selectAdv').empty();
			$('#selectAdv').append($("<option>select</option>")); 
			$.get("getdata?func=getAdv&reasons=" + reasonsArr,function(data){
				data.forEach(function(item){
					$('#selectAdv').append($("<option></option>").attr("value",item._id).text(item.name)); 
				})
			});
		}
		else
			$('#btn_send').css("display","block");
	}
});

$("#chkboxAdv").change(function() {
    if(this.checked) {
    	$("#selectExch").val($("#selectExch option:first").val());
    	$('#selectReasons').empty();
    	$('#filters').append('<div class="form-group">'
				+'<div class="col-xs-12 col-sm-7">'      
				+'<div class="input-group">'
				+'<div class="input-group-addon">Advertiser</div>'
				+'<select class="form-control" id="selectAdv">'
				+'</select></div></div></div>');
    	advVisible = true;
    }
    else{
    	$('#filters').empty();
    	advVisible = false;
    }
    	
});

function prepareTree(root){
	formVisiblity = false;
	$('form').toggle('slow');
	$('.col-xs-12>h3').hide();
    $('#btnHideSelects').css('margin-top','10px');
    $('#btnHideSelects').text('Down');
	$('#btnHideSelects').show();
	$('#tree-container').empty();
	$('#tree-container').show();
	showTree(root);
}

$("#btn_send").click(function(){
	
	if(advVisible){
		var advId = $("#selectAdv option:selected").val();
		if(advId === 'select'){
			showTopMessage("Choose Advertiser please");
			return;
		}
		var advName = $("#selectAdv option:selected").text();
		$.get('getdata?func=getAdvTree&advId='+advId+'&advName='+advName,function(root){
			prepareTree(root);
		});
	}
	else
		$.get('getdata?func=getTree&reasons=' + reasonsArr, function(root){
			prepareTree(root);
		});
});
