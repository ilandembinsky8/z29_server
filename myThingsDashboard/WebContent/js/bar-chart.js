// DATA FOR EXAMPLE
//var barChartData = [{"Advertiser":"3SuissesBEFR\r","Count":93678},{"Advertiser":"3SuissesBENL\r","Count":42629},{"Advertiser":"Abindenurlaub\r","Count":15072},{"Advertiser":"AlitaliaIT\r","Count":2572},{"Advertiser":"Ausilium1\r","Count":29471},{"Advertiser":"Auto-ies\r","Count":16230},{"Advertiser":"BangerheadSE\r","Count":72196},{"Advertiser":"Base\r","Count":235041},{"Advertiser":"Basler-fashion\r","Count":55394},{"Advertiser":"Beate Uhse\r","Count":21558},{"Advertiser":"Beaverbrooks UK\r","Count":173443},{"Advertiser":"Bigstar PL\r","Count":32399},{"Advertiser":"Bobber.ru\r","Count":92866},{"Advertiser":"BON`A PARTE DE\r","Count":50132},{"Advertiser":"BON`A PARTE DK\r","Count":8758},{"Advertiser":"BON`A PARTE NL\r","Count":22784},{"Advertiser":"BON`A PARTE SE\r","Count":20913},{"Advertiser":"BrightHouse\r","Count":26340},{"Advertiser":"BUY2 IL\r","Count":10762},{"Advertiser":"CasinoDrive\r","Count":3125},{"Advertiser":"CiceksepetiTR\r","Count":202},{"Advertiser":"Cloggs\r","Count":91869},{"Advertiser":"congstar\r","Count":68556},{"Advertiser":"CzasNaButyPL\r","Count":35945},{"Advertiser":"Damart\r","Count":546780},{"Advertiser":"Deal\r","Count":9503},{"Advertiser":"DecathlonNL\r","Count":20586},{"Advertiser":"Deindesign\r","Count":381950},{"Advertiser":"Denby\r","Count":127089},{"Advertiser":"EnglishFirst.RU\r","Count":52803},{"Advertiser":"eToro\r","Count":14125},{"Advertiser":"EuropeAssistance\r","Count":179409},{"Advertiser":"EvolutionSlimming\r","Count":306029},{"Advertiser":"Fineartprint\r","Count":217740},{"Advertiser":"Fonic DE\r","Count":42782},{"Advertiser":"FootWayFI\r","Count":13644},{"Advertiser":"FootWayNO\r","Count":1596},{"Advertiser":"FootWaySE\r","Count":16826},{"Advertiser":"FrenchConnection UK\r","Count":66946},{"Advertiser":"FurnitureINFashion\r","Count":478035},{"Advertiser":"Futuroscope\r","Count":34016},{"Advertiser":"Gebuhrenfrei\r","Count":423962},{"Advertiser":"Gemo FR\r","Count":312250},{"Advertiser":"Grosbill-FR\r","Count":8566},{"Advertiser":"GroupdealNL\r","Count":111448},{"Advertiser":"hairgalleryIT\r","Count":132284},{"Advertiser":"Homes Direct 365 UK\r","Count":26941},{"Advertiser":"HP de\r","Count":25806},{"Advertiser":"HSE24DE\r","Count":571019}];

////////////////////////////////////////
/// BAR CHART JS
////////////////////////////////////////

function drawBarChart(barChartData){
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
var svg = d3.select("#bar-chart").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", 
          "translate(" + margin.left + "," + margin.top + ")");

// load the data
d3.json('', function() {
	barChartData.forEach(function(d) {
        d.Advertiser = d.Advertiser;
        d.Count = +d.Count;
    });
  // scale the range of the data
  x.domain(barChartData.map(function(d) { return d.Advertiser; }));
  y.domain([0, d3.max(barChartData, function(d) { return d.Count; })]);
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
      .data(barChartData)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) { return x(d.Advertiser); })
      .attr("width", x.rangeBand())
      .attr("y", function(d) { return y(d.Count); })
      .attr("height", function(d) { return height - y(d.Count); });

});
}