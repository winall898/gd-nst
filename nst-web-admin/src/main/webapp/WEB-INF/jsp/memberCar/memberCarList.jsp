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
	<title>车辆信息</title>
</head>
<body>
	<table id="memberCardg" title=""></table>
	<div id="memberCartb" style="padding:5px;height:auto">
		<form id="memberCarSearchForm" method="post">
		<div>
			<table>
				<tr>
					<td>车牌号码：</td>
					<td><input type="text" id="carNumber" name="carNumber" style="width:150px" placeholder="请输入车牌号">&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>用户手机：</td>
					<td><input type="text" id="mobile" name="mobile" style="width:150px"  maxlength="11" placeholder="请输入联系人手机">&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>用户姓名：</td>
					<td><input type="text" id="userName" name="userName" style="width:220px"  maxlength="30" placeholder="请输入联系人姓名">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			    <td>业务范围</td>
				<td>
				<select  name="memberCarServiceType" id="memberCarServiceType" style="width: 154px;">
				 <option value="">全部</option>
				 <option value="1">干线业务</option>
				 <option value="2">同城业务</option>
				</select>
				</td>
				</tr>
				<tr>
					<td>车辆类型：</td>
					<td>
					<select  name="carType" id="carType" style="width: 154px;">
					        <option value="">全部</option>
 							<option value="1">小型面包 </option>
							<option value="2">金杯     </option>
							<option value="3">小型平板 </option>
							<option value="4">中型平板 </option>
							<option value="5">小型厢货 </option>
							<option value="6">大型厢货 </option>
							<option value="7">敞车    </option>
							<option value="8">厢式货车 </option>
							<option value="9">高栏车   </option>
							<option value="10">平板车   </option>
							<option value="11">集装箱   </option>
							<option value="12">保温车   </option>
							<option value="13">冷藏车   </option>
							<option value="14">活鲜水车 </option>
							<option value="15">其他     </option>
					</select>
					
					</td>
					<td>车辆状态：</td>
					<td>
					<select  name="isDeleted" id="isDeleted" style="width: 154px;">
					        <option value="">全部</option>
 							<option value="1">已删除 </option>
 							<option value="0">使用中</option>
 					</select>
					</td>
				  <td>创建时间：</td>
					<td>
						<input  type="text" id="startDate" name="startDate"  
							onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})"   
							onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})" 
							style="width:100px" placeholder="开始时间"> ~
						<input  type="text" id="endDate" name="endDate"   
							onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'})"   
							onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'})" 
							style="width:100px" placeholder="结束时间">&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				<td></td>
				<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td colspan="2">
						<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search">查询</a>&nbsp;&nbsp;
						<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
					</td>
				</tr>	
			</table>
		</div>
		</form>
		

	<div id="deleteCar" class="easyui-dialog" style="width:300px;height:200px;" closed="true" modal="true"
		data-options="title:'删除',buttons:'#dtable'" >
		<h5 style="float: center;font-size:14px;margin:10px;">确定删除此条车辆信息</h5>
	
	</div>
	<div id="dtable">
		<input  type="hidden" name="fatherId" id="fatherId" value="" />
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="icon-save-btn" onclick="deleteMemberCar()"> 确定 </a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#deleteCar').dialog('close')">取消</a>
	</div>
	<div id="win" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" >
	
	</div>	
			<div style="background:#efefef;padding:5px 0 5px 0;height:25px;">
			<div style="float: left;font-size:14px;margin-left:5px;">车辆信息列表</div>
			<div style="float:right;margin-right:10px;">
			<!-- <a class="easyui-linkbutton" iconCls="icon-reload" onclick='location.reload(true)'">刷新</a> -->
			<gd:btn btncode='MEMBERCAREXP'>
			<a class="easyui-linkbutton icon-save-btn" iconCls="icon-account-excel" id="exportData" >导出</a>&nbsp;&nbsp;
			</gd:btn>
			</div>
		</div>
		
	</div>
</body>                                                   
<script type="text/javascript" src="${CONTEXT}js/memberCar/memberCarList.js"></script>
<script type="text/javascript" src="${CONTEXT}js/lightbox/js/lightbox-2.6.min.js"></script>
<script type="text/javascript">
function carNumberFormatter(v,r,i) {
			return "<gd:btn btncode='MEMBERCARDETAIL'><a  class='operate' href='javascript:editObj("+r.id+")' >	</gd:btn>"+v+"<gd:btn btncode='MEMBERCARDETAIL'></a>	</gd:btn>"
		}
		
function updateOperate(value,row,index){
	var html="";
	if(row.isDeleted=='1'){
		html=html+"删除";
	}else{
		html=html+"<gd:btn btncode='BTDMCLD01'><a class='operate' style='margin: 0;'  href='#' onclick=updateAction('"+row.id+"');>删除</a></gd:btn>";
	}
	return html;
}

</script>
</html>

