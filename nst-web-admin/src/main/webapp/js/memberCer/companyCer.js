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
    });
    
	$("#auditData").click(function(){ 
		var row = $('#demodg').datagrid("getSelections");
		if($(row).length < 1 ) {
		   warningMessage("请选择要审核的数据！");
		}else{
		   var cerStatus = $('#demodg').datagrid('getSelected').cerStatus;
	       if(cerStatus==1){
	    	   warningMessage("当前认证申请已审核！");
	       }else{
	    	   var id = $('#demodg').datagrid('getSelected').id;	    	  
	    	   if(id){
		    	   auditDialog(id);
	    	   }else{
	    		   warningMessage("未找到审核ID！");
	    	   }
	       }
		}
	});
	
	$("#exportData").click(function(){
		var params = {
				"userType" : 2,
				"companyName" : $("#companyName").val(),
				"mobile" : $("#mobile").val(),
				"cerStatus" : $("#cerStatus").val(),
				"certificFrom" : $("#certificFrom").val(),
				"applyForBeginTime" : $("#applyForBeginTime").val(),
				"applyForEndTime" : $("#applyForEndTime").val(),
				"auditBeginTime" : $("#auditBeginTime").val(),
				"auditEndTime" : $("#auditEndTime").val(),
			};

			var paramList = 'userType='+params.userType+"&companyName="+params.companyName+ 
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
    				url: CONTEXT+'memberCer/company/exportCheck',
    				data : params,
    				type:'post',
    				success : function(data){
    					//检测通过
    					if (data && data.code == 10000){
    						if (!disableExport){
    							slideMessage("数据正在导出中, 请耐心等待...");
    							disableExport = true;
    							//启动下载
    							$.download(CONTEXT+'memberCer/company/exportData',paramList,'post');
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
	});
});

function auditDialog(id,mobile){
	$('#auditDialog').dialog({'title':'企业认证审核', 'width':899, 'height':484, 'href':CONTEXT+'memberCer/company/audit/'+id}).dialog('open');
}

function showDialog(id){
	$('#auditDialog').dialog({'title':'企业认证审核', 'width':899, 'height':418, 'href':CONTEXT+'memberCer/company/audit/'+id + '?isShow=0'}).dialog('open');
}

function loadData(){
	var queryParams = {
			"companyName" : $("#companyName").val(),
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
		url:CONTEXT+'memberCer/company/query',
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
			{field:'account',title:'账号',width:100,align:'left',formatter : function(i,r,v) {
				var valueText = $("#hid_showInfo").val();
				if(valueText){
					return "<span style='color:blue' onclick='showDialog("+r.id+")' >"+i+"</span>"					
				}else{
					return i;
				}			
			}},
			{field:'companyName',title:'企业名称',width:100,align:'left'},
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
			{field:'cerStatus',title:'企业认证状态',width:100,align:'center',formatter:function(value,row,index){
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
	}); 
}

function save(){
	if(!$("#hidStatus").val()){
		warningMessage("请选择审核结果！");
		return false;
	}else if($.trim($("#auditOpinion").val()).length==0){
		warningMessage("请输入审核意见！");
		return false;
	}else if($.trim($("#auditOpinion").val()).length>50){
		warningMessage("审核意见字数不能超过50个！");
		return false;
	}else if(!$("#hidMemberId").val()){
		warningMessage("会员ID不能为空！");
		return false;
	}else if(!$("#hidUserType").val()){
		warningMessage("用户认证类型不能为空！");
		return false;
	}else{
		$.ajax({
	        type: "POST",
	        url: CONTEXT + "memberCer/company/save",
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
}

jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};