var memberArray;
var ysMemberArray;//原有的物流公司数组
var number;//列表序号全局变量
var deleteIds;//需要删除rule_logistic
var ysIds;//原有已分配物流公司ID
$(document).ready(function(){
	initValidateLimitEdit();
	initEditAreaTopList();
	memberArray = new Array();
	ysMemberArray = new Array();
	ysIds = new Array();
	number = 0;
	deleteIds = new Array();
	var id = $("#ruleId").val();
	$.ajax({ 
        url: CONTEXT + "rule/getMemberInfoByRuleId", 
        type: 'POST', 
        dataType: 'json', 
        data: {id: id}, 
        success: function (obj){ 
        	$.each( obj, function(index,item){
        		number = number + 1;
                memberDisplay(item, index+1);
                memberArray.push(item);
                ysMemberArray.push(item);
                ysIds.push(item.id);
            });  
        } 
    });
	
	if($("#onOffEdit").val() == 0){
		if($("#dayLimtEdit").val() != null && $("#dayLimtEdit").val() != 0){
			$("#dayLimitCheckEdit").attr("checked", "checked");
			$("#dayLimtEdit").removeAttr("disabled");
		}else if($("#dayLimtEdit").val() == 0){
			$("#dayLimtEdit").val('');
		}
		if($("#totalLimtEdit").val() != null && $("#totalLimtEdit").val() != 0){
			$("#totalLimtCheckEdit").attr("checked", "checked");
			$("#totalLimtEdit").removeAttr("disabled");
		}else if($("#totalLimtEdit").val() == 0){
			$("#totalLimtEdit").val('');
		}
	}else{
		if($("#dayLimtEdit").val() == 0){
			$("#dayLimtEdit").val('');
		}
		if($("#totalLimtEdit").val() == 0){
			$("#totalLimtEdit").val('');
		}
	}
	
    $("#dayLimitCheckEdit").change(function() {
        if($("#dayLimitCheckEdit").attr("checked")=="checked"){
        	$("#dayLimtEdit").removeAttr("disabled");
        }else{
        	$("#dayLimtEdit").attr("disabled", "disabled");
        	$("#dayLimtEdit").val('');
        }
    });
	
    $("#totalLimtCheckEdit").change(function() {
        if($("#totalLimtCheckEdit").attr("checked")=="checked"){
        	$("#totalLimtEdit").removeAttr("disabled");
        }else{
        	$("#totalLimtEdit").attr("disabled", "disabled");
        	$("#totalLimtEdit").val('');
        }
    }); 

});

function validayRuleName(name){
	if(name.value!=""||name.value1!=null){
		var url = CONTEXT + "rule/validayRuleName";
		jQuery.post(url, {name:name.value}, function(data) {
			if (data.result>=1) {
				$("#validateNameEdit").html("已存在该规则名称!");
			}else{
				$("#validateNameEdit").html("");
			}
		})
	}
}

function addCompanyEdit(){
	$('#companyDialog').dialog({'title':'物流公司/车主选择', 'width':600, 'height':350, 'href':CONTEXT+'rule/addCompany'}).dialog('open');
}

$("#selectCompany").click(function(){
	//判断是否选中
	var row = $('#companydg').datagrid("getSelections");
    if($(row).length < 1 ) {
		slideMessage("请选择要操作的数据！");
        return ;
    }else{
    	memberCheck(row);
    	$('#companyDialog').dialog('close');
    }
});

function memberCheck(rows){
	$.each(rows, function(i, item) {
		number = number + 1;
		memberCompare(item, number);
    });
}
function memberCompare(item, num){
	var flag = false;
	if(memberArray.length < 1){
		memberArray.push(item);
		memberDisplay(item, num);
		return;
	}
	$.each(memberArray, function(i, item2) {
		if(item2.id == item.id){
			number = number -1;
			flag = true;
			return false;
		}
    });
	if(flag){
		$.messager.alert("添加已存在","<"+item.companyName+">已存在于当前物流规则！","warning");
		return;
	}
	memberArray.push(item);
	memberDisplay(item, num);
}
function memberDisplay(row, num){
	var options = "";
    for(var i=1; i<10; i++){
    	var result = '';
    	if(row.level==i){
    		result = "<option value='" + i + "' selected='selected'>" +i +"</option>";
    	}else{
    		result = "<option value='" + i + "'>" +i +"</option>";
    	}
    	options = options + result;
    }
       var memberTypeOptions = "";
       if(row.memberType==null){
    	   memberTypeOptions = "<option value='请选择' selected='selected'>请选择</option><option value=2>车主</option><option value=3>物流公司</option>";
       }
    	if(row.memberType==2){
    		memberTypeOptions = "<option value='" + row.memberType + "' selected='selected'>" +row.memberTypeStr +"</option><option value=3>物流公司</option>";
    	}
    	if(row.memberType==3){
    		memberTypeOptions = "<option value='" + row.memberType + "' selected='selected'>" +row.memberTypeStr +"</option><option value=2>车主</option>";
    	}
    	if(typeof(row.totalLimt) == "undefined"){
    		row.totalLimt=0;
    	}
    	if(typeof(row.dayLimt) == "undefined"){
    		row.dayLimt=0;
    	}
    	if(typeof(row.startTimeStr) == "undefined"){
    		row.startTimeStr="";
    	}
    	if(typeof(row.endTimeStr) == "undefined"){
    		row.endTimeStr="";
    	}
    	if(typeof(row.dayCount) == "undefined"){
    		row.dayCount="";
    	}
    	if(typeof(row.totalCount) == "undefined"){
    		row.totalCount="";
    	}
    	

    	if($("#onOffEdit").val() <= 1){
        	
			$("#showCompanyEdit").append(
					"<tr><td style='text-align: center;'>" + num + "</td>" + 
		            "<td style='text-align: center;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;' title='" + row.userName + "'>" + row.userName + "</td>" + 
		            "<td style='text-align: center;'>" + row.mobile    + "</td>" +
		            "<td style='text-align: center;'><select id='memberTypeEdit_"+row.id+"' name='memberType'>" +
		            memberTypeOptions +
		            "</select></td>" +
		            "<td style='text-align: center;'><select id='levelEdit_"+row.id+"' name='level'>"+
		            options +
		            "</select></td>"+
		            "<td style='text-align: center;' ><input type='text' name='totalLimt' value='"+row.totalLimt+"' id='totalLimtEdit_"+row.id+"'  class='easyui-validatebox validatebox-text' validType='number' required='true' style='width:40px'/></td>"+
		            "<td style='text-align: center;' ><input type='text' name='dayLimt' value='"+row.dayLimt+"' id='dayLimtEdit_"+row.id+"'  class='easyui-validatebox validatebox-text' validType='number' required='true' style='width:40px'/></td>"+
		            "<td style='text-align: center;'><input type='text' id='startTimeEdit_"+row.id+"' name='startTime'  onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){startTimeEdit_"+row.id+".focus();},maxDate:'#F{$dp.$D(\\'endTimeEdit_"+row.id+"\\')}'})\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){startTimeEdit_"+row.id+".focus();},maxDate:'#F{$dp.$D(\\'endTimeEdit_"+row.id+"\\')}'})\" style='width:70px'/></td>"+
		            "<td style='text-align: center;'><input type='text' id='endTimeEdit_"+row.id+"' name='endTime'  onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){endTimeEdit_"+row.id+".focus();},minDate:'#F{$dp.$D(\\'startTimeEdit_"+row.id+"\\')}'})\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){endTimeEdit_"+row.id+".focus();},minDate:'#F{$dp.$D(\\'startTimeEdit_"+row.id+"\\')}'})\" style='width:70px'/></td>"+
		            "<td style='text-align: center;'>" + row.dayCount + "</td>"+
		            "<td style='text-align: center;'>" + row.totalCount + "</td>"+
		            "<td style='text-align: center;'><a style='cursor: pointer;' onclick='deleteMemberEdit("+ row.id +")'>删除</a></td></tr>");
        	$("#startTimeEdit_"+row.id).val(row.startTimeStr);
        	
        	$("#endTimeEdit_"+row.id).val(row.endTimeStr);
    	}
    	
    	if($("#onOffEdit").val() == 2){
			$("#showCompanyEdit").append(
					"<tr><td style='text-align: center;'>" + num + "</td>" + 
		            "<td style='text-align: center;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;' title='" + row.userName + "'>" + row.userName + "</td>" + 
		            "<td style='text-align: center;'>" + row.mobile    + "</td>" +
		            "<td style='text-align: center;'><select id='memberTypeEdit_"+row.id+"' name='memberType' disabled='disabled'>" +
		            memberTypeOptions +
		            "</select></td>" +
		            "<td style='text-align: center;'><select id='levelEdit_"+row.id+"' name='level' disabled='disabled'>"+
		            options +
		            "</select></td>"+
		            "<td style='text-align: center;' ><input type='text' name='totalLimt' value='"+row.totalLimt+"' id='totalLimtEdit_"+row.id+"'  class='easyui-validatebox validatebox-text' disabled='disabled' validType='number' required='true' style='width:40px'/></td>"+
		            "<td style='text-align: center;' ><input type='text' name='dayLimt' value='"+row.dayLimt+"' id='dayLimtEdit_"+row.id+"'  class='easyui-validatebox validatebox-text' disabled='disabled' validType='number' required='true' style='width:40px'/></td>"+
		            "<td style='text-align: center;'><input type='text' id='startTimeEdit_"+row.id+"' name='startTime' value=' "+row.startTimeStr+" ' style='width:70px' disabled='disabled'/></td>"+
		            "<td style='text-align: center;'><input type='text' id='endTimeEdit_"+row.id+"' name='endTime' value=' "+row.endTimeStr+" ' style='width:70px' disabled='disabled'/></td>"+
		            "<td style='text-align: center;'>" + row.dayCount + "</td>"+
		            "<td style='text-align: center;'>" + row.totalCount + "</td>");
    		}
    	}


function editOnclick(){
	if(!$("#editRuleInfoForm").form('validate')){
		return false;
	}
	if($("#validateNameEdit").html()!=""){
		slideMessage("已存在该规则名称!");
		return;
	}
	if($("#onOffEdit").val()==0&&($("#cityIdEdit").val()==null||$("#cityIdEdit").val()=="")){
		$.messager.alert("警告","请选择所在城市!","warning");
		return;
	}
	var result=true;
	$.each(memberArray, function(i, item) {
		if($("#memberTypeEdit_"+item.id).val()=="请选择"){
			$.messager.alert("警告","请用户类型!","warning");
			result=false;
			return ;
		}
		if($("#dayLimtEdit_"+item.id).val()==null||$("#dayLimtEdit_"+item.id).val()==""){
			$.messager.alert("警告","日分配上限不能为空！","warning");
			result=false;
			return ;
		}
		if($("#totalLimtEdit_"+item.id).val()==null||$("#totalLimtEdit_"+item.id).val()==""){
			$.messager.alert("警告","日分配上限不能为空！","warning");
			result=false;
			return ;
		}
		
		var dayLimt=$("#dayLimtEdit_"+item.id).val();
		var totalLimt=$("#totalLimtEdit_"+item.id).val();
		if(parseInt(dayLimt)>parseInt(totalLimt)){
			$.messager.alert("警告","日分配上限不能超出货源分配总数！","warning");
			result=false;
			return;
		}
		
		if($("#startTimeEdit_"+item.id).val()==null||$("#startTimeEdit_"+item.id).val()==""){
			$.messager.alert("警告","分配开始时间不能为空！","warning");
			result=false;
			return ;
		}
		
		if($("#endTimeEdit_"+item.id).val()==null||$("#endTimeEdit_"+item.id).val()==""){
			$.messager.alert("警告","分配结束时间不能为空！","warning");
			result=false;
			return ;
		}
    });
	
	if(result){
		if(memberArray.length<1){
			$.messager.alert("警告","当前物流规则内至少存在一个物流公司！","warning");
			return;
		}else{
			saveEditRuleInfo();
		}
	}
}

function deleteMemberEdit(id){
	$("#showCompanyEdit").empty();
	deleteIds.push(id);
	number = 0;
	var str = "";
	if($("#onOffEdit").val() <= 1){
		 str =	"<tr><th colspan='4'>使用以上规则的公司/车主</th>" +
				"<th colspan='5' style='text-align: right'><button onclick='addCompanyEdit();' >添加公司/车主</button></th></tr>"+
				"<tr><th style='text-align: center;word-break:keep-all;padding:0 5px;'>序号</th>" +
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;white-space:nowrap;'>公司/车主</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>电话</th>" +
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>用户类型</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>优先级</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>总分配上限</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>日分配上限</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>分配开始时间</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>分配结束时间</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>已使用日配额</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>已使用总配额</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>操作</th></tr>";
	}else{
		 str =  "<tr><th colspan='7'>使用以上规则的公司/车主</th></tr>"+
				"<tr><th style='text-align: center;word-break:keep-all;padding:0 5px;'>序号</th>" +
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;white-space:nowrap;'>公司/车主</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>电话</th>" +
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>用户类型</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>优先级</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>总分配上限</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>日分配上限</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>分配开始时间</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>分配结束时间</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>已使用日配额</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>已使用总配额</th>"+
				"<th style='text-align: center;word-break:keep-all;padding:0 5px;'>操作</th></tr>";
	}
	$("#showCompanyEdit").html(str);
	$.each(memberArray, function(i, item) {
		if(item.id == id){
			memberArray.splice($.inArray(item, memberArray), 1);
			return false;
		}
	});

	$.each(memberArray, function(i, item){
		number = i+1;
		memberDisplay(item, i+1);
	});
}

function saveEditRuleInfo(){
	var param = {id:$('#ruleId').val(), name:$('#nameEdit').val(), 
			provinceId:$('#provinceIdEdit').val(),cityId:$('#cityIdEdit').val(),
			areaId:$('#areaIdEdit').val(), 
			sourceType:$("input[name='sourceTypeEdit']:checked").val()};
	jQuery.post(CONTEXT + "rule/saveEdit", param, function(data) {
		if (data.success == true) {
			slideMessage("保存成功！");
			var onOff=$("#onOffEdit").val();
			var ruleInfoId = $("#ruleId").val();
			if(memberArray.length>0){
				var ids=new Array();
				var levels = new Array();
				var ids_update = new Array();//memeberId的物流规则需要更改
				var levels_update = new Array();//更新的level值
				
				var memberTypes = new Array();
				var dayLimts = new Array();
				var totalLimts = new Array();
				var startTimes = new Array();
				var endTimes = new Array();
				var memberType_update = new Array();//更新的用户类型
				var dayLimt_update = new Array();//更新每日上限
				var totalLimt_update = new Array();//更新总上限
				var startTime_update = new Array();
				var endTime_update = new Array();
				$.each(memberArray, function(i, item) {
					var level = $("#levelEdit_"+item.id).val();
					var memberType = $("#memberTypeEdit_"+item.id).val();
					var dayLimt = $("#dayLimtEdit_"+item.id).val();
					var totalLimt = $("#totalLimtEdit_"+item.id).val();
					var startTime = $("#startTimeEdit_"+item.id).val();
					var endTime = $("#endTimeEdit_"+item.id).val();
					if(ysIds.indexOf(item.id) == -1){
						ids.push(item.id);
						levels.push(level);
						memberTypes.push(memberType);
						dayLimts.push(dayLimt);
						totalLimts.push(totalLimt);
						startTimes.push(startTime);
						endTimes.push(endTime);
					}else{
						ids_update.push(item.id);
						levels_update.push(level);
						memberType_update.push(memberType);
						dayLimt_update.push(dayLimt);
						totalLimt_update.push(totalLimt);
						startTime_update.push(startTime);
						endTime_update.push(endTime);
					}
				});
				if(ids.length>0){
					insertRuleLogistic(ids.join(), ruleInfoId, levels.join(),memberTypes.join(),totalLimts.join(),dayLimts.join(),startTimes.join(),endTimes.join(),onOff);
				}
				if(ids_update.length>0){
					updateRuleLogistic(ids_update.join(), ruleInfoId, levels_update.join(),memberType_update.join(),totalLimt_update.join(),dayLimt_update.join(),startTime_update.join(),endTime_update.join());
				}
			}
			if(deleteIds.length>0){
				deleteRuleLogistic(deleteIds.join(), ruleInfoId);
			}
			$("#ruleInfodg").datagrid('reload');
			$('#editDialog').dialog('close');
		} else {
			warningMessage(data.msg);
			return;
		}
	});
}

function insertRuleLogistic(ids,ruleInfoId, levels,memberType,totalLimt,dayLimt,startTime,endTime,onOff){
	var url = CONTEXT + "rule/insertRuleLogistic";
	jQuery.post(url, {ids:ids, ruleInfoId:ruleInfoId, levels:levels,memberType:memberType,totalLimt:totalLimt,dayLimt:dayLimt,startTime:startTime,endTime:endTime,onOff:onOff}, function(data) {
	});
}

function updateRuleLogistic(ids,ruleInfoId, levels,memberType,totalLimt,dayLimt,startTime,endTime){
	var url = CONTEXT + "rule/updateRuleLogistic";
	jQuery.post(url, {ids:ids, ruleInfoId:ruleInfoId, levels:levels,memberType:memberType,totalLimt:totalLimt,dayLimt:dayLimt,startTime:startTime,endTime:endTime}, function(data) {
	});
}

function deleteRuleLogistic(ids, ruleInfoId){
	var url = CONTEXT + "rule/delete";
	jQuery.post(url, {ids:ids, ruleInfoId:ruleInfoId}, function(data) {
	});
}

function initValidateLimitEdit(){
	$.extend($.fn.validatebox.defaults.rules, {
		dayLimtEditFuc:{
			validator: function (value) {
	            return /^[1-9][0-9]{0,10}$/.test(value);
			},
	        message: '请输入大于零的自然数！' 
		},
		totalLimtEditFuc:{
			validator: function (value) {
				 return /^[1-9][0-9]{0,10}$/.test(value);
			},
	        message: '请输入大于零的自然数！' 
		}
	});
}

function initEditAreaTopList(){
	var areaTopList = queryAreaTopList();
	$.each(areaTopList, function(i, item){
		$("#provinceIdEdit").append("<option value='"+item.areaID+"'>"+item.area+"</option>");
	}); 
}

$("#provinceIdEdit").change(function(){
	$("#cityIdEdit").html("<option value=''>请选择城市</option>");
	$("#areaIdEdit").html("<option value=''>请选择区/县</option>");
	var parentId = $(this).val();
	var areaList = queryAreaChildList(parentId);
	$.each(areaList, function(i, item){
		$("#cityIdEdit").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
	});
});

$("#cityIdEdit").change(function(){
	$("#areaIdEdit").html("<option value=''>请选择区/县</option>");
	var parentId = $(this).val();
	var areaList = queryAreaChildList(parentId);
	$.each(areaList, function(i, item){
		$("#areaIdEdit").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
	});
});
