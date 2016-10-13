$(document).ready(function() {
	$.get("getdata?func=getEx", function(data){
        for(var i = 0; i < data.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",data[i].id).text(data[i].name)); 
    });
});
//******* changed by ahmad & nawras - calendar **********  
$('#referDate').datepicker(); 
$('#compareDate').datepicker(); 



$("#exSelect").change(function() {
    $('#noBid').html('<label for="firstname" class="col-sm-0 control-label">NoBidReasons</label>');
    $('#noBid').append('<select class="form-control" id="noBidReasonSelect" name="multiselect[]" multiple="multiple"></select>');
    $('#btn_send').html(' <div class="form-group"><div class="col-sm-offset-2 col-sm-10"><button id="submit_btn" type="button" class="btn btn-primary">Submit</button></div>');

    var exchSelectedId = $("#exSelect option:selected").val();
    //******* 
    var test = $("#referDate").val();
    alert(test); 
    //***
    
    $.get("getdata?func=getNoBid&exchId=" + exchSelectedId, function(data) {
//*********************** Added by Avgana *******************************//
    	var refDate = $("#referDate").val();
        var compDate = $("#compareDate").val();
//*********************** start-changed multiSelect by Osama*********************************//

        var selectedValues=[];
        function onDeselect(e){
        	var dataItem=e.dataItem;
            var index = selectedValues.indexOf(dataItem.id);
            if (index >= 0) 
            	selectedValues.splice( index, 1 );
            $( "#"+dataItem.id ).prop( "checked", false ); 
          }
          
        function onSelect(e){
            var dataItem=e.dataItem;
            var index = selectedValues.indexOf(dataItem.id);
            if (index >= 0)
            	selectedValues.splice( index, 1 );
            $( "#"+dataItem.id ).prop( "checked", true );
            selectedValues.push(dataItem.id);
        }
       
        $("#noBidReasonSelect").kendoMultiSelect({
        	placeholder: "Select NoBidReasons",
        	height:300,
            dataTextField: "name",
            dataValueField: "id",
            dataSource: data,
            itemTemplate: "#:data.name# <input id=#:data.id# type='checkbox'/>",
            select:onSelect,
            deselect: onDeselect,
            autoClose:false,
            animation: {
            	  close: {
            		  effects: "fadeOut zoom:out",
            		  duration: 300
                  },
                  open: {
                	  effects: "fadeIn zoom:in",
                	  duration: 300
                  }
              }
         });
          
 //*********************** end-changed multiSelect by osama*********************************//	  

        $("#submit_btn").click(function(){
        	$.get("getdata?func=getAdv&exchId=" + exchSelectedId+"&noBidId=" + selectedValues + "&refDate=" + refDate + "&compDate=" + compDate, function(root){
        		$('#tree-container').empty();
        		$('#tree-container').show();
        		$('form').toggle("slow");
        		$('#btnHideSelects').show();
        		showTree(root);
        	}); 	  
        });
    });
});
