<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.apache.shiro.authc.AuthenticationException" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	
	<shiro:hasPermission name="page:welcome">
		<script type="text/javascript">
			location.href='${ctx}/welcome/index';
		</script>
	</shiro:hasPermission>
	
	
	<jsp:include page="/include.jsp"></jsp:include>
	<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
	
	<style type="text/css">
		body{ font-family:"微软雅黑",Verdana, Geneva, sans-serif; font-size:12px; background:#fff; padding: 0px;}
		.textbox .textbox-prompt { font-size: 16px; font-weight: bold; width:98%; }
		.easyui-fluid { width:68%;  height: 38px; }
		
		.help{ font-size:14px; font-weight:100; color:#787878; text-decoration:none;}
		.t { height:76px; line-height:90px; background:#f5f5f5; border-bottom:1px solid #e5ecf0; overflow:hidden;}
		.c{ width:964px; height:460px; margin:20px auto 0 auto; background:url(static/images/login_bg_0.jpg) no-repeat; }
	    .boxc{ background:#fff; margin:100px 50px 20px 0px; padding: 40px 0px 30px 50px; float:right; width: 40%;}
	    
	    .c{ width:964px; height:460px; margin:4px auto 0 auto; background:url(static/images/login_bg_0.jpg) no-repeat; }
	</style>
 
 </head>
<body>
	
	<div class="t">
		<span style="margin: 10px 0px 0px 30px;;font-size:30px;font-weight: 100; line-height: 1.25;">
			<spring:message code="app.title"></spring:message>
		</span>
		<span class="help">
			<spring:message code="login.tips"></spring:message>
		</span>
	</div>
	
	<div class="c">
		<div id="login-panel-container" class="boxc" >
			<%
				AuthenticationException ex = (AuthenticationException) request.getAttribute("shiroLoginFailure");
				if (ex != null) {
			%>
				<div class="alert">
					<%=ex.getMessage() %>
				</div>
			<%
				}
			%>
			
			<span class="help">用户登录</span>	
		    <form id="loginForm" method="post" >
								 
				 <div style="margin-bottom: 20px;">
		    		<span class="textbox easyui-fluid">
		    			<span class="textbox-addon-right" style="right: 0px;">
		    				<a tabindex="-1" icon-index="0" class="textbox-icon icon-man textbox-icon-disabled" href="javascript:void(0)" style="width: 38px; height: 38px;"></a>
		    			</span>
		    		
		    			<input maxlength="20" tabindex="2" id="username" name="username" type="text" autocomplete="off"
		    					 class="textbox-text validatebox-text textbox-prompt" placeholder="用户名"
		    					 style="padding: 12px; margin-left: 0; margin-right: 0;">
					
		    		</span>
					<span for="username" class="error"></span>
		
		    	</div>

		    	<div style="margin-bottom: 20px;">
		    		<span class="textbox easyui-fluid">
		    			<span class="textbox-addon-right" >
		    				<a tabindex="-1" icon-index="0" class="textbox-icon icon-lock textbox-icon-disabled" href="javascript:void(0)" style="width: 38px; height: 38px;"></a>
		    			</span>
		    			<input maxlength="10" tabindex="3" id="password" name="password" type="password" autocomplete="off"
		    					 class="textbox-text validatebox-text textbox-prompt" placeholder="密码"
		    					 style="padding: 12px; margin-left: 0; margin-right: 0;">
		    		</span>
		    		
		    		<span for="password" class="error"></span>
		    		
		    	</div>


		    	<div style="margin-bottom: 20px;">
		    		<span class="textbox easyui-fluid" >
		    		   <span class="textbox-addon-right" style="right: 0px;">
		               <img tabindex="-1" class="textbox-icon " alt="验证码" title="看不清，下一张" 
		                    src="kaptcha.jpg" id="captchaimg" onclick="nextCaptcha();"
		               		style="cursor: pointer;width: 80px;height: 30px;margin-left: 4px; margin-top: 4px;" />
		    			</span>
		    			<input tabindex="4" id="captcha" name="captcha" type="text" autocomplete="off"
		    					 class="textbox-text validatebox-text textbox-prompt" placeholder="验证码"
		    					 style="padding: 12px; margin-left: 0px; margin-right: 0px;" maxlength="4" />

		    		</span>
		    		<span for="captcha" class="error"></span>
		    		
		    	</div>
				 
		    </form>

	    	<a  tabindex="5" id="submitLinkBtn" class="easyui-linkbutton" style="width:48%;background-color: #7cbe56; font-weight: bold;" onclick="submitForm()">
	    		<span style="font-size:16px; font-weight: bold;">登 &nbsp;  录</span>
	    	</a>
	    	
		</div>
	
	</div>
		
	<div style="clear: both;"></div>
	
	<!-- 
	<div style="width:1008px; height: 184px; margin:4px auto 0 auto;">
		<img alt="校园风采" src="static/images/login-header.png" >
	</div>
	 -->
		
	<div style="float: right;margin-right: 100px;margin-top: 10px;margin-bottom:  10px;">
		<jsp:include page="/footer.jsp"/>
	</div>
			
	<div style="clear: both;"></div>


	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${resourceRoot}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
	
	<script type="text/javascript">
			var opt = {
				rules:{
					username:{required:true,remote:"${ctx}/login/checkLoginName" },
				 	password:{required:true,remote:{	url: "${ctx}/login/checkPwd", type: "get",  data: { username: function(){return $("#username").val();} } }  }
					,captcha:{required:true,remote:"${ctx}/login/kaptcha"}
				},
				messages:{
					username:{required:"登录名不能为空",remote:"登录名不存在"},
					password:{required:"密码不能为空",remote:"密码错误"}
					,captcha:{required:"请输入验证码",remote:"验证码错误"}
				}
			};
			
			function submitForm(){
				$('#loginForm').form('submit',{
					onSubmit:function() {
						var result = $("#loginForm").valid();
						if(result){
							document.getElementById('loginForm').submit();
						}
						return false;
					}
				});
			}

			function clearForm(){
				$('#loginForm').form('clear');
			}

			function nextCaptcha(){
				document.getElementById("captchaimg").src="kaptcha.jpg?q="+new Date();
			}

			$(document).ready(function(){
				$("#loginForm").validate(opt);
				$('#username').focus();

				$('.easyui-fluid').click(function(){
					$($(this).find('input')[0]).focus();
				});

				$(document).keydown(function(e){
					if (e.keyCode == 13) {
						$('#submitLinkBtn').focus();
						submitForm();
					}
				});
			});

	</script>
</body>
</html>
