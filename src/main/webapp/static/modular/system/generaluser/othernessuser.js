/**
 * 用户管理--无归属会员
 */
var OthernessUser = {
    id: "othernessUser",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
OthernessUser.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '手机号归属市', field: 'city', align: 'center', valign: 'middle', sortable: false},
        {title: '用户总计', field: 'sum', align: 'center', valign: 'middle', sortable: false},
        {title: '正式用户数', field: 'fullmembers', align: 'center', valign: 'middle', sortable: false},
        {title: '体验用户数', field: 'sup', align: 'center', valign: 'middle', sortable: false},
        {title: '体验过期用户数', field: 'suped', align: 'center', valign: 'middle', sortable: false}
        ];
        
    return columns;
};

/**
 * 导出列表
 */
OthernessUser.exportList = function () {
       window.location.href=Feng.ctxPath + "/generalUser/exportothernessUserStatistics"; 
};

$(function () {
    var defaultColunms = OthernessUser.initColumn();
    var table = new BSTable("othernessUser", "/generalUser/othernessUserStatistics", defaultColunms);
    table.setPaginationType("client");
    OthernessUser.table = table.init();
});

