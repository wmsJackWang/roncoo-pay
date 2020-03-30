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
	<input type="hidden" id=orderStr value='${orderStr}'/>
		
	<script>
    	$(document).ready(function() {
    		var orderStr = $("#orderStr").val();
    		ap.tradePay({
    			orderStr: orderStr
      	    }, function(res){
      	    	if(res.resultCode == "9000"){
      	    		alert("支付成功")
      	    	}
      	    	else{
      	    		alert("支付失败",res.resultCode)
      	    	}
      	     	
      	    });
    	})
	</script>

</body>
</html>