<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('userActive'));
        var option = {
            title: {
                text: '近三星期用户活跃度',
                subtext: '内容详情'
            },
            tooltip: {},
            legend: {
                type: "scroll",
                data: ['内容']
            },
            xAxis: {
                data: ["一星期内", "二星期内", "三星期内"]
            },
            yAxis: {},
        }

        myChart.setOption(option);
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/User/queryUserCount",
            success: function (data) {
                myChart.setOption({
                    series: [{
                        name: '内容',
                        data: data,
                        type: "bar"
                    }]
                })
            }
        })
    })
</script>
<div id="userActive" style="width: 600px;height:400px;"></div>