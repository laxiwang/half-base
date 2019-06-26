/**
 * 文件夹（可用于添加和修改对话框）
 */
var Filderinfo = {
    filderInfoData : {},
    zTreeInstance : null,
    validateFields : {

    }
};

/**
 * 清除数据
 */
Filderinfo.clearData = function() {
    this.filderInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Filderinfo.set = function(key, val) {
    this.filderInfoData[key] = (typeof value == "undefined") ? $("#" + key).val()
        : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Filderinfo.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
Filderinfo.close = function() {
    if (window.parent.File.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.File.layerIndex);
    }

};

/**
 * 收集数据
 */
Filderinfo.collectData = function() {
    this.set('id').set('name').set('sex').set('phone').set('province').set('city').set('county').set('address').set('birthday').set('branchsaler');
};

/**
 * 收集数据
 */
Filderinfo.collectData_add = function() {
    this.set('name');
};

/**
 * 提交新增
 */
Filderinfo.add = function() {
    var name=$("#name").val();
    if(name == null || name == ''){
        Feng.info("名称不能为空！");
        return false;
    }
    var role=$("#role").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/file/folderAdd", function(data) {
        Feng.success("新增成功!");
        if (window.parent.File != undefined) {
            window.parent.File.table.refresh();
            Filderinfo.close();
        }
    }, function(data) {
        Feng.error("新增失败!" + data.responseJSON.message + "!");
    });
    ajax.set("name",name);
    ajax.set("role",role);
    ajax.start();
};
