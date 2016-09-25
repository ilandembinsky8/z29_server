$(document).ready(function() {

	// Load select input values
	$.get("getdata?func=getEx", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
	
	$.get("getdata?func=getAdv", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#advSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
});


$("#btn_logOut").click(function(){
	$.post("logout",function(data){
		var jsonData = JSON.parse(data);
		window.location = jsonData.redirectPath;
	});
});



$("#exSelect").change(function(){
	$("#advSelect").empty().append($("<option></option>").text("select")); 
	$.get("getdata?func=getAdvForEx&exID=" + this.value, function(data){
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