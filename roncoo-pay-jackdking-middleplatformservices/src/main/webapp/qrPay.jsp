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
	<title></title>
    <style>
    	*{ margin:0; padding:0;box-sizing:border-box;}
        body{font-family: 'Microsoft YaHei';}
        #amount,#error{height: 80px; line-height: 80px; text-align: center; color: #f00; font-size: 30px; font-weight: bold;}
        #error{font-size: 20px;}
        #info{padding: 0 10px; font-size: 12px;}
        table{width: 100%; border-collapse: collapse;}
        tr{border: 1px solid #ddd;}
        td{padding: 10px;}
        .fr{text-align: right; font-weight: bold;}
        html,body{
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		.mask{
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background: rgba(0,0,0,.5);
			display: none;
		}
		.dialog{
			position: absolute;
			width: 85%;
			height: 1.7rem;
			top: 0;
			bottom: 0;
			left: 0;
			right: 0;
			margin: auto;
			z-index: 1000;
			background: #fff;
			font-size: 0.14rem;
			border-radius: 6px;
			box-shadow: 0 0 30px #565656;
			color: #303030;
			font-family:"华文楷体";
			display: none;
		}
		.dialog .con{
			width: 100%;
			height: 1.2rem;
			padding: 0.4rem 0.2rem;
			border-bottom: 1px solid #dcdcdc;
			text-align: center;
			font-size: 0.16rem;
			font-weight: bold;
			color: rgb(41,41,41);
		}
		.dialog .footer{
			width: 100%;
			text-align: center;
			line-height: 0.48rem;
		}
		.dialog .footer .btn{
			text-decoration: none;
			color: rgb(91,106,145);
			font-weight: bold;
			font-size: 0.18rem;
		}
    </style>
    <script>
		var winW = document.documentElement.clientWidth;
	    var desW = 750;
	    var scale = 750/200;
	    document.documentElement.style.fontSize = winW/scale+"px";
	    window.onresize = function(){
	        var winW = document.documentElement.clientWidth;
	        document.documentElement.style.fontSize = winW/scale+"px";
	    }
	</script>
	<script src='<c:url context="/roncopay"  value="/js/jquery-2.1.0.min.js"/>'></script>
    <script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
    <script src="//res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="//wx.gtimg.com/wxpay_h5/fingerprint2.min.1.4.1.js"></script>
	<script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.inc.min.js"></script>
</head>
<body>
	<div class="mask" id="mask"></div>
	<div class="dialog" id="dialog">
		<p class="con" id="dialogCon"></p>
		<div class="footer" id="dialogBtn">
			<a href="javascript:void(0)" class="btn">确定</a>
		</div>
	</div>
	<input type="hidden" value="${resMsg}" id="resMsg"/>
	<input type="hidden" id="orderStr" value='${orderStr}'/>
	<c:if test="${result == 'failed'}" >
		<script>
			window.onload = function(){
				if(true){
					setTimeout(function(){
						var resMsg = $("#resMsg").val()	
						$("#dialogCon").text(resMsg);
						$("#dialog").show();
						$("#mask").show();
						$("#dialogBtn").click(function(){
							$("#dialog").hide();
							$("#mask").hide();
						})
					},500)
				}
			}
		</script>
	</c:if>
	<c:if test="${client == 'zfb'}" >
		<%-- ${orderMap.payUrl} --%>
    	<script>
	    	$(document).ready(function() {
	    		var orderStr = $("#orderStr").val();
	    		ap.tradePay({
	    			orderStr: orderStr
	      	    }, function(res){
	      	    	return false;
	      	    	if(res.resultCode == "9000"){
	      	    		ap.alert("支付成功");
	      	    	}
	      	    	else{
	      	    		ap.alert("apres",res.resultCode,res.callbackUrl,res.memo,res.result);
	      	    	}
	      	     	
	      	    });
	    	})
    	</script>
	</c:if>
	<input type="hidden" id="orderMap1" value='${orderMap}'/>
	<c:if test="${client == 'wx'}" >
    	<script>
	    	$(document).ready(function() {
	    		var orderMap1 =$("#orderMap1").val();
	    		var orderMap2 =JSON.parse(orderMap1);
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
	                        	WeixinJSBridge.call('closeWindow');
	                        	return false;
	                            if(res.err_msg == "get_brand_wcpay_request:ok" ) {    
	                                alert('支付成功！');
	                            } else if(res.err_msg == "get_brand_wcpay_request:fail") {
	                                alert('支付失败：' + res.err_msg);
	                            }
	                           
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
	</c:if>
</body>
</html>