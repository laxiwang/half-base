/**
 * 用户管理--普通用户管理的单例对象
 */
var GeneralUser = {
    id: "GeneralUser",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
GeneralUser.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
       /* {
            title: '序号',
            align: 'center',
            halign: 'center',
            formatter: function (value, row, index) {
                var options = $('#GeneralUser').bootstrapTable('getOptions');
                console.log(options)
                return "<img/>"+ options.pageSize * (options.pageNumber - 1) + index + 1;
            }
        },*/
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: false},
        {title: '角色', field: 'uRole', align: 'center', valign: 'middle', sortable: false, width:'100px'},
        {title: '分会', field: 'bName', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号归属地', field: 'address', align: 'center', valign: 'middle', sortable: false},
        {title: '注册时间', field: 'uCreateTime', align: 'center', valign: 'middle', sortable: true },
        {title: '推广人信息', field: 'inviterInfo', align: 'center', valign: 'middle', sortable: false },
        {title: '状态', field: 'uStatus', align: 'center', valign: 'middle', sortable: false , width:'100px'},
        {title: '用户积分', field: 'integral', align: 'center', valign: 'middle', sortable: true},
        {title: '最近播放记录', field: 'partlog', align: 'center', valign: 'middle', sortable: false,visible: true},
        {title: '总播放次数', field: 'playCount', align: 'center', valign: 'middle', sortable: true,visible: true},
        {title: '总播放时长', field: 'playSum', align: 'center', valign: 'middle', sortable: true,visible: true},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: false }
        ];
        
    return columns;
};


/**
 * 查询用户列表
 */
GeneralUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['userStatus'] = $("#userStatus").val();
    GeneralUser.table.refresh({query: queryData});
};

/**
 * 点击修改按钮时
 */
GeneralUser.openChangeUser = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑用户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/generalUser/user_edit/' + this.seItem.uid
        });
        this.layerIndex = index;
    }
};
/**
 * 修改备注跳转
 */
GeneralUser.openChangeRemark = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改备注',
            area: ['300px', '300px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/generalUser/remark_edit/' + this.seItem.uid
        });
        this.layerIndex = index;
    }
};
//修改备注
GeneralUser.remarkUpdate =function () {
    var userId = $("#id").val();
    var remark = $("#remark").val();
    var ajax = new $ax(Feng.ctxPath + "/generalUser/remark_edit", function (data) {
   
    	if(data == 1){
    		 Feng.success("修改成功!");
    		 window.parent.GeneralUser.table.refresh();
     	}
    	GeneralUser.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("userId", userId);
    ajax.set("remark", remark);
    ajax.start();
}
/**
 * 关闭此对话框
 */
GeneralUser.close = function() {
	if (window.parent.GeneralUser.layerIndex == -1) {
		parent.layer.close(1);
	} else {
		parent.layer.close(window.parent.GeneralUser.layerIndex);
	}

};
/**
 * 点击查看播放记录
 */
GeneralUser.playPecord = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '播放记录',
            area: ['950px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/generalUser/playRecordIndex/' + this.seItem.uid
        });
        this.layerIndex = index;
    }
};
GeneralUser.userBookStatus = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程状态',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/generalUser/userBooksStatus_index/' + this.seItem.uid
        });
        this.layerIndex = index;
    }
};

/**
 * 点击新增按钮时
 */
GeneralUser.AddUser = function () {
        var index = layer.open({
            type: 2,
            title: '新增用户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/generalUser/generaluser__add_index/' 
        });
    this.layerIndex = index;
};
/**
 * 点击邀请时
 * 
 */

GeneralUser.addSalerInvite = function () {
    if (this.check()) {
        var userId = this.seItem.uid;
        var ajax = new $ax(Feng.ctxPath + "/generalUser/addSalerInvite/"+ this.seItem.uid, function (data) {
        	if(data == -1){
        		 Feng.error("用户已在邀请中 或 已接受邀请 !");
        		return false;
        	}
        	if(data == 0){
       		 Feng.error("分会信息异常，或系统内部错误  !");
       		 return false;
         	}
        	if(data == 1){
        		 Feng.success("邀请已发送!");
        		 GeneralUser.table.refresh();
         	}
          
        }, function (data) {
            Feng.error("邀请失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};
/**
 * 导出用户列表
 */
GeneralUser.exportList = function () {
		var condition=$("#condition").val();
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
	    var userStatus=$("#userStatus").val();
	    if(userStatus==null || userStatus==''){
	    	userStatus=-1;
		}
       window.location.href=Feng.ctxPath + "/generalUser/exportList/"+condition+"/"+beginTime+"/"+endTime+"/"+userStatus; 
};
/**
 * 导出二维码
 */
GeneralUser.exportPicture = function () {
	 if (this.check()) {
		 window.location.href=Feng.ctxPath + "/saler/exportPicture/"+this.seItem.uid ; 
	 }
      
};


/**
 * 冻结用户账户
 * @param userId
 */
GeneralUser.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.uid;
        var ajax = new $ax(Feng.ctxPath + "/generalUser/freeze", function (data) {
            Feng.success("冻结成功!");
            GeneralUser.table.refresh();
        }, function (data) {
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
GeneralUser.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.uid;
        var ajax = new $ax(Feng.ctxPath + "/generalUser/unfreeze", function (data) {
            Feng.success("解除冻结成功!");
            GeneralUser.table.refresh();
        }, function (data) {
            Feng.error("解除冻结失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
}


$(function () {
    var defaultColunms = GeneralUser.initColumn();
    var table = new BSTable("GeneralUser", "/generalUser/list", defaultColunms);
    //table.setPaginationType("client");
    GeneralUser.table = table.init();
});
/**
 * 检查是否选中
 */
GeneralUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	GeneralUser.seItem = selected[0];
        return true;
    }
};

GeneralUser.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#userStatus").val("");
    GeneralUser.search();
}