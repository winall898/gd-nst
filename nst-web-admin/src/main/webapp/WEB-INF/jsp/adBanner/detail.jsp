<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div style="margin: 10px auto;width:80%">
	<table>
		<tr>
			<td>广告名称：</td>
			<td>${dto.name }</td>
		</tr>
		<tr>
			<td>广告渠道：</td>
			<td>${dto.channelStr }</td>
		</tr>
		<tr>
			<td>广告类型：</td>
			<td>${dto.typeStr }</td>
		</tr>
		<tr>
			<td>开始时间:</td>
			<td><fmt:formatDate value="${dto.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>结束时间:</td>
			<td><fmt:formatDate value="${dto.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>排序：</td>
			<td>${dto.sort }</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>${dto.stateStr }</td>
		</tr>
		<tr>
			<td>上传图片:</td>
			<td><a href="${dto.imgUrl }" target="_blank"><img src="${dto.imgUrl }" title="图片" width="200px" height="200px"/></a></td>
		</tr>
		<tr>
			<td>创建人:</td>
			<td>${dto.createUserName }</td>
		</tr>
		<tr>
			<td>创建时间:</td>
			<td><fmt:formatDate value="${dto.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>修改人:</td>
			<td>${dto.updateUserName }</td>
		</tr>
		<tr>
			<td>修改时间:</td>
			<td><fmt:formatDate value="${dto.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>
</div>
