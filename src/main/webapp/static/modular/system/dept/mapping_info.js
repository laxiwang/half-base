/**
 * 初始化分会映射详情对话框
 */
var MappingInfo = {
    deptInfoData : {},
    zTreeInstance : null,
    validateFields: {
        pName: {
            validators: {
                notEmpty: {
                    message: '分会名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
MappingInfo.clearData = function() {
    this.deptInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MappingInfo.set = function(key, val) {
    this.deptInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MappingInfo.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MappingInfo.close = function() {
    parent.layer.close(window.parent.Mapping.layerIndex);
}

/**
 * 点击分会ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MappingInfo.onClickDept = function(e, treeId, treeNode) {
    $("#pName").attr("value", MappingInfo.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
}

/**
 * 显示分会选择的树
 *
 * @returns
 */
MappingInfo.showDeptSelectTree = function() {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentDeptMenu").css({
        left : pNameOffset.left + "px",
        top : pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏分会选择的树
 */
MappingInfo.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
MappingInfo.collectData = function() {
    this.set('id').set('province').set('city');
}

/**
 * 验证数据是否为空
 */
MappingInfo.validate = function () {
    $('#deptInfoForm').data("bootstrapValidator").resetForm();
    $('#deptInfoForm').bootstrapValidator('validate');
    return $("#deptInfoForm").data('bootstrapValidator').isValid();
}


MappingInfo.addSubmit = function() {
    var pid= $('#pid').val();
    var province= $('#province').val()
    var city= $('#city').val()
    if(pid == null || pid == ''){
        Feng.info("分会不能为空！");
        return false;
    }
    if(province == null || province == ''){
        Feng.info("省不能为空！");
        return false;
    }
    if(city == null || city == ''){
        Feng.info("市不能为空！");
        return false;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/mapping_add", function(data){
        Feng.success("添加成功!");
        window.parent.Mapping.table.refresh();
        MappingInfo.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set('pid',$('#pid').val());
    ajax.set('province',$('#province').val());
    ajax.set('city',$('#city').val());
    ajax.start();
}



/**
 * 提交修改
 */
MappingInfo.editSubmit = function() {

    this.clearData();
    this.collectData();
    var pid=$('#pid').val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/mapping_update", function(data){
        Feng.success("修改成功!");
        window.parent.Mapping.table.refresh();
        MappingInfo.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.set("pid",pid);

    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
        event.target).parents("#parentDeptMenu").length > 0)) {
        MappingInfo.hideDeptSelectTree();
    }
}

$(function() {
    Feng.initValidator("deptInfoForm", MappingInfo.validateFields);

    var ztree = new $ZTree("parentDeptMenuTree", "/dept/tree");
    ztree.bindOnClick(MappingInfo.onClickDept);
    ztree.init();
    MappingInfo.zTreeInstance = ztree;
});
