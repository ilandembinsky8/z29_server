var root = 
{"name":"Global","children":[{"name":"Denby\r","children":[{"name":"Denby_Visitors (DR)\r","children":[{"name":"Denby_Visitors (DR)_Default_Ad_Group\r","children":[{"name":"Lite_1114_Denby\r"},{"name":"Html_pager_0115\r"}]}]},{"name":"Denby (DR)\r","children":[{"name":"Denby (DR)_Default_Ad_Group\r","children":[{"name":"Html_pager_0115\r"},{"name":"G2gal_0912_Denby\r"},{"name":"Lite_1114_Denby\r"}]}]}]},{"name":"Cloggs\r","children":[{"name":"Cloggs (DR)\r","children":[{"name":"Cloggs (DR)_Default_Ad_Group\r","children":[{"name":"SLB_0913_Cloggs\r"},{"name":"G2html_0814_Cloggs\r"},{"name":"G2_SLB_1014_Cloggs\r"},{"name":"Html_SLB_1113_Cloggs\r"},{"name":"G2_SLB_1113_Cloggs\r"},{"name":"LP_0812_Cloggs\r"},{"name":"G2_SLB_0913_Cloggs\r"},{"name":"G2_SLB_0315_Cloggs_opening\r"}]}]},{"name":"Cloggs-MobileRet (DR)\r","children":[{"name":"Cloggs-MobileRet (DR)_Default_Ad_Group\r","children":[{"name":"G2html_0814_Cloggs\r"}]}]}]},{"name":"OrangeMobile\r","children":[{"name":"OrangeMobile-BasicLog\r","children":[{"name":"BasicLog\r","children":[{"name":"C_0715_Basic_Log\r"}]}]},{"name":"OrangeMobile-BasicNLog\r","children":[{"name":"BasicNLog\r","children":[{"name":"C_0715_Basic_NLog\r"}]}]},{"name":"OrangeMobile-ProductLog\r","children":[{"name":"ProductLog\r","children":[{"name":"Html_slb_0715-ProductLog\r"},{"name":"Html_pager_0715-ProductLog\r"},{"name":"Html_pagerBubble_0715-ProductLog\r"},{"name":"C_0715_Product_Log\r"}]}]},{"name":"OrangeMobile-ProductNLog\r","children":[{"name":"ProductNLog\r","children":[{"name":"C_0715_Product_NLog\r"}]}]}]}]};


$(document).ready(function() {

	$.get("getdata?func=getEx", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
	
	$('.date').mask('00/00/0000', {placeholder: "MM/DD/YYYY"});
	$('.time').mask('00:00', {placeholder: "00:00"});
});

$("#exSelect").change(function() {
	
    $('#noBid').html('<div class="input-group-addon">NoBidReasons</div>');
    $('#noBid').append('<select class="form-control" id="noBidReasonSelect" name="multiselect[]" multiple="multiple"></select>');

    var exchSelectedId = $("#exSelect option:selected").val();
    //$.get("getdata?func=getNoBid&exchId=" + exchSelectedId, function(data) {
        
    	//var jsonData = JSON.parse(data);
    	
        //for (var i = 0; i < jsonData.length; i++) 
            //$('#noBidReasonSelect').append($("<option></option>").attr("value", jsonData[i].id).text(jsonData[i].name));
        
        $('#noBidReasonSelect').multiselect({
            nonSelectedText: 'Choose NoBids!',
            maxHeight: 200,
            includeSelectAllOption: true,
            enableFiltering: true,
            buttonWidth: '100%',

            onSelectAll: function() {
            },
            onChange: function() {
                /* var selectedNoBidsValues = [];
                 $('#noBidReasonSelect :selected').each(function(i, selected) {
                     selectedNoBidsValues[i] = $(selected).val();
                 });*/
                 noBidSelectedId = $("#noBidReasonSelect option:selected").val();
                
                 //$.get("getdata?func=getAdv&exchId=" + exchSelectedId+"&noBidId="+noBidSelectedId, function(data) {
                	 $('#tree-container').empty();
                	 //showTree(JSON.parse(data));
                	 showTree(root);
                 //});
             }
        });
    //});
});

