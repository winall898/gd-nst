/**
 * 物流信息处理
 * @author jasmine
 */
var logisticsCtrl  = new NSTApi.client();

NSTApi.client.prototype.infoHandler = {
	/**
	 * 静态变量
	 * 请求地址URL
	 */
	urlItems : {
		// queryOrderURL :"/nst-web-api/OrderInfoTrans/queryOrderInfoTransAndSourceExamineByOrderNo"
		queryOrderURL :"/OrderInfoTrans/queryOrderInfoTransAndSourceExamineByOrderNo"
	},
	/**
	 * 初始化
	 */
	init : function(){
		var _this = this;
		var orderNo = logisticsCtrl.getReqParam("orderNo");
		// 安卓查询参数串
		var anVal = 'orderNo:' + orderNo;
		// ios查询参数串
		var iosVal = '{"data":"{\'orderNo\':\'' + orderNo + '\'}","fun":"handleData"}';
		var data = logisticsCtrl.enCode(anVal, iosVal, 'handleData', function(data){
			_this.getData(data);
		});
		if(logisticsCtrl.appVersion.android){
			_this.getData(data);
		}
	},
	// 请求物流数据
	getData : function(param){
		var _this = this;
		$.ajax({
			url: this.urlItems.queryOrderURL,
			data : {"param" : param},
			type: "get",
			dataType: "text",
			contentType: "application/json",
			cache: false,
			success: function(data){
				var data = logisticsCtrl.deCode(data, '{"data":"'+data+'","fun":"decryptCompleted"}', "decryptCompleted", function(data){
					var datasVal = JSON.stringify(data).replace(/\\/g,"");
					datasVal = JSON.parse(datasVal.substring(1,datasVal.length-1));
					_this.render(datasVal);
				});
				if(logisticsCtrl.appVersion.android){
					_this.render(data);
				}
				
				//打电话功能
				 $("#phone").on("tap", function(){
					var phone = $(this).attr("data-phone");
					logisticsCtrl.call(phone);
				});
			}
		});
	},
	// 渲染页面
	render : function(data){
		$("#phone").attr("data-phone", data.result.shipperMobile);
		$("#consignee").html($("#consigneeTemp").tmpl(data.result));
		$("#logisMsg").html($("#logisMsgTemp").tmpl(data.result.orderInfoTransList));
		$("#goods").html($("#goodsTemp").tmpl(data.result.sourceExamineDTO));
	}
};

$(function(){
	// 初始化
	logisticsCtrl.infoHandler.init();
});