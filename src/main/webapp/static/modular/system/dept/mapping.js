/**
 * 分会映射管理初始化
 */
var Mapping = {
    id: "MappingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Mapping.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'mappingId', align: 'center', valign: 'middle',width:'50px'},
        {title: '省', field: 'province', align: 'center', valign: 'middle', sortable: false},
        {title: '市', field: 'city', align: 'center', valign: 'middle', sortable: false},
        {title: '分会', field: 'fullName', align: 'center', valign: 'middle', sortable: false}];
};

/**
 * 检查是否选中
 */
Mapping.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        this.seItem = selected[0];
        console.log(this.seItem);
        return true;
    }
};

/**
 * 点击添加分会
 */
Mapping.openAddMapping = function () {

    var index = layer.open({
        type: 2,
        title: '添加映射',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dept/mapping_add_index'
    });
    this.layerIndex = index;
};

/**
 * 打开查看映射详情
 */
Mapping.openMappingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '映射详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/mapping_update_index/' + Mapping.seItem.mappingId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除分会映射
 */
Mapping.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dept/mapping_delete", function () {
                Feng.success("删除成功!");
                Mapping.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            console.log(Mapping.seItem);
            ajax.set("id",Mapping.seItem.mappingId);
            ajax.start();
        };
        Feng.confirm("是否刪除该分会映射?", operation);
    }
};

/**
 * 查询分会列表
 */
Mapping.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Mapping.table.refresh({query: queryData});
};

Mapping.resetSearch = function () {
    $("#condition").val("");
    Mapping.search();
}
$(function () {
    var defaultColunms = Mapping.initColumn();
    var table = new BSTable("MappingTable", "/dept/mappingList", defaultColunms);
    table.setPaginationType("client");
    Mapping.table = table.init();
});
