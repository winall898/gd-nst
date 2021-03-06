var disableExport = false;
function getSearchParams(){
	var params = {
		"sProvinceId" : $("#sourceShipperSearchForm #sProvinceId").val(),
		"sCityId" : $("#sourceShipperSearchForm #sCityId").val(),
		"sAreaId" : $("#sourceShipperSearchForm #sAreaId").val(),
		"eProvinceId" : $("#sourceShipperSearchForm #eProvinceId").val(),
		"eCityId" : $("#sourceShipperSearchForm #eCityId").val(),
		"eAreaId" : $("#sourceShipperSearchForm #eAreaId").val(),
		"memberName" : $("#sourceShipperSearchForm #memberName").val(),
		"memberMobile" : $("#sourceShipperSearchForm #memberMobile").val(),
		"goodsType" : $("#sourceShipperSearchForm #goodsType").val(),
		"sendGoodsType" : $("#sourceShipperSearchForm #sendGoodsType").val(),
		"startDate" : $("#sourceShipperSearchForm #startDate").val(),
		"endDate" : $("#sourceShipperSearchForm #endDate").val(),
		"sourceType" : $("#sourceShipperSearchForm #sourceType").val(),
		"clients" : $("#sourceShipperSearchForm #clients").val(),
		"sourceStatus" : $("#sourceShipperSearchForm #sourceStatus").val(),
		"sourceGoodsType" : $("#sourceShipperSearchForm #sourceGoodsType").val(),
		"id" : $("#sourceShipperSearchForm #id").val(),
		"logisticMobile" : $("#sourceShipperSearchForm #logisticMobile").val(),
		"isDeleted" : $("#sourceShipperSearchForm #isDeleted").val()
	};
	return params;
}

function initPageListData(){
	loadListData(null);
	$("#sourceShipperdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function initValidateRules(){
	$.extend($.fn.validatebox.defaults.rules, {
		totalWeight:{
			validator: function (value) {
				if(parseFloat(value) == 0){
					return false;
				}
	            return /^\d{1,3}(\.\d{1,2})?$/.test(value);
			},
	        message: '请输入正确的范围，可填范围0.01-999.99 ，不能为0'
		},
		freight:{
			validator: function (value) {
	            return /^\d{1,6}(\.\d{1,2})?$/.test(value);
			},
	        message: '请输入正确的范围，可填范围0-999999'
		}
	});
}

function initAreaTopList(){
	var areaTopList = queryAreaTopList();
	$.each(areaTopList, function(i, item){
		$("#sProvinceId").append("<option value='"+item.areaID+"'>"+item.area+"</option>");   
		$("#eProvinceId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
	}); 
}

function loadListData(queryParams){
	queryParams = !queryParams ? {} : queryParams;
	//数据加载
	$('#sourceShipperdg').datagrid({
		url:CONTEXT+'sourceShipper/queryPage',
		queryParams: queryParams, 
		height: 'auto', 
		nowrap:true,
		toolbar:'#sourceShippertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:false,
		fit:true,
		columns:[[
			{field:'id',title:'货源ID',align:'center'},
			{field:'memberName',title:'发布人姓名',align:'left'},
			{field:'memberMobile',title:'发布人手机',align:'center'},
			{field:'createTime',title:'发布时间',align:'center'},
			{field:'logisticName',title:'分配公司/车主',align:'left',formatter:logisticNameFormatter},
			{field:'logisticMobile',title:'分配公司/车主手机',align:'center',formatter:logisticMobileFormatter},
			{field:'sourceGoodsTypeStr',title:'货源类型',align:'center'},
			{field:'sourceTypeStr',title:'线路类型',align:'center'},
			{field:'sDetailStr',title:'发货地',align:'left',formatter:sDetailFormatter},
			{field:'eDetailStr',title:'目的地',align:'left',formatter:eDetailFormatter},
			{field:'goodsTypeStr',title:'货物类型',align:'center'},
			{field:'totalWeight',title:'货物重量',align:'center', formatter:totalWeightFormatter},
			{field:'sendGoodsTypeStr',title:'发货方式',align:'center'},
			{field:'freight',title:'意向运费',align:'center',formatter:freightFormatter},
			{field:'clientsStr',title:'发布来源',align:'center'},
			{field:'orderBeforeCount',title:'司机接单次数',align:'center'},
			{field:'sourceStatusStr',title:'货源状态',align:'center'},
			{field:'isDeleted',title:'货源删除状态',align:'center',formatter:isDeletedFormatter},
		]],
		onLoadSuccess: function (data) {
			$(this).datagrid("fixRownumber");
		}
	}); 
}

function logisticNameFormatter(val, row, index){
	//"直发", "分配中"状态不显示"分配公司/车主"
	if(row.nstRule == 1 ||row.nstRule==4||row.nstRule==3){
		return "";
	}
	return val;
}

function logisticMobileFormatter(val, row, index){
	//"直发", "分配中"状态不显示"分配公司/车主手机号码"
	if(row.nstRule == 1 ||row.nstRule==4||row.nstRule==3){
		return "";
	}
	return val;
}

function isDeletedFormatter(val, row, index){
	if(val == 1){
		return "已删除";
	} else {
		return "未删除";
	}
}

function freightFormatter(val){
	if(val != undefined && val == "0"){
		return "面议";
	}
	return val;
}

function totalWeightFormatter(val){
	if(val != undefined && val != ""){
		return val + "吨";
	}
	return val;
}

function nstRuleFormatter(val, row) {
	if(val == 1 || val == 4) {
		return "自有";
	} else if(val == 2 || val == 5) {
		return "分配";
	} else if (val == 3) {
		// 6+1平台配送，‘分配中’状态为‘指派’
		if (row.platform == 1) {
			return "指派";
		} else if (row.platform == 0){
			return "分配";
		}
	} else if(val == 6 || val == 7) {
		return "指派"
	}
	return "";
}

function openDetailDialog(id){
	$('#detailDialog').dialog({'title':'货源详情', 'width':900, 'height':'400', 'href':CONTEXT+'sourceShipper/detail/'+id}).dialog('open');
}

function openEditDialog(id){
	$('#editDialog').dialog({'title':'货源详情', 'width':900, 'height':'400', 'href':CONTEXT+'sourceShipper/edit/'+id}).dialog('open');
}

function saveEdit(){
	if(!$("#editSourceShipperForm").form('validate')){
		return false;
	}
	
	var totalWeight = $("#editSourceShipperForm #totalWeight").val();
	var totalSize = $("#editSourceShipperForm #totalSize").val();
	if(totalWeight == "" && totalSize == ""){
		warningMessage("体积与重量必需填写一个！");
		return false;
	}
	
	$.ajax({
        type: "POST",
        url: CONTEXT + "sourceShipper/saveEdit",
        data:$('#editSourceShipperForm').serialize(),
        dataType: "json",
        success: function(data){
        	if (data.code == 10000) {
    			slideMessage("操作成功！");
    			$('#editDialog').dialog('close');
    			$("#sourceShipperdg").datagrid('reload');
    		}else{
    			warningMessage(data.result);
    			return;
    		}
        },
		error:function(){
			warningMessage("服务器出错");
		}
	});
}

function batchDelete(){
	var row = $('#sourceShipperdg').datagrid("getSelections");
	if($(row).length < 1 ) {
	   warningMessage("请选择要删除的数据！");
	   return;
	}
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
			var ids = getSelections("id");
			$.ajax({
		        type: "POST",
		        url: CONTEXT + "sourceShipper/delete",
		        data:{"ids": ids},
		        dataType: "json",
		        success: function(data){
		        	if (data.code == 10000) {
		    			slideMessage("操作成功！");
		    			$("#sourceShipperdg").datagrid('reload');
		    		}else{
		    			warningMessage(data.result);
		    			return;
		    		}
		        },
		        error : function(){
					warningMessage("服务器出错");
				}
			});
		}
	});
}

function reload(){
	disableExport = false;
	$('#sourceShipperdg').datagrid("load", getSearchParams());
}

function exportData(){
	if (disableExport){
		slideMessage("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");
		return;
	}
	disableExport = true;

	var params = getSearchParams();
	var paramList = "";
	for(key in params){ 
		paramList +=  key+"="+ params[key]+"&"
    } 
	$.ajax({
		url: CONTEXT+'sourceShipper/exportCheck',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.code == 10000){
				slideMessage("数据正在导出中, 请耐心等待...");
				$.download(CONTEXT+'sourceShipper/export',paramList,'post');
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

$(function(){
	initPageListData();
	initValidateRules();
	initAreaTopList();
	fixRownumber();
	//查询
	$('#btn-search').click(function(){
		if(!$("#sourceShipperSearchForm").form('validate')){
			return false;
		}
		loadListData(getSearchParams());
	});
	
	//重置
	$('#btn-reset').click(function(){
		$("#sourceShipperSearchForm")[0].reset();
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