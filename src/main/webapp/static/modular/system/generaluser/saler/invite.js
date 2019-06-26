/**
 * 邀请记录管理的单例对象
 */
var Invite = {
    id: "invite",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Invite.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: false},
        {title: '邀请时间', field: 'inviteTime', align: 'center', valign: 'middle', sortable: true},
        {title: '变更时间', field: 'updatetime', align: 'center', valign: 'middle', sortable: true}
      /*  {title: '销售总计', field: 'fee', align: 'center', valign: 'middle', sortable: true}*/
        ];
        
    return columns;
};


/**
 * 查询用户列表
 */
Invite.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['status'] = $("#status").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    Invite.table.refresh({query: queryData});
};



/**
 * 检查是否选中
 */
Invite.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	Invite.seItem = selected[0];
        return true;
    }
};

Invite.resetSearch = function () {
	$("#condition").val("");
    $("#status").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    Invite.search();
}

$(function () {
    var defaultColunms = Invite.initColumn();
    var table = new BSTable("invite", "/saler/inviteList", defaultColunms);
    table.setPaginationType("client");
    Invite.table = table.init();
});