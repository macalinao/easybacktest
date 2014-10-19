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

		if (!/^(buy|sell)/i.test(inp)) {
			console.log('invalid');
			return;
		}

		$("#rules").append($("<li class='notLaggy animated rubberBand'>").text(inp));
		strategy.push(inp);
		$("#emptyL").addClass('gone');

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

		$.post('/strategy_test', {
			initial: 10000,
			stock: 'SPX Index',
			strategy: strategy.join('; ')
		}, function(data) {
			if (data === 'omg') {
				alert('Invalid input.');
				window.location.reload();
				return;
			}
			$('#preQuery').addClass('animated fadeOutLeftBig');
			$('#postQuery').removeClass('hide').addClass('animated fadeInRightBig');
			
			window.jdata = JSON.parse(data);

			var dataIdx = {};
			var dataMapped = _.map(jdata.dailyData, function(item) {
				var date = new Date(item.date);
				dataIdx[date.toString()] = item;
				return [date, item.value];
			});
			var sp500 = _.map(jdata.benchmark, function(item) {
				var date = new Date(item.date);
				dataIdx[date.toString()] = item;
				return [date, item.value];
			});

			$('#btResults').highcharts('StockChart', {
				rangeSelector: {
					selected: 100000
				},
				title: {
					text: 'Backtesting Results'
				},
				series: [{
					name: 'Algorithm',
					data: dataMapped
				}, {
					name: 'S&P 500',
					data: sp500
				}],
				tooltip: {
					formatter: function() {
						var item = dataIdx[(new Date(this.x)).toString()];
						return Highcharts.dateFormat('%b %d, %Y', this.x) + '<br />'
							+ '<b>Value:</b> $' + this.points[0].y.toFixed(2) + '<br />'
							+ '<b>Value (benchmark):</b> $' + this.points[1].y.toFixed(2) + '<br />'
							+ '<b>% Diff:</b> ' + ((this.points[1].y - this.points[0].y) / this.points[1].y * -100).toFixed(2) + '%<br />'
							+ '<b>Shares:</b> ' + item.shares + '<br />'
							+ '<b>Cash:</b> $' + item.cash.toFixed(2) + '<br />'
							+ '<b>Open:</b> $' + item.open.toFixed(2);
					}
				}
			})
		});
	});
});