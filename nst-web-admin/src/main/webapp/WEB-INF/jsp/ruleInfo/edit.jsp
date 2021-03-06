<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="margin: 10px auto;width:80%">
	<form action="" id="editRuleInfoForm">
		<input type="hidden" id="ruleId" name="id" value="${entity.id}"/>
		<input type="hidden" id="onOffEdit" name="onOffEdit" value="${entity.onOff}"/>
		<table>
			<tr>
				<td style="text-align:right">规则名称：</td>
				<c:if test="${entity.onOff != 0}">
					<td><input type="text" name="name" id="nameEdit" value="${entity.name}" style="width:153px" class="easyui-validatebox" validType="length[1,30]" ONBLUR='validayRuleName(this)' required="true" disabled="disabled"/>
					&nbsp;<font color="red">*</font><span id="validateNameEdit" style="color:red;"></span></td>
				</c:if>
				<c:if test="${entity.onOff == 0}">
					<td><input type="text" name="name" id="nameEdit" value="${entity.name}" style="width:153px" class="easyui-validatebox" validType="length[1,30]" ONBLUR='validayRuleName(this)' required="true"/>
					&nbsp;<font color="red">*</font><span id="validateNameEdit" style="color:red;"></span></td>
				</c:if>
			</tr>
			<tr>
				<td style="text-align:right">货源类别：</td>
					<td>
					    <c:if test="${entity.onOff != 0}">
							<c:if test="${entity.sourceType==1}">
								<input type="radio" name="sourceTypeEdit"  value="2" disabled="disabled" />同城 
								<input type="radio" name="sourceTypeEdit" value="1" checked="checked" disabled="disabled" />干线
							</c:if>
							<c:if test="${entity.sourceType==2}">
								<input type="radio" name="sourceTypeEdit" value="2" checked="checked" disabled="disabled"/>同城 
								<input type="radio" name="sourceTypeEdit" value="1" disabled="disabled"/>干线
							</c:if>
						</c:if>
						<c:if test="${entity.onOff == 0}">
							<c:if test="${entity.sourceType==1}">
								<input type="radio" name="sourceTypeEdit" value="2"/>同城 
									<input type="radio" name="sourceTypeEdit" value="1" checked="checked"/>干线
								</c:if>
								<c:if test="${entity.sourceType==2}">
									<input type="radio" name="sourceTypeEdit" value="2" checked="checked"/>同城 
									<input type="radio" name="sourceTypeEdit" value="1"/>干线
							</c:if>
						</c:if>
					</td>
			</tr>
			<tr>
				<td style="text-align:right">所在城市：</td>
				<td>
					<c:if test="${entity.onOff != 0}">
							<select name="provinceId" id="provinceIdEdit" style="width:100px" disabled="disabled">
								<option value='${entity.provinceId==null ? "请选择省份":entity.provinceId}'>${entity.provinceIdStr==null ? "请选择省份":entity.provinceIdStr}</option>
							</select>
							<select name="cityId" id="cityIdEdit" style="width:100px" disabled="disabled">
								<option value='${entity.cityId==null ? "请选择城市":entity.cityId}'>${entity.cityIdStr==null ? "请选择城市":entity.cityIdStr}</option>
							</select>
							<select name="areaId" id="areaIdEdit" disabled="disabled">
								<option value="">${entity.areaStr==null ? "请选择区/县":entity.areaStr}</option>
							</select>
					</c:if>
					<c:if test="${entity.onOff == 0}">
							<select name="provinceId" id="provinceIdEdit" style="width:100px">
								<option value='${entity.provinceId==null ? "请选择省份":entity.provinceId}'>${entity.provinceIdStr==null ? "请选择省份":entity.provinceIdStr}</option>
							</select>
							<select name="cityId" id="cityIdEdit" style="width:100px">
								<option value='${entity.cityId==null ? "请选择城市":entity.cityId}'>${entity.cityIdStr==null ? "请选择城市":entity.cityIdStr}</option>
							</select>
							<select name="areaId" id="areaIdEdit">
								<option value="">${entity.areaStr==null ? "请选择区/县":entity.areaStr}</option>
							</select>
					</c:if>
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td style="text-align:right">其他通用默认规则：</td>
				<td>
					1、每个货源分配给3个公司/车主
				</td>
			</tr>
			<tr>
				<td></td>
				<td>2、货源在公司/车主最多停留15分钟</td>
			</tr>
			<tr>
				<td></td>
				<td>3、每次轮询分配1条货源，同一个货源不能分配给同一个公司/车主两次 </td>
			</tr>
			<tr>
				<td></td>
				<td>4、在同等优先级条件下，按完成货运订单量由多至少、注册时间由早到迟优先分配</td>
			</tr>
		</table>
	</form>
		<div style="position:absolute; height:250px; width:720px;overflow-x:auto;">
			<table id="showCompanyEdit">
				<tr>
					<th colspan="4">使用以上规则的公司/车主</th>
					<c:if test="${entity.onOff == 0 || entity.onOff == 1}">
						<th colspan="5" style="text-align: right"><button onclick="addCompanyEdit();" >添加公司/车主</button></th>
					</c:if>
				</tr>
				<tr>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">序号</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;white-space:nowrap;">公司/车主</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">电话</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">用户类型</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">优先级</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">总分配上限</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">日分配上限</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">分配开始时间</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">分配结束时间</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">已使用日配额</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">已使用总配额</th>
					<th style="text-align: center; word-break:keep-all;padding:0 5px;">操作</th>
				</tr>
				
			</table>
		</div>

	<div id="companyDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-companyDialog">
		<div id="dlg-companyDialog" style="text-align:center">
        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="selectCompany">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#companyDialog').dialog('close')">关闭</a>
        </div>
	</div>
</div>
<script type="text/javascript" src="${CONTEXT}../js/ruleInfo/edit.js"></script>