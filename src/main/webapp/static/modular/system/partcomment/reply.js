/**
 * 评论回复
 */
var Reply = {
    replyInfoForm : {},
    zTreeInstance : null,
    validateFields : {

    }
};
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Reply.set = function(key, val) {
    this.replyInfoForm[key] = (typeof value == "undefined") ? $("#" + key).val()
        : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Reply.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
Reply.close = function () {
    if (window.parent.Reply.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.Reply.layerIndex);
    }
};
/**
 * 收集数据
 */
Reply.collectData = function() {
    this.set('id').set('content');
};
/**
 * 提交修改
 */
Reply.editSubmit = function () {
    this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/partComment/reply", function (data) {
        Feng.success("修改成功!");
        window.parent.PartComment.table.refresh();
        Reply.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.replyInfoForm);
    ajax.start();
};
/**
 * 关闭此对话框
 */
Reply.close = function() {
    if (window.parent.PartComment.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.PartComment.layerIndex);
    }

};