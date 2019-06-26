/**
 *   评论
 */
var PartComment = {
    id: "PartCommentTab",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
PartComment.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'commentId', visible: false, align: 'center', valign: 'middle'},
        {title: 'userId', field: 'userId', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '手机号', field: 'userPhone', align: 'center', valign: 'middle', sortable: false},
        {title: '课程名称', field: 'bookName', align: 'center', valign: 'middle', sortable: false},
        {title: '节名称', field: 'partName', align: 'center', valign: 'middle', sortable: false},
        {title: '评论内容', field: 'commentContent', align: 'center', valign: 'middle', sortable: false,width :'200px'},
        {title: '评论类型', field: 'commentType', align: 'center', valign: 'middle', sortable: false},
        {title: '热门状态', field: 'isHot', align: 'center', valign: 'middle', sortable: false },
        {title: '回复状态', field: 'reply', align: 'center', valign: 'middle', sortable: false },
        {title: '评论时间', field: 'commentCreateTime', align: 'center', valign: 'middle', sortable: true }
    ];

    return columns;
};


/**
 * 查询用户列表
 */
PartComment.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['type'] = $("#type").val();
    queryData['hot'] = $("#hot").val();
    queryData['reply'] = $("#reply").val();
    PartComment.table.refresh({query: queryData});
};
/**
 * 热门操作
 */
PartComment.setHot = function () {
    if (this.check()) {
        var commentId = this.seItem.commentId;
        var ajax = new $ax(Feng.ctxPath + "/partComment/setHot", function (data) {
            Feng.success("设置成功!");
            PartComment.table.refresh();
        }, function (data) {
            Feng.error("设置失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", commentId);
        ajax.start();
    }
};
/**
 * 回复
 */
PartComment.reply_edit = function () {
    if (this.check()) {
        var commentId = this.seItem.commentId;
        var index = layer.open({
            type: 2,
            title: '评论回复',
            area: ['400px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/partComment/reply_edit/' + this.seItem.commentId
        });
        this.layerIndex = index;
    }
};
/**
 * 删除操作
 */
PartComment.deleteComment= function () {
    if (this.check()) {
        var operation = function() {
            var commentId = PartComment.seItem.commentId;
            var ajax = new $ax(Feng.ctxPath + "/partComment/delete", function (data) {
                Feng.success("删除成功!");
                PartComment.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", commentId);
            ajax.start();
        }
        Feng.confirm("是否刪除该评论?", operation);
    }

};
/**
 * 关闭此对话框
 */
PartComment.close = function() {
    if (window.parent.PartComment.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.PartComment.layerIndex);
    }

};

$(function () {
    var defaultColunms = PartComment.initColumn();
    var table = new BSTable("PartCommentTab", "/partComment/list", defaultColunms);
    //table.setPaginationType("client");
    PartComment.table = table.init();
});
/**
 * 检查是否选中
 */
PartComment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        PartComment.seItem = selected[0];
        return true;
    }
};

PartComment.resetSearch = function () {
    $("#condition").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#type").val("");
    $("#hot").val("");
    $("#reply").val("");
    PartComment.search();
}