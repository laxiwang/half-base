
var SalerUsers = {
    id: "salerUsers",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
SalerUsers.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: true},
        {title: '角色', field: 'uRole', align: 'center', valign: 'middle', sortable: false, width:'100px'},
        {title: '分会', field: 'bName', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号归属地', field: 'address', align: 'center', valign: 'middle', sortable: false},
        {title: '注册时间', field: 'uCreateTime', align: 'center', valign: 'middle', sortable: true },
        {title: '状态', field: 'uStatus', align: 'center', valign: 'middle', sortable: false , width:'100px'},
        {title: '用户积分', field: 'integral', align: 'center', valign: 'middle', sortable: true}
        ];
        
    return columns;
};
$(function () {
    var defaultColunms = SalerUsers.initColumn();
    var inUserid=$("#inUserid").val();
    var table = new BSTable("salerUsers", "/saler/salerUserList?inUserid="+inUserid, defaultColunms);
    table.setPaginationType("client");
    SalerUsers.table = table.init();
});
