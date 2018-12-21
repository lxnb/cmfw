<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<script type="text/javascript">
    $(function () {
        $("#albumname").validatebox({
            required: true
        });
        $("#albumthor").validatebox({
            required: true
        });
        $("#albumread").validatebox({
            required: true
        });
        $("#brief").validatebox({
            required: true
        });
        $("#li").validatebox({
            required: true
        });
        //提交表单
        $("#insertalbumbtn").linkbutton({
            onClick: function () {
                $("#insertalbum").form("submit", {
                    url: "${pageContext.request.contextPath }/Album/insertAlbum",
                    onSubmit: function () {
                        return $("#insertalbum").form("validate");
                    },
                    success: function (res) {
                        if (res == "") {
                            $("#insertalbumdiv").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加成功"
                                });
                        } else {
                            $("#insertalbumdiv").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加失败,请稍后再试"
                                });
                        }
                        $("#album").treegrid("load");
                    }
                });
            }
        });

        $("#insertalbumbtn2").linkbutton({
            onClick: function () {
                $("#insertProf").form("clear");
            }
        });
    });

</script>
<form method="post" id="insertalbum" enctype="multipart/form-data">
    专辑名：<input name="title" id="albumname" type="text"/><br/>
    作者：<input name="author" id="albumthor" type="text"/><br/>
    朗读：<input name="broadcast" id="albumread" type="text"/><br/>
    简介：<input name="brief" id="brief" type="text"/><br/>
    封面：<input name="file" id="lala" type="file"/><br/>
    <input type="button" value="添加" id="insertalbumbtn"/><input type="button" id="insertalbumbtn2" value="重置"/>
</form>