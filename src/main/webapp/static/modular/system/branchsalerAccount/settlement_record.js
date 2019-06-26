/**
 *结算记录
 */
var SettlementRecord = {
    id: "SettlementRecordTAB",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
SettlementRecord.initColumn = function () {
    var columns = [
           {field: 'selectItem', radio: true},
          {title: 'sid', field: 'sId', visible: false, align: 'center', valign: 'middle'},
          {title: 'bid', field: 'bId', visible: false, align: 'center', valign: 'middle'},
          {title: '分会名称', field: 'fullName',  align: 'center',  valign: 'middle', sortable: false},
          {title: '分会等级', field: 'levelName', align: 'center', valign: 'middle', sortable: false},
          {title: '结算金额', field: 'fee', align: 'center', align: 'middle', sortable: true},
          {title: '结算时间段', field: 'settlementTime',  align: 'center', valign: 'middle', sortable: false},
          {title: '结算时间', field: 'createTime',  align: 'center', valign: 'middle', sortable: true},
          {title: '结算订单类型', field: 'paysources',  align: 'center', valign: 'middle', sortable: false},
          {title: '结算状态', field: 'payStatus',  align: 'center', valign: 'middle', sortable: false},
          {title: '开票状态', field: 'status',  align: 'center', valign: 'middle', sortable: false},
          {title: '发票号码', field: 'invoiceNumber',  align: 'center', valign: 'middle', sortable: false}
        ];
    return columns;
};


/**
 * 查询结算列表
 */
SettlementRecord.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['bsr_status'] = $("#bsr_status").val();
    queryData['pay_status'] = $("#pay_status").val();
    SettlementRecord.table.refresh({query: queryData});
};

/**
 * 设置打款状态
 */
SettlementRecord.payStatus= function () {
    if (this.check()) {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/branchsalerAccount/updateSettlementRecordPayStatus", function(data) {
            Feng.success("修改成功!");
              SettlementRecord.table.refresh();

        }, function(data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id",this.seItem.sId);
        ajax.start();
    }
}
/**
 * 导出结算记录表格
 */
SettlementRecord.exportList = function () {
		var condition=$("#condition").val();
		if(condition==null || condition==''){
			condition='null';
		}
        var bsr_status=$("#bsr_status").val();
        if(bsr_status==null || bsr_status==''){
            bsr_status=-1;
        }
        var pay_status=$("#pay_status").val();
        if(pay_status==null || pay_status==''){
            pay_status=-1;
        }
	    var beginTime=$("#beginTime").val();
	    if(beginTime==null || beginTime==''){
	    	beginTime='null';
		}
	    var endTime=$("#endTime").val();
	    if(endTime==null || endTime==''){
	    	endTime='null';
		}
       window.location.href=Feng.ctxPath + "/branchsalerAccount/exportSettlementRecord/"+condition+"/"+bsr_status+"/"+pay_status+"/"+beginTime+"/"+endTime;
};

/**
 * 点击发票按钮时
 */
SettlementRecord.setInvoiceNumber = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '设置发票',
            area: ['600px', '350px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/branchsalerAccount/setInvoiceNumber_index/'+this.seItem.sId
        });
        this.layerIndex = index;
    }

};
/**
 * 检查是否选中
 */
SettlementRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SettlementRecord.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = SettlementRecord.initColumn();
    var table = new BSTable("SettlementRecordTAB", "/branchsalerAccount/settlementRecord", defaultColunms);
    table.setPaginationType("client");
    SettlementRecord.table = table.init();
    
});
/**
 * 提交修改发票
 */
SettlementRecord.setInvoiceNumberSubmit = function() {
    var id= $("#id").val();
    var num= $("#num").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/branchsalerAccount/updateSettlementRecord", function(data) {
        Feng.success("修改成功!");
        if (window.parent.SettlementRecord != undefined) {
            window.parent.SettlementRecord.table.refresh();
            SettlementRecord.close();
        }
    }, function(data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.set("num",num);
    ajax.start();
};
/**
 * 关闭此对话框
 */
SettlementRecord.close = function() {
    if (window.parent.SettlementRecord.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.SettlementRecord.layerIndex);
    }

};
SettlementRecord.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#bsr_status").val("");
    $("#pay_status").val("");
    SettlementRecord.search();
}