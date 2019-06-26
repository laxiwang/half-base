/**
 * 渠道分会数初始化
 */
var AgentBranchSalerNum = {
    id: "AgentBranchSalerNumTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AgentBranchSalerNum.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
       /* {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle',width:'50px'},*/
        {title: '分会简称', field: 'simplename', align: 'center', valign: 'middle', sortable: true,width:'150px'},
        {title: '分会全称', field: 'fullname', align: 'center', valign: 'middle', sortable: true,width:'150px'},
        {title: '分会级别', field: 'levelName', align: 'center', valign: 'middle', sortable: true },
        /*{title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},*/
        {title: '总计', field: 'sum', align: 'center', valign: 'middle', sortable: true},
        {title: '省', field: 'sheng', align: 'center', valign: 'middle', sortable: true},
        {title: '市', field: 'shi', align: 'center', valign: 'middle', sortable: true},
        {title: '区县', field: 'qu', align: 'center', valign: 'middle', sortable: true},
        {title: '小渠道', field: 'xq', align: 'center', valign: 'middle', sortable: true}
    ];
};







/**
 * 查询分会列表
 */
AgentBranchSalerNum.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    AgentBranchSalerNum.table.refresh({query: queryData});
};
/**
 * 导出表格
 */
AgentBranchSalerNum.exportList = function () {
    var condition= $("#condition").val();
    if(condition==null || condition==''){
        condition='null';
    }
    var beginTime=$("#beginTime").val();
    if(beginTime==null || beginTime==''){
        beginTime='null';
    }
    var endTime=$("#endTime").val();
    if(endTime==null || endTime==''){
        endTime='null';
    }
    window.location.href=Feng.ctxPath + "/agent/branchsaler/export_agent_branchSaler_num/"+beginTime+"/"+endTime+"/"+condition;
    Feng.info("数据正在导出,请耐心等待。");
};

AgentBranchSalerNum.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    AgentBranchSalerNum.search();
}
AgentBranchSalerNum.widthAuto = function () {
    $(".fixed-table-container").css("width",'1500px');
}
$(function () {
    var defaultColunms = AgentBranchSalerNum.initColumn();
    var table = new BSTreeTable(AgentBranchSalerNum.id, "/agent/branchsaler/agent_branchSaler_num", defaultColunms);
    table.setExpandColumn(0);

    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    AgentBranchSalerNum.table = table;
    AgentBranchSalerNum.widthAuto();
});
