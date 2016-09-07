/**
 * 
 */
var url="http://localhost:8080/Tsofen_Dynamic_group";

$(document).ready(function(){
	$("#getData").click(function (){
		var index=$("#index").val();
		
		$.get(url+"getFromServer2.jsp?index="+index,function(data){
			showData(data);
		});
	});
	});


