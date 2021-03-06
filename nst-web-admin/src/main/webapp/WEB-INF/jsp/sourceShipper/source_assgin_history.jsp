<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table id="sourceAssignHistorydg" title=""></table>
<script type="text/javascript">
$(document).ready(function(){
	var sourceId = $("#sourceId").val();
	loadSourceHisListData(sourceId);
});
function loadSourceHisListData(sourceId){
	$('#sourceAssignHistorydg').datagrid({
		url:CONTEXT+'sourceShipper/assignHistoryList/'+sourceId,
		height: 'auto', 
		nowrap:true,
		pageSize:10,
		rownumbers:true,
		pagination:false,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'createTime',title:'分配时间',width:100,align:'center'},
			{field:'assignName',title:'分配公司/车主',width:100,align:'center'},
			{field:'assignMobile',title:'分配公司/车主电话',width:100,align:'center'},
			{field:'statusStr',title:'分配处理状态',width:100,align:'center'},
			{field:'updateTime',title:'分配处理时间',width:100,align:'center', formatter:updateTimeFormatter}
		]]
	}); 
}
function updateTimeFormatter(val, row, index){
	if(row.status == "1"){
		return "";
	}
	return row.updateTime;
}
</script>