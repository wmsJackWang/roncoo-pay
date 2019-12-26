<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script>
    	var base =  ${request.contextPath};
    </script>
    
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="jackdking">
    
    <!-- Bootstrap core CSS -->
    <link href="//cdn.jsdelivr.net/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="//cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="//cdn.jsdelivr.net/respond/1.4.2/respond.min.js"></script>
    
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="//cdn.jsdelivr.net/ie10-viewport/1.0.0/ie10-viewport.min.js"></script>
	<script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
	<script src="//cdn.jsdelivr.net/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="${base}/js/qrcode.min.js"></script>
    <![endif]-->
   
</head>
<body>

<div id="money" style="position: absolute;top: 280;left: 800" >价格:${orderPrice}，商品名称:${productName}</div>
<div id="qrcode" style="position: absolute;top: 300;left: 800" ></div>
</body>
<script>
    var qrcode = new QRCode(document.getElementById("qrcode"), {
        width : 200,
        height : 200
    });

    function makeCode () {
 
        //var qrText = 'http://xxpay-shop.ngrok.cc/goods/qrPay/' + (vAmt*100);
        var qrText = "${codeUrl}";
        qrcode.makeCode(qrText);
    }

    makeCode();

</script>
</html>
