;(function($){
	
	var CLASSTIME_URL = "/examScheduler/app/service/secured/classtime";
	
	$( document ).ready(function() {
		initializeTable();
	});
	
	function initializeTable(){
		$("#lessonsTime").jqGrid({
			url:CLASSTIME_URL,
			datatype:'json',
			mtype:"POST",
			loadBeforeSend: function(xhr)
			{
			   xhr.setRequestHeader("Content-Type", "application/json");
			   return xhr;
			},
			colNames:["ID","Номер лекции", "Время начала", "Время окончания"],
			colModel:[
			{name:'id', index:'id',width:60, editable:false, editoptions:{readonly:true, size:10},hidden:true},
			{name:'lessonNumber', index:'lessonNumber', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
			{name:'timeStart', index:'timeStart', width:200, editable:true, editrules:{required:true}, editoptions:{size:10}},
			{name:'timeEnd', index:'timeEnd', width:200, editable:true, editrules:{required:true}, editoptions:{size:10}}
			],
			rowNum:20,
			height:500,
			rownumbers:true,
			pager:'#lessonsTimePager',
			sortname:'id',
			viewrecords:true,
			sortorder:"asc",
			caption:"Список времени занятий",
			emptyrecords:"Пустое поле",
			loadonce:false,
			loadcomplete:function(){},
			jsonReader:{
				root:"lessonsTimeList",
				page:"page",
				repeatitems:false,
				cell:"cell",
				id:"id"
			}
		});
		
		$("#lessonsTime").jqGrid('navGrid','#lessonsTimePager',
				{edit:true, add:true, del:true, search:false},
				{},
				{},
				{},
				{
					sopt:['eq','ne','lt','gt','cn','bw','ew'],
					closeOnEscape:true,
					multipleSearch:true,
					closeAfterSearch:true
				}
			);
	}
		
})(jQuery);