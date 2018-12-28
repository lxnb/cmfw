<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    $(function () {
        $("#excel").validatebox({
            required: true
        });
        //提交表单
        $("#insertexcel").linkbutton({
            onClick: function () {
                $("#excelfrom").form("submit", {
                    url: "${pageContext.request.contextPath }/Banner/insert",
                    onSubmit: function () {
                        return $("#excelfrom").form("validate");
                    },
                    success: function (res) {
                        if (res == "") {
                            $("#inseruserExcel").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加成功"
                                });
                        } else {
                            $("#inseruserExcel").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加失败,请稍后再试"
                                });
                        }
                        $("#usersTable").datagrid("reload");
                    }
                });
            }
        });

        $("#addexcel").linkbutton({
            onClick: function () {
                $("#insertProf").form("clear");
            }
        });
    });

</script>
<form method="post" id="excelfrom" enctype="multipart/form-data">
    文档路径：<input name="file" id="excel" type="file"/><br/>
    <input type="button" value="添加" id="insertexcel"/><input type="button" id="addexcel" value="重置"/>
</form>