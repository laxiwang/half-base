/**
 * 分润结算
 */
var Settlement = {
    id: "SettlementTB",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Settlement.initColumn = function () {
    var columns = [
          {field: 'selectItem', radio: true},
          {title: 'id', field: 'branchsalerId', visible: false, align: 'center', valign: 'middle'},
          {title: '分会', field: 'branchsalerName', align: 'center',  valign: 'middle', sortable: false},
          {title: '待结算金额', field: 'changeAmount', align: 'center', valign: 'middle', sortable: true},
          {title: '待结算销售金额', field: 'xiaoshou', align: 'center', valign: 'middle', sortable: false},
          {title: '待结算管理金额', field: 'guanli', align: 'center', valign: 'middle', sortable: false}
        ];
    return columns;
};

Settlement.settlement_do = function () {
		    var beginTime=$("#beginTime").val();
		    if(beginTime==null || beginTime==''){
		    	  Feng.error("请输入开始时间");
		    	  return false;
			}
		    var endTime=$("#endTime").val();
		    if(endTime==null || endTime==''){
		    	 Feng.error("请输入结束时间");
		    	 return false;
			}
		    var condition=$("#condition").val();

            var id_array=new Array();

            $('.paysource:checked').each(function(){
                id_array.push($(this).val());//向数组中添加元素
            });
            var paysources = id_array.join(',');//将数组元素连接起来以构建一个字符串
         var operation = function() {

        var ajax = new $ax(Feng.ctxPath + "/branchsalerAccount/settlement_do/", function(data){
            if(data == 0 ){
                Feng.info("暂无需要结算的分会！");
                return false;
            }
            Feng.success("结算成功!");
            Settlement.table.refresh();
        },function(data){
            Feng.error("结算失败" + data.responseJSON.message + "!");
        });
        ajax.set("condition", condition);
        ajax.set("beginTime", beginTime);
        ajax.set("endTime", endTime);
        ajax.set("paysources", paysources);
        ajax.start();
    }
    Feng.confirm("确定结算?",operation);
}
/**
 * 检查是否选中
 */
Settlement.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	Settlement.seItem = selected[0];
        return true;
    }
};


Settlement.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['condition'] = $("#condition").val();
    var id_array=new Array();

    $('.paysource:checked').each(function(){
        id_array.push($(this).val());//向数组中添加元素
    });
    var paysources = id_array.join(',');//将数组元素连接起来以构建一个字符串
    if(id_array.length==0){
        Feng.info("至少选择一个订单支付渠道!");
        return false;
    }
    queryData['paysources'] = paysources;
    Settlement.table.refresh({query: queryData});
};




$(function () {
    var defaultColunms = Settlement.initColumn();
    var table = new BSTable("SettlementTB", "/branchsalerAccount/settlement", defaultColunms);
    table.setPaginationType("client");
    Settlement.table = table.init();
    
});

Settlement.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#condition").val("");

   $(".paysource").each(function () {
        $(this).prop("checked", true);
    })
    Settlement.search();
}