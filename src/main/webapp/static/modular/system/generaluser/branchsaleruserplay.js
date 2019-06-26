/**
 * 分会会长播放记录排名
 */
var BranchSaleruserPlay = {
    id: "BranchSaleruserPlayTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
BranchSaleruserPlay.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
           {
               title: '排名',
               align: 'center',
               halign: 'center',
          /*     formatter: function (value, row, index) {
                   console.log(row)
                   var options = $('#BranchSaleruserPlayTable').bootstrapTable('getOptions');
                   return options.pageSize * (options.pageNumber - 1) + index + 1;
               }*/
               field: 'index'
           },
        {title: '姓名', field: 'userName', align: 'center', valign: 'middle', sortable: false},
      /*  {title: '电话', field: 'userPhone', align: 'center', valign: 'middle', sortable: false},*/
        {title: '分会', field: 'userBranchSaler', align: 'center', valign: 'middle', sortable: false},
        {title: '总播放时长', field: 'userPlaySum', align: 'center', valign: 'middle', sortable: false}
    ];

    return columns;
};

/**
 * 查询用户列表
 */
BranchSaleruserPlay.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BranchSaleruserPlay.table.refresh({query: queryData});
};
BranchSaleruserPlay.resetSearch = function () {
    $("#condition").val("");
    BranchSaleruserPlay.search();
}
$(function () {
    var defaultColunms = BranchSaleruserPlay.initColumn();
    var table = new BSTable("BranchSaleruserPlayTable", "/generalUser/branchsalerUserPlay_do", defaultColunms);
    table.setPaginationType("client");
    table.setPageSize("20");
    BranchSaleruserPlay.table = table.init();
});


