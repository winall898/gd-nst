var sourceAssignHisCtrl  = new NSTAdmin.client();

NSTAdmin.client.prototype.sourceAssignHisMain = {
	disableExport : false,
	/**
	 * 请求地址URL
	 */
	urlItems : {
		queryByPageURL : CONTEXT+'sourceAssignHis/queryPage',
		queryTopAreaURL : CONTEXT+'area/queryTopList',
		queryChildAreaURL : CONTEXT+'area/queryChildList',
		exportCheckURL : CONTEXT+'sourceAssignHis/exportCheck',
		exportDataURL : CONTEXT+'sourceAssignHis/export'
	},
	/**
	 * 加载分页数据
	 */
	loadListData : function(queryParams) {
		_this = this;
		queryParams = !queryParams ? {} : queryParams;
		//数据加载
		$('#sourceAssignHisDg').datagrid({
			url:_this.urlItems.queryByPageURL,
			queryParams: queryParams,
			height: 'auto', 
			nowrap:true,
			toolbar:'#sourceAssignHisTb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			fitColumns:false,
			fit:true,
			columns:[[
				{field:'sourceId',title:'货源ID',align:'center'},
				{field:'sourceMemberName',title:'发布人姓名',align:'left'},
				{field:'sourceMemberMobile',title:'发布人手机',align:'center'},
				{field:'sourceCreateTime',title:'发布时间',align:'center'},
				{field:'ruleType',title:'货源类型',align:'center', formatter:_this.ruleTypeFormatter},
				{field:'assignName',title:'分配公司/车主',align:'left'},
				{field:'assignMobile',title:'分配公司/车主手机',align:'center'},
				{field:'goodsTypeStr',title:'货物类型',align:'center'},
				{field:'totalWeight',title:'货物重量',align:'center',formatter:_this.totalWeightFormatter},
				{field:'sourceTypeStr',title:'线路类型',align:'center'},
				{field:'sDetailStr',title:'发货地',align:'left'},
				{field:'eDetailStr',title:'目的地',align:'left'},
				{field:'clientsStr',title:'发布来源',align:'center'},
				{field:'createTime',title:'分配时间',align:'center'},
				{field:'statusStr',title:'分配处理状态',align:'center'}
			]],
			onLoadSuccess: function (data) {
				$(this).datagrid("fixRownumber");
			}
		}); 
	},
	/**
	 * 分页初始化
	 */
	initPageListData : function() {
		_this = this;
		_this.loadListData(null);
		$("#sourceAssignHisDg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	},
	/***
	 * 货物重量格式化
	 * @param val
	 * @returns {String}
	 */
	totalWeightFormatter : function (val) {
		if (val > 0) {
			return val + "吨";
		} else {
			return "";
		}
	},
	ruleTypeFormatter : function (val) {
		if(val == 0) {
			return "指派";
		} else if (val == 1) {
			return "分配";
		}
		return "";
	},
	/**
	 * 获取省份列表
	 * @returns
	 */
	queryTopArea : function () {
		var result;
		$.ajax({
			async: false, 
			url: _this.urlItems.queryTopAreaURL,
			type:'post',
			dataType:"json",
			success : function(data){
				result = data.result;
			}
		});
		return result;
	},
	/***
	 * 根据parentId获取下级区域列表
	 * @param parentId
	 * @returns
	 */
	queryAreaChildList : function (parentId){
		var result;
		$.ajax({
			async: false, 
			url: _this.urlItems.queryChildAreaURL + '/' + parentId,
			type:'post',
			dataType:"json",
			success : function(data){
				result = data.result;
			}
		});
		return result;
	},
	/***
	 * 初始化省份列表
	 */
	initAreaTopList : function (){
		_this = this;
		var areaTopList = _this.queryTopArea();
		$.each(areaTopList, function(i, item){
			$("#sProvinceId").append("<option value='"+item.areaID+"'>"+item.area+"</option>");   
			$("#eProvinceId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
		}); 
	},
	/**
	 * 获取查询参数
	 * @returns {___anonymous2668_3926}
	 */
	getSearchParams : function (){
		var params = {
			"sProvinceId" : $("#sourceShipperSearchForm #sProvinceId").val(),
			"sCityId" : $("#sourceShipperSearchForm #sCityId").val(),
			"sAreaId" : $("#sourceShipperSearchForm #sAreaId").val(),
			"eProvinceId" : $("#sourceShipperSearchForm #eProvinceId").val(),
			"eCityId" : $("#sourceShipperSearchForm #eCityId").val(),
			"eAreaId" : $("#sourceShipperSearchForm #eAreaId").val(),
			"sourceMemberName" : $("#sourceShipperSearchForm #sourceMemberName").val(),
			"sourceMemberMobile" : $("#sourceShipperSearchForm #sourceMemberMobile").val(),
			"sourceId" : $("#sourceShipperSearchForm #sourceId").val(),
			"clients" : $("#sourceShipperSearchForm #clients").val(),
			"sourceType" : $("#sourceShipperSearchForm #sourceType").val(),
			"goodsType" : $("#sourceShipperSearchForm #goodsType").val(),
			"ruleType" : $("#sourceShipperSearchForm #ruleType").val(),
			"sourceStatus" : $("#sourceShipperSearchForm #sourceStatus").val(),
			"assignMobile" : $("#sourceShipperSearchForm #assignMobile").val(),
			"status" : $("#sourceShipperSearchForm #status").val(),
			"sourceStartDate" : $("#sourceShipperSearchForm #sourceStartDate").val(),
			"sourceEndDate" : $("#sourceShipperSearchForm #sourceEndDate").val(),
			"assignStartDate" : $("#sourceShipperSearchForm #assignStartDate").val(),
			"assignEndDate" : $("#sourceShipperSearchForm #assignEndDate").val(),
		};
		return params;
	},
	/***
	 * 导出数据
	 */
	exportData : function (){
		_this = this;
		if (_this.disableExport){
			slideMessage("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");
			return;
		}
		_this.disableExport = true;
		
		var params = _this.getSearchParams();
		var paramList = "";
		for(key in params){ 
			paramList +=  key+"="+ params[key]+"&"
	    }
		$.ajax({
			url: _this.urlItems.exportCheckURL,
			data : params,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.code == 10000){
					slideMessage("数据正在导出中, 请耐心等待...");
					$.download(_this.urlItems.exportDataURL, paramList, 'post');
				}else{
					warningMessage(data.msg);
				}
			},
			error : function(){
				warningMessage("服务器出错");
			}
		});
		// 10秒后导出按钮重新可用
		setInterval(function(){
			_this.disableExport = false;
		}, 10000);
	},
	event : function () {
		_this = this;
		//查询
		$('#btn-search').click(function(){
			if(!$("#sourceShipperSearchForm").form('validate')){
				return false;
			}
			var searchParams = _this.getSearchParams();
			_this.loadListData(searchParams);
		});
		
		//导出
		$("#btn-export").click(function() {
			_this.exportData();
		});
		
		//重置
		$('#btn-reset').click(function(){
			$("#sourceShipperSearchForm")[0].reset();
		});
		
		$("#sProvinceId").change(function(){
			$("#sCityId").html("<option value=''>请选择城市</option>");
			$("#sAreaId").html("<option value=''>请选择区/县</option>");
			var parentId = $(this).val();
			var areaList = _this.queryAreaChildList(parentId);
			$.each(areaList, function(i, item){
				$("#sCityId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
			});
		});
		
		$("#eProvinceId").change(function(){
			$("#eCityId").html("<option value=''>请选择城市</option>");
			$("#eAreaId").html("<option value=''>请选择区/县</option>")
			var parentId = $(this).val();
			var areaList = _this.queryAreaChildList(parentId);
			$.each(areaList, function(i, item){
				$("#eCityId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
			});
		});
		
		$("#sCityId").change(function(){
			$("#sAreaId").html("<option value=''>请选择区/县</option>");
			var parentId = $(this).val();
			var areaList = _this.queryAreaChildList(parentId);
			$.each(areaList, function(i, item){
				$("#sAreaId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
			});
		});
		
		$("#eCityId").change(function(){
			$("#eAreaId").html("<option value=''>请选择区/县</option>");
			var parentId = $(this).val();
			var areaList = _this.queryAreaChildList(parentId);
			$.each(areaList, function(i, item){
				$("#eAreaId").append("<option value='"+item.areaID+"'>"+item.area+"</option>"); 
			});
		});
	}
};

/**
 * 初始化
 */
$(function(){
	// 初始化分页
	sourceAssignHisCtrl.sourceAssignHisMain.initPageListData();
	// 初始化事件
	sourceAssignHisCtrl.sourceAssignHisMain.event();
	// 初始化省份列表
	sourceAssignHisCtrl.sourceAssignHisMain.initAreaTopList();
	
	fixRownumber();
});
