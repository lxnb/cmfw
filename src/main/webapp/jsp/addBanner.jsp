<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<script type="text/javascript">
    $(function () {
        $("#iname").validatebox({
            required: true
        });
        $("#proadd").validatebox({
            required: true
        });
        $("#li").validatebox({
            required: true
        });
        //提交表单
        $("#insertproform").linkbutton({
            onClick: function () {
                $("#insertProf").form("submit", {
                    url: "${pageContext.request.contextPath }/Banner/insert",
                    onSubmit: function () {
                        return $("#insertProf").form("validate");
                    },
                    success: function (res) {
                        if (res == "") {
                            $("#insertprodiv").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加成功"
                                });
                        } else {
                            $("#insertprodiv").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加失败,请稍后再试"
                                });
                        }
                        $("#dg").datagrid("load");
                    }
                });
            }
        });

        $("#addprobtn1").linkbutton({
            onClick: function () {
                $("#insertProf").form("clear");
            }
        });
    });

</script>
<form method="post" id="insertProf" enctype="multipart/form-data">
    图片名：<input name="title" id="iname" type="text"/><br/>
    图片简介：<input name="description" id="proadd" type="text"/><br/>
    上传图片：<input name="file" id="li" type="file"/><br/>
    <input type="button" value="添加" id="insertproform"/><input type="button" id="addprobtn1" value="重置"/>
</form>