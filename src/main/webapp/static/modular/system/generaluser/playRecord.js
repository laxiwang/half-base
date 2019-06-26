/**
 * 用户管理--播放记录
 */
var playPecord = {
    id: "playPecord",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    branchsalerid:0
};

/**
 * 初始化表格的列
 */
playPecord.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '课程名称', field: 'partName', align: 'center', valign: 'middle', sortable: true},
        {title: '查阅次数', field: 'palyCount', align: 'center', valign: 'middle', sortable: true, width:'100px'},
        {title: '最近播放时长', field: 'palyTimeEnd', align: 'center', valign: 'middle', sortable: true},
        {title: '单节总播放时长', field: 'playSum', align: 'center', valign: 'middle', sortable: true},
        {title: '最近播放时间', field: 'playTime', align: 'center', valign: 'middle', sortable: true}
        ];
    return columns;
};
$(function () {
    var defaultColunms = playPecord.initColumn();
    var userid=$("#userid").val();
    var table = new BSTable("playPecord", "/generalUser/playRecordList?userid="+userid, defaultColunms);
    console.log(table);
    table.setPaginationType("client");
    playPecord.table = table.init();
});

/**
 * 查询用户列表
 */
playPecord.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    playPecord.table.refresh({query: queryData});
    
};
playPecord.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    playPecord.search();
}
