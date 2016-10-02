$(document).ready(function() {

	$.get("getkpidata?func=getEx", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
	
	/*$.get("getdata?func=getAdv", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#advSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });*/
});


$("#exSelect").change(function(){
	var val = this.value;
	$("#advSelect").html($("<option></option>").text("select")); 
	$.get("getkpidata?func=getAdv&exchId=" + val, function(data){
		var jsonData = JSON.parse(data);
		for(var i = 0; i < jsonData.length; i++) 
			$('#advSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
	});
});




/*$("#campaignSelect").change(function(){
	$("#multiSelectors").empty();
	$("#multiSelectors").append("<div class='row'>"
				+ "<div class='col-sm-3'>"
				+ "<label>Ad Group</label></div>"
				+ "<div class='col-sm-9'>"
				+ "<select class='form-control' id='ad-groupSelect'>"
	  			+ "<option>select</option></select></div>");
});*/