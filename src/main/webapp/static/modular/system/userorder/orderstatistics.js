/**
 * 用户管理-订单统计
 */
var Orderstatistics = {

};

//统计接口
Orderstatistics.statistics= function (){
	$.ajax({
	    type : "POST",
	    url : Feng.ctxPath+"/userOrder/orderStatistics",
		data:'',
	    success : function (msg) {	
	    	if(msg != null && msg != ''){
	    		$("#h51").val(msg.h51);
		    	$("#tuiguang").val(msg.tuiguang);
		    	$("#xiaoshou").val(msg.xiaoshou);
		    	$("#ios").val(msg.ios);
		    	$("#android").val(msg.android);
		    	$("#card").val(msg.card);
		    	$("#give").val(msg.give);
		    	$("#sum").val(msg.sum);
		    	
	    	}else{
	    		$("#h51").val("0");
	    		$("#h52").val("0");
	    		$("#h53").val("0");
	    		$("#ios").val("0");
	    		$("#android").val("0");
	    		$("#card").val("0");
	    		$("#give").val("0");
	    		$("#sum").val("0");
	    	}
	    },
		error:function(){
			alert("错误");
		}
	});
}

$(function () {
	Orderstatistics.statistics();
});


