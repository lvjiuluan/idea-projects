<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>支付</title>
</head>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<body>
<#--<h1>订单号为: ${orderId}</h1>-->
<#--<p>支付金额为：${amount}</p>-->
<div id="myQrcode"></div>
<div id="orderId" hidden>${orderId}</div>
<div id="returnUrl" hidden>${returnUrl}</div>

</body>
<style>
    #myQrcode {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>
<script>
    jQuery('#myQrcode').qrcode({
        text: "${codeUrl}"
    });
    $(function () {
        // 定时器
        setInterval(function () {
            console.log('开始查询支付状态...')
            $.ajax({
                url: '/pay/queryByOrderId',
                data: {
                    'orderId': $('#orderId').text()
                },
                success: function (result) {
                    console.log(result);
                    if (result.platformStatus != null && result.platformStatus === 'SUCCESS') {
                        location.href = $('#returnUrl').text()
                    }
                },
                error: function (result) {
                    alert(result);
                }
            })
        }, 2000)
    })
</script>
</html>