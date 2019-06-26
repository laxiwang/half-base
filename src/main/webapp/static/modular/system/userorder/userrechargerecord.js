/**
 * 充值记录
 */
var UserRechargeRecord = {
    id: "UserRechargeRecordTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
UserRechargeRecord.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'userPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '分会', field: 'branchSalerName', align: 'center', valign: 'middle', sortable: false},
        {title: '充值金额', field: 'rechargePoint', align: 'center', valign: 'middle', sortable: true},
        {title: '充值时间', field: 'creatTime', align: 'center', valign: 'middle', sortable: true}
    ];
    return columns;
};


UserRechargeRecord.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    UserRechargeRecord.table.refresh({query: queryData});
};



$(function () {
    var defaultColunms = UserRechargeRecord.initColumn();
    var table = new BSTable("UserRechargeRecordTable", "/userOrder/userRechargeRecordList", defaultColunms);
    table.setPaginationType("client");
    UserRechargeRecord.table = table.init();
});

/**
 * 检查是否选中
 */
UserRechargeRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        UserRechargeRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 导出列表
 */
UserRechargeRecord.exportList = function () {
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
    window.location.href=Feng.ctxPath + "/userOrder/exportUserRechargeRecord/"+condition+"/"+beginTime+"/"+endTime;
};

UserRechargeRecord.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    UserRechargeRecord.search();
}