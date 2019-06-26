/**
 *分会分润详情
 */
var BacDetails = {
    id: "BacDetailsTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
BacDetails.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '订单分会', field: 'orderBranchSaler',  align: 'center',  valign: 'middle', sortable: false},
        {title: '分会级别', field: 'orderLevelName', align: 'center', valign: 'middle', sortable: false},
        {title: '订单金额', field: 'orderFee', align: 'center', align: 'middle', sortable: true},
        {title: '订单编号', field: 'orderNo', align: 'center', visible: false, valign: 'middle', sortable: false},
        {title: '支付渠道', field: 'paySource', align: 'center', valign: 'middle', sortable: false},
        {title: '支付方式', field: 'payRole', align: 'center', align: 'middle', sortable: false},
        {title: '分润时间', field: 'orderCreateTime', align: 'center', align: 'middle', sortable: true},
        {title: '小渠道', field: 'xqChangeAmount', align: 'center', align: 'middle', sortable: false},
        {title: '区级', field: 'quChangeAmount', align: 'center', align: 'middle', sortable: false},
        {title: '市级', field: 'shiChangeAmount', align: 'center', align: 'middle', sortable: false},
        {title: '省级', field: 'shengChangeAmount', align: 'center', align: 'middle', sortable: false},
        {title: '总会', field: 'zongChangeAmount', align: 'center', align: 'middle', sortable: false}
    ];
    return columns;
};

/**
 * 查询列表
 */
BacDetails.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    BacDetails.table.refresh({query: queryData});
};

/**
 * 导出列表
 */
BacDetails.exportList = function () {
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
    window.location.href=Feng.ctxPath + "/branchsalerAccount/exportBranchsalerAccountDetails/"+condition+"/"+beginTime+"/"+endTime;
};


$(function () {
    var defaultColunms = BacDetails.initColumn();
    var table = new BSTable("BacDetailsTable", "/branchsalerAccount/branchsalerAccountDetails", defaultColunms);
    table.setPaginationType("client");
    BacDetails.table = table.init();
});

BacDetails.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    BacDetails.search();
}