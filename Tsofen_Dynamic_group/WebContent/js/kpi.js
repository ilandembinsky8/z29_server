function showData(data){ 
 	// Load select input values 
 	var jsonData = JSON.parse(data.trim());
	//data = data.trim();
	alert(data);
 	for(var i = 0; i < jsonData.length; i++)  
      	$('#exchangeSelect').append($("<option></option>").text(jsonData[i].data)); 
 } 
