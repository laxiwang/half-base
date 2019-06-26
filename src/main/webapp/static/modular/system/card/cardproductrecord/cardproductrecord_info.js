/**
 * 初始化对话框
 */
var CardProductRecordInfo = {
		cardProductRecordData : {}
   
};

/**
 * 关闭此对话框
 */
CardProductRecordInfo.close = function() {
    parent.layer.close(window.parent.CardProductRecord.layerIndex);
}


/**
 * 收集数据
 */
CardProductRecordInfo.collectData = function() {
    this.set('cardNoStart').set('cardNoEnd').set('remark').set('fee').set('type');
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardProductRecordInfo.set = function(key, val) {
    this.cardProductRecordData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardProductRecordInfo.get = function(key) {
    return $("#" + key).val();
}

/**
 * 提交添加生产
 */
CardProductRecordInfo.addSubmit  = function() {
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
      var ajax = new $ax(Feng.ctxPath + "/card/cardProductRecord/_add", function(data){
    	if(data == -1 ){
    		Feng.error("生产卡段错误，已经有卡号被生产 !");
    		return false;
    	}
        Feng.success("添加成功!");
        window.parent.CardProductRecord.table.refresh();
        CardProductRecordInfo.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardProductRecordData);
    ajax.start();
}

