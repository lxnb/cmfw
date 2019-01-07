<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>持名法州后台管理中心</title>

    <link rel="icon" href="img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="css/common.css" type="text/css">
    <link rel="stylesheet" href="css/login.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/icon.css">
    <script type="text/javascript" src="script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="script/common.js"></script>
    <script type="text/javascript">

        $(function () {
            //点击更换验证码：
            $("#captchaImage").click(function () {//点击更换验证码
                $(this).prop("src", "${pageContext.request.contextPath}/Admin/code?time=" + new Date());
            });


            //初始化按钮
            $("#adminLogin").linkbutton({
                onClick: function () {

                    $("#loginForm").form("submit", {
                        //地址
                        url: "${pageContext.request.contextPath}/Admin/login",
                        //提交前表单验证
                        onSubmit: function () {
                            return $("#loginForm").form("validate");
                        },
                        //成功响应后函数
                        success: function (result) {
                            if (result == "") {
                                location.href = "${pageContext.request.contextPath}/main/main.jsp";
                            } else {
                                $("#loginspan").text(result);

                            }
                        }
                    });
                }
            });


        });
    </script>
</head>
<body>

<div class="login">
    <form id="loginForm" method="post">

        <table>
            <tbody>
            <tr>
                <td width="190" rowspan="2" align="center" valign="bottom">
                    <img src="img/header_logo.gif"/>
                </td>
                <th>
                    用户名:
                </th>
                <td>
                    <input type="text" name="name" class="text" value="" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th>
                    密&nbsp;&nbsp;&nbsp;码:
                </th>
                <td>
                    <input type="password" name="password" class="text" value="" maxlength="20" autocomplete="off"/>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <th>验证码:</th>
                <td>
                    <input type="text" id="enCode" name="enCode" class="text captcha" maxlength="6"/>
                    <img id="captchaImage" class="captchaImage" src="${pageContext.request.contextPath}/Admin/code"
                         title="点击更换验证码"/>
                </td>
            </tr>
            <tr>
                <td>
                    <span id="loginspan"></span>
                </td>
                <th>
                    &nbsp;
                </th>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <th>&nbsp;</th>
                <td>
                    <input type="button" class="homeButton" value="" onclick="location.href='/'"><a
                        id="adminLogin">提交</a>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="powered">COPYRIGHT ? 2008-2017.</div>
        <div class="link">
            <a href="http://www.chimingfowang.com/">持名佛网首页</a> |
            <a href="http://www.chimingbbs.com/">交流论坛</a> |
            <a href="">关于我们</a> |
            <a href="">联系我们</a> |
            <a href="">授权查询</a>
        </div>
    </form>
</div>
</body>
</html>
