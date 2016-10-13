var data = [{"Advertiser":"CasinoDrive\r","Count":3125},{"Advertiser":"CiceksepetiTR\r","Count":202},{"Advertiser":"Cloggs\r","Count":91869},{"Advertiser":"congstar\r","Count":68556},{"Advertiser":"CzasNaButyPL\r","Count":35945},{"Advertiser":"Damart\r","Count":546780},{"Advertiser":"Deal\r","Count":9503},{"Advertiser":"DecathlonNL\r","Count":20586},{"Advertiser":"Deindesign\r","Count":381950},{"Advertiser":"Denby\r","Count":127089},{"Advertiser":"EnglishFirst.RU\r","Count":52803},{"Advertiser":"eToro\r","Count":14125},{"Advertiser":"EuropeAssistance\r","Count":179409},{"Advertiser":"EvolutionSlimming\r","Count":306029},{"Advertiser":"Fineartprint\r","Count":217740},{"Advertiser":"Fonic DE\r","Count":42782},{"Advertiser":"FootWayFI\r","Count":13644},{"Advertiser":"FootWayNO\r","Count":1596},{"Advertiser":"FootWaySE\r","Count":16826},{"Advertiser":"FrenchConnection UK\r","Count":66946},{"Advertiser":"FurnitureINFashion\r","Count":478035},{"Advertiser":"Futuroscope\r","Count":34016},{"Advertiser":"Gebuhrenfrei\r","Count":423962},{"Advertiser":"Gemo FR\r","Count":312250},{"Advertiser":"Grosbill-FR\r","Count":8566},{"Advertiser":"GroupdealNL\r","Count":111448},{"Advertiser":"hairgalleryIT\r","Count":132284},{"Advertiser":"Homes Direct 365 UK\r","Count":26941},{"Advertiser":"HP de\r","Count":25806},{"Advertiser":"HSE24DE\r","Count":571019}];
var dates = ['x'];
var impressions = ['impressions'];
var clicks = ['clicks'];
var clicksHeight=0;
var impressionsHeight=0;
var clicksChecked=true;
var impressionsChecked=true;

$(document).ready(function() {
	
	$.get("getkpidata?func=getDates", function(dateData){
        for(var i = 0; i < dateData.length; i++){
        	$('#fromDate').append($("<option></option>").attr("value",dateData[i].date).text(dateData[i].date)); 
        	$('#toDate').append($("<option></option>").attr("value",dateData[i].date).text(dateData[i].date));
        }
    }).done(function(){
    	$.get("getkpidata?func=getExchanges", function(exchangeData){
            for(var i = 0; i < exchangeData.length; i++) 
            	$('#exch').append($("<option></option>").attr("value",exchangeData[i].id).text(exchangeData[i].name)); 
        });
    });
});


$("#exch").change(function(){
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var exchId = this.value;

	if(fromDate === 'select' || toDate === 'select'){
		showTopMessage("Fill the date please !");
		$("#exch").val($("#exch option:first").val());
		if(fromDate === 'select')
			$('#fromDate').focus();
		else if(toDate === 'select')
			$('#toDate').focus();
		return;
	}
	
	$("#adv").html('<option>select</option>');
	$("#camp").html('<option>select</option>');
	$("#group").html('<option>select</option>');
	$.get("getkpidata?func=getAdvertisers&exchId="+exchId+"&fromDate="+fromDate+"&toDate="+toDate, function(data){
		for(var i = 0; i < data.length; i++) 
			$('#adv').append($("<option></option>").attr("value",data[i].id).text(data[i].name)); 
	});
});

$("#adv").change(function(){
	var advId = this.value;
	$("#camp").html('<option>select</option>');
	$("#group").html('<option>select</option>');
	$.get("getkpidata?func=getCampaigns&advId="+advId,function(data){	
		for(var i = 0; i < data.length; i++) 
			$('#camp').append($("<option></option>").attr("value",data[i].id).text(data[i].name));
	});
});

$("#camp").change(function(){
	var campId = this.value;
	$("#group").html('<option>select</option>');
	$.get("getkpidata?func=getGroups&campId="+campId,function(data){	
		for(var i = 0; i < data.length; i++) 
			$('#group').append($("<option></option>").attr("value",data[i].id).text(data[i].name));
	});
});

$("#group").change(function(){
	var groupId = this.value;
	$("#creative").html('<option>select</option>');
	$.get("getkpidata?func=getCreatives&groupId="+groupId,function(data){	
		for(var i = 0; i < data.length; i++) 
			$('#creative').append($("<option></option>").attr("value",data[i].id).text(data[i].name));
	});
});


$("#creative").change(function(){
	var creativeId = this.value; 
	$.get("getkpidata?func=getGraphJson&creativeId="+creativeId,function(data){
		
		var date1;
		for(var i=0; i<data.length; i++){
                   
                     date1 =Date.parse(data[i].date+" 12:30");
                    
			dates.push(date1);
			impressions.push(data[i].impressions);
			clicks.push(data[i].clicks);
                        
                        if((data[i].clicks)>clicksHeight){
                            clicksHeight=data[i].clicks;
                        }
                        if((data[i].impressions)>impressionsHeight){
                            impressionsHeight=data[i].impressions;
                        }

		}
		
		console.log(dates);
		console.log(impressions);
		console.log(clicks);
		
		////SHOW KPI GRAPH
		$('form').toggle("slow");
		$('#btnHideSelects').show();
		$('#chart').empty();
                drawKpiGraph(dates,impressions,clicks,clicksHeight,impressionsHeight);
                 
		
                
	});
});

function draw(){
    $('#chart').empty();
    if ((clicksChecked===true)&&(impressionsChecked===true)){
    drawKpiGraph(dates,impressions,clicks,clicksHeight,impressionsHeight);    
    }
    else if(clicksChecked===true){
    drawKpiClicksGraph(dates,clicks,clicksHeight);   
    }
    else if(impressionsChecked===true){
    drawKpiImpressionsGraph(dates,impressions,impressionsHeight);  
    }
        
}


$('#clicks').change(function() {
        if($(this).is(":checked")) {
         clicksChecked=true;  
        }
        else{
           
           clicksChecked=false; 
        }
    draw();           
});
$('#impressions').change(function() {
        if($(this).is(":checked")) {
          impressionsChecked=true;  
        }
        else{
            impressionsChecked=false;
        }
     draw();            
});