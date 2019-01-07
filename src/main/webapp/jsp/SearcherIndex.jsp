<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    $(function () {
        $('#searchertable').datagrid({
            url: "${pageContext.request.contextPath }/Chapter/queryChapter?test=" + searcher,
            columns: [[
                {field: 'id', title: '章节id', width: 100},
                {field: 'title', title: '章节名称', width: 100},
                {field: 'size', title: '大小', width: 100},
                {field: 'uploadDate', title: '上架时间', width: 100},
                {field: 'albumId', title: '专辑id', width: 100}
            ]],
            fitColumns: true,
            striped: true,
            fit: true
        });
    })
</script>
<table id="searchertable"></table>