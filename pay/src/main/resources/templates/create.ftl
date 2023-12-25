<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>支付</title>
</head>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<body>
<h1>订单号为: ${orderId}</h1>
<p>支付金额为：${amount}</p>
<div id="myQrcode"></div>
</body>
<script>
    jQuery('#myQrcode').qrcode({
        text: "${codeUrl}"
    });
</script>
</html>