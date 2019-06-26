/**
 *用户管理--分会分润记录的单例对象
 */
var Bac = {
    id: "Bac",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Bac.initColumn = function () {
    var columns = [
           {field: 'selectItem', radio: true},
          {title: 'id', field: 'bacId', visible: false, align: 'center', valign: 'middle'},
          {title: '订单号', field: 'orderno', visible: false, align: 'center',  valign: 'middle', sortable: false},
          {title: '订单来源', field: 'orderBranchsaler', align: 'center', valign: 'middle', sortable: false},
          {title: '分润金额', field: 'changeAmount', align: 'center', align: 'middle', sortable: true},
         /* {title: '变动之前余额', field: 'beforeChangeAmount', visible: false, align: 'center', valign: 'middle', sortable: true},
          {title: '变动之后余额', field: 'AfterChangeAmount', visible: false, align: 'center', valign: 'middle', sortable: true},*/
          {title: '类型', field: 'type', align: 'center', visible: false, valign: 'middle', sortable: true},
          {title: '分润时间', field: 'addTime', align: 'center', valign: 'middle', sortable: true},
          {title: '订单支付渠道', field: 'paySource', align: 'center', align: 'middle', sortable: false},
          {title: '订单支付方式', field: 'payRole', align: 'center', align: 'middle', sortable: false}
       /*   {title: 'id', field: 'bacId', visible: false, align: 'center', valign: 'middle'},
          {title: '姓名', field: 'uName', align: 'center',  valign: 'middle', sortable: true},
          {title: '手机号', field: 'userPhone', align: 'center',  valign: 'middle', sortable: true},
          {title: '分会', field: 'userbranchsalerfullName', align: 'center', valign: 'middle', sortable: true},
          {title: '课程名称', field: 'bookName', align: 'center', valign: 'middle', sortable: true},
          {title: '课程价格', field: 'bookFee', align: 'center', valign: 'middle', sortable: true},
          {title: '支付渠道', field: 'paySource', align: 'center', valign: 'middle', sortable: true},
          {title: '支付方式', field: 'payRole', align: 'center', valign: 'middle', sortable: true},
          {title: '推广/销售人', field: 'inName', align: 'center', valign: 'middle', sortable: true},
          {title: '分润金额', field: 'changeAmount', align: 'center', align: 'middle', sortable: true},
          {title: '订单时间', field: 'addTime', align: 'center', valign: 'middle', sortable: true}*/
        ];
    return columns;
};


/**
 * 查询列表
 */
Bac.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['accountType'] = $("#accountType").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['paysource'] = $("#paysource").val();
    queryData['payrole'] = $("#payrole").val();
    Bac.table.refresh({query: queryData});
    Bac.statistics($("#condition").val(),$("#accountType").val(), $("#paysource").val(),$("#payrole").val(),$("#beginTime").val(),$("#endTime").val());
};

/**
 * 导出列表
 */
Bac.exportList = function () {
    var condition=$("#condition").val();
    if(condition==null || condition==''){
        condition='null';
    }
    var accountType=$("#accountType").val();
    if(accountType==null||accountType==''){
        accountType=-1;
    }
    var beginTime=$("#beginTime").val();
    if(beginTime==null || beginTime==''){
        beginTime='null';
    }
    var endTime=$("#endTime").val();
    if(endTime==null || endTime==''){
        endTime='null';
    }
    var paysource=$("#paysource").val();
    if(paysource==null || paysource==''){
        paysource=-1;
    }
    var payrole=$("#payrole").val();
    if(payrole==null || payrole==''){
        payrole=-1;
    }
    window.location.href=Feng.ctxPath + "/branchsalerAccount/exportList/"+condition+"/"+accountType+"/"+paysource+"/"+payrole+"/"+beginTime+"/"+endTime;
};


$(function () {
    var defaultColunms = Bac.initColumn();
    var table = new BSTable("Bac", "/branchsalerAccount/list", defaultColunms);
    table.setPaginationType("client");
    Bac.table = table.init();
    var paysource=$("#paysource").val();
    if(paysource==null || paysource==''){
        paysource=-1;
    }
    var payrole=$("#payrole").val();
    if(payrole==null || payrole==''){
        payrole=-1;
    }
    Bac.statistics($("#condition").val(),$("#accountType").val(),paysource,payrole ,$("#beginTime").val(),$("#endTime").val());

});
//统计接口
Bac.statistics= function (condition,accountType,paysource,payrole,beginTime,endTime){
	$.ajax({
	    type : "POST",
	    url : Feng.ctxPath+"/branchsalerAccount/statistics",
		data:'condition='+condition+"&accountType="+accountType+"&paysource="+paysource+"&payrole="+payrole+"&beginTime="+beginTime+"&endTime="+endTime,
	    success : function (msg) {
	    	$("#all").html(msg.all);
	    	/*$("#in").html(msg.in);
	    	$("#out").html(msg.out);*/
	    },
		error:function(){
			alert("错误");
		}
	});
}



Bac.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#accountType").val("");
    $("#paysource").val("");
    $("#payrole").val("");
    Bac.search();
}