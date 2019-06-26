/**
 * 新增用户图形管理
 */
var UserNewsEchartsDay = {

};


UserNewsEchartsDay.echartsInit = function(beginTime,endTime) {
    // 基于准备好的dom，初始化echarts实例
    var d=document.getElementById('userNews');
    //d.style.width="";
    d.style.height="400px";
    d.style.marginTop="35px";
    var myChart = echarts.init(d);
    myChart.showLoading();
    UserNewsEchartsDay.echatsUpdate(myChart,beginTime,endTime);

};

/**
 * 查询
 */
UserNewsEchartsDay.search = function () {
    var  beginTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    if(((new Date(endTime)-new Date(beginTime))/(1000 * 60 * 60 * 24))>30){
        Feng.info("时间维度不允许超过31天！");
        return false;
    }
    UserNewsEchartsDay.echartsInit(beginTime,endTime);
};
/**
 * 重置
 */
UserNewsEchartsDay.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    UserNewsEchartsDay.echartsInit("","");
}

var colors = ['#5793f3', '#d14a61', '#675bba'];
UserNewsEchartsDay.echatsUpdate= function(myChart,beginTime,endTime){
    $.get('/echarts/userNewsEchartsDay_do?beginTime='+beginTime+"&endTime="+endTime).done(function (data){
        var xmax1= Feng.checkNum(Math.max.apply(null,data.sData1));
        var xmax2= Feng.checkNum(Math.max.apply(null,data.sData2));
        myChart.hideLoading();
        myChart.setOption({
            textStyle:{
                fontWeight:'bold',
                fontFamily:'Microsoft YaHe',
                fontSize:16
            },
            animation:true,
            color:colors,
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            grid: {
                right: '20%'
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true},

                }
            },
            legend: {
                data:['注册用户','下单用户','转化率']
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.xData,
                    axisPointer: {
                        type: 'shadow'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '注册用户',
                    min: 0,
                    max: xmax1,
                    interval: xmax1/5,
                    axisLine: {
                        lineStyle: {
                            color: colors[0]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} 人'
                    },
                    position: 'left'
                },
                {
                    type: 'value',
                    name: '下单用户',
                    min: 0,
                    max: xmax2,
                    interval: xmax2/5,
                    axisLine: {
                        lineStyle: {
                            color: colors[1]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} 人'
                    },
                    position: 'right'
                },
                {
                    type: 'value',
                    name: '转化率',
                    min: 0,
                    max: 100,
                    interval: 20,
                    axisLine: {
                        lineStyle: {
                            color: colors[2]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} %'
                    },
                    position: 'right',
                    offset: 80
                }
            ],
            series: [
                {
                    name:'注册用户',
                    type:'bar',
                    data:data.sData1
                },
                {
                    name:'下单用户',
                    type:'bar',
                    data:data.sData2,
                    yAxisIndex: 1
                },
                {
                    name:'转化率',
                    type:'line',
                    data:data.sData3,
                    yAxisIndex: 2
                }
            ]
        });
    });

}
$(function () {
    UserNewsEchartsDay.echartsInit("","");
});
