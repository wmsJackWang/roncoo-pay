<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/pay/config/edit" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="yhzfpz">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>用户支付配置</legend>
                    <dl>
							<dt>用户编号：</dt>
							<dd>
								<input type="text" name="userNo" size="25" maxlength="128" class="readonly" value="${rpUserPayConfig.userNo }"/>
							</dd>
					</dl>
					<dl>
							<dt>用户名：</dt>
							<dd>
								<input type="text" name="userName" maxlength="80" class="readonly" value="${rpUserPayConfig.userName }"/>
							</dd>
					</dl>
					<dl>
							<dt>支付产品编号：</dt>
							<dd>
								<input type="text" name="product.productCode" size="25" id="productCode"  maxlength="128" class="required readonly" value="${rpUserPayConfig.productCode }"/>
								<a class="btnLook" href="${baseURL}/pay/product/lookupList" lookupGroup="product" callback="product_callback">搜索</a>
							</dd>
					</dl>
					<dl>
							<dt>支付产品名称：</dt>
							<dd>
								<input type="text" name="product.productName" maxlength="80" class="required readonly" value="${rpUserPayConfig.productName }"/>
							</dd>
					</dl>
					<dl>
							<dt>收款方式：</dt>
							<dd>
								<select name="fundIntoType" id="fundIntoType">
		                            <c:forEach var="item" items="${FundInfoTypeEnums}">
		                                <option value="${item.name }" <c:if test="${item.name==rpUserPayConfig.fundIntoType}">selected="selected"</c:if>>${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>风险预存期：</dt>
							<dd>
								<input type="text" name="riskDay" maxlength="10" class="required digits" value="${rpUserPayConfig.riskDay }"/>
							</dd>
					</dl>
					<dl>
							<dt>是否自动结算：</dt>
							<dd>
								<input type="radio" name="isAutoSett" value="YES" <c:if test="${rpUserPayConfig.isAutoSett=='YES'}">checked="checked"</c:if>/>是
								<input type="radio" name="isAutoSett" value="NO" <c:if test="${rpUserPayConfig.isAutoSett=='NO'}">checked="checked"</c:if>/>否
							</dd>
					</dl>
					<dl>
						<dt>安全等级：</dt>
						<dd>
							<select name="securityRating" id="securityRating">
								<c:forEach var="item" items="${SecurityRatingEnum}">
									<option value="${item.name }" <c:if test="${item.name == rpUserPayConfig.securityRating}"> selected="selected"</c:if>>${item.desc }</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>IP白名单：</dt>
						<dd>
							<textarea  rows="2" cols="30" name="merchantServerIp" id="merchantServerIp"> ${rpUserPayConfig.merchantServerIp }</textarea>
						</dd>
					</dl>
				</fieldset>
				
				<fieldset id="we_field" style="display: none;">
					<legend>微信设置</legend>
					<table style="border-spacing: 10px">
						<tr>
							<td>APPID：</td>
							<td><input type="text" name="we_appId" id="we_appId" maxlength="128" class="required" value="${wxUserPayInfo.appId }"/>&nbsp;&nbsp;请到“<a href="https://mp.weixin.qq.com/" target="_blank" style="color: blue;">微信公众平台</a>”上点击“开发者中心”，复制AppID，例如：wx0fe958a123233135</span></td>
						</tr>
						<tr>
							<td>商户密钥：</td>
							<td><input type="text" name="we_partnerKey" id="we_partnerKey" maxlength="200" class="required" value="${wxUserPayInfo.partnerKey }"/>&nbsp;&nbsp;请到“<a href="https://pay.weixin.qq.com/" target="_blank" style="color: blue;">微信支付商户平台</a>”点击 帐户设置→API安全→设置密钥（请根据提示安装财付通证书），32位秘钥</td>
						</tr>
						<tr>
							<td>商户号：</td>
							<td><input type="text" name="we_merchantId" id="we_merchantId" maxlength="150" class="required" value="${wxUserPayInfo.merchantId }"/>&nbsp;&nbsp;微信支付开通成功的通知邮件里，复制“微信支付商户号”，例如：10054321</td>
						</tr>
					</table>
				</fieldset>
				
				<fieldset id="ali_field" style="display: none;">
					<legend>支付宝设置</legend>
					<table style="border-spacing: 10px">
						<tr>
							<td>合作者身份ID：</td>
							<td><input type="text" name="ali_partner" id="ali_partner" maxlength="128" class="required" value="${aliUserPayInfo.appId }"/></td>
						</tr>
						<tr>
							<td>MD5_KEY：</td>
							<td><input type="text" name="ali_key" id="ali_key" maxlength="150" class="required" value="${aliUserPayInfo.partnerKey }"/></td>
						</tr>
						<tr>
							<td>收款账号(商户号)：</td>
							<td><input type="text" name="ali_sellerId" id="ali_sellerId" maxlength="200" class="required" value="${aliUserPayInfo.merchantId }"/></td>
						</tr>
						<tr>
							<td>APPID：</td>
							<td><input type="text" name="ali_appid" id="ali_appid" maxlength="200" class="required" value="${aliUserPayInfo.appId }"/></td>
						</tr>
						<tr>
							<td>支付宝私钥：</td>
							<td><input type="text" name="ali_rsaPrivateKey" id="ali_rsaPrivateKey" maxlength="10000" class="required" value="${aliUserPayInfo.rsaPrivateKey }"/></td>
						</tr>
						<tr>
							<td>支付宝公钥：</td>
							<td><input type="text" name="ali_rsaPublicKey" id="ali_rsaPublicKey" maxlength="10000" class="required" value="${aliUserPayInfo.rsaPublicKey }"/></td>
						</tr>
					</table>
				</fieldset  id="jd_field" style="display: none;">
					<legend>京东设置</legend>
					<table style="border-spacing: 10px">
						<tr>
							<input  type ="hidden" value = "${JD_CLUB_NUMBER_CARD_ID_ID}"  name ="JD_CLUB_NUMBER_CARD_ID_ID" />
							<input  type ="hidden" value = "${JD_DES_SCERET_KEY_ID}"  name ="JD_DES_SCERET_KEY_ID" />
							<input  type ="hidden" value = "${JD_MD5_SCERET_KEY_ID}"  name ="JD_MD5_SCERET_KEY_ID" />
							<input  type ="hidden" value = "${JD_RSA_SCERET_KEY_ID}"  name ="JD_RSA_SCERET_KEY_ID" />
							<input  type ="hidden" value = "${JD_RSA_PUBLIC_KEY_ID}"  name ="JD_RSA_PUBLIC_KEY_ID" />
						</tr>
						<tr>
							<td>京东商户号：</td>
							<td><input type="text" name="JD_SELLER_ID" id="JD_SELLER_ID" maxlength="100" class="required" value="${jdUserPayInfo.merchantId}"/></td>
						</tr>
						<tr>
							<td>京东支付会员卡号：</td>
							<td><input type="text" name="JD_CLUB_NUMBER_CARD_ID" id="JD_CLUB_NUMBER_CARD_ID" maxlength="100" class="required" value="${JD_CLUB_NUMBER_CARD_ID}"/></td>
						</tr>
						<tr>
							<td>京东DES秘钥：</td>
							<td><input type="text" name="JD_DES_SCERET_KEY" id="JD_DES_SCERET_KEY" maxlength="100" class="required" value="${JD_DES_SCERET_KEY}"/></td>
						</tr>
						<tr>
							<td>京东MD5秘钥：</td>
							<td><input type="text" name="JD_MD5_SCERET_KEY" id="JD_MD5_SCERET_KEY" maxlength="100" class="required" value="${JD_MD5_SCERET_KEY}"/></td>
						</tr>						
						<tr>
							<td>RSA秘钥：</td>
							<td><input type="text" name="JD_RSA_SCERET_KEY" id="JD_RSA_SCERET_KEY" maxlength="10000" class="required" value="${JD_RSA_SCERET_KEY}"/></td>
						</tr>						
						<tr>
							<td>RSA公钥：</td>
							<td><input type="text" name="JD_RSA_PUBLIC_KEY" id="JD_RSA_PUBLIC_KEY" maxlength="10000" class="required" value="${JD_RSA_PUBLIC_KEY}"/></td>
						</tr>
					</table>
				<fieldset>
				
				</fieldset  id="union_field" style="display: none;">
					<legend>银联设置</legend>
					<table style="border-spacing: 10px">
						<tr>
							<td>appid：</td>
							<td><input type="text" name="union_appid" id="union_appid" maxlength="100" class="" value="${unionUserPayInfo.appId}"/></td>
						</tr>
						<tr>
							<td>mchid：</td>
							<td><input type="text" name="union_mchid" id="union_mchid" maxlength="100" class="" value="${unionUserPayInfo.merchantId}"/></td>
						</tr>
					</table>
				<fieldset>
				
					
				</fieldset>
			</div>
		</div>
		<div class="formBar">
			<ul style="float: left;">
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

<script>

function changeWePay(){
	var fundIntoType = $("#fundIntoType").val();
	var productCode = $("#productCode").val();
	
	$("#we_field").hide();
	$("#we_appId").attr("class", "");
	$("#we_merchantId").attr("class", "");
	$("#we_partnerKey").attr("class", "");
	
	$("#ali_field").hide();
	$("#ali_partner").attr("class", "");
	$("#ali_sellerId").attr("class", "");
	$("#ali_key").attr("class", "");
	$("#ali_appid").attr("class", "");
	$("#ali_rsaPrivateKey").attr("class", "");
	$("#ali_rsaPublicKey").attr("class", "");
	
	if(fundIntoType == 'MERCHANT_RECEIVES'){
		if(productCode != ""){
			$.ajax({  
	            type: "GET",
	            dataType : "json",
	            data:{productCode : productCode},
	            url: "${baseURL }/pay/way/getPayWay",
	            //请求成功完成后要执行的方法  
	            success: function(result){
	                if(result.length > 0){
	                   for (var i=0;i<result.length;i++){
	                      var obj = result[i];
	                      if(obj.name == "WEIXIN"){
	                      	$("#we_field").show();
	              			$("#we_appId").attr("class", "required");
	              			$("#we_merchantId").attr("class", "required");
	              			$("#we_partnerKey").attr("class", "required");
	              			
	                      }else if(obj.name == "ALIPAY"){
	                  		$("#ali_field").show();
	              			$("#ali_partner").attr("class", "required");
	              			$("#ali_sellerId").attr("class", "required");
	              			$("#ali_key").attr("class", "required");
	              			$("#ali_appid").attr("class", "required");
	              			$("#ali_rsaPrivateKey").attr("class", "required");
	              			$("#ali_rsaPublicKey").attr("class", "required");
	                      }else if(obj.name == "JINGDONG"){     
	                        $("#jd_field").show();
	                        $("#JD_CLUB_NUMBER_CARD_ID").attr("class", "required");
	              			$("#JD_DES_SCERET_KEY").attr("class", "required");
	              			$("#JD_MD5_SCERET_KEY").attr("class", "required");
	                      }
	                   }
	                }
	            },  
	            error : function() {
	            	alert("系统异常！");  
	            }   
	        });
		}
		
	}
}	 

changeWePay();

$("#fundIntoType").change(function(){
	changeWePay();
    });

function product_callback()
{
	changeWePay();
}
</script>