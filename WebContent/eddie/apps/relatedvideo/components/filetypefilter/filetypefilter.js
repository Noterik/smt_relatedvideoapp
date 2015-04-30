var Filetypefilter = function(options){
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	
	function callSearch() {
	
		$( "#filtertype" ).change(function() {
			var value = $(this).val();
			eddie.putLou('', 'filetypefilter('+ value +')');
			console.log(value);
		});
	
	};

	
	callSearch();
	return self;
}