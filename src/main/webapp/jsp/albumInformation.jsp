<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    $(function () {
        $('#albumInformation').propertygrid({
            url: '${pageContext.request.contextPath}/Album/queryOneAlbum?id=' + album,
            showGroup: true,
            scrollbarSize: 0,
            columns: [[
                {field: 'title', title: '专辑名', width: 100, resizable: true},
                {field: 'count', title: '章节数', width: 100, resizable: false},
                {field: 'coverImg', title: '插图路径', width: 100, resizable: true},
                {field: 'score', title: '评分', width: 100, resizable: true},
                {field: 'author', title: '作者', width: 100, resizable: true},
                {field: 'broadcast', title: '翻译者', width: 100, resizable: true},
                {field: 'brief', title: '简介', width: 100, resizable: true},
                {field: 'pubDate', title: '上架时间', width: 100, resizable: true}

            ]]
        });

    });

</script>
<table id="albumInformation" style="width:300px"></table>
