function showData(data){
	
	var json = $.parseJSON(data);
	
	var returnVal = "<table><thead><tr><td>Index</td><td>Data</td></tr></thead><tbody>";
	
	/*json.forEach(function(item, index){
		returnVal += "<tr><td>"+ item.index +"</td><td>"+ item.data +"</td></tr>";
	});*/

	returnVal += "<tr><td>"+ json.index +"</td><td>"+ json.data +"</td></tr>";
	returnVal += "</tbody></table>";

	$(".content").html(returnVal);
}



var json = '{"index":"123","data":" ali"}';
showData(json);


/*$( document ).ready(function() {
    $("#getData").click(function(){
		
		var json = '{ "employees" : [' +
				   '{ "firstName":"John" , "lastName":"Doe" },' +
				   '{ "firstName":"Anna" , "lastName":"Smith" },' +
				   '{ "firstName":"Peter" , "lastName":"Jones" } ]}';
		showData(json);
	});
});*/






