var data = [{"Advertiser":"CasinoDrive\r","Count":3125},{"Advertiser":"CiceksepetiTR\r","Count":202},{"Advertiser":"Cloggs\r","Count":91869},{"Advertiser":"congstar\r","Count":68556},{"Advertiser":"CzasNaButyPL\r","Count":35945},{"Advertiser":"Damart\r","Count":546780},{"Advertiser":"Deal\r","Count":9503},{"Advertiser":"DecathlonNL\r","Count":20586},{"Advertiser":"Deindesign\r","Count":381950},{"Advertiser":"Denby\r","Count":127089},{"Advertiser":"EnglishFirst.RU\r","Count":52803},{"Advertiser":"eToro\r","Count":14125},{"Advertiser":"EuropeAssistance\r","Count":179409},{"Advertiser":"EvolutionSlimming\r","Count":306029},{"Advertiser":"Fineartprint\r","Count":217740},{"Advertiser":"Fonic DE\r","Count":42782},{"Advertiser":"FootWayFI\r","Count":13644},{"Advertiser":"FootWayNO\r","Count":1596},{"Advertiser":"FootWaySE\r","Count":16826},{"Advertiser":"FrenchConnection UK\r","Count":66946},{"Advertiser":"FurnitureINFashion\r","Count":478035},{"Advertiser":"Futuroscope\r","Count":34016},{"Advertiser":"Gebuhrenfrei\r","Count":423962},{"Advertiser":"Gemo FR\r","Count":312250},{"Advertiser":"Grosbill-FR\r","Count":8566},{"Advertiser":"GroupdealNL\r","Count":111448},{"Advertiser":"hairgalleryIT\r","Count":132284},{"Advertiser":"Homes Direct 365 UK\r","Count":26941},{"Advertiser":"HP de\r","Count":25806},{"Advertiser":"HSE24DE\r","Count":571019}];

$(document).ready(function() {

	$.get("getkpidata?func=getEx", function(data){
		var jsonData = JSON.parse(data);
        for(var i = 0; i < jsonData.length; i++) 
        	$('#exch').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
    });
});


$("#exch").change(function(){
	var val = this.value;
	$("#adv").html($("<option></option>").text("select")); 
	$.get("getkpidata?func=getAdv&exchId=" + val, function(data){
		var jsonData = JSON.parse(data);
		for(var i = 0; i < jsonData.length; i++) 
			$('#adv').append($("<option></option>").attr("value",jsonData[i].id).text(jsonData[i].name)); 
	});
});

$("#adv").change(function(){
	$('form').toggle("slow");
	$('#btnHideSelects').show();
	drawBarChart(data);	
});
