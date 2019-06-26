/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var UserInfoDlg = {
	userInfoData : {},
	zTreeInstance : null,
	validateFields : {
		phone : {
			validators : {
				notEmpty : {
					message : '手机号不能为空'
				},
				regexp: {
					regexp: /^1\d{10}$/,
					message: '手机号格式错误'
				}
			}
		}
	}
};

/**
 * 清除数据
 */
UserInfoDlg.clearData = function() {
	this.userInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.set = function(key, val) {
	this.userInfoData[key] = (typeof value == "undefined") ? $("#" + key).val()
			: value;
	return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.get = function(key) {
	return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
UserInfoDlg.close = function() {
	if (window.parent.GeneralUser.layerIndex == -1) {
		parent.layer.close(1);
	} else {
		parent.layer.close(window.parent.GeneralUser.layerIndex);
	}

};

/**
 * 收集数据
 */
UserInfoDlg.collectData = function() {
	this.set('id').set('name').set('sex').set('phone').set('province').set('city').set('birthday').set('branchsaler');
};

/**
 * 收集数据
 */
UserInfoDlg.collectData_add = function() {
	this.set('name').set('phone').set('province').set('city').set('branchsaler').set('memberstatus');
};

/**
 * 提交新增
 */
UserInfoDlg.add = function() {
	this.clearData();
	this.collectData_add();

	if (!this.validate()) {
		return;
	}
	//提交信息
	var ajax = new $ax(Feng.ctxPath + "/generalUser/add", function(data) {

		if (data == -1) {
			Feng.error("手机号已存在!");
			return false;
		}

		Feng.success("新增成功!");
		if (window.parent.GeneralUser != undefined) {
			window.parent.GeneralUser.table.refresh();
			UserInfoDlg.close();
		}
	}, function(data) {
		Feng.error("新增失败!" + data.responseJSON.message + "!");
	});
	ajax.set(this.userInfoData);
	ajax.start();
};
/**
 * 点击增加积分
 */
UserInfoDlg.addintegralForUser = function() {
	var integral = $("#integral").val();
	if (integral == null || integral == '') {
		Feng.error("积分不能为空");
		return false;
	}
	if (isNaN(integral)) {
		Feng.error(integral + "不是数字");
		return false;
	}
	if (integral <= 0) {
		Feng.error("积分不能小于等于0");
		return false;
	}
	var userId = $("#id").val();
	var ajax = new $ax(Feng.ctxPath + "/generalUser/addintegralForUser/"
			+ userId + "/" + integral + "/", function(data) {
		if (data == 0) {
			Feng.error("新增积分失败,系统内部错误  !");
			return false;
		}
		if (data == 1) {
			Feng.success("新增积分成功!");
			if (window.parent.GeneralUser != undefined) {
				window.parent.GeneralUser.table.refresh();
				UserInfoDlg.close();
			}
		}
	}, function(data) {
		Feng.error("新增积分失败!" + data.responseJSON.message + "!");
	});
	ajax.start();
}
/**
 * 点击开通会员
 */
UserInfoDlg.openMember = function() {
	var book = $("#book").val();
	var userId = $("#id").val();
	var ajax = new $ax(Feng.ctxPath + "/generalUser/openMember/"
			+ userId + "/" + book + "/", function(data) {
		if (data == -1) {
			Feng.error("用户已是该课程的会员 !");
			return false;
		}
		if (data == 0) {
			Feng.error("开通会员失败,系统内部错误  !");
			return false;
		}
		if (data == 1) {
			Feng.success("开通会员成功!");
			if (window.parent.GeneralUser != undefined) {
				window.parent.GeneralUser.table.refresh();
				UserInfoDlg.close();
			}
		}
	}, function(data) {
		Feng.error("开通会员失败!" + data.responseJSON.message + "!");
	});
	ajax.start();
}

/**
 * 验证数据是否为空
 */
UserInfoDlg.validate = function() {
	$('#userInfoForm').data("bootstrapValidator").resetForm();
	$('#userInfoForm').bootstrapValidator('validate');
	return $("#userInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交修改
 */
UserInfoDlg.editSubmit = function() {
	this.clearData();
	this.collectData();

	if (!this.validate()) {
		return;
	}
	//提交信息
	var ajax = new $ax(Feng.ctxPath + "/generalUser/user_edit", function(data) {
		Feng.success("修改成功!");
		if (window.parent.GeneralUser != undefined) {
			window.parent.GeneralUser.table.refresh();
			UserInfoDlg.close();
		}
	}, function(data) {
		Feng.error("修改失败!" + data.responseJSON.message + "!");
	});
	ajax.set(this.userInfoData);
	ajax.start();
};

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		UserInfoDlg.hideDeptSelectTree();
	}
}

/**
 * 显示分会选择的树
 *
 * @returns
 */
UserInfoDlg.showDeptSelectTree = function() {

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
		UserInfoDlg.hideDeptSelectTree();
	}
}

/**
 * 隐藏分会选择的树
 */
UserInfoDlg.hideDeptSelectTree = function() {
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
UserInfoDlg.onClickDept = function(e, treeId, treeNode) {
	$("#pName").attr("value", UserInfoDlg.zTreeInstance.getSelectedVal());
	$("#branchsaler").attr("value", treeNode.id);
}

/**
 * 隐藏分会选择的树
 */
UserInfoDlg.hideDeptSelectTree = function() {
	$("#parentDeptMenu").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

$(function() {
	Feng.initValidator("userInfoForm", UserInfoDlg.validateFields);
	var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeByBranchsaler");
	ztree.bindOnClick(UserInfoDlg.onClickDept);
	ztree.init();
	UserInfoDlg.zTreeInstance = ztree;

});
