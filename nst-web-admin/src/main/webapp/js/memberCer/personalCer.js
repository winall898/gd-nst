var disableExport = false;
$(document).ready(function(){
	fixRownumber();
	loadData();
	//分页加载
	$("#demodg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
    $("#btn-search").click(function(){ 
    	loadData();
	});
    
    $("#btn-reset").click(function(){
    	$("#demoSearchForm")[0].reset();
    })
	
	$("#auditData").click(function(){ 
		var row = $('#demodg').datagrid("getSelections");
		if($(row).length < 1 ) {
		   warningMessage("请选择要审核的数据！");
		}else{
		   var cerStatus = $('#demodg').datagrid('getSelected').cerStatus;
    	   var id = $('#demodg').datagrid('getSelected').id;	    	  
    	   if(id){
	    	   auditDialog(id);
    	   }else{
    		   warningMessage("未找到审核ID！");
    	   }
		}
	});
	
	$("#exportData").click(function(){
		var params = {
				"userType" : 1,
				"realName" : $("#realName").val(),
				"mobile" : $("#mobile").val(),
				"cerStatus" : $("#cerStatus").val(),
				"certificFrom" : $("#certificFrom").val(),
				"applyForBeginTime" : $("#applyForBeginTime").val(),
				"applyForEndTime" : $("#applyForEndTime").val(),
				"auditBeginTime" : $("#auditBeginTime").val(),
				"auditEndTime" : $("#auditEndTime").val(),
			};

			var paramList = 'userType='+params.userType+"&realName="+params.realName+ 
				"&mobile="+params.mobile+"&cerStatus="+params.cerStatus+
				"&certificFrom="+params.certificFrom+
				"&applyForBeginTime="+params.applyForBeginTime+"&applyForEndTime="+params.applyForEndTime+
				"&auditBeginTime="+params.auditBeginTime+"&auditEndTime="+params.auditEndTime;
			if(disableExport){
				slideMessage("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");								
				setInterval(function(){
					disableExport = false;
				}, 10000);
	        }else{
	        	$.ajax({
					url: CONTEXT+'memberCer/personal/exportCheck',
					data : params,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.code == 10000){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true;
								//启动下载
								$.download(CONTEXT+'memberCer/personal/exportData',paramList,'post');
							}							
						}else{
							warningMessage(data.result);
						}
					},
					error : function(data){
						warningMessage(data.msg);
					}
				})
	        }			
	})
})

function auditDialog(id){
	$('#auditDialog').dialog({'title':'个人认证审核', 'width':899, 'height':484, 'href':CONTEXT+'memberCer/personal/audit/'+id}).dialog('open');
}

function showDialog(id){	
	$('#auditDialog').dialog({'title':'个人认证审核', 'width':899, 'height':418, 'href':CONTEXT+'memberCer/personal/audit/'+id + '?isShow=0'}).dialog('open');
}

function loadData(){
	var queryParams = {
			"realName" : $("#realName").val(),
			"applyForBeginTime" : $("#applyForBeginTime").val(),
			"applyForEndTime" : $("#applyForEndTime").val(),
			"cerStatus" : $("#cerStatus").val(),
			"mobile" : $("#mobile").val(),
			"certificFrom" : $("#certificFrom").val(),
			"auditBeginTime" : $("#auditBeginTime").val(),
			"auditEndTime" : $("#auditEndTime").val()
		};
	//数据加载
	$('#demodg').datagrid({
		url:CONTEXT+'memberCer/personal/query',
		//width: 1000,  
		queryParams: queryParams,
		height: 'auto', 
		nowrap:true,
		toolbar:'#demotb',
		pageSize:50,
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
            {field:'id',title:'',width:50,align:'center',hidden:true},
			{field:'account',title:'账号',width:100,align:'left',formatter:accountFormatter},
			{field:'realName',title:'姓名',width:100,align:'left'},
			{field:'mobile',title:'手机',width:100,align:'center'},
			{field:'certificFrom',title:'认证来源',width:100,align:'center',formatter:function(value,row,index){
				if(value=='1'){
					return '农速通-货主';
				}else if(value=='2'){
					return '农速通-车主';
				}else if(value=='3'){
					return '农速通-物流公司';
				}
			}},
			{field:'applyForTime',title:'认证申请时间',width:100,align:'center'},
			{field:'cerStatus',title:'个人认证状态',width:100,align:'center',formatter:function(value,row,index){
				if(value=='0'){
					return '认证中';
				}else if(value=='1'){
					return '已认证';
				}else if(value=='2'){
					return '已驳回';
				}
			}},
			{field:'auditor',title:'审核人',width:100,align:'center'},
			{field:'auditTime',title:'审核时间',width:100,align:'center'}
		]],
		  onLoadSuccess: function (data) {
			  $(this).datagrid("fixRownumber");
		  }
	})
}

function save(){
	if(!$("#auditForm input[name='cerStatus']").val()){
		warningMessage("请选择审核结果！");
		return false;
	}else if(!$("#hidMemberId").val()){
		warningMessage("会员ID不能为空！");
		return false;
	}else if(!$("#hidUserType").val()){
		warningMessage("用户认证类型不能为空！");
		return false;
	}
	if($("#noPassRadio").attr("checked")){
		if($.trim($("#auditOpinion").val()).length==0){
			warningMessage("请输入审核意见！");
			return false;
		}
	} 
	
	if($.trim($("#auditOpinion").val()).length>200){
		warningMessage("审核意见字数不能超过200个！");
		return false;
	}
	
	$.ajax({
        type: "POST",
        url: CONTEXT + "memberCer/personal/save",
        data:$('#auditForm').serialize(),
        dataType: "json",
        success: function(data){
        	if (data.number > 0) {
    			slideMessage("操作成功！");
    			$('#auditDialog').dialog('close');
    			$("#demodg").datagrid('reload');
    		}else{
    			warningMessage("操作失败");
    			return false;
    		}
        }
	});
		
}