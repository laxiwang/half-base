/**
 * 活跃用户统计
 */
var Active = {
    id: "ActiveTab",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Active.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '类型', field: 'name', align: 'center', valign: 'middle', sortable: false},
        {title: '人数', field: 'value', align: 'center', valign: 'middle', sortable: false}
        ];
    return columns;
};


/**
 * 查询用户列表
 */
Active.search = function () {
	var beginTime= $("#beginTime").val();
	var endTime= $("#endTime").val();
	if(beginTime==null||endTime==null){
		Feng.error("开始时间与结束时间需同时输入 !");
		return false;
	}
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    Active.table.refresh({query: queryData});
};


/**
 * 关闭此对话框
 */
Active.close = function() {
	if (window.parent.Active.layerIndex == -1) {
		parent.layer.close(1);
	} else {
		parent.layer.close(window.parent.Active.layerIndex);
	}

};
;


$(function () {
    var defaultColunms = Active.initColumn();
    var table = new BSTable("ActiveTab", "/generalUser/userActive", defaultColunms);
    table.setPaginationType("client");
    Active.table = table.init();
});
/**
 * 检查是否选中
 */
Active.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	Active.seItem = selected[0];
        return true;
    }
};

Active.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    Active.search();
}