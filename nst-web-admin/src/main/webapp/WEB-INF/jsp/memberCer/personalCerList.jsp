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
		<title>个人认证</title>
	</head>
<body>
	<table id="demodg" title="">
	</table>
	<div id="demotb" style="padding:5px;height:auto">
		<form id="demoSearchForm" method="post">
		<div>
			<table>
				<tr>
					<td style="float:right">姓名：</td>
					<td><input type="text" id="realName" name="realName" style="width:150px" placeholder="请输入农速通会员姓名" maxlength="30"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>申请时间：</td>
					<td>
						<input  type="text" id="applyForBeginTime" name="applyForBeginTime"  
							onFocus="WdatePicker({onpicked:function(){applyForBeginTime.focus();},maxDate:'#F{$dp.$D(\'applyForEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
							onClick="WdatePicker({onpicked:function(){applyForBeginTime.focus();},maxDate:'#F{$dp.$D(\'applyForEndTime\')}',dateFmt:'yyyy-MM-dd'})" 
							style="width:100px" placeholder="开始时间"> ~
						<input  type="text" id="applyForEndTime" name="applyForEndTime"   
							onFocus="WdatePicker({onpicked:function(){applyForEndTime.focus();},minDate:'#F{$dp.$D(\'applyForBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
							onClick="WdatePicker({onpicked:function(){applyForEndTime.focus();},minDate:'#F{$dp.$D(\'applyForBeginTime\')}',dateFmt:'yyyy-MM-dd'})" 
							style="width:100px" placeholder="结束时间">&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>认证状态：</td>
					<td>
						<select id="cerStatus" name="cerStatus" style="width:155px">
							<option value="">全部</option>
							<option value="0">认证中</option>							
							<option value="1">已认证</option>
							<option value="2">已驳回</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>								                 
				</tr>
				<tr>
					<td style="float:right">手机：</td>
					<td><input type="text" id="mobile" name="mobile" style="width:150px" placeholder="请输入农速通会员手机" maxlength="11" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>审核时间：</td>
                    <td>
						<input type="text" id="auditBeginTime" name="auditBeginTime"  
							onFocus="WdatePicker({onpicked:function(){auditBeginTime.focus();},maxDate:'#F{$dp.$D(\'auditEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
							onClick="WdatePicker({onpicked:function(){auditBeginTime.focus();},maxDate:'#F{$dp.$D(\'auditEndTime\')}',dateFmt:'yyyy-MM-dd'})" 
							style="width:100px" placeholder="开始时间"> ~
						<input  type="text" id="auditEndTime" name="auditEndTime"   
							onFocus="WdatePicker({onpicked:function(){auditEndTime.focus();},minDate:'#F{$dp.$D(\'auditBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
							onClick="WdatePicker({onpicked:function(){auditEndTime.focus();},minDate:'#F{$dp.$D(\'auditBeginTime\')}',dateFmt:'yyyy-MM-dd'})" 
							style="width:100px" placeholder="结束时间">&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>认证来源</td> 
					<td>
						<select id="certificFrom" name="certificFrom" style="width:155px">
							<option value="">全部</option>
							<option value="1">农速通-货主</option>							
							<option value="2">农速通-车主</option>
							<option value="3">农速通-物流公司</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>		                  
					<td colspan="2">
						<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search">查询</a>&nbsp;&nbsp;
						<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div style="background:#efefef;width: 1129px;height: 40px">
		    <div style="float:left;font-size:14px;margin-left:5px;margin-top:9px">个人认证列表</div>
		    <div style="float:left;margin-top:8px">
		        <gd:btn btncode="GRRZ01"><input id="hid_showInfo" type="hidden" value="0" /></gd:btn>
				<gd:btn btncode="GRRZ02"><a id="auditData" class="easyui-linkbutton" >审核</a></gd:btn>
				<gd:btn btncode="GRRZ03"><a id="exportData" class="easyui-linkbutton" iconCls="icon-account-excel">导出</a></gd:btn>
			</div>
		</div>
		
        <div id="auditDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#auditDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
		
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/memberCer/personalCer.js"></script>
<script type="text/javascript">
	function accountFormatter(val, row) {
		return "<gd:btn btncode='GRRZ01'><a class='operate' href='javascript:showDialog("+row.id+")'></gd:btn>"+ val +"<gd:btn btncode='GRRZ01'></a></gd:btn>"
	}
</script>
</html>