<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
	<base href="${CONTEXT }">
	<title>demo</title>
</head>
<body>
	<table id="orderInfodg" title=""></table>
	<div id="orderInfotb" style="height:auto;padding:0">
		<div style="overflow-x:auto">
			<div style="width:1100px;">
				<form id="orderInfoSearchForm" method="post">
					<table>
						<tr>
							<td align="right">货运订单号：</td>
							<td><input type="text" id="orderNo" name="orderNo" validtype="number" class="easyui-validatebox" style="width:180px" placeholder="请输入运单号">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td align="right">发货地：</td>
							<td><select name="sProvinceId" id="sProvinceId" style="width:100px">
									<option value="">请选择省份</option>
								</select>
								<select name="sCityId" id="sCityId" style="width:100px">
									<option value="">请选择城市</option>
								</select>
								<select name="sAreaId" id="sAreaId">
									<option value="">请选择区/县</option>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="right">目的地：</td>
							<td><select name="eProvinceId" id="eProvinceId" style="width:100px">
									<option value="">请选择省份</option>
								</select>
								<select name="eCityId" id="eCityId" style="width:100px">
									<option value="">请选择城市</option>
								</select>
								<select name="eAreaId" id="eAreaId" style="width:87px">
									<option value="">请选择区/县</option>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">订单状态：</td>
							<td>
								<select id="orderStatus" name="orderStatus" style="width:185px">
									<option value="">全部</option>
									<option value="1">待收货</option>
									<option value="2">已完成</option>
									<option value="3">已关闭</option>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="right">发布人姓名：</td>
							<td><input type="text" id="shipperName" name="shipperName" style="width:290px" maxlength="30" placeholder="请输入发布人姓名">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td align="right">发布人手机：</td>
							<td><input type="text" id="shipperMobile" name="shipperMobile" validtype="number" class="easyui-validatebox" style="width:290px" maxlength="11" placeholder="请输入发布人手机号码">&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td align="right">车主姓名：</td>
							<td><input type="text" id="driverName" name="driverName" style="width:180px" maxlength="30" placeholder="请输入车主姓名">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td align="right">车主手机：</td>
							<td><input type="text" id="driverMobile" name="driverMobile" validtype="number" class="easyui-validatebox" style="width:290px" maxlength="11" placeholder="请输入车主手机">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td align="right">物流状态：</td>
							<td>
								<select id="transStatus" name="transStatus" style="width:295px">
									<option value="">全部</option>
									<option value="1">待验货</option>
									<option value="2">已发货</option>
									<option value="3">已送达</option>
									<option value="5">已拒收</option>
									<option value="4">验货不通过</option>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							
						</tr>
						<tr>
							<td align="right">支付状态：</td>
							<td>
								<select id="payStatus" name="payStatus" style="width:185px">
									<option value="">全部</option>
									<option value="1">待支付</option>
									<option value="2">支付成功</option>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="right">发运方式：</td>
							<td>
								<select id="sendGoodsType" name="sendGoodsType" style="width:295px">
									<option value="">全部</option>
									<c:forEach var="sendGoodsType" items="<%=cn.gdeng.nst.enums.SendGoodsTypeEnum.values() %>">
										<option value="${sendGoodsType.code }">${sendGoodsType.name}</option>
									</c:forEach>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="right">货物类型：</td>
							<td>
								<select id="goodsType" name="goodsType" style="width:295px">
									<option value="">全部</option>
									<c:forEach var="goodsType" items="<%=cn.gdeng.nst.enums.GoodsTypeEnum.values() %>">
										<option value="${goodsType.code }">${goodsType.name}</option>
									</c:forEach>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							
							<td align="right">货运订单类型：</td>
							<td>
								<select id="sourceType" name="sourceType" style="width:185px">
									<option value="">全部</option>
									<c:forEach var="sourceType" items="<%=cn.gdeng.nst.enums.OrderInfoTypeEnum.values() %>">
										<option value="${sourceType.code }">${sourceType.name}</option>
									</c:forEach>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="right">订单生成时间：</td>
							<td>
								<input type="text" id="startDate" name="startDate"  
									onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})"   
									onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})" 
									style="width:136px" placeholder="开始时间" readonly="readonly"> ~
								<input type="text" id="endDate" name="endDate"   
									onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'})"   
									onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'})" 
									style="width:136px" placeholder="结束时间" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td colspan="2">
								<gd:btn btncode="HYDDGL001"><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search">查询</a>&nbsp;&nbsp;</gd:btn>
								<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
							</td>
						</tr>	
					</table>
				</form>
			</div>
		</div>
		<div style="background:#efefef;line-height:35px;height:35px;">
			<div style="float: left;font-size:14px;">货运订单列表</div>
			<div style="float:right;margin-right:10px;">
				<gd:btn btncode="HYDDGL002"><a class="easyui-linkbutton icon-save-btn" iconCls="icon-account-excel" id="btn-export" onclick="exportData()">导出</a>&nbsp;&nbsp;</gd:btn>
			</div>
		</div>
	</div>
	<div id="detailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
		<div id="dlg-buttonsDetail" style="text-align:center">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
        </div>
	</div>	
</body>
<script type="text/javascript" src="${CONTEXT}js/orderInfo/main.js"></script>
<script type="text/javascript">
function sDetailFormatter(val, row, index){
	var content = "";
	if(val != undefined){
		content += val;
	}
	return "<gd:btn btncode='HYDDGL003'><a class='operate' href='javascript:;' onclick=detail('"+row.id+"');></gd:btn>"+content+"<gd:btn btncode='HYDDGL003'></a></gd:btn>";
}

function eDetailFormatter(val, row, index){
	var content = "";
	if(val != undefined){
		content += val;
	}
	return "<gd:btn btncode='HYDDGL003'><a class='operate' href='javascript:;' onclick=detail('"+row.id+"');></gd:btn>"+content+"<gd:btn btncode='HYDDGL003'></a></gd:btn>";
}
</script>
</html>

