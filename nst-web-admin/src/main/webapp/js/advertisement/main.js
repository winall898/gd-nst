function loadPageData(queryParams){
	queryParams = !queryParams ? {} : queryParams;
	//数据加载
	$('#advertisementdg').datagrid({
		url:CONTEXT+'advertisement/queryPage',
		queryParams: queryParams,
		height: 'auto', 
		nowrap:true,
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'id',title:'',width:50,checkbox:true},
			{field:'name',title:'广告名称',width:100,align:'center'},
			{field:'channelStr',title:'广告渠道',width:100,align:'center'},
			{field:'cityId',hidden:'true'},
			{field:'cityName',title:'省市',width:100,align:'center',formatter:cityNameFormatter},
			{field:'sort',title:'排序',width:100,align:'center'},
			{field:'startTime',title:'开始时间',width:100,align:'center'},
			{field:'endTime',title:'结束时间',width:100,align:'center'},
			{field:'status',hidden:'true'},
			{field:'statusStr',title:'状态',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'},
			{field:'opt',title:'操作',width:150,align:'center', formatter:optFormatter}
		]]
	}); 
}

function cityNameFormatter(val, row, index){
	if(row.cityId == 0){
		return "默认";
	}
	return val;
}

function initPage(){
	loadPageData(null);
	//分页加载
	$("#advertisementdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function getSearchParams(){
	var params = {
		provinceId:$("#adSearchForm #provinceId").val(),
		cityId:$("#adSearchForm #cityId").val(),
		name:$("#adSearchForm #name").val(),
		startDate:$("#adSearchForm #startDate").val(),
		endDate:$("#adSearchForm #endDate").val(),
		channel:$("#adSearchForm #channel").val(),
		status: $("#adSearchForm #status").val()
	};
	return params;
}

// 新增保存
function saveAdd(){
	if(!$("#addAdvertisementForm").form('validate')){
		return false;
	}
	if($("#addAdvertisementForm #J_Urls1").val() == ''){
		warningMessage("请上传文件！");
		return false;
	}
	
	$("#addAdDialog #icon-save-btn").linkbutton('disable');
	$.ajax({
        type: "POST",
        url: CONTEXT + "advertisement/saveAdd",
        data:$('#addAdvertisementForm').serialize(),
        dataType: "json",
        success: function(data){
        	if (data.code == 10000) {
    			slideMessage("操作成功！");
    			$('#addAdDialog').dialog('close');
    			$("#advertisementdg").datagrid('reload');
    		}else{
    			warningMessage(data.msg);
    		}
        	$("#addAdDialog #icon-save-btn").linkbutton('enable');
        },
        error: function(){
        	warningMessage("服务器出错");
        	$("#addAdDialog #icon-save-btn").linkbutton('enable');
        }
	});
}

// 修改保存
function saveEdit(){
	if(!$("#editAdForm").form('validate')){
		return false;
	}
	if($("#editAdForm #edit_J_Urls1").val() == ''){
		warningMessage("请上传文件！");
		return false;
	}
	$.ajax({
        type: "POST",
        url: CONTEXT + "advertisement/saveEdit",
        data:$('#editAdForm').serialize(),
        dataType: "json",
        success: function(data){
        	if (data.code == 10000) {
    			slideMessage("操作成功！");
    			$('#editDialog').dialog('close');
    			$("#advertisementdg").datagrid('reload');
    		}else{
    			warningMessage(data.msg);
    			return;
    		}
        },
        error: function(){
        	warningMessage("服务器出错");
        }
	});
}

// 修改并且上架操作保存
function saveEditAndPutOn(){
	if(!$("#editAdForm").form('validate')){
		return false;
	}
	if($("#editAdForm #J_Urls1").val() == ''){
		warningMessage("请上传文件！");
		return false;
	}
	$.ajax({
        type: "POST",
        url: CONTEXT + "advertisement/saveEditAndPutOn",
        data:$('#editAdForm').serialize(),
        dataType: "json",
        success: function(data){
        	if (data.code == 10000) {
    			slideMessage("操作成功！");
    			$('#editDialog').dialog('close');
    			$("#advertisementdg").datagrid('reload');
    		}else{
    			warningMessage(data.msg);
    			return;
    		}
        },
        error: function(){
        	warningMessage("服务器出错");
        }
	});
}

function editClose(){
	$("#advertisementdg").datagrid('reload');
	$('#editDialog').dialog('close')
}

// 上下架操作
function updateStatus(id, status){
	jQuery.messager.confirm('提示', '您确定要修改所选数据吗?', function(r){
		if (r){
			$.ajax({
		        type: "POST",
		        url: CONTEXT + "advertisement/updateStatus",
		        data:{"id": id, "status" : status},
		        dataType: "json",
		        success: function(data){
		        	if (data.code == 10000) {
		    			slideMessage("操作成功！");
		    			$("#advertisementdg").datagrid("load", getSearchParams());
		    		}else{
		    			warningMessage(data.msg);
		    			return;
		    		}
		        },
		        error: function(){
		        	warningMessage("服务器出错");
		        }
			});
		}
	});
}

// 查看弹框
function detail(id){
	$('#detailDialog').dialog({'title':'查看', 'width':600, 'height':400, 'href':CONTEXT+"advertisement/detail/"+id}).dialog('open');
}

// 编辑弹框
function editObj(id){
	$('#editDialog').dialog({'title':'编辑', 'width':600, 'height':400, 'href':CONTEXT+"advertisement/edit/"+id}).dialog('open');
}

//同步默认广告保存
function saveSync() {
	if (!$("#syncForm #syncChannelSelect").val()) {
		warningMessage("请选择同步的渠道！");
		return;
	}
	if ($("#syncForm input[name='adId']:checked").length <= 0) {
		warningMessage("请选择同步的广告！");
		return;
	}
	$.ajax({
        type: "POST",
        url: CONTEXT + "advertisement/saveSync",
        dataType: "json",
        data: $("#syncForm").serialize(),
        success: function(data){
        	if (data.code == 10000) {
        		slideMessage("操作成功！");
        		$('#syncDialog').dialog('close');
    			$("#advertisementdg").datagrid("load", getSearchParams());
        	}
        }
	});
}

$(document).ready(function(){
	initPage();
	
	// 初始化省份城市树
	$('#treeMenu').tree({
	   	url:CONTEXT+'advertisement/queryTreeNodes',
       	animate:true,
       	formatter:function(node){
   			if(node.attributes != undefined && node.attributes.hasAd){
   				return "<font color='blue'>"+node.text+"</font>";
   			}
   			return node.text;
   	   	},
   	   	onBeforeExpand:function(node,param){
   	   		$('#treeMenu').tree('options').url = CONTEXT+'advertisement/queryTreeNodes/' + node.id;
   		}, 
   	   	onClick:function(node){
   	   		if(node.attributes != undefined){
   	   			if(node.attributes.level == 1){
   	   				$("#adSearchForm #provinceId").val(node.id);
   	   				$("#adSearchForm #cityId").val("");
   	   			}
   	   			else if(node.attributes.level == 2){
   	   				var parentId = $('#treeMenu').tree("getParent", node.target).id;
   	   				$("#adSearchForm #provinceId").val(parentId);
	   				$("#adSearchForm #cityId").val(node.id);
   	   			}else{
   	   				$("#adSearchForm #provinceId").val("");
   	   				$("#adSearchForm #cityId").val("");
   	   			}
   	   		}else{
   	   			$("#adSearchForm #provinceId").val("");
  				$("#adSearchForm #cityId").val("");
   	   		}
   	   		loadPageData(getSearchParams());
   	   	}
   	}); 
	
	// 新增
   	$("#icon-add").click(function(){
		var node = $('#treeMenu').tree("getSelected");
	   	if(!node){
			warningMessage("请选择一个城市！");
		   	return;
	   	}
	   	if(node.id == ''){
	   		warningMessage("请选择一个城市！");
			return;
	   	}
	   	var isLeaf = $('#treeMenu').tree('isLeaf', node.target);
	   	//只能选择叶子节点
	   	if(!isLeaf){
			warningMessage("请选择一个城市！");
		  return;
	   	}
	   	var url = CONTEXT+'advertisement/add';
	   	if(node.attributes != undefined){
	   		if(node.attributes.level == 1){
	   			url += "?provinceId="+node.id+"&cityId="+node.id;
	   		}
	   		else if(node.attributes.level == 2){
	   			var provinceId = $('#treeMenu').tree("getParent", node.target).id;
	   			url += "?provinceId="+provinceId+"&cityId="+node.id;
	   		}
	   	}
		$('#addAdDialog').dialog({'title':'添加广告', 'width':600, 'height':400, 'href':url}).dialog('open');
   	});

   	// 查询
   	$("#icon-search").click(function(){
   		loadPageData(getSearchParams());
   	});
   	
   	// 重置
	$('#icon-reset').click(function(){
		$("#adSearchForm")[0].reset();
	});
	
	
	$("#icon-refresh").click(function(){
		$("#advertisementdg").datagrid("load", getSearchParams());
   	});
	
	// 同步默认广告
	$("#icon-sync").click(function(){
		var node = $('#treeMenu').tree("getSelected");
	   	if(!node){
			warningMessage("请选择城市！");
		   	return;
	   	}
	   	var cityId = node.id;
	   	if(cityId <= 0){
	   		warningMessage("请选择城市！");
			return;
	   	}
	   	var isLeaf = $('#treeMenu').tree('isLeaf', node.target);
	   	//只能选择叶子节点
	   	if(!isLeaf){
			warningMessage("请选择城市！");
		    return;
	   	}
	   	
		$('#syncDialog').dialog({'title':'同步默认广告', 'width':600, 'height':400, 'href':CONTEXT+"advertisement/sync/"+cityId}).dialog('open');
	});
});