var Platformfilter = function(options){
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	console.log("platform");
	$( "#filterplatform" ).change(function() {
		var value = $(this).val();
		eddie.putLou('', 'platformfilter('+ value +')');
		console.log(value);
	});
	

	return self;
}