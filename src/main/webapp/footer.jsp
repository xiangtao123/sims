<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="panel-title">
	<span id="clock">2018-01-01 20:22</span>
	<!-- <span style="margin-left: 80px;"><a href="${ctx }/share/member.html" style="color:#666; " target="_blank">关注我们，下载客户端！</a></span> -->
	<span style="float: right;">权限管理系统v0.0.1 copyright&copy;姐闪网</span>
</div>		

<script type="text/javascript">
	<!--
	
	var clock = function(){};
	clock.days = ['日','一','二','三','四','五','六'];
	clock.refresh = function(){
		var date = new Date();
		var time =  date.getFullYear() + 
					   '-' + ( date.getMonth()  		> 8 ? (date.getMonth()+1) 	: ('0' + (date.getMonth()+1)	) ) +
					   '-'  + ( date.getDate()			> 9 ? date.getDate() 		: ('0' + date.getDate()			) ) +
					   ' ' + ( date.getHours() 		> 9 ? date.getHours() 		: ('0' + date.getHours()	) ) +
					   ':' + ( date.getMinutes() 		> 9 ? date.getMinutes() 	: ('0' + date.getMinutes()	) ) +  
					   ' 周'  + clock.days[date.getDay()]	   ;
		$('#clock').text(time);
		setTimeout(clock.refresh, 500);
	};
		
	$(function(){
		clock.refresh();
	});
	
	//-->
	
</script>

