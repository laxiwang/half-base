
var SalerOrders = {
    id: "salerOrders",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
SalerOrders.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uoid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'phone', align: 'center', valign: 'middle', sortable: false},
        {title: '课程名称', field: 'bookName', align: 'center', valign: 'middle', sortable: false},
        {title: '课程价格', field: 'orderFee', align: 'center', valign: 'middle', sortable: true},
        {title: '支付渠道', field: 'paySource', align: 'center', valign: 'middle', sortable: false},
        {title: '支付方式', field: 'payRole', align: 'center', valign: 'middle', sortable: false},
        {title: '推广/销售人', field: 'inName', align: 'center', valign: 'middle', sortable: false},
        {title: '付款时间', field: 'payTime', align: 'center', valign: 'middle', sortable: true,width:'250px'}
        ];
    return columns;
};
$(function () {
    var defaultColunms = SalerOrders.initColumn();
    var inUserid=$("#inUserid").val();
    var table = new BSTable("salerOrders", "/saler/salerOrderList?inUserid="+inUserid, defaultColunms);
    table.setPaginationType("client");
    SalerOrders.table = table.init();
});
