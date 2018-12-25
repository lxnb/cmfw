<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<script type="text/javascript">
    $(function () {
        var myChart = echarts.init(document.getElementById('distribution'));

        option = {
            title: {
                text: '用户分布',
                subtext: '中国用户',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['用户数量']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: '用户数量',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: []
                }

            ]
        };
        myChart.setOption(option)

        $.post("${pageContext.request.contextPath}/User/distribution", function (data) {
            console.log(data);
            myChart.setOption({
                series: [{
                    data: data
                }]
            });
        }, "json");
    })
</script>
<div id="distribution" style="width: 600px;height:400px;"></div>

