$(function() {
	var strategy = [];

	var actionVerbs = {
		buy: [
			'buy',
			'get',
			'purchase',
			'long'
		],
		sell: [
			'sell',
			'throw',
			'dump',
			'short'
		]
	};

	var worthless = [
		'place',
		'do'
	];

	var securities = {
		bitcoin: [
			'btc',
			'bitcoin'
		],
		aapl: [
			'apple',
			'aapl'
		]
	};

	var dictionary = [actionVerbs, worthless, securities];

	$('#command-input').keypress(function(e){
	    if(e.which !== 13) {
	    	return;
	    }

		var inp = $(this).val();

		$("#rules").append($("<li>").text(inp));
		strategy.push(inp);

		$(this).val('');
	});

	$('#goButton').click(function() {
		$('#preQuery .command').addClass('animated fadeOutUpBig');
		$('#preQuery .signals').addClass('animated fadeOutDownBig');
		$('#loadingSpinner').removeClass('hide').addClass('animated fadeIn');
		(new Spinner({
			color: '#ccc',
			radius: 60,
			length: 40,
			width: 20
		})).spin(document.getElementById('loadingSpinner'));

		$.post('http://localhost:4567/strategy_test', {
			initial: 10000,
			stock: 'ACN US Equity',
			strategy: strategy.join('; ')
		}, function(data) {
			if (data === 'omg') {
				alert('Invalid input.');
				return;
			}
			$('#preQuery').addClass('animated fadeOutLeftBig');
			$('#postQuery').removeClass('hide').addClass('animated fadeInRightBig');
			
			window.jdata = JSON.parse(data);

			$('#btResults').highcharts('StockChart', {
				rangeSelector: {
					selected: 100000
				},
				title: {
					text: 'AAPL Stock Price'
				},
				series: [{
					name: 'AAPL',
					data: _.map(jdata.dailyData, function(item) {
						return [new Date(item.date), item.value];
					}),
					tooltip: {
						valueDecimals: 2
					}
				}]
			})
		});
	});
});