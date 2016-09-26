var jsonBarChartData;

$(document).ready(function() {

	// Load select input values
	$.get("getdata?func=getEx", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#exSelect').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
});

$("#exSelect").change(function() {
    $("#noBidReasonLabel").empty();
    $('#noBidReasonLabel').append('<label class="control-label">No Bid Reason</label>');

    $("#noBidReason").empty();
    $('#noBidReason').append('<select class="form-control" id="noBidReasonSelect" name="multiselect[]" multiple="multiple">' +
        '</select></div></div>');

    SelectedID = $("#exSelect option:selected").val();
    var exchSelectedID = $("#exSelect option:selected").val();
    // Load NoBids select input texts and values for a specific exchange
    $.get("getdata?func=getNoBid&exchID=" + exchSelectedID, function(data) {

        var jsonData = JSON.parse(data);

        for (var i = 0; i < jsonData.length; i++) 
            $('#noBidReasonSelect').append($("<option></option>").attr("value", jsonData[i].id).text(jsonData[i].name));
        
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
                	 jsonBarChartData = JSON.parse(data);
                 	 drawBarChart();
                 });
             }
        });
    });
});


function refreshMultiSelect() {

    $("#noBidReasonSelect").multiselect("deselectAll", false);
    $("#noBidReasonSelect").multiselect('rebuild');
    $("#noBidReasonSelect").multiselect('refresh');

}

////////////////////////////////////////
/// BAR CHART JS
////////////////////////////////////////

function drawBarChart(){
//set the dimensions of the canvas
	
var margin = {top: 20, right: 20, bottom: 70, left: 40},
    width = 600 - margin.left - margin.right,
    height = 300 - margin.top - margin.bottom;


// set the ranges
var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

var y = d3.scale.linear().range([height, 0]);

// define the axis
var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")


var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(10);

// add the SVG element
var svg = d3.select(".bar-chart").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", 
          "translate(" + margin.left + "," + margin.top + ")");

// load the data
d3.json(jsonBarChartData, function(error,data) {
	jsonBarChartData.forEach(function(d) {
        d.Advertiser = d.Advertiser;
        d.Count = +d.Count;
    });
  // scale the range of the data
  x.domain(jsonBarChartData.map(function(d) { return d.Advertiser; }));
  y.domain([0, d3.max(jsonBarChartData, function(d) { return d.Count; })]);
  // add axis
  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis)
    .selectAll("text")
      .style("text-anchor", "end")
      .attr("dx", "-.8em")
      .attr("dy", "-.55em")
      .attr("transform", "rotate(-90)" );

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 5)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Count");


  // Add bar chart
  svg.selectAll("bar")
      .data(jsonBarChartData)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) { return x(d.Advertiser); })
      .attr("width", x.rangeBand())
      .attr("y", function(d) { return y(d.Count); })
      .attr("height", function(d) { return height - y(d.Count); });

});
}











