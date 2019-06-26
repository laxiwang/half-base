/**
 * 激活记录管理初始化
 */
var CardActiveRecord = {
    id: "CardActiveRecord",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardActiveRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '卡号范围', field: 'cardNoRange', align: 'center', valign: 'middle', sortable: false},
        {title: '激活时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: false}
        
        ];
};



/**
 * 点击激活
 */
CardActiveRecord.active = function () {
    var index = layer.open({
        type: 2,
        title: '增加激活',
        area: ['500px', '450px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/card/cardActiveRecord/add'
    });
    this.layerIndex = index;
};



/**
 * 查询激活记录列表
 */
CardActiveRecord.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    CardActiveRecord.table.refresh({query: queryData});
};
/**
 * 导出列表
 */
CardActiveRecord.exportList = function () {
	    var beginTime=$("#beginTime").val();
	    if(beginTime==null || beginTime==''){
	    	beginTime='null';
		}
	    var endTime=$("#endTime").val();
	    if(endTime==null || endTime==''){
	    	endTime='null';
		}
        window.location.href=Feng.ctxPath + "/card/cardActiveRecord/exportList/"+beginTime+"/"+endTime; 
};
/**
 * 检查是否选中
 */
CardActiveRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	CardActiveRecord.seItem = selected[0];
        return true;
    }
};
/**
 * 关闭此对话框
 */
CardActiveRecord.close = function () {
    parent.layer.close(window.parent.CardActiveRecord.layerIndex);
};
$(function () {
	    var defaultColunms = CardActiveRecord.initColumn();
	    var table = new BSTable("CardActiveRecord", "/card/cardActiveRecord/list", defaultColunms);
	    table.setPaginationType("client");
	    CardActiveRecord.table = table.init();
});

CardActiveRecord.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    CardActiveRecord.search();
}