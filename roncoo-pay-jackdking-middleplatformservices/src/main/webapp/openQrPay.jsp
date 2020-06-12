<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="jackdking">

    <title>聚合支付</title>

    <!-- Bootstrap core CSS -->
    <link href="//cdn.jsdelivr.net/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href='<c:url context="/roncopay" value="/css/main.css"/>'   rel="stylesheet"  type="text/css">
    <link rel="icon" type="image/x-icon" href='<c:url context="/roncopay"  value="/img/aggregate/favicon.ico"/>'>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="//cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="//cdn.jsdelivr.net/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<!-- Begin page content -->
<div class="container">
    <div class="page-header">
        <h1>聚合支付系统</h1>
    </div>
    <div class="main" align="center">
        <div class="inwrap">
            <h3>#扫码测试#</h3>
            <h5>
                <input type="radio" name="amount" id="amount" value="1" checked="checked"> 0.01 元&nbsp;&nbsp;
                <input type="radio" name="amount" id="amount" value="100"> 1.00 元&nbsp;&nbsp;
                <input type="radio" name="amount" id="amount" value="1000"> 10.00 元&nbsp;&nbsp;
                任意: <input type="text" id="othAmt" style="width: 60px;" value=""> 元
            </h5>
            <div class="example" >
                <div id="qrcode"></div>
                <div><h3 id="vAmt" style="color: red">0.01元</h3></div>
                <div><h4 >请使用支付宝或微信手机客户端扫一扫</h4></div>
            </div>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted">&copy;2020 比特支付 </p>
    </div>
</footer>


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="//cdn.jsdelivr.net/ie10-viewport/1.0.0/ie10-viewport.min.js"></script>
<script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
<script src="//cdn.jsdelivr.net/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src='<c:url context="/roncopay"  value="/js/qrcode.min.js"/>'></script>

<script>
    var qrcode = new QRCode(document.getElementById("qrcode"), {
        width : 200,
        height : 200
    });

    function makeCode () {
        var elText = document.getElementById("othAmt");
        var amt = $.trim(elText.value);
        var vAmt = (amt/1).toFixed(2);
        if (amt == '') {
            amt = $("input[name='amount']:checked").val();
            vAmt = (amt/100).toFixed(2);
        }
        if(vAmt == 'NaN' || vAmt <= 0) {
            alert("输入金额不正确");
            $("#othAmt").val('');
            return;
        }
        $("#vAmt").text(vAmt+'元');
        //var qrText = 'http://xxpay-shop.ngrok.cc/goods/qrPay/' + (vAmt*100);
        
        //微信jsapi支付  +  支付宝 wap支付
        //var qrText = 'http://wallet-test.sohupay.com/testwallet/trans/qrPay.pay?amount=' + (vAmt*100);
        qrText = 'http://wallet-test.sohupay.com/testwallet/trans/qrPay.pay?amt=' + vAmt;
        qrcode.makeCode(qrText);
    }

    makeCode();

    $("input:radio").click(function () {
        $("#othAmt").val('');
        makeCode();
    });

    $("#othAmt").on("blur", function () {
        makeCode();
    }).on("keydown", function (e) {
        if (e.keyCode == 13) {
            makeCode();
        }
    });

</script>

</body>
</html>
