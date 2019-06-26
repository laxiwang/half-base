/**
 * 用户管理--无归属订单
 */
var OthernessOrder = {
    id: "othernessOrder",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
OthernessOrder.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '手机号归属市', field: 'city', align: 'center', valign: 'middle', sortable: false},
        {title: '支付总计', field: 'sum', align: 'center', valign: 'middle', sortable: false},
        {title: '公众号支付', field: 'paySource0', align: 'center', valign: 'middle', sortable: false},
        {title: 'ios支付', field: 'paySource1', align: 'center', valign: 'middle', sortable: false},
        {title: 'android支付', field: 'paySource2', align: 'center', valign: 'middle', sortable: false},
        {title: '实体卡支付', field: 'paySource3', align: 'center', valign: 'middle', sortable: false}
        ];
        
    return columns;
};

/**
 * 导出列表
 */
OthernessOrder.exportList = function () {
       window.location.href=Feng.ctxPath + "/generalUser/exportothernessOrderStatistics"; 
};

$(function () {
    var defaultColunms = OthernessOrder.initColumn();
    var table = new BSTable("othernessOrder", "/generalUser/othernessOrderStatistics", defaultColunms);
    table.setPaginationType("client");
    OthernessOrder.table = table.init();
});

