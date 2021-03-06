var disableExport = false;
$(document).ready(function(){
	initAreaTopList();
	loadListData(null);
	//分页加载
	$("#memberLinedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
	//查询
	$('#btn-search').click(function(){
		loadListData(getSearchParams());
	});
	
	//重置
	$('#btn-reset').click(function(){
		$("#SearchForm")[0].reset();
	});
	
	$("#sProvinceId").change(function(){
		$("#sCityId").html("<option value=''>请选择城市</option>");
		$("#sAreaId").html("<option value=''>请选择区/县</option>");
		var parentId = $(this).val();
		var areaList = queryAreaChildList(parentId);
		$.each(areaList, function(i, item){
			$("#sCityId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
		});
	});
	
	$("#eProvinceId").change(function(){
		$("#eCityId").html("<option value=''>请选择城市</option>");
		$("#eAreaId").html("<option value=''>请选择区/县</option>")
		var parentId = $(this).val();
		var areaList = queryAreaChildList(parentId);
		$.each(areaList, function(i, item){
			$("#eCityId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
		});
	});
	
	$("#sCityId").change(function(){
		$("#sAreaId").html("<option value=''>请选择区/县</option>");
		var parentId = $(this).val();
		var areaList = queryAreaChildList(parentId);
		$.each(areaList, function(i, item){
			$("#sAreaId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
		});
	});
	
	$("#eCityId").change(function(){
		$("#eAreaId").html("<option value=''>请选择区/县</option>");
		var parentId = $(this).val();
		var areaList = queryAreaChildList(parentId);
		$.each(areaList, function(i, item){
			$("#eAreaId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
		});
	});
});

function getSearchParams(){
	var params = {
			"sProvinceId" : $("#SearchForm #sProvinceId").val(),
			"sCityId" : $("#SearchForm #sCityId").val(),
			"sAreaId" : $("#SearchForm #sAreaId").val(),
			"eProvinceId" : $("#SearchForm #eProvinceId").val(),
			"eCityId" : $("#SearchForm #eCityId").val(),
			"eAreaId" : $("#SearchForm #eAreaId").val(),
			"lineType" : $("#SearchForm #lineType").val(),
			"publisher" : $("#SearchForm #publisher").val(),
			"phone" : $("#SearchForm #phone").val(),
			"isDeleted" : $("#SearchForm #isDeleted").val()	
		};
	return params;
}
function loadListData(queryParams){
	queryParams = !queryParams ? {} : queryParams;
	//数据加载
	$('#memberLinedg').datagrid({
		url:CONTEXT+'memberLine/queryPage',
		queryParams: queryParams,
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#memberLinetb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'id',title:'',width:50,checkbox:true},
			{field:'sDetailStr',title:'出发地',width:100,align:'left',formatter: sDetailFmt},
			{field:'eDetailStr',title:'目的地',width:100,align:'left',formatter: eDetailFmt},
			{field:'sourceTypeStr',title:'线路类型',width:100,align:'center'},
			{field:'publisher',title:'用户姓名',width:100,align:'left'},
			{field:'phone',title:'用户手机',width:100,align:'center'},
			{field:'createTimeStr',title:'发布时间',width:100,align:'center'},
			{field:'isDeletedStr',title:'线路状态',width:100,align:'center'}
		]]
	}); 
}

function showDetail(id){
	$('#detailDialog').dialog({'title':'常跑线路详情','href':CONTEXT+'memberLine/showDetail/'+id,'width': 600,'height': 300}).dialog('open');
}

function initAreaTopList(){
	var areaTopList = queryAreaTopList();
	$.each(areaTopList, function(i, item){
		$("#sProvinceId").append("<option value='"+item.areaID+"'>"+item.area+"</option>");   
		$("#eProvinceId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
	}); 
}

function queryAreaTopList(){
	var result;
	$.ajax({
		async: false, 
		url: CONTEXT+'area/queryTopList',
		type:'post',
		dataType:"json",
		success : function(data){
			result = data.result;
		}
	});
	return result;
}

function queryAreaChildList(parentId){
	var result;
	$.ajax({
		async: false, 
		url: CONTEXT+'area/queryChildList/'+parentId,
		type:'post',
		dataType:"json",
		success : function(data){
			result = data.result;
		}
	});
	return result;
}

function exportData(){
	if (disableExport){
		slideMessage("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");
		return;
	}
	disableExport = true;
	var params = getSearchParams();
	var paramList = 'sProvinceId='+params.sProvinceId+"&sCityId="+params.sCityId+ 
		"&sAreaId="+params.sAreaId+"&eProvinceId="+params.eProvinceId+
		"&eCityId="+params.eCityId+"&eAreaId="+params.eAreaId+
		"&publisher="+params.publisher+"&phone="+params.phone+
		"&lineType="+params.lineType+"&isDeleted="+params.isDeleted;
	$.ajax({
		url: CONTEXT+'memberLine/exportCheck',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.code == 10000){
				slideMessage("数据正在导出中, 请耐心等待...");
				//启动下载
				$.download(CONTEXT+'memberLine/export',paramList,'post');
			}else{
				warningMessage(data.msg);
			}
		},
		error : function(){
			warningMessage("服务器出错");
		}
	});
	
	// 10秒后导出按钮重新可用
	setInterval(function(){
		disableExport = false;
	}, 10000);
}
