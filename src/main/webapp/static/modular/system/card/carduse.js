
var CardUse = {
    id: "CardUseTable",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardUse.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '卡号', field: 'card_no', align: 'center', valign: 'middle', sortable: true},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: false},
        {title: '价格', field: 'fee', align: 'center', valign: 'middle', sortable: true},
        {title: '使用人', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '使用人分会', field: 'bname', align: 'center', valign: 'middle', sortable: false},
        {title: '使用时间', field: 'useTime', align: 'center', valign: 'middle', sortable: true}
        ];
};


/**
 * 查询卡列表
 */
CardUse.search = function () {
    var queryData = {};
    queryData['cardNoStart'] = $("#cardNoStart").val();
    queryData['cardNoEnd'] = $("#cardNoEnd").val();
    queryData['phone'] = $("#phone").val();
    CardUse.table.refresh({query: queryData});
};

$(function () {
	    var defaultColunms = CardUse.initColumn();
	    var table = new BSTable("CardUseTable", "/card/cardUseRecordList", defaultColunms);
	    table.setPaginationType("client");
	    CardUse.table = table.init();
});
CardUse.resetSearch = function () {
    $("#cardNoStart").val("");
    $("#cardNoEnd").val("");
    $("#phone").val("");
    CardUse.search();
}