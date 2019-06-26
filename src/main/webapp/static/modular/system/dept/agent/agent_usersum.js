/**
 * 渠道分会用户数初始化
 */
var AgentUserSum = {
    id: "AgentUserSumTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AgentUserSum.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
      /*  {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle',width:'50px'},*/
        {title: '分会简称', field: 'simplename', align: 'center', valign: 'middle', sortable: true,width:'150px'},
        {title: '分会全称', field: 'fullname', align: 'center', valign: 'middle', sortable: true,width:'150px'},
        {title: '分会级别', field: 'levelName', align: 'center', valign: 'middle', sortable: true },
        {title: '注册用户', field: 'count', align: 'center', valign: 'middle', sortable: true },
        {title: '正式用户', field: 'fullmembers', align: 'center', valign: 'middle', sortable: true },
        {title: '体验中用户', field: 'sup', align: 'center', valign: 'middle', sortable: true },
        {title: '体验过期用户', field: 'suped', align: 'center', valign: 'middle', sortable: true },
        {title: '赠卡用户', field: 'cardGiving', align: 'center', valign: 'middle', sortable: true },
        {title: '付费用户', field: 'paid', align: 'center', valign: 'middle', sortable: true },
        {title: '实体卡支付用户', field: 'cardPay', align: 'center', valign: 'middle', sortable: true },
        {title: '积分兑换用户', field: 'exchange', align: 'center', valign: 'middle', sortable: true },
        {title: '知识送礼用户', field: 'giftGiving', align: 'center', valign: 'middle', sortable: true }
    ];
};

/**
 * 查询分会列表
 */
AgentUserSum.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    AgentUserSum.table.refresh({query: queryData});
};
/**
 * 导出表格
 */
AgentUserSum.exportList = function () {
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
    window.location.href=Feng.ctxPath + "/agent/branchsaler/export_agent_userSum/"+beginTime+"/"+endTime+"/"+condition;
    Feng.info("数据正在导出,请耐心等待。");
};

AgentUserSum.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    AgentUserSum.search();
}
AgentUserSum.widthAuto = function () {
    $(".fixed-table-container").css("width",'1500px');
}
$(function () {
    var defaultColunms = AgentUserSum.initColumn();
    var table = new BSTreeTable(AgentUserSum.id, "/agent/branchsaler/agent_userSum", defaultColunms);
    table.setExpandColumn(0);
    
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    AgentUserSum.table = table;
    AgentUserSum.widthAuto();
});
