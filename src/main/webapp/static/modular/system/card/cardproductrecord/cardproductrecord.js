/**
 * 生产记录管理初始化
 */
var CardProductRecord = {
    id: "CardProductRecord",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardProductRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '卡号范围', field: 'cardNoRange', align: 'center', valign: 'middle', sortable: false},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: false},
        {title: '价格', field: 'fee', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: false},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: false},
        {title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '投放时间', field: 'putontime', align: 'center', valign: 'middle', sortable: true},
        {title: '导出时间', field: 'exporttime', align: 'center', valign: 'middle', sortable: true}
        ];
};



/**
 * 点击添加生产时
 */
CardProductRecord.openAddCardProductRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加生产',
        area: ['500px', '450px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/card/cardProductRecord/add'
    });
    this.layerIndex = index;
};


/**
 * 投放生产记录
 */
CardProductRecord.puton = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/card/cardProductRecord/puton/"+CardProductRecord.seItem.id, function (data) {
        	  if(data == -1 ){
        		  Feng.error(" 生产记录不存在!");
        		  return false;
        	  }
           	  if(data == -2 ){
       		  Feng.error(" 当前记录已经投放不能重复投放!");
       		  return false;
       	      }
       	      if(data == -3 ){
        		  Feng.error(" 已经有卡号被投放!");
        		  return false;
        	    }
       	      Feng.success("投放成功!");
                CardProductRecord.table.refresh();
            }, function (data) {
                Feng.error("投放失败!" + data.responseJSON.message + "!");
            });
            ajax.start();
        };
        Feng.confirm("是否投放该记录?", operation);
    }
};
/**
 * 导出生产记录的半成品实体卡
 */
CardProductRecord.exportExcel = function () {
    if (this.check()) {
    		 window.location.href=Feng.ctxPath + "/card/cardProductRecord/export_excel/"+CardProductRecord.seItem.id; 
    }
};
/**
 * 删除生产记录
 */
CardProductRecord.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/card/cardProductRecord/delete/"+CardProductRecord.seItem.id, function (data) {
                
                
        	  if(data == -1 ){
        		  Feng.error(" 生产记录不存在!");
        		  return false;
        	  }
           	  if(data == -2 ){
       		  Feng.error(" 当前记录已经投放不能删除!");
       		  return false;
       	      }
       	         if(data == -3 ){
        		  Feng.error(" 已经有卡号被投放!");
        		  return false;
        	      }
       	      Feng.success("删除成功!");
                CardProductRecord.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.start();
        };
        Feng.confirm("是否刪除该记录?", operation);
    }
};

/**
 * 导出列表
 */
CardProductRecord.exportPicture = function () {
	 if (this.check()) {
       window.location.href=Feng.ctxPath + "/card/cardProductRecord/exportPicture/"+CardProductRecord.seItem.id; 
	 }
};
/**
 * 查询生产记录列表
 */
CardProductRecord.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    CardProductRecord.table.refresh({query: queryData});
};

/**
 * 检查是否选中
 */
CardProductRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	CardProductRecord.seItem = selected[0];
        return true;
    }
};
/**
 * 关闭此对话框
 */
CardProductRecord.close = function () {
    parent.layer.close(window.parent.CardProductRecord.layerIndex);
};
$(function () {
	    var defaultColunms = CardProductRecord.initColumn();
	    var table = new BSTable("CardProductRecord", "/card/cardProductRecord/list", defaultColunms);
	    table.setPaginationType("client");
	    CardProductRecord.table = table.init();
});
CardProductRecord.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    CardProductRecord.search();
}