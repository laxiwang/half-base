/**
 * 用户管理管理--订单管理的单例对象
 */
var UserOrder = {
    id: "UserOrder",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
UserOrder.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '省级分会', field: 'tempSimplename',  visible: false, align: 'center', valign: 'middle', sortable: false},
        {title: '订单分会', field: 'branchsalerName',  visible: false, align: 'center', valign: 'middle', sortable: false},
        {title: '分会级别', field: 'levelName', visible: false, align: 'center',  valign: 'middle', sortable: true},
        {title: 'id', field: 'uoid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'phone', align: 'center', valign: 'middle', sortable: false},
        {title: '课程名称', field: 'bookName', align: 'center', valign: 'middle', sortable: false},
        {title: '课程价格', field: 'orderFee', align: 'center', valign: 'middle', sortable: true},
        {title: '支付状态', field: 'orderState', visible: false, align: 'center', valign: 'middle', sortable: false},
        {title: '支付渠道', field: 'paySource', align: 'center', valign: 'middle', sortable: false},
        {title: '支付方式', field: 'payRole', align: 'center', valign: 'middle', sortable: false},
        {title: '推广/销售人', field: 'inName', align: 'center', valign: 'middle', sortable: false},
        {title: '付款时间', field: 'payTime', align: 'center', valign: 'middle', sortable: true,width:'250px'},
        {title: '订单编号', field: 'orderNo',  visible: false, align: 'center', valign: 'middle', sortable: false}


        ];
    return columns;
};


/**
 * 查询用户列表
 */
UserOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['paysource'] = $("#paysource").val();
    queryData['payrole'] = $("#payrole").val();
    UserOrder.table.refresh({query: queryData});
};



$(function () {
    var defaultColunms = UserOrder.initColumn();
    var table = new BSTable("UserOrder", "/userOrder/list", defaultColunms);
    table.setPaginationType("client");
    UserOrder.table = table.init();
});

/**
 * 检查是否选中
 */
UserOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	UserOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 导出列表
 */
UserOrder.exportList = function () {
		var condition=$("#condition").val();
		if(condition==null || condition==''){
			condition='null';
		}
	    var beginTime=$("#beginTime").val();
	    if(beginTime==null || beginTime==''){
	    	beginTime='null';
		}
	    var endTime=$("#endTime").val();
	    if(endTime==null || endTime==''){
	    	endTime='null';
		}
	    var paysource=$("#paysource").val();
	    if(paysource==null || paysource==''){
	    	paysource=-1;
		}
	    var payrole=$("#payrole").val();
	    if(payrole==null || payrole==''){
	    	payrole=-1;
		}
       window.location.href=Feng.ctxPath + "/userOrder/exportList/"+condition+"/"+beginTime+"/"+endTime+"/"+paysource+"/"+payrole; 
};

UserOrder.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#paysource").val("");
    $("#payrole").val("");
    UserOrder.search();
}