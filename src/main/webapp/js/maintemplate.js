(function($){
	
	
	$( document ).ready(function() {
		$.growl.notice({ message: "Messages was initialized!" });
		$("#logout-button").click(function(){
			$.ajax({
				type:"POST",
				url:"/examScheduler/app/service/secured/session/logout",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(data){
					window.location = '/examScheduler/app/pages/login';
				},
				failure: function(errMsg){
					alert(errMsg);
				}
			});
		});
	});
	
})(jQuery);