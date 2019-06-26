/**
 * 文件
 */
var Fileinfo = {

};
/**
 * 上传
 */
Fileinfo.add = function() {

};
var fileM=document.querySelector("#fileUp");
$("#fileUp").on("change",function() {
    var fileObj = fileM.files[0];
    var formData = new FormData();
    formData.append('file', fileObj);
    $.ajax({
        url: Feng.ctxPath + "/file/fileAdd",
        type: "post",
        dataType: "json",
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (json_data) {
            Feng.success("上传成功!");
            if (window.parent.File != undefined) {
                window.parent.File.table.refresh();
                Fileinfo.close();
            }
        },
    });
});
/**
 * 关闭此对话框
 */
Fileinfo.close = function() {
    if (window.parent.File.layerIndex == -1) {
        parent.layer.close(1);
    } else {
        parent.layer.close(window.parent.File.layerIndex);
    }

};
