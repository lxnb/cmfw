<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    var album;
    var toolbar = [{
        iconCls: 'icon-help',
        text: "专辑详情",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row != null) {
                var bb = $("#album").treegrid("getLevel", row.id);
                if (bb == 1) {
                    /*album = row.id;
                    $("#albumInformationdiv").dialog("open");*/
                    $.messager.alert(
                        row.title + "的详细信息",
                        "章节数量:" + row.count +
                        '<br><img src="${pageContext.request.contextPath}' + row.coverImg + '" style="height:150px;">' +
                        "<br>作者:" + row.author +
                        "<br>播音:" + row.broadcast +
                        "<br>简介:" + row.brief +
                        "<br>上架时间:" + row.pubDate
                    )
                } else {
                    alert("只可查看专辑详情哦");
                }
            } else {
                alert("请先选中行");
            }
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
                var bb = $("#album").treegrid("getLevel", row.id);
                console.log(bb);
                if (bb == 1) {
                    album = row.id;
                    $("#insertchapterdiv").dialog("open");
                } else {
                    alert("请选择专辑行");
                }
            } else {
                alert("请先选中行");
            }
        }
    }, '-', {
        iconCls: 'icon-undo',
        text: "下载音频",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row != null) {
                var bb = $("#album").treegrid("getLevel", row.id);
                if (bb != 1) {
                    $.post("${pageContext.request.contextPath}/Chapter/downLoad",
                        {url: row.url},
                        function (res) {
                            if (res == "") {
                                $.messager.show({
                                    title: '下载提示',
                                    msg: '正在下载',
                                    timeout: 5000,
                                    showType: 'slide'
                                });
                            } else {
                                $.messager.show({
                                    title: '下载提示',
                                    msg: '下载失败，请稍后重试',
                                    timeout: 5000,
                                    showType: 'slide'
                                });
                            }
                        })
                } else {
                    alert("请选择章节行");
                }
            } else {
                alert("请先选中行"
                )
                ;
            }
        }
    }]

    function opi(value, row, index) {
        if (row.children == null) {
            return "<p>" + value + "</p>" +
                "<audio controls='controls' src='${pageContext.request.contextPath}" + value + "'></audio>";
        }
    }

    $(function () {
        $('#album').treegrid({
            url: '${pageContext.request.contextPath}/Album/queryAlbum',
            idField: 'id',
            treeField: 'title',
            columns: [[
                {title: '名字', field: 'title', width: 180},
                {field: 'size', title: '大小', width: 60},
                {field: 'duration', title: '时长 Date', width: 80},
                {field: 'url', title: '下载链接', width: 80, formatter: opi},
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

        $("#albumInformationdiv").dialog({
            closed: "true",
            title: "专辑详情",
            iconCls: "icon-add",
            width: 400,
            height: 420,
            href: "${pageContext.request.contextPath}/jsp/albumInformation.jsp",
            minimizable: true,
            maximizable: true,
            modal: true,
            cache: false
        });
    });
</script>
<div id="insertalbumdiv"></div>
<div id="insertchapterdiv"></div>
<div id="albumInformationdiv"></div>
<table id="album">
</table>
<%--<audio controls autoplay>
    <source src="${pageContext.request.contextPath}/myradio/王上 - 青春大概.mp3" type="audio/ogg" controls />
</audio>--%>

