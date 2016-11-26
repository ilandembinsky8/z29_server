
$(document).ready(function() {
	$.get("getkpidata?func=getDates", function(dateData){
        for(var i = 0; i < dateData.length; i++){
        	$('#fromDate').append($("<option></option>").attr("value",dateData[i].date).text(dateData[i].date)); 
        	$('#toDate').append($("<option></option>").attr("value",dateData[i].date).text(dateData[i].date));
        }
    }).done(function(){
    	$.get("getkpidata?func=getExchanges", function(exchangeData){
            for(var i = 0; i < exchangeData.length; i++) 
            	$('#exch').append($("<option></option>").attr("value",exchangeData[i].id).text(exchangeData[i].name)); 
        });
    });
});


$("#exch").change(function(){
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var exchId = this.value;

	if(fromDate === 'select' || toDate === 'select'){
		showTopMessage("Fill the date please !");
		$("#exch").val($("#exch option:first").val());
		if(fromDate === 'select')
			$('#fromDate').focus();
		else if(toDate === 'select')
			$('#toDate').focus();
		return;
	}
	
	$("#adv").html('<option>select</option>');
	$("#camp").html('<option>select</option>');
	$("#group").html('<option>select</option>');
	$("#creative").html('<option>select</option>');
	$.get("getkpidata?func=getAdvertisers&exchId="+exchId+"&fromDate="+fromDate+"&toDate="+toDate, function(data){
		for(var i = 0; i < data.length; i++) 
			$('#adv').append($("<option></option>").attr("value",data[i].id).text(data[i].name)); 
	});
});

$("#adv").change(function(){
	var advId = this.value;
	$("#camp").html('<option>select</option>');
	$("#group").html('<option>select</option>');
	$("#creative").html('<option>select</option>');
	$.get("getkpidata?func=getCampaigns&advId="+advId,function(data){	
		for(var i = 0; i < data.length; i++) 
			$('#camp').append($("<option></option>").attr("value",data[i].id).text(data[i].name));
	});
});

$("#camp").change(function(){
	var campId = this.value;
	$("#group").html('<option>select</option>');
	$("#creative").html('<option>select</option>');
	$.get("getkpidata?func=getGroups&campId="+campId,function(data){	
		for(var i = 0; i < data.length; i++) 
			$('#group').append($("<option></option>").attr("value",data[i].id).text(data[i].name));
	});
});

$("#group").change(function(){
	var groupId = this.value;
	$("#creative").html('<option>select</option>');
	$.get("getkpidata?func=getCreatives&groupId="+groupId,function(data){	
		for(var i = 0; i < data.length; i++) 
			$('#creative').append($("<option></option>").attr("value",data[i].id).text(data[i].name));
	});
});


$("#creative").change(function(){
	var creativeId = this.value; 
	$.get("getkpidata?func=getGraphJson&creativeId="+creativeId,function(data){
		
		var dates = ['x']; 
		var impressions = ['impressions'];
		var clicks = ['clicks'];
		
		for(var i=0; i<data.length; i++){
			dates.push(data[i].date);
			impressions.push(data[i].impressions);
			clicks.push(data[i].clicks);
		}
		
		////SHOW KPI GRAPH
		formVisiblity = false;
		$('form').toggle("slow");
		$('.col-xs-12>h3').hide();
		$('#btnHideSelects').css('margin-top',"10px");
		$('#btnHideSelects').show();
		$('#chart').empty();
		drawKpiGraph(dates,impressions,clicks);
	});
});

