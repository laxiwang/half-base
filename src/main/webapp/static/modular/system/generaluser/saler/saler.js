/**
 * 销售管理的单例对象
 */
var Saler = {
    id: "Saler",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Saler.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '邀请时间', field: 'inviteTime', align: 'center', valign: 'middle', sortable: true},
        {title: '生效时间', field: 'updatetime', align: 'center', valign: 'middle', sortable: true},
        {title: '提成', field: 'remark', align: 'center', valign: 'middle', sortable: false},
        {title: '销售总计', field: 'fee', align: 'center', valign: 'middle', sortable: true}
        ];
        
    return columns;
};


/**
 * 查询用户列表
 */
Saler.search = function () {
    var queryData = {};
/*    queryData['status'] = $("#status").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();*/
    queryData['condition'] =$("#condition").val();
    Saler.table.refresh({query: queryData});
};
/**
 * 点击查看销售记录
 */
Saler.salerOrderList = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '销售记录',
            area: ['1000px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/saler/salerOrderListIndex/' + this.seItem.uid
        });
        this.layerIndex = index;
    }
};
Saler.closeSalerInvite = function () {
    if (this.check()) {
        var userId = this.seItem.uid;
        var ajax = new $ax(Feng.ctxPath + "/saler/closeSalerInvite/"+ this.seItem.uid, function (data) {
            if(data == -1){
                Feng.error("没有邀请记录 或 已撤销   !");
                return false;
            }
            if(data == 0){
                Feng.error("系统内部错误  !");
                return false;
            }
            if(data == 1){
                Feng.success("撤销成功!");
                Saler.table.refresh();
            }

        }, function (data) {
            Feng.error("撤销失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};
/**
 * 点击查看推广记录
 */
Saler.salerUserList = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '推广记录',
            area: ['1000px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/saler/salerUserListIndex/' + this.seItem.uid
        });
        this.layerIndex = index;
    }
};
/**
 * 修改用户提成跳转
 */
Saler.updateSalerInviteIndex = function () {
	  if (this.check()) {
	        var index = layer.open({
	            type: 2,
	            title: '提成修改',
	            area: ['300px', '300px'], //宽高
	            fix: false, //不固定
	            maxmin: true,
	            content: Feng.ctxPath + '/saler/updateSalerInvite/' + this.seItem.uid
	        });
	        this.layerIndex = index;
	    }
}
	
/**
 * 修改提成
 */
Saler.updateSalerInvite = function () {
	        var userId =  $("#userId").val();
	        var remark = $("#remark").val();
	        var ajax = new $ax(Feng.ctxPath + "/saler/updateSalerInvite", function (data) {
	        	
	        	if(data == 0){
	       		 Feng.error("系统内部错误  !");
	       		 return false;
	         	}
	        	if(data == 1){
	        		 Feng.success("修改成功!");
	        		 Saler.table.refresh();
	         	}
	        	 if (window.parent.Saler != undefined) {
	     			 window.parent.Saler.table.refresh();
	     			Saler.close();
	     		  }
	          
	        }, function (data) {
	            Feng.error("修改失败!" + data.responseJSON.message + "!");
	            if (window.parent.Saler != undefined) {
	     			 window.parent.Saler.table.refresh();
	     			 t.close();
	     		  }
	        });
	        ajax.set("userId", userId);
	        ajax.set("remark", remark);
	        ajax.start();
}
	
/**
 * 检查是否选中
 */
Saler.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	Saler.seItem = selected[0];
        return true;
    }
};
/**
 * 导出列表
 */
Saler.exportList = function () {
	   var beginTime=$("#beginTime").val();
	    if(beginTime==null || beginTime==''){
	    	beginTime='null';
		}
	    var endTime=$("#endTime").val();
	    if(endTime==null || endTime==''){
	    	endTime='null';
		}
       window.location.href=Feng.ctxPath + "/saler/exportList/"+beginTime+"/"+endTime; 
};

/**
 * 导出二维码
 */
Saler.exportPicture = function () {
	 if (this.check()) {
		 window.location.href=Feng.ctxPath + "/saler/exportPicture/"+this.seItem.uid ; 
	 }
      
};
Saler.resetSearch = function () {
    $("#status").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#condition").val("");
    Saler.search();
}
/**
 * 关闭此对话框
 */
Saler.close = function() {
	if (window.parent.Saler.layerIndex == -1) {
		parent.layer.close(1);
	} else {
		parent.layer.close(window.parent.Saler.layerIndex);
	}

};
$(function () {
    var defaultColunms = Saler.initColumn();
    var table = new BSTable("Saler", "/saler/list", defaultColunms);
    table.setPaginationType("client");
    Saler.table = table.init();
});