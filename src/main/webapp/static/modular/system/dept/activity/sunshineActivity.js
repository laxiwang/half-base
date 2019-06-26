/**
 *阳光普照活动
 */
var SunshineActivity = {
    id: "SunshineActivityTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SunshineActivity.initColumn = function () {
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
SunshineActivity.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SunshineActivity.seItem = selected[0];
        return true;
    }
};


/**
 * 搜索
 */
var time='2019-04-16 23:59:59';
SunshineActivity.search = function () {
    var queryData = {};
    var timeType=$("#timeType").val();
    queryData['timeType'] = $("#timeType").val();

    if(timeType==1){
        time='2019-04-16 23:59:59';
    }
    if(timeType==2){
        time='2019-05-06 23:59:59';
    }
    if(timeType==3){
        time='2019-05-26 23:59:59';
    }
   setInterval( newTimeFunc,1000);
    SunshineActivity.table.refresh({query: queryData});
}
function newTimeFunc() {
    newTime(time,1000);
}
SunshineActivity.resetSearch = function () {
    $("#timeType").val("1");
    newTimeFunc();
    SunshineActivity.search();
}


//这个函数是为了适应格式 比如：01分01秒;
function p (n){
    return n < 10 ? '0'+ n : n;
}

//倒计时函数
function newTime (date){
    //定义当前时间
    var startTime = new Date();
    //定义结束时间
    var endTime = new Date(date);

    //算出中间差并且已毫秒数返回; 除以1000将毫秒数转化成秒数方便运算；
    var countDown = (endTime.getTime() - startTime.getTime())/1000;

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
    document.getElementById('time').innerHTML = html;

    //别忘记当时间为0的，要让其知道结束了;
    if(countDown < 0){
        document.getElementById('time').innerHTML = '已结束!';
    }
}




$(function () {
    var defaultColunms = SunshineActivity.initColumn();
    var table = new BSTable("SunshineActivityTable", "/branchsalerActivity/sunshineActivity", defaultColunms);
    table.setPaginationType("client");
    SunshineActivity.table = table.init();
    setInterval( newTimeFunc,1000);
});


