/**
 * 划拨记录管理初始化
 */
var CardWholeSale = {
    id: "CardWholeSale",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardWholeSale.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '卡号范围', field: 'cardNoRange', align: 'center', valign: 'middle', sortable: false},
        {title: '划拨分会', field: 'fromBname', align: 'center', valign: 'middle', sortable: false},
       // {title: '划拨价格', field: 'fee', align: 'center', valign: 'middle', sortable: true},
        {title: '划拨状态', field: 'status', align: 'center', valign: 'middle', sortable: false},
		{title: '划拨类型', field: 'type', align: 'center', valign: 'middle', sortable: false},
		{title: '划拨时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '确认时间', field: 'updatetime', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: true}
        ];
};


/**
 * 查询生产记录列表
 */
CardWholeSale.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['status'] = $("#status").val();
    CardWholeSale.table.refresh({query: queryData});
};


/**
 * 确认接收
 */
CardWholeSale.confirmWholeSale = function () {
    if (this.check()) {
	    //提交信息	
	      var ajax = new $ax(Feng.ctxPath + "/card/cardWholeSale/confirmWholeSale/"+this.seItem.id, function(data){
	    	if(data == -1 ){
	    		Feng.error("划拨记录不存在!");
	    		return false;
	    	}
	    	if(data == -2 ){
	    		Feng.error("划拨记录已被划拨方取消，请与划拨分会联系!");
	    		return false;
	    	}
	    	if(data == -3 ){
	    		Feng.error("登录人分会 与 接收划拨记录分会 不一致!");
	    		return false;
	    	}
	    	if(data == -4 ){
	    		Feng.error("该记录已接收!");
	    		return false;
	    	}
	        Feng.success("接收成功!");
	        CardWholeSale.table.refresh();
	    },function(data){
	        Feng.error("接收失败!" + data.responseJSON.message + "!");
	    });
	    ajax.start();
    }
};
/**
 * 导出列表
 */
CardWholeSale.exportList = function () {
	    var beginTime=$("#beginTime").val();
	    if(beginTime==null || beginTime==''){
	    	beginTime='null';
		}
	    var endTime=$("#endTime").val();
	    if(endTime==null || endTime==''){
	    	endTime='null';
		}
	    var status=$("#status").val();
	    if(status==null || status==''){
	    	status=-1;
		}
	    var flag= false;
        window.location.href=Feng.ctxPath + "/card/cardWholeSale/exportWholeList/"+beginTime+"/"+endTime+"/"+status+"/"+flag; 
};
/**
 * 导出半月卡表格
 */
CardWholeSale.exportHalfExecl = function () {
	if (this.check()) {
		var id=this.seItem.id;
		var ajax = new $ax(Feng.ctxPath + "/card/cardWholeSale/checkHalfCard/"+id, function(data){
			if(data==1){
				Feng.error("当前接收记录不是半月卡!");
				return false;
			}
			if(data==2){
				Feng.error("当前接收记录不是确认状态!");
				return false;
			}
			window.location.href=Feng.ctxPath + "/card/cardWholeSale/exportHalfExecl/"+id;

		},function(data){
			Feng.error("服务器异常!" + data.responseJSON.message + "!");
		});
		ajax.start();

	}
}

/**
 * 导出半月卡图片
 */
CardWholeSale.exportHalfPicture = function () {
	 if (this.check()) {
		   var id=this.seItem.id;
	       var ajax = new $ax(Feng.ctxPath + "/card/cardWholeSale/checkHalfCard/"+id, function(data){
			   if(data==1){
				   Feng.error("当前接收记录不是半月卡!");
				   return false;
			   }
			   if(data==2){
				   Feng.error("当前接收记录不是确认状态!");
				   return false;
			   }
			   window.location.href=Feng.ctxPath + "/card/cardWholeSale/exportHalfPicture/"+id;
	    },function(data){
	        Feng.error("服务器异常!" + data.responseJSON.message + "!");
	    });
	    ajax.start();



		
	 }
};

/**
 * 检查是否选中
 */
CardWholeSale.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	CardWholeSale.seItem = selected[0];
        return true;
    }
};
/**
 * 关闭此对话框
 */
CardWholeSale.close = function () {
    parent.layer.close(window.parent.CardWholeSale.layerIndex);
};
$(function () {
	    var defaultColunms = CardWholeSale.initColumn();
	    var table = new BSTable("CardWholeSale", "/card/cardWholeSale/wholeInList", defaultColunms);
	    table.setPaginationType("client");
	    CardWholeSale.table = table.init();
});
CardWholeSale.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#status").val("");
    CardWholeSale.search();
}