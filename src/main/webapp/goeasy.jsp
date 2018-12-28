<%@page pageEncoding="utf-8" %>
<html>
<head>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        var goEasy = new GoEasy({
            appkey: "BC-f85983491bd943b0b09ca386f90fd862"
        });
        goEasy.publish({
            channel: "140",
            message: "Hello, GoEasy!"
        });
    })
</script>
<body>
<h2>this is goeasy</h2>
</body>
</html>