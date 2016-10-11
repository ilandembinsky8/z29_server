$(document).ready(function() {
	$.get("getdata?func=getEx", function(data){
        for(var i = 0; i < data.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",data[i].id).text(data[i].name)); 
    });
});

$("#exSelect").change(function() {
    $('#noBid').html('<label for="firstname" class="col-sm-2 control-label">NoBidReasons</label>');
    $('#noBid').append('<select class="form-control" id="noBidReasonSelect" name="multiselect[]" multiple="multiple"></select>');
    $('#btn_send').html(' <div class="form-group"><div class="col-sm-offset-2 col-sm-10"><button id="" type="button" class="btn btn-primary">Submit</button></div>');

    var exchSelectedId = $("#exSelect option:selected").val();
    $.get("getdata?func=getNoBid&exchId=" + exchSelectedId, function(data) {
    	  
//*********************** start-changed multiSelect by Osama*********************************//	  
    	  var selectedValues=[];
          function onDeselect(e){
              var dataItem=e.dataItem;
              var index = selectedValues.indexOf(dataItem.id);
              if (index >= 0) {
                selectedValues.splice( index, 1 );
              //  alert(selectedValues);
              }
              $( "#"+dataItem.id ).prop( "checked", false );
             
          }
          
          function onSelect(e){
              var dataItem=e.dataItem;
              var index = selectedValues.indexOf(dataItem.id);
              if (index >= 0) {
                selectedValues.splice( index, 1 );
               // alert(selectedValues);
              }
              $( "#"+dataItem.id ).prop( "checked", true );
              selectedValues.push(dataItem.id);
             // alert(selectedValues);
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

    });
});

/*       $('#noBidReasonSelect').multiselect({
            nonSelectedText: 'Choose NoBids!',
            maxHeight: 200,
            includeSelectAllOption: true,
            enableFiltering: true,
            buttonWidth: '100%',

            onSelectAll: function() {
            },
            onChange: function() {
                 var selectedNoBidsValues = [];
                 $('#noBidReasonSelect :selected').each(function(i, selected) {
                     selectedNoBidsValues[i] = $(selected).val();
                 });
                 var refDate = $("#referDate").val();
                 alert(refDate);
                 var compDate = $("#compareDate").val();

                 $.get("getdata?func=getAdv&exchId=" + exchSelectedId+"&noBidId="+selectedNoBidsValues+ "&refDate=" + refDate, function(data) {
                	 /*$('#tree').on('click',function(){
                		 window.open("displayTree.html?data="+data);
                	 });
                	 $('#tree-container').empty();
                	 $('#tree-container').show();
                	// var root = JSON.parse(data.trim());
                	 showTree(data);
                });
             }
        });*/