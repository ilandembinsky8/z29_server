$(document).ready(function() {

    // Load exchanges select input texts and values
    $.get("getdata?func=getEx", function(data) {
        var jsonData = JSON.parse(data);
        for (var i = 0; i < jsonData.length; i++)
            $('#exSelect').append($("<option></option>").attr("value", jsonData[i].id).text(jsonData[i].name));
    });
});

$("#exSelect").change(function() {
    $("#noBidReasonLabel").empty();
    $('#noBidReasonLabel').append('<label class="control-label">No Bid Reason</label>');

    $("#noBidReason").empty();
    $('#noBidReason').append('<select class="form-control" id="noBidReasonSelect" name="multiselect[]" multiple="multiple">' +
        '</select></div></div>');


    var exchSelectedID = $("#exSelect option:selected").val();
    // Load NoBids select input texts and values for a specific exchange
    $.get("getdata?func=getNoBidReasons&exchID=" + exchSelectedID, function(data) {


        var jsonData = JSON.parse(data);

        for (var i = 0; i < jsonData.length; i++) {
            $('#noBidReasonSelect').append($("<option></option>").attr("value", jsonData[i].id).text(jsonData[i].name));
        }
        $('#noBidReasonSelect').multiselect({
            nonSelectedText: 'Choose NoBids!',
            maxHeight: 200,
            includeSelectAllOption: true,
            enableFiltering: true,
            buttonWidth: '100%',

            onSelectAll: function() {
                // alert('onSelectAll triggered!');
            },
            onChange: function() {
               /* var selectedNoBidsValues = [];
                $('#noBidReasonSelect :selected').each(function(i, selected) {
                    selectedNoBidsValues[i] = $(selected).val();
                    alert("selectedNoBidValue=" + selectedNoBidsValues[i]);
                });*/
                noBidSelectedID = $("#noBidReasonSelect option:selected").val();
                //alert(noBidSelectedID);
                $.get("getdata?func=getGraph&exchID=" + exchSelectedID+"&noBidID="+noBidSelectedID, function(data) {
               // jsonBarChartData = JSON.parse(data);
                
                
                });
            }
        });
    });
});

function getBarChartData(){return jsonBarChartData;}

function refreshMultiSelect() {

    $("#noBidReasonSelect").multiselect("deselectAll", false);
    $("#noBidReasonSelect").multiselect('rebuild');
    $("#noBidReasonSelect").multiselect('refresh');

}

