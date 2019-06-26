/**
 * 批量更新用户分会
 */
var BchUserBranchSalerChange = {
    bchUserBranchSalerChangeData : {},
    zTreeInstance : null
};

/**
 * 清除数据
 */
BchUserBranchSalerChange.clearData = function() {
    this.bchUserBranchSalerChangeData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BchUserBranchSalerChange.set = function(key, val) {
    this.bchUserBranchSalerChangeData[key] = (typeof value == "undefined") ? $("#" + key).val()
        : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BchUserBranchSalerChange.get = function(key) {
    return $("#" + key).val();
};




/**
 * 提交新增
 */
BchUserBranchSalerChange.add = function() {
    var arrStr = document.getElementsByName("phone");

    var phones = [];
    var branchsaler = $("#branchsaler").val();
    if (branchsaler == null || branchsaler == "") {
        Feng.error("分会不能为空");
        return false;
    }
    for (var i = 0; i < arrStr.length; i++) {
        if (arrStr[i].value != null && arrStr[i].value != "") {
            if (arrStr[i].value.length != 11) {
                Feng.error("第" + (i + 1) + "个手机号" + arrStr[i].value + "输入有误!");
                break;
            } else {
                if(phones.indexOf(arrStr[i].value) == -1){
                    phones.push(arrStr[i].value);
                }
            }
        }
    }

    if (phones.length == 0) {
        Feng.error("请输入正确手机号");
        return false;
    }
    var ajax = new $ax(Feng.ctxPath + "/generalUser/userChangeBranchsaler", function (data) {

        if (data.res == 1) {
            Feng.error("分会不存在!");
            return false;
        }
        if (data.res == 2) {
            Feng.error("手机号：" + data.phone + "，用户不存在 请检查后重新操作");
            return false;
        }
        if (data.res == 3) {
            Feng.error("手机号：" + data.phone + "，原分会与修改分会一致  请检查后重新操作");
            return false;
        }
        if (data.res == 0) {
            Feng.success("更新成功!");
            window.parent.UserBchange.table.refresh();
            BchUserBranchSalerChange.close();
        }
    }, function (data) {
        Feng.error("更新失败" + data.responseJSON.message + "!");
    });
    ajax.set('arr', phones.join(","));
    ajax.set("branchsaler", branchsaler);
    ajax.start();
};
 BchUserBranchSalerChange.addPhone= function(){
    var div=document.getElementById("phones");
    var input = document.createElement("input");
    input.name="phone";
    input.type="number";
    input.style="margin-bottom:10px;margin-right: 10px;float:left;";
    div.appendChild(input);
}


function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
        event.target).parents("#menuContent").length > 0)) {
        BchUserBranchSalerChange.hideDeptSelectTree();
    }
}


/**
 * 显示分会选择的树
 *
 * @returns
 */
BchUserBranchSalerChange.showDeptSelectTree = function() {

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
        BchUserBranchSalerChange.hideDeptSelectTree();
    }
}

/**
 * 隐藏分会选择的树
 */
BchUserBranchSalerChange.hideDeptSelectTree = function() {
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
BchUserBranchSalerChange.onClickDept = function(e, treeId, treeNode) {
    $("#pName").attr("value", BchUserBranchSalerChange.zTreeInstance.getSelectedVal());
    $("#branchsaler").attr("value", treeNode.id);
}

/**
 * 隐藏分会选择的树
 */
BchUserBranchSalerChange.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

$(function() {
    Feng.initValidator("BchUserBranchSalerChangeInfo", BchUserBranchSalerChange.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeByBranchsaler");
    ztree.bindOnClick(BchUserBranchSalerChange.onClickDept);
    ztree.init();
    BchUserBranchSalerChange.zTreeInstance = ztree;
});
/**
 * 关闭此对话框
 */
BchUserBranchSalerChange.close = function() {
    if (window.parent.UserBchange.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.UserBchange.layerIndex);
    }
};