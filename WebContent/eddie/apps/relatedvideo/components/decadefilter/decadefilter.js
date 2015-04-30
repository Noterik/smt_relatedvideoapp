var Decadefilter = function(options){
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	console.log("decadeFilter");
	$( "#filterdecade" ).change(function() {
		var value = $(this).val();
		eddie.putLou('', 'decadefilter('+ value +')');
		console.log(value);
	});
	

	return self;
}

