<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    var album;
    var toolbar = [{
        iconCls: 'icon-help',
        text: "专辑详情",
        handler: function () {
            alert('编辑按钮')
        }
    }, '-', {
        iconCls: 'icon-add',
        text: "添加专辑",
        handler: function () {
            $("#insertalbumdiv").dialog("open");

        }
    }, '-', {
        iconCls: 'icon-add',
        text: "添加章节",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row != null) {
                var aa = $("#album").treegrid("getRoot");
                console.log(aa);
                if (aa == null) {
                    alert("请选择专辑")
                }
                album = aa.id;
                $("#insertchapterdiv").dialog("open");
            } else {
                alert("请先选中行");
            }
        }
    }, '-', {
        iconCls: 'icon-undo',
        text: "下载音频",
        handler: function () {
            alert('帮助按钮')
        }
    }]

    $(function () {
        $('#album').treegrid({
            url: '${pageContext.request.contextPath}/Album/queryAlbum',
            idField: 'id',
            treeField: 'title',
            columns: [[
                {title: '名字', field: 'title', width: 180},
                {field: 'size', title: '大小', width: 60},
                {field: 'duration', title: '时长 Date', width: 80},
                {field: 'url', title: '下载链接', width: 80},
                {field: 'uploadDate', title: '上传时间', width: 80}
            ]],
            fit: true,
            fitColumns: true,
            toolbar: toolbar,

            checkbox: true
        });


        $("#insertalbumdiv").dialog({
            closed: "true",
            title: "请添加专辑",
            iconCls: "icon-add",
            width: 400,
            height: 420,
            href: "${pageContext.request.contextPath}/jsp/addAlbum.jsp",
            minimizable: true,
            maximizable: true,
            modal: true,
            cache: false
        });


        $("#insertchapterdiv").dialog({
            closed: "true",
            title: "请添加章节",
            iconCls: "icon-add",
            width: 400,
            height: 420,
            href: "${pageContext.request.contextPath}/jsp/addChapter.jsp",
            minimizable: true,
            maximizable: true,
            modal: true,
            cache: false
        });
    });
</script>
<div id="insertalbumdiv"></div>
<div id="insertchapterdiv"></div>
<table id="album"></table>
