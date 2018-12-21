<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<script type="text/javascript">
    $(function () {
        $("#chaptername").validatebox({
            required: true
        });
        $("#xixi").validatebox({
            required: true
        });
        //提交表单
        $("#insertchapterbtn").linkbutton({
            onClick: function () {
                console.log(album);
                $("#insertchapter").form("submit", {
                    url: "${pageContext.request.contextPath }/Chapter/insertChapter",
                    onSubmit: function () {
                        return $("#insertchapter").form("validate");
                    },
                    success: function (res) {
                        if (res == "") {
                            $("#insertchapterdiv").dialog("close"),
                                $.messager.show({
                                    title: "提示窗口",
                                    msg: "添加成功"
                                });
                        } else {
                            $("#insertchapterdiv").dialog("close"),
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

        $("#insertchapterbtn2").linkbutton({
            onClick: function () {
                $("#insertProf").form("clear");
            }
        });

        $("#albumidaa").val("" + album + "");
    });

</script>
<form method="post" id="insertchapter" enctype="multipart/form-data">
    <input id="albumidaa" type="text" style="display:none" name="id"/><br/>
    章节名：<input name="title" id="chaptername" type="text"/><br/>
    上传音频：<input name="file" id="xixi" type="file"/><br/>
    <input type="button" value="添加" id="insertchapterbtn"/><input type="button" id="insertchapterbtn2" value="重置"/>
</form>