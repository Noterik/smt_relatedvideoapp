var Searchkeys = function(options){
	
	console.log("searchfilters");
	Component.apply(this, arguments);
	
	var self = this;
	var settings = {};
	
	$.extend(settings, options);

		//eddie.putLou('', 'decadefilter('+ value +')');
	return self;
}
	Searchkeys.prototype = Object.create(Component.prototype);
	Searchkeys.prototype.initEvents= function(){
		console.log($('#testSave'));
		console.log($('.dismiss'));
		$('.dismiss').click(function(){
			
			var val = $(this).attr("id");
			var hashtagVal = '#' + val;
			$(hashtagVal).parent().parent().parent().hide();
			eddie.putLou('', 'removefilters('+ val +')');
			
			//$('.element').parents('.container1')
			//$('.dismiss').click(function(){
			    //$("id").hide();
		
			
			console.log(val);
			console.log("EP_CHICHO TI KOKI");
		});
	}