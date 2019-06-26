/**
 * 报表用户一单例
 * @type {{seItem: null, id: string, table: null, layerIndex: number}}
 */
var ReportUser = {
    id: "ReportUserTable",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ReportUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '类型', field: 'name', align: 'center', valign: 'middle', sortable: false},
        {title: '数值', field: 'value', align: 'center', valign: 'middle', sortable: false}
    ];
};


/**
 * 查询卡列表
 */
ReportUser.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    ReportUser.table.refresh({query: queryData});
};
/**
 * 导出列表
 */
ReportUser.exportList = function () {
   var beginTime= $("#beginTime").val();
   var endTime=$("#endTime").val();

    var beginTime=$("#beginTime").val();
    if(beginTime==null || beginTime==''){
        beginTime='null';
    }
    var endTime=$("#endTime").val();
    if(endTime==null || endTime==''){
        endTime='null';
    }
   window.location.href=Feng.ctxPath + "/report/exportUserList/"+beginTime+"/"+endTime;
};

/**
 * 检查是否选中
 */
ReportUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ReportUser.seItem = selected[0];
        return true;
    }
};
$(function () {
    var defaultColunms = ReportUser.initColumn();
    var table = new BSTable("ReportUserTable", "/report/reportUserList", defaultColunms);
    table.setPaginationType("client");
    table.setPageSize("20");
    ReportUser.table = table.init();
});
ReportUser.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    ReportUser.search();
}