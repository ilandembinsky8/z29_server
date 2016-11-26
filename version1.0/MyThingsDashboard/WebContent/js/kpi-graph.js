function drawKpiGraph(dates,impressions,clicks){
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
                format: '%Y-%m-%d'
            },
            label: 'Dates'
          },
          y: {
            label: {
              text: 'Impressions',
              position: 'outer-middle'
            }
          },
          y2: {
            show: true,
            label: {
              text: 'Clicks',
              position: 'outer-middle'
            },
            max: 30,
            min:0
          }
        },
        zoom: {
          enabled: true
        },
      });
}