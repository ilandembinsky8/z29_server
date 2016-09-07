/**
 * 
 */
var url="http://52.59.230.191:8080/Tsofen_Dynamic_group/";

$(document).ready(function(){
		
		$.get(url+"getFromServer3.jsp",function(data){
			showData(data);
		});
	});


