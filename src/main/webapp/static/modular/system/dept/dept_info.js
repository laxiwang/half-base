/**
 * 初始化分会详情对话框
 */
var DeptInfoDlg = {
    deptInfoData : {},
    zTreeInstance : null,
    validateFields: {
        simplename: {
            validators: {
                notEmpty: {
                    message: '分会名称不能为空'
                }
            }
        },
        fullname: {
            validators: {
                notEmpty: {
                    message: '分会全称不能为空'
                }
            }
        },
        pName: {
            validators: {
                notEmpty: {
                    message: '上级名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
DeptInfoDlg.clearData = function() {
    this.deptInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.set = function(key, val) {
    this.deptInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeptInfoDlg.close = function() {
    parent.layer.close(window.parent.Dept.layerIndex);
}

/**
 * 点击分会ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
DeptInfoDlg.onClickDept = function(e, treeId, treeNode) {
    $("#pName").attr("value", DeptInfoDlg.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
}

/**
 * 显示分会选择的树
 *
 * @returns
 */
DeptInfoDlg.showDeptSelectTree = function() {
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
DeptInfoDlg.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
DeptInfoDlg.collectData = function() {
    this.set('id').set('simplename').set('fullname').set('tips').set('num').set('pid').set('phone').set('username').set('level');
}

/**
 * 验证数据是否为空
 */
DeptInfoDlg.validate = function () {
    $('#deptInfoForm').data("bootstrapValidator").resetForm();
    $('#deptInfoForm').bootstrapValidator('validate');
    return $("#deptInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加分会
 */
DeptInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/add", function(data){
    	if(data == -1 ){
    		 Feng.error("当前分会等级有误，不允许比上级分会的等级高!");
    		 return false;
    	}
        Feng.success("添加成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DeptInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
  
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/update", function(data){
    	  if(data == -1 ){
    			 Feng.error("当前分会等级有误，不允许比上级分会的等级高!");
    			 return false;
    		}
        Feng.success("修改成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        DeptInfoDlg.hideDeptSelectTree();
    }
}

$(function() {
    Feng.initValidator("deptInfoForm", DeptInfoDlg.validateFields);

    var ztree = new $ZTree("parentDeptMenuTree", "/dept/tree");
    ztree.bindOnClick(DeptInfoDlg.onClickDept);
    ztree.init();
    DeptInfoDlg.zTreeInstance = ztree;
});
