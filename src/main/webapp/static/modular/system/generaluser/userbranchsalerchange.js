/**
 * 用户变更分会记录
 */
var UserBchange = {
    id: "UserBchangeTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
UserBchange.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'phone', align: 'center', valign: 'middle', sortable: false},
        {title: '原分会', field: 'oldBname', align: 'center', valign: 'middle', sortable: false},
        {title: '现分会', field: 'newBname', align: 'center', valign: 'middle', sortable: false},
        {title: '变更类型', field: 'changeType',  align: 'center', valign: 'middle', sortable: false},
        {title: '变更时间', field: 'changeTime', align: 'center', valign: 'middle', sortable: false},
        {title: '管理员账号', field: 'account', align: 'center', valign: 'middle', sortable: false}
        ];
    return columns;
};


/**
 * 查询用户列表
 */
UserBchange.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    UserBchange.table.refresh({query: queryData});
};

/**
 * 批量变更分会跳转
 */
UserBchange.userChangeBranchsalerIndex = function () {
    var index = layer.open({
        type: 2,
        title: '批量变更',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/generalUser/userChangeBranchsalerIndex/'
    });
    this.layerIndex = index;
}

$(function () {
	
    var defaultColunms = UserBchange.initColumn();
    var table = new BSTable("UserBchangeTable", "/generalUser/userBranchsalerChange", defaultColunms);
    table.setPaginationType("client");
    UserBchange.table = table.init();
});

/**
 * 检查是否选中
 */
UserBchange.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	UserBchange.seItem = selected[0];
        return true;
    }
};

/**
 * 关闭此对话框
 */
UserBchange.close = function() {
    if (window.parent.UserBchange.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.UserBchange.layerIndex);
    }
}

UserBchange.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    UserBchange.search();
}