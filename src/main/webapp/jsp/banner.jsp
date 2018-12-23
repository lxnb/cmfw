<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    $(function () {
        var toolbar = [{
            iconCls: 'icon-add',
            text: "添加",
            handler: function () {
                $("#insertprodiv").dialog("open");
            }
        }, '-', {
            iconCls: 'icon-edit',
            text: "修改",
            handler: function () {
                //获取选中行
                var row = $("#dg").edatagrid("getSelected");
                if (row != null) {
                    //编辑指定行
                    var index = $("#dg").edatagrid("getRowIndex", row);
                    $("#dg").edatagrid("editRow", index);
                } else {
                    alert("请先选中行")
                }
            }
        }, '-', {
            iconCls: 'icon-remove',
            text: "删除",
            handler: function () {
                //获取选中行
                var row = $("#dg").edatagrid("getSelected");
                if (row != null) {
                    $.messager.confirm('确认对话框', '您确认删除吗？', function (r) {
                        if (r) {
                            $.post("${pageContext.request.contextPath}/Banner/delete", {
                                id: row.bId,
                                imgPath: row.imgPath
                            }, function (res) {
                                $.messager.show({
                                    title: '提示窗口',
                                    msg: '删除成功。',
                                    timeout: 5000,
                                    showType: 'slide'
                                });
                                $("#dg").datagrid("load");
                            })
                        }
                    });
                } else {
                    alert("请先选中行")
                }
            }
        }, '-', {
            iconCls: 'icon-save',
            text: "保存",
            handler: function () {
                $("#dg").edatagrid("saveRow")
            }
        }]
        $("#dg").edatagrid({
            url: "${pageContext.request.contextPath}/Banner/queryAll",
            updateUrl: "${pageContext.request.contextPath}/Banner/update",
            columns: [[
                {field: 'title', title: '图片名', width: 100, align: 'center'},
                {field: 'imgPath', title: '图片路径', width: 100, align: 'center'},
                {
                    field: 'status', title: '图片状态', width: 100, align: 'center', formatter: aaa, editor: {
                        type: 'combobox',
                        options: {
                            editable: false,
                            valueField: 'label',
                            textField: 'value',
                            data: [{
                                label: 'Y',
                                value: '前台显示'
                            }, {
                                label: 'N',
                                value: '前台不显示'
                            }]
                        }
                    }
                },
                {
                    field: 'pubDate', title:
                        '上传时间', width:
                        100, align:
                        'center'
                }
                ,
                {
                    field: 'description', title:
                        '图片描述', width:
                        100, align:
                        'center'
                }
            ]],
            fitColumns: true,
            fit:
                true,
            pagination:
                true,
            rownumbers:
                true,
            striped:
                true,
            pageSize:
                4,
            pageList:
                [2, 4, 6, 8],
            toolbar:
            toolbar,
            view:
            detailview,
            detailFormatter:

                function (rowIndex, rowData) {
                    return '<table><tr>' +
                        '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}' + rowData.imgPath + '" style="height:50px;"></td>' +
                        '<td style="border:0">' +
                        '<p>图片名: ' + rowData.title + '</p>' +
                        '<p>图片简介: ' + rowData.description + '</p>' +
                        '</td>' +
                        '</tr></table>';
                }

        })
        ;

        $("#insertprodiv").dialog({
            closed: "true",
            title: "请添加轮播图",
            iconCls: "icon-add",
            width: 400,
            height: 420,
            href: "${pageContext.request.contextPath}/jsp/addBanner.jsp",
            minimizable: true,
            maximizable: true,
            modal: true,
            cache: false
        });

    })
    function aaa(value, row, index) {
        if (value == "Y") {
            return "前台显示";
        } else {
            return "前台不显示";
        }
    }


</script>
<div id="insertprodiv"></div>
<table id="dg"></table>
