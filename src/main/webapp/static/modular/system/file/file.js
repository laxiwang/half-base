/**
 * 文件管理的单例
 */
var File = {
    id: "FileTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
File.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle',width:'50px'},
        {title: 'folder_id', field: 'folder_id', visible: false, align: 'center', valign: 'middle',width:'50px'},
        {title: 'url', field: 'fileUrl', visible: false, align: 'center', valign: 'middle',width:'50px'},
        {title: '名称', field: 'fileName', align: 'center', valign: 'middle', sortable: false},
        {title: '类型', field: 'isFolder', align: 'center', valign: 'middle', sortable: false},
        {title: '级别', field: 'role', align: 'center', valign: 'middle', sortable: false},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true}
       ]
    return columns;
};


/**
 * 检查是否选中
 */
File.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        File.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加文件夹
 */
File.openAddFolder = function () {
    var index = layer.open({
        type: 2,
        title: '添加文件夹',
        area: ['400px', '350px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/file/folder_add'
    });
    this.layerIndex = index;
};
/**
 * 删除文件夹
 */
File.delFolder = function () {
    if (this.check()) {
        var id = File.seItem.id;
        if(File.seItem.is_folder != 1 ){
            Feng.info("该数据不是文件夹！");
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/file/delFolder", function (data) {
            if(data == -1){
                Feng.info("该文件夹下有内容！");
                return false;
            }
            Feng.success("删除成功!");
            File.table.refresh();
        }, function (data) {
            Feng.error("删除失败!");
        });
        ajax.set("id", id);
        ajax.start();
    }
};

/**
 * 下载文件
 */
File.upLoadFile = function () {
    if (this.check()) {
        var id = File.seItem.id;
        if(File.seItem.is_folder == 1 ){
            Feng.info("该数据不是文件！");
            return false;
        }
        window.location.href=Feng.ctxPath + "/file/upLoadFile/"+id;
    }
};

/**
 * 删除文件
 */
File.delFile = function () {
    if (this.check()) {
        var id = File.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/file/delFile", function (data) {
            if(data == -1){
                Feng.info("该文件夹下有内容！");
                return false;
            }
            Feng.success("删除成功!");
            File.table.refresh();
        }, function (data) {
            Feng.error("删除失败!");
        });
        ajax.set("id", id);
        ajax.start();
    }
};
/**
 * 进入文件夹
 */
File.inFolder = function () {
    //$table.bootstrapTable('getRowByUniqueId', 1)
    if (this.check()) {
        var queryData = {};
        queryData['folderId'] = File.seItem.id;
        queryData['inOut'] = true;
        if(File.seItem.is_folder != 1 ){
            Feng.info("该数据不是文件夹！");
            return false;
        }
      /*  var path = "${shiro.filePath()}";
        $("#path").html(path);
        alert(path);*/
        File.table.refresh({query: queryData});
        queryData['folderId']=null;

    }
};
/**
 * 返回上一层文件夹
 */
File.outFolder = function () {
    var queryData = {};
    queryData['inOut'] = false;
    File.table.refresh({query: queryData});
};
/**
 * 点击上传文件
 */
File.openAddFile = function () {
    var index = layer.open({
        type: 2,
        title: '添加文件',
        area: ['300px', '250px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/file/file_add'
    });
    this.layerIndex = index;
};

/**
 * 搜索
 */
File.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['inOut'] = null;queryData['folderId']=null;
    File.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = File.initColumn();
    var table = new BSTable("FileTable", "/file/list", defaultColunms);
    table.setPaginationType("client");
    File.table = table.init();
});

File.resetSearch = function () {
    $("#condition").val("");
    File.search();
}