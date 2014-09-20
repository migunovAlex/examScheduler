;(function($){
	
	var GET_ACTIVIST_LIST_URL = "/examScheduler/app/service/secured/classtime/all";
	
	$( document ).ready(function() {
		initializeTable();
	});
	
	function initializeTable(){
		$("#lessonsTime").jqGrid({
			url:GET_ACTIVIST_LIST_URL,
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
	}
		
		$("#lessonsTime").jqGrid('navGrid','#lessonsTimePager',
			{edit:false, add:false, del:false, search:true},
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
		
		$("#lessonsTime").navButtonAdd('#lessonsTimePager',
			{
				caption:"Добавить",
				buttonicon:"ui-icon-plus",
				onClickButton: addRow,
				position:"last",
				title:"",
				cursor:"pointer"
			}
		);
		
		$("#lessonsTime").navButtonAdd('#lessonsTimePager',
			{
				caption:"Редактировать",
				buttonicon:"ui-icon-pencil",
				onClickButton:editRow,
				position:"last",
				title:"",
				cursor:"pointer"
			}
		);
		
		$("#lessonsTime").navButtonAdd('#lessonsTimePager',
			{
				caption:"Удалить",
				buttonicon:"ui-icon-trash",
				onClickButton:deleteRow,
				position:"last",
				title:"",
				cursor:"pointer"
			}
		);
		
		$("#btnFilter").click(function(){
			$("#lessonsTime").jqGrid('searchGrid', {multipleSearch: false, sopt:['eq']});
		});
		
		function addRow(){
			alert('Add Row');
		}
	
		function editRow(){
			alert('Edit Row');
		}
		
		function deleteRow(){
			alert('Delete Row');
		}


	
})(jQuery);