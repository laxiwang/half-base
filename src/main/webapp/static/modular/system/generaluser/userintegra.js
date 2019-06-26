/**
 * 积分排行
 */
var UserIntegra = {
    id: "UserIntegraTab",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
UserIntegra.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '用户名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'userPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '积分', field: 'userFen', align: 'center', valign: 'middle', sortable: false}
    ];
    return columns;
};


/**
 * 查询用户列表
 */
UserIntegra.search = function () {
    var beginTime= $("#beginTime").val();
    var endTime= $("#endTime").val();
    if(beginTime==null||endTime==null){
        Feng.error("开始时间与结束时间需同时输入 !");
        return false;
    }
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    UserIntegra.table.refresh({query: queryData});
};


/**
 * 关闭此对话框
 */
UserIntegra.close = function() {
    if (window.parent.UserIntegra.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.UserIntegra.layerIndex);
    }

};
;


$(function () {
    var defaultColunms = UserIntegra.initColumn();
    var table = new BSTable("UserIntegraTab", "/generalUser/userIntegra", defaultColunms);
    table.setPaginationType("client");
    UserIntegra.table = table.init();
});
/**
 * 检查是否选中
 */
UserIntegra.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        UserIntegra.seItem = selected[0];
        return true;
    }
};

UserIntegra.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    UserIntegra.search();
}