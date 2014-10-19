(function() {

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



	$('#command-input').bind('input', function() {
		var inp = $(this).val();
		console.log(inp);
	});


	$('#command-input').bind("enterKey", function(e){
		var inp = $(this).val();

		$("#rules").append($("<li>").text(inp));


		$(this).val('');
	});

})();