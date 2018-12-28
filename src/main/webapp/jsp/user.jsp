<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    $(function () {
        var usertoolbar = [{
            text: "导入",
            iconCls: 'icon-edit',
            handler: function () {
                $("#inseruserExcel").dialog("open");
            }
        }, '-', {
            text: "导出",
            iconCls: 'icon-help',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/User/outExcel"
            }
        }]

        $('#usersTable').datagrid({
            url: '${pageContext.request.contextPath}/User/queryAll',
            columns: [[
                {field: 'name', title: '用户名', width: 100},
                {field: 'dharma', title: '法号', width: 100},
                {field: 'phone', title: '手机号', width: 100},
                {field: 'password', title: '密码', width: 100},
                {field: 'salt', title: '盐', width: 100},
                {field: 'sign', title: '简述', width: 100},
                {field: 'headPic', title: '头像', width: 100, formatter: pic},
                {field: 'sex', title: '性别', width: 100, formatter: sex},
                {field: 'province', title: '省份', width: 100},
                {field: 'city', title: '城市', width: 100},
                {field: 'status', title: '账号状态', width: 100, formatter: userstatus},
                {field: 'regDate', title: '注册时间', width: 100},
            ]],
            fitColumns: true,
            striped: true,
            pagination: true,
            fit: true,
            pageSize: 5,
            pageList: [5, 10, 15, 20],
            toolbar: usertoolbar
        });


        $("#inseruserExcel").dialog({
            closed: "true",
            title: "文档导入",
            iconCls: "icon-add",
            width: 400,
            height: 420,
            href: "${pageContext.request.contextPath}/jsp/insertExcel.jsp",
            minimizable: true,
            maximizable: true,
            modal: true,
            cache: false
        });
    })

    function pic(value, row, index) {
        return "<img src='${pageContext.request.contextPath}/myimg/" + value + "'></img>"
    }

    function sex(value, row, index) {
        if (value == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    function userstatus(value, row, index) {
        if (value == 1) {
            return "正常";
        } else {
            return "冻结";
        }
    }
</script>
<div id="inseruserExcel"></div>
<table id="usersTable"></table>