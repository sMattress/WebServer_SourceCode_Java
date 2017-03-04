<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">

</head>

<body>
	<div class="navbar-wrapper">
		<div class="container-fluid">
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header" style="font-size: 20px">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target="#navbar"
							aria-expanded="false" aria-controls="navbar">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand">智能床垫后台管理系统</a>
					</div>

					<div id="navbar" class="navbar-collapse collapse"
						style="font-size: 17px">
						<ul class="nav navbar-nav" id="header">
							<li class="dropdown" id="customermanager"><a href="#"
								class="dropdown-toggle" data-toggle="dropdown" role="button"
								aria-haspopup="true" aria-expanded="false">用户管理<span
									class="caret"></span></a>
								<ul class="dropdown-menu">
									<li id="onlinecustomer"><a
										href="<%=basePath%>customer/customersOnlineList">在线用户</a></li>
									<li id="customerlist"><a
										href="<%=basePath%>customer/customersList">用户列表</a></li>
								</ul></li>

							<li class="dropdown" id="devicemanager"><a href="#"
								class="dropdown-toggle" data-toggle="dropdown" role="button"
								aria-haspopup="true" aria-expanded="false">设备管理<span
									class="caret"></span></a>
								<ul class="dropdown-menu">
									<li id="devicelist"><a
										href="<%=basePath%>deviceManager/devicesList">设备列表</a></li>
									<li id="releasedevicelist"><a
										href="<%=basePath%>deviceManager/releaseDeviceList">出厂设备</a></li>
									<li id="validatedevicelist"><a
										href="<%=basePath%>deviceManager/validatedeviceList">工作设备</a></li>
									<li id="invalidatedevicelist"><a
										href="<%=basePath%>deviceManager/invalidatedeviceList">报废设备</a></li>
									<li id="enterdevice"><a
										href="<%=basePath%>deviceManager/enterdevice">录入设备</a></li>
								</ul></li>
							<li id="knowledgemanager"><a
								href="<%=basePath%>knowledge/knowledgeList">知识管理</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li id="managerinfo"><a>超级管理员</a></li>
							<li id="logout"><a href="<%=basePath%>manager/logout">注销</a></li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
	</div>
	<!-- 	Bootstrap core JavaScript
    ==================================================
	Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="../js/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<!-- 	IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>




