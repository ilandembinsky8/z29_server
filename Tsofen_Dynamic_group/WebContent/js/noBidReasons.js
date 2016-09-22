$(document).ready(function() {

	// Load select input values
	$.get("getdata?func=getEx", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
});

$("#exSelect").change(function(){
        $("#noBidReasonLabel").empty(); 
	$('#noBidReasonLabel').append('<label class="control-label">No Bid Reason</label>');
				  
    
        $("#noBidReason").empty(); 
	$('#noBidReason').append(  '<select class="form-control" id="advSelect" name="multiselect[]" multiple="multiple">'
				   + '</select></div></div>');
	
	 
	$.get("getdata?func=getNoBid&Exchangeid=" + this.value, function(data){
		var jsonData = JSON.parse(data);
		for(var i = 0; i < jsonData.length; i++){ 
			$('#advSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
                    }
                    $('#advSelect').multiselect({
                        nonSelectedText: 'Choose Advertisers!',
                        maxHeight: 200,
                        includeSelectAllOption: true,
                        enableFiltering: true,
                        buttonWidth: '100%',
                        
                        onChange: function() {
                            
                        }
                        
                    });  
	});
});

	
	
	
}











