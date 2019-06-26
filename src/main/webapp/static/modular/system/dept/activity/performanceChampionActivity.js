/**
 *业绩冠军活动
 */
var PerformanceChampionActivity = {
    id: "PerformanceChampionActivityTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PerformanceChampionActivity.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '排名', field: 'order',  align: 'center', valign: 'middle'},
        {title: '分会', field: 'fullname', align: 'center', valign: 'middle'},
        /*{title: '业绩总', field: 'num',  align: 'center', valign: 'middle'},*/
        {title: '业绩差额', field: 'resNum', align: 'center', valign: 'middle'}
    ]
    return columns;
};


/**
 * 检查是否选中
 */
PerformanceChampionActivity.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        PerformanceChampionActivity.seItem = selected[0];
        return true;
    }
};


/**
 * 搜索
 */
var time='1558886399000';
PerformanceChampionActivity.search = function () {
    var queryData = {};
    var level= $("#level").val();
    queryData['level'] =level;
    setInterval( newTimeFunc,1000);
    if(level==1){
        content2();
    }else {
        content1();
    }
    PerformanceChampionActivity.table.refresh({query: queryData});
}
function newTimeFunc() {
    newTime(time,1000);
}
PerformanceChampionActivity.resetSearch = function () {
    $("#level").val("2");
    newTimeFunc();
    content1();
    PerformanceChampionActivity.search();
}


//这个函数是为了适应格式 比如：01分01秒;
function p (n){
    //alert(n)
    return n < 10 ? '0'+ n : n;
}

//倒计时函数
function newTime (date){
    //定义当前时间
    var startTime = new Date();


    //算出中间差并且已毫秒数返回; 除以1000将毫秒数转化成秒数方便运算；

    var countDown = ( date- startTime.getTime())/1000;


    //获取天数 1天 = 24小时  1小时= 60分 1分 = 60秒
    var oDay = parseInt(countDown/(24*60*60));


    //获取小时数
    //特别留意 %24 这是因为需要剔除掉整的天数;
    var oHours = parseInt(countDown/(60*60)%24);

    //获取分钟数
    //同理剔除掉分钟数
    var oMinutes = parseInt(countDown/60%60);

    //获取秒数
    //因为就是秒数  所以取得余数即可
    var oSeconds = parseInt(countDown%60);

    //下面就是插入到页面事先准备容器即可;
    var html =  "<span>" + p(oDay) + "天</span>"+ "<span>" + p(oHours) + "时</span>" + "<span>" + p(oMinutes) + "分</span>" +"<span>" + p(oSeconds) + "秒</span>";
    document.getElementById('time1').innerHTML = html;

    //别忘记当时间为0的，要让其知道结束了;
    if(countDown < 0){
        document.getElementById('time1').innerHTML = '已结束!';
    }
}




$(function () {
    var defaultColunms = PerformanceChampionActivity.initColumn();
    var table = new BSTable("PerformanceChampionActivityTable", "/branchsalerActivity/performanceChampionActivity", defaultColunms);
    table.setPaginationType("client");
    PerformanceChampionActivity.table = table.init();
    setInterval( newTimeFunc,1000);
    content1();
});


function content1() {
    document.getElementById('content').innerHTML = '第一名 : 10万元现金 <br/><br/>第二名 ：5万元现金';
}
function content2() {
    document.getElementById('content').innerHTML = '第一名 : 5万元全价实体卡';
}