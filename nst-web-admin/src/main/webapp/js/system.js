var NSTAdmin = {};
NSTAdmin.client = function(path){
	//编写公共服务
};
NSTAdmin.client.prototype = {
		/**
		 * 
		 * @param str
		 * @param length
		 * @returns string
		 * @description 截取指定长度字符串 默认10
		 */
		subString : function(str , length){
			if(undefined == length){
				length = 12;
			}
			if(null == str || str.trim().length <= 0){
				return "";
			}
			str = str.trim();
			if(str.length <= length){
				return str;
			}
			return str.substr(0 , length)+"...";
		},
		/**
		 * 去除字符串左右空格,如果是null,NULL,Null......则返回空白
		 * @returns
		 */
		getString : function (str) {
			if(null == str){
				return "";
			}
			if(typeof(str) != "string"){
				return str;
			}
			if("NULL" == str.toUpperCase().trim()){
				return "";
			}
			return str .trim();
		}
	};

/**
 * 去除字符串左右空格
 * 为了兼容IE
 */
String.prototype.trim = function () {
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};
/** 
 * 时间对象的格式化; 
 */
Date.prototype.format = function(format) {
	/* 
	 * eg:format="yyyy-MM-dd hh:mm:ss"; 
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month  
		"d+" : this.getDate(), // day  
		"h+" : this.getHours(), // hour  
		"m+" : this.getMinutes(), // minute  
		"s+" : this.getSeconds(), // second  
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
		"S" : this.getMilliseconds()
	// millisecond  
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/**
 * ajax扩展
 */
$(function(){
	//首先备份下jquery的ajax方法
    var _ajax=$.ajax;
     
    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){}
        }
        if(opt.error){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }
         
        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus){
                //成功回调方法增强处理
                fn.success(data, textStatus);
            },
            beforeSend:function(XHR){
                //提交前回调方法
            	XHR.setRequestHeader("Request-Type", "ajax");
               // $('body').append("<div id='ajaxInfo' style=''>正在加载,请稍后...</div>");
            },
            dataFilter : function(data, type){
            	if("html" == type
            			&& data.indexOf('"code":30000') > -1){
            		var _data = eval("(" + data + ")")
            		warningMessage(_data.msg);
            		setTimeout("window.top.location.href='"+PATH+"/sys/loginOut'", 2000);
            		return null;
            	}
            	if(type!="json"){
                    return data;
            	}
            	var _data = eval("(" + data + ")")
    			if(_data.code != 10000
    					&& _data.success == false){
    				warningMessage(_data.msg);
    				if(_data.code == 30000){
    					setTimeout("window.top.location.href='"+PATH+"/sys/loginOut'", 2000);
    				}
    			}
				return data;
			},
            complete:function(XHR, TS){
                //请求完成后回调函数 (请求成功或失败之后均调用)。
                $("#ajaxInfo").remove();;
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
				//errorMessage("系统服务异常");
            	console.log("ajax error start-------------------");
				console.log("XMLHttpRequest:"+JSON.stringify(XMLHttpRequest));
				console.log("textStatus:"+JSON.stringify(textStatus));
				console.log("errorThrown:"+JSON.stringify(errorThrown));
				console.log("ajax error end-------------------");
				
			}
        });
        return _ajax(_opt);
    };
});
