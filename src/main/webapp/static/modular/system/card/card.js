
var Card = {
    id: "Card",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Card.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '卡号', field: 'card_no', align: 'center', valign: 'middle', sortable: true},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: false},
        {title: '价格', field: 'fee', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: false},
        {title: '分会', field: 'bname', align: 'center', valign: 'middle', sortable: false},
        {title: '使用人', field: 'phone', align: 'center', valign: 'middle', sortable: false},
        {title: '使用时间', field: 'useTime', align: 'center', valign: 'middle', sortable: true},
        {title: '激活时间', field: 'activeTime', align: 'center', valign: 'middle', sortable: true},
        {title: '划拨时间', field: 'wholeTime', align: 'center', valign: 'middle', sortable: true}
        ];
};


/**
 * 查询卡列表
 */
Card.search = function () {
    var queryData = {};
    queryData['cardNoStart'] = $("#cardNoStart").val();
    queryData['cardNoEnd'] = $("#cardNoEnd").val();
    queryData['status'] = $("#status").val();
    Card.table.refresh({query: queryData});
};
/**
 * 导出列表
 */
Card.exportList = function () {
        window.location.href=Feng.ctxPath + "/saler/cardWholeSale/exportWholeList/"+beginTime+"/"+endTime+"/"+status+"/"+flag; 
};
/**
 * 导出实体卡
 */
Card.exportPicture = function () {
    if (this.check()) {
        window.location.href=Feng.ctxPath + "/card/exportPicture/"+Card.seItem.card_no;
    }

};
/**
 * 检查是否选中
 */
Card.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Card.seItem = selected[0];
        return true;
    }
};
$(function () {
	    var defaultColunms = Card.initColumn();
	    var table = new BSTable("Card", "/card/list", defaultColunms);
	    //table.setPaginationType("client");
	    Card.table = table.init();
});
Card.resetSearch = function () {
    $("#cardNoStart").val("");
    $("#cardNoEnd").val("");
    $("#status").val("");
    Card.search();
}