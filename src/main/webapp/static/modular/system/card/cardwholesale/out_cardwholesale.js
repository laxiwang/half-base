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
        {title: '划拨给的分会', field: 'toBname', align: 'center', valign: 'middle', sortable: false},
       // {title: '划拨价格', field: 'fee', align: 'center', valign: 'middle', sortable: true},
        {title: '划拨状态', field: 'status', align: 'center', valign: 'middle', sortable: false},
        {title: '划拨类型', field: 'type', align: 'center', valign: 'middle', sortable: false},
        {title: '划拨时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '确认时间', field: 'updatetime', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: false}
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
 * 点击划拨时
 */
CardWholeSale.openAddCardWholeSale = function () {
    var index = layer.open({
        type: 2,
        title: '添加划拨',
        area: ['500px', '450px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/card/cardWholeSale/add'
    });
    this.layerIndex = index;
};

/**
 * 点击调拨时
 */
CardWholeSale.openAddCardWholeSalePrivate = function () {
    var index = layer.open({
        type: 2,
        title: '添加划拨',
        area: ['500px', '450px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/card/cardWholeSale/addPrivate'
    });
    this.layerIndex = index;
};
/**
 * 取消划拨
 */
CardWholeSale.cancelWholeSale = function () {
    if (this.check()) {
	    //提交信息	
	      var ajax = new $ax(Feng.ctxPath + "/card/cardWholeSale/cancelWholeSale/"+this.seItem.id, function(data){
	    	if(data == -1 ){
	    		Feng.error("划拨记录不存在!");
	    		return false;
	    	}
	    	if(data == -2 ){
	    		Feng.error("划拨记录已被确认接受，请与划拨给的分会联系处理!");
	    		return false;
	    	}
	        Feng.success("取消成功!");
	        CardWholeSale.table.refresh();
	    },function(data){
	        Feng.error("取消失败!" + data.responseJSON.message + "!");
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
	    var flag= true;
        window.location.href=Feng.ctxPath + "/card/cardWholeSale/exportWholeList/"+beginTime+"/"+endTime+"/"+status+"/"+flag; 
};

/**
 * 关闭此对话框
 */
CardWholeSale.close = function () {
    parent.layer.close(window.parent.CardWholeSale.layerIndex);
};
$(function () {
	    var defaultColunms = CardWholeSale.initColumn();
	    var table = new BSTable("CardWholeSale", "/card/cardWholeSale/wholeOutList", defaultColunms);
	    table.setPaginationType("client");
	    CardWholeSale.table = table.init();
});
CardWholeSale.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#status").val("");
    CardWholeSale.search();
}