<%@page pageEncoding="utf-8" %>
<html>
<head>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
</head>
<script type="text/javascript">
    var goEasy = new GoEasy({
        appkey: "BC-f85983491bd943b0b09ca386f90fd862"
    });
    goEasy.subscribe({
        channel: "140",
        onMessage: function (message) {
            alert("Channel:" + message.channel + " content:" + message.content);
        }
    });
</script>
<body>
<h2>Hello World! I LOVE YOU TOO</h2>
</body>
</html>