/**
 * 用户管理--用户状态统计
 */
var Userstatus = {
    id: "userstatus",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Userstatus.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: false},
        {title: '角色', field: 'uRole', align: 'center', valign: 'middle', sortable: false, width:'100px'},
        {title: '分会', field: 'bName', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号归属地', field: 'address', align: 'center', valign: 'middle', sortable: false},
        {title: '注册时间', field: 'uCreateTime', align: 'center', valign: 'middle', sortable: true },
        {title: '状态', field: 'uStatus', align: 'center', valign: 'middle', sortable: false , width:'100px'},
        {title: '用户积分', field: 'integral', align: 'center', valign: 'middle', sortable: true},
        {title: '最近播放记录', field: 'partlog', align: 'center', valign: 'middle', sortable: true}
        ];
        
    return columns;
};


//统计接口
Userstatus.statistics= function (condition,beginTime,endTime){
	$.ajax({
	    type : "POST",
	    url : Feng.ctxPath+"/generalUser/userStatusStatistics",
		data:'condition='+condition+"&beginTime="+beginTime+"&endTime="+endTime,
	    success : function (msg) {	
	    	if(msg != null && msg != ''){
	    		$("#count").val(msg.count);
		    	$("#paid").val(msg.paid);
				$("#cardPay").val(msg.cardPay);
		    	$("#sup").val(msg.sup);
		    	$("#suped").val(msg.suped);
		    	
		    	$("#giftGiving").val(msg.giftGiving);
		    	$("#cardGiving").val(msg.cardGiving);
		    	$("#exchange").val(msg.exchange);
		    	$("#fullmembers").val(msg.fullmembers);
		    	
		   
		    	$("#bookName1").val(msg.bookSum1);
		    	$(".bookName1 label").html(msg.bookName1);
		    	
		    
		    	$("#bookName2").val(msg.bookSum2);
		    	$(".bookName2 label").html(msg.bookName2);
		    	
		    	$("#bookName3").val(msg.bookSum3);
		    	$(".bookName3 label").html(msg.bookName3);

				$("#bookName4").val(msg.bookSum4);
				$(".bookName4 label").html(msg.bookName4);

				$("#bookName5").val(msg.bookSum5);
				$(".bookName5 label").html(msg.bookName5);
	    	}else{
	    		$("#count").val("0");
		    	$("#paid").val("0");
				$("#cardPay").val("0");
		    	$("#sup").val("0");
		    	$("#suped").val("0");
		    	$("#giftGiving").val("0");
		    	$("#cardGiving").val("0");
		    	$("#exchange").val("0");
		    	$("#fullmembers").val("0");
		    	
		    	
	    	}
	    },
		error:function(){
			alert("错误");
		}
	});
}

$(function () {
	Userstatus.statistics('','','');
});

