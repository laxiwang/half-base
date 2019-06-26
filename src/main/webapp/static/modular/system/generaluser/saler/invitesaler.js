/**
 * 邀请销售管理的单例对象
 */
var Invitesaler = {
    id: "invitesaler",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
Invitesaler.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'uid', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'uName', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'uPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '邀请状态', field: 'silStatus', align: 'center', valign: 'middle', sortable: false},
        {title: '销售总计', field: 'fee', align: 'center', valign: 'middle', sortable: false}
       ];
        
    return columns;
};


/**
 * 查询用户列表
 */
Invitesaler.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Invitesaler.table.refresh({query: queryData});
};



/**
 * 检查是否选中
 */
Invitesaler.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	Invitesaler.seItem = selected[0];
        return true;
    }
};
Invitesaler.openSalerInvite = function () {
	  if (this.check()) {
	        var index = layer.open({
	            type: 2,
	            title: '邀请销售大使',
	            area: ['300px', '300px'], //宽高
	            fix: false, //不固定
	            maxmin: true,
	            content: Feng.ctxPath + '/saler/openSalerInvite/' + this.seItem.uid
	        });
	        this.layerIndex = index;
	    }
}
	

/**
 * 点击邀请时
 * 
 */

Invitesaler.addSalerInvite = function () {
        var userId = $("#id").val();
        if(userId == null || userId == ''){
        	 Feng.error("请选择用户!");
        		return false;
        }
        var remark = $("#remark").val();
        if(remark == null || remark == '' ){
       	 	Feng.error("请输入提成!");
       		return false;
        }
        remark = Base64.encode(remark);
        var ajax = new $ax(Feng.ctxPath + "/saler/addSalerInvite/"+remark+"/"+userId, function (data) {
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
        		 window.parent.Invitesaler.table.refresh();
        		 Invitesaler.close();
         	}
        }, function (data) {
            Feng.error("邀请失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
};

/**
 * 点击撤销邀请时
 * 
 */
Invitesaler.closeSalerInvite = function () {
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
        		 Invitesaler.table.refresh();
         	}
          
        }, function (data) {
            Feng.error("撤销失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};


Invitesaler.resetSearch = function () {
	$("#condition").val("");
	Invitesaler.search();
}
/**
 * 关闭此对话框
 */
Invitesaler.close = function() {
	if (window.parent.Invitesaler.layerIndex == -1) {
		parent.layer.close(1);
	} else {
		parent.layer.close(window.parent.Invitesaler.layerIndex);
	}

};
$(function () {
    var defaultColunms = Invitesaler.initColumn();
    var table = new BSTable("invitesaler", "/saler/inviteSalerList", defaultColunms);
    //table.setPaginationType("client");
    Invitesaler.table = table.init();
});
var Base64 = {
		_keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
		encode: function(e) {
			var t = "";
			var n, r, i, s, o, u, a;
			var f = 0;
			e = Base64._utf8_encode(e);
			while (f < e.length) {
				n = e.charCodeAt(f++);
				r = e.charCodeAt(f++);
				i = e.charCodeAt(f++);
				s = n >> 2;
				o = (n & 3) << 4 | r >> 4;
				u = (r & 15) << 2 | i >> 6;
				a = i & 63;
				if (isNaN(r)) {
					u = a = 64
				} else if (isNaN(i)) {
					a = 64
				}
				t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(u) + this._keyStr.charAt(a)
			}
			return t
		},
		decode: function(e) {
			var t = "";
			var n, r, i;
			var s, o, u, a;
			var f = 0;
			e = e.replace(/[^A-Za-z0-9+/=]/g, "");
			while (f < e.length) {
				s = this._keyStr.indexOf(e.charAt(f++));
				o = this._keyStr.indexOf(e.charAt(f++));
				u = this._keyStr.indexOf(e.charAt(f++));
				a = this._keyStr.indexOf(e.charAt(f++));
				n = s << 2 | o >> 4;
				r = (o & 15) << 4 | u >> 2;
				i = (u & 3) << 6 | a;
				t = t + String.fromCharCode(n);
				if (u != 64) {
					t = t + String.fromCharCode(r)
				}
				if (a != 64) {
					t = t + String.fromCharCode(i)
				}
			}
			t = Base64._utf8_decode(t);
			return t
		},
		_utf8_encode: function(e) {
			e = e.replace(/rn/g, "n");
			var t = "";
			for (var n = 0; n < e.length; n++) {
				var r = e.charCodeAt(n);
				if (r < 128) {
					t += String.fromCharCode(r)
				} else if (r > 127 && r < 2048) {
					t += String.fromCharCode(r >> 6 | 192);
					t += String.fromCharCode(r & 63 | 128)
				} else {
					t += String.fromCharCode(r >> 12 | 224);
					t += String.fromCharCode(r >> 6 & 63 | 128);
					t += String.fromCharCode(r & 63 | 128)
				}
			}
			return t
		},
		_utf8_decode: function(e) {
			var t = "";
			var n = 0;
			var r = c1 = c2 = 0;
			while (n < e.length) {
				r = e.charCodeAt(n);
				if (r < 128) {
					t += String.fromCharCode(r);
					n++
				} else if (r > 191 && r < 224) {
					c2 = e.charCodeAt(n + 1);
					t += String.fromCharCode((r & 31) << 6 | c2 & 63);
					n += 2
				} else {
					c2 = e.charCodeAt(n + 1);
					c3 = e.charCodeAt(n + 2);
					t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
					n += 3
				}
			}
			return t
		}
	}
