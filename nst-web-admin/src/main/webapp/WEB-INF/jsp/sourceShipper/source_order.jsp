<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table id="orderdg" title=""></table>
<script type="text/javascript">
$(document).ready(function(){
	var sourceId = $("#sourceId").val();
	loadOrderData(sourceId);
});
function loadOrderData(sourceId){
	$('#orderdg').datagrid({
		url:CONTEXT+'sourceShipper/orderList/'+sourceId,
		height: 'auto', 
		nowrap:true,
		rownumbers:true,
		pagination:false,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'orderTypeStr',title:'订单类型',width:100,align:'center'},
			{field:'orderNo',title:'订单号',width:100,align:'center'},
			{field:'orderStatusStr',title:'订单状态',width:100,align:'center'},
			{field:'sellerName',title:'收款方姓名',width:100,align:'center'},
			{field:'sellerMobile',title:'收款方手机',width:100,align:'center'},
			{field:'buyerName',title:'付款方姓名',width:100,align:'center'},
			{field:'buyerMobile',title:'付款方手机',width:100,align:'center'},
			{field:'payStatusStr',title:'支付状态',width:100,align:'center'},
			{field:'payMoney',title:'订单金额',width:100,align:'center'}
		]]
	}); 
}
</script>