function drawKpiGraph(dates,impressions,clicks,clicksHeight,impressionsHeight){
        if (clicksHeight<30){
            clicksHeight=30;
        }
	var chart = c3.generate({
        data: {
          x: 'x',
        columns: [
            dates,
            impressions,
            clicks
        ],
        axes: {
        	impressions: 'y',
                clicks: 'y2'
        }
        },
        axis: {
          x: {
          	type: 'timeseries',
                tick: {
                    format: '%Y-%m-%d %H'
                },
            label: 'Dates'
          },
          y: {
            label: {
              text: 'Impressions',
              position: 'outer-middle'
            },
            max: impressionsHeight,
            min:0
          },
          y2: {
            show: true,
            label: {
              text: 'Clicks',
              position: 'outer-middle'
            },
            max: clicksHeight,
            min:0
          }
        },
        
        zoom: {
          enabled: true
        },
        
      });
};

function drawKpiClicksGraph(dates,clicks,clicksHeight){
        if (clicksHeight<30){
            clicksHeight=30;
        }
	var chart = c3.generate({
        data: {
          x: 'x',
        columns: [
            dates,
            
            clicks
        ],
        axes: {
        	
                clicks: 'y'
        }
        },
        axis: {
          x: {
          	type: 'timeseries',
            tick: {
                format: '%Y-%m-%d'
            },
            label: 'Dates'
          },
          
          y: {
            show: true,
            label: {
              text: 'Clicks',
              position: 'outer-middle'
            },
            max: clicksHeight,
            min:0
          }
        },

        zoom: {
          enabled: true
        }
      });
};
function drawKpiImpressionsGraph(dates,impressions,impressionsHeight){

	var chart = c3.generate({
        data: {
          x: 'x',
        columns: [
            dates,
            
            impressions
        ],
        axes: {
        	
                impressions: 'y'
        }
        },
        axis: {
          x: {
          	type: 'timeseries',
            tick: {
                format: '%Y-%m-%d'
            },
            label: 'Dates'
          },
          
          y: {
            show: true,
            label: {
              text: 'impressions',
              position: 'outer-middle'
            },
            max: impressionsHeight,
            min:0
          }
        },
        zoom: {
          enabled: true
        }
      });
};