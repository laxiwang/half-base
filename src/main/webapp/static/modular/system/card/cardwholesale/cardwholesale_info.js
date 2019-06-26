/**
 * 初始化对话框
 */
var CardWholeSaleInfo = {
		CardWholeSaleData : {}
   
};

/**
 * 关闭此对话框
 */
CardWholeSaleInfo.close = function() {
    parent.layer.close(window.parent.CardWholeSale.layerIndex);
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardWholeSaleInfo.set = function(key, val) {
    this.CardWholeSaleData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardWholeSaleInfo.get = function(key) {
    return $("#" + key).val();
}
/**
 * 显示分会选择的树
 *
 * @returns
 */
CardWholeSaleInfo.showDeptSelectTree = function() {
	
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentDeptMenu").css({
        left : pNameOffset.left + "px",
        top : pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
    	CardWholeSaleInfo.hideDeptSelectTree();
    }
}

/**
 * 隐藏分会选择的树
 */
CardWholeSaleInfo.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}
/**
 * 点击分会ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
CardWholeSaleInfo.onClickDept = function(e, treeId, treeNode) {
    $("#pName").attr("value", CardWholeSaleInfo.zTreeInstance.getSelectedVal());
    $("#branchsaler").attr("value", treeNode.id);
}


/**
 * 隐藏分会选择的树
 */
CardWholeSaleInfo.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}
/**
 * 收集数据
 */
CardWholeSaleInfo.collectData = function() {
    this.set('cardNoStart').set('cardNoEnd').set('remark').set('branchsaler');
}

/**
 * 提交添加划拨
 */
CardWholeSaleInfo.addSubmit  = function() {
    this.collectData();
    var cardNoStart=parseInt($("#cardNoStart").val());
    var cardNoEnd=parseInt($("#cardNoEnd").val());
    var branchsaler=parseInt($("#branchsaler").val());
    if(branchsaler == null || branchsaler == ''){
    	Feng.error("划拨分会不能为空!");
		return false;
    }
	if(cardNoStart == null || cardNoStart == '' || cardNoEnd == null || cardNoEnd == ''){
		Feng.error("范围不正确!");
		return false;
	}
	if(isNaN(cardNoStart)||isNaN(cardNoEnd)){
		Feng.error("输入的不是数字");
		return false;
	}
	if(cardNoStart<1||cardNoEnd<1){
		Feng.error("不能小于1");
		return false;
	}
	if(cardNoEnd < cardNoStart){
		Feng.error("结束编号不能小于开始编号!");
		return false;
	}
    //提交信息
      var ajax = new $ax(Feng.ctxPath + "/card/cardWholeSale/wholeSale/", function(data){
    	if(data == -1 ){
    		Feng.error("登陆者分会不存在 !");
    		return false;
    	}
    	if(data == -2 ){
    		Feng.error("划拨分会 不能与被划拨分会相同 !");
    		return false;
    	}
    	if(data == -3 ){
    		Feng.error("被划拨分会 不存在 !");
    		return false;
    	}
    	if(data == -4 ){
    		Feng.error("待划拨的会员卡范围错误，会员卡不存在或已激活 !");
    		return false;
    	}
    	if(data == -5 ){
    		Feng.error("划拨卡号已被划拨正等待接收分会确认，请检查划拨会员卡号 !");
    		return false;
    	}
    	if(data == -6 ){
    		Feng.error("接受划拨分会不是当前分会的直属分会 !");
    		return false;
    	}
        Feng.success("添加成功!");
        window.parent.CardWholeSale.table.refresh();
        CardWholeSaleInfo.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.CardWholeSaleData);
    ajax.start();
}

$(function () {
    Feng.initValidator("CardWholeSaleInfo", CardWholeSaleInfo.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeByBranchsalerNoSelf");
    ztree.bindOnClick(CardWholeSaleInfo.onClickDept);
    ztree.init();
    CardWholeSaleInfo.zTreeInstance = ztree;
});



