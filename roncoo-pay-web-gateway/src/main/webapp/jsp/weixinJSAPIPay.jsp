<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no" name="format-detection">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href='<c:url value="/img/favicon.ico"/>'/>
    <link rel="bookmark" href='<c:url value="/img/favicon.ico"/>'/>
	<title>sohu-paycenter</title>
    <style>
        body{font-family: 'Microsoft YaHei';}
        #amount,#error{height: 80px; line-height: 80px; text-align: center; color: #f00; font-size: 30px; font-weight: bold;}
        #error{font-size: 20px;}
        #info{padding: 0 10px; font-size: 12px;}
        table{width: 100%; border-collapse: collapse;}
        tr{border: 1px solid #ddd;}
        td{padding: 10px;}
        .fr{text-align: right; font-weight: bold;}
    </style>
    <script src='<c:url value="/js/jquery-2.1.0.min.js"/>'></script>
    <script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
    <script src="//res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="//wx.gtimg.com/wxpay_h5/fingerprint2.min.1.4.1.js"></script>
	<script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.inc.min.js"></script>
</head>
<body>
	<%-- <div id="amount">¥ ${amount}</div>
	<div id="info">
	    <table>
	        <tr>
	            <td>buy goods:</td>
	            <td class="fr"></td>
	        </tr>
	        <tr>
	            <td>receiving side:</td>
	            <td class="fr">sohu</td>
	        </tr>
	    </table>
	</div> --%>

	<input type="hidden" id="orderMap1" value='${orderMap}'/>
	
	<input type="hidden" id="orderMap2" value="${orderMap}"/>
	<script>
    	$(document).ready(function() {
    		var orderMap1 =$("#orderMap1").val();
    		var orderMap2 =JSON.parse(orderMap1);
    		console.log("orderMap1",orderMap1)
    		
    		console.log("orderMap2",orderMap2)
    		 function onBridgeReady(){
                WeixinJSBridge.invoke(
                        'getBrandWCPayRequest', {
                            "appId" : orderMap2.result.appId,     //公众号名称，由商户传入
                            "timeStamp" : orderMap2.result.timeStamp,         //时间戳，自1970年以来的秒数
                            "nonceStr" : orderMap2.result.nonceStr, //随机串
                            "package" : orderMap2.result.package,
                            "signType" : orderMap2.result.signType,         //微信签名方式：
                            "paySign" : orderMap2.result.sign //微信签名,paySign 采用统一的微信支付 Sign 签名生成方法，注意
                        },
                        function(res) {
                        	alert("res",res)
                            if(res.err_msg == "get_brand_wcpay_request:ok" ) {    
                                alert('支付成功！');
                            } else if(res.err_msg == "get_brand_wcpay_request:fail") {
                                alert('支付失败：' + res.err_msg);
                            }
                            WeixinJSBridge.call('closeWindow');
                        }
                );
            }
            if (typeof WeixinJSBridge == "undefined") {
                if ( document.addEventListener ) {
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
            } else {
                onBridgeReady();
            }
        });
	</script>
</body>
</html>