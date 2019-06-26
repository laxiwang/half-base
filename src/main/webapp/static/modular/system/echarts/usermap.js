/**
 * 用户地图
 */
var UserMap = {

};


UserMap.echartsInit = function(beginTime,endTime) {
    // 基于准备好的dom，初始化echarts实例
    var d=document.getElementById('UserMap');
    //d.style.width="";
    d.style.height="400px";
    d.style.marginTop="35px";
    var myChart = echarts.init(d);
    myChart.showLoading();
    UserMap.echatsUpdate(myChart,beginTime,endTime);

};





UserMap.echatsUpdate = function(myChart,beginTime,endTime){
    $.get('/echarts/userMap_do').done(function (data){
        console.log(data)
        myChart.hideLoading();

        myChart.setOption({
            title : {
                text: '用户分布图',
                //subtext: '目前只显示中国',
                left: 'center'
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data:['用户人数']
            },
            visualMap: {
                min: 0,
                max: 5000,
                left: 'left',
                top: 'bottom',
                text:['高','低'],           // 文本，默认为数值文本
                calculable : true
            },

            toolbox: {
                show: true,
                orient : 'vertical',
                left: 'right',
                top: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            scaleLimit:{
                min:1,
                max:20
            },
            series : [
                {
                    name: '用户人数',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:data

                }

            ]
        });
        var Province = "";
        myChart.on('click', function (params) {
            //重新绘制
            if(!this.isProvince){
                this.isProvince = true;
                var myChart2= echarts.init(document.getElementById('UserMap'));
                Province = params.name;
                myChart2.setOption({
                    title : {
                        text: '用户分布图',
                        //subtext: '目前只显示中国',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:['用户人数']
                    },
                    visualMap: {
                        min: 0,
                        max: 5000,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },

                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    scaleLimit:{
                        min:1,
                        max:20
                    },
                    series: [
                        {
                            name: '用户人数',
                            type: 'map',
                            mapType: Province,
                            layoutSize: 300,//省份地图大小为600xp。
                            label: {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:data
                        }
                    ]
                });
            }
        });

    });

}
$(function () {
    UserMap.echartsInit("","");
});
