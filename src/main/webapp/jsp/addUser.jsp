<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
    //页面加载后，定义初始化按钮
    $(function () {
        //初始化表单控件---phone
        $("#phone").textbox({
            required: true,
        });
        //初始化表单控件---password
        $("#password").textbox({
            required: true,
        });
        //初始化表单控件---sign
        $("#sign").textbox({
            required: true,
        });
        //初始化表单控件---name
        $("#name").textbox({
            required: true,
        });
        //初始化表单控件---aharma
        $("#aharma").textbox({
            required: true,
        });
        //初始化表单控件---sex
        $("#sex").textbox({
            required: true,
        });
        //初始化表单控件---province
        $("#province").textbox({
            required: true,
        });
        $("#headPic").validatebox({
            required: true
        });
        //初始化表单控件---city
        $("#city").textbox({
            required: true,
        });
        //初始化表单控件---保存按钮
        $("#addFormRegistBtn").linkbutton({
            onClick: function () {
                // 提交表单 -- 调form的submit方法
                $("#addForm").form({
                    url: "${pageContext.request.contextPath}/User/registUser",
                    onSubmit: function () {
                        // 表单验证 -- 调form的validate方法
                        return true;
                    },
                    success: function () {
                        $.messager.show({
                            title: "系统提示",
                            msg: "注册成功！"
                        });
                        $("#registDialog").dialog("close");
                        $("#usersTable").datagrid("load");
                    },
                });
            }
        });
    });
</script>
<form id="addForm" method="post" enctype="multipart/form-data">
    手机号： <input type="text" name="phone" id="phone"/><br/>
    密码： <input type="text" name="password" id="password"/><br/>
    标识： <input type="text" name="sign" id="sign"/><br/>
    头像： <input name="file" id="headPic" type="file"/><br/>
    姓名： <input type="text" name="name" id="name"/><br/>
    法名： <input type="text" name="dharma" id="aharma"/><br/>
    性别： <input type="text" name="sex" id="sex"/><br/>
    省名： <input type="text" name="province" id="province"/><br/>
    城市名： <input type="text" name="city" id="city"/><br/>
    <a id="addFormRegistBtn">注册</a>
</form>