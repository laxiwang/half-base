/**
 * 初始化对话框
 */
var CardActiveRecordInfo = {
		cardActiveRecordData : {}
   
};

/**
 * 关闭此对话框
 */
CardActiveRecordInfo.close = function() {
    parent.layer.close(window.parent.CardActiveRecord.layerIndex);
}


/**
 * 收集数据
 */
CardActiveRecordInfo.collectData = function() {
    this.set('cardNoStart').set('cardNoEnd').set('remark');
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardActiveRecordInfo.set = function(key, val) {
    this.cardActiveRecordData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardActiveRecordInfo.get = function(key) {
    return $("#" + key).val();
}

/**
 * 提交添加生产
 */
CardActiveRecordInfo.addSubmit  = function() {
    this.collectData();
    var cardNoStart=parseInt($("#cardNoStart").val());
    var cardNoEnd=parseInt($("#cardNoEnd").val());
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
      var ajax = new $ax(Feng.ctxPath + "/card/cardActiveRecord/_add", function(data){
    	if(data == -1 ){
    		Feng.error(" 会员卡号已激活或者已使用或者未划拨，请检查会员卡号!");
    		return false;
    	}
    	if(data == -2 ){
    		Feng.error(" 激活卡号不属于您的分会，请检查会员卡号 !");
    		return false;
    	}
    	if(data == -3){
    		Feng.error(" 划拨卡号已被划拨正等待接收分会确认，请检查激活会员卡号 !");
    		return false;
    	}
        Feng.success("添加成功!");
        window.parent.CardActiveRecord.table.refresh();
        CardActiveRecordInfo.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardActiveRecordData);
    ajax.start();
}

