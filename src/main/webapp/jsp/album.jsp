<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    var album;
    var searcher;
    var toolbar = [{
        iconCls: 'icon-help',
        text: "专辑详情",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row != null) {
                var bb = $("#album").treegrid("getLevel", row.id);
                if (bb == 1) {
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
                    console.log(row.title);
                    location.href = "${pageContext.request.contextPath}/Chapter/downLoad?url=" + row.url + "&title=" + row.title;
                } else {
                    alert("请选择章节行");
                }
            } else {
                alert("请先选中行"
                )
                ;
            }
        }
    }, '-', {
        iconCls: 'icon-undo',
        text: "导出表格",
        handler: function () {
            location.href = "${pageContext.request.contextPath}/Album/outExcel";
        }
    }]



    $(function () {
        $('#album').treegrid({
            url: '${pageContext.request.contextPath}/Album/queryAlbum',
            idField: 'id',
            onDblClickRow: function (row) {
                $("#audio_dialog").dialog("open");
                $("#audio_url").prop("src", "${pageContext.request.contextPath}" + row.url);
                $("#img_url").prop("src", "${pageContext.request.contextPath}/myimg/9.jpg");
            },
            treeField: 'title',
            columns: [[
                {title: '名字', field: 'title', width: 180},
                {field: 'size', title: '大小', width: 60},
                {field: 'duration', title: '时长 Date', width: 80},
                {field: 'url', title: '下载链接', width: 80/*, formatter: opi*/},
                {field: 'uploadDate', title: '上传时间', width: 80}
            ]],
            fit: true,
            fitColumns: true,
            toolbar: toolbar,
            pagination: true,
            pageSize: 2,
            pageList: [2, 4, 6, 8],
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

        $('#searchbox').searchbox({
            searcher: function (value, name) {
                searcher = value;
                var a = $("#tt").tabs("exists", "搜索页面")
                if (a) {
                    $("#tt").tabs("select", "搜索页面")
                } else {
                    $('#tt').tabs('add', {
                            title: "搜索页面",
                            iconCls: "icon-help",
                            href: "${pageContext.request.contextPath}/jsp/SearcherIndex.jsp",
                            selected: true,
                            closable: true
                        }
                    )
                }
            },
            menu: '#mm',
            prompt: '请输入值'
        });
    });
</script>
<div id="insertalbumdiv"></div>
<div id="insertchapterdiv"></div>
<div id="albumInformationdiv"></div>
<div id="audio_dialog" class="easyui-dialog " title="在线播放" style="width:400px;height:400px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">

    <div class="center-in-center">
        <img src="" id="img_url" class="logo">
        <audio id="audio_url" src="" controls="controls" autoplay="autoplay" loop="loop">
        </audio>
    </div>
</div>
<input style="width:300px" id="searchbox"/>
<div id="mm" style="width:150px">
    <div data-options="name:'size'">按照大小</div>
    <div data-options="name:'title',selected:true">按照名称</div>
    <div data-options="name:'xxx'">按照XXX</div>
</div>
<table id="album">
</table>

