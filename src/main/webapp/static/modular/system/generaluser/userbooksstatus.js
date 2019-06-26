/**
 * 用户课程列表
 */
var UserBooksStatus = {
    id: "UserBooksStatusTab",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
UserBooksStatus.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'userPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '课程', field: 'bookName', align: 'center', valign: 'middle', sortable: false},
        {title: '状态', field: 'userBookStatus', align: 'center', valign: 'middle', sortable: false}
    ];
    return columns;
};


/**
 * 查询用户列表
 */
UserBooksStatus.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    UserBooksStatus.table.refresh({query: queryData});
};



$(function () {

    var defaultColunms = UserBooksStatus.initColumn();
    var userid=$("#userid").val();
    var table = new BSTable("UserBooksStatusTab", "/generalUser/userBooksStatus?userId="+userid, defaultColunms);
    table.setPaginationType("client");
    UserBooksStatus.table = table.init();
});

/**
 * 检查是否选中
 */
UserBooksStatus.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        UserBooksStatus.seItem = selected[0];
        return true;
    }
};



UserBooksStatus.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    UserBooksStatus.search();
}