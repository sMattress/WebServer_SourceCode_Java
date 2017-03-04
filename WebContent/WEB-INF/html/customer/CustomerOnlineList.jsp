<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="../images/ic_system.png">
<title>在线用户</title>
<link rel="stylesheet" href="../css/bootstrap.css" />
<script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<!-- INCLUDES -->
<link rel="stylesheet" href="../css/bootstrap-table-expandable.css">
<script src="../js/bootstrap-table-expandable.js"></script>
<style type="text/css">
.nav-sidebar {
	margin-right: -21px; /* 20px padding + 1px border */
	margin-left: -20px;
}

.nav-sidebar>.active>a, .nav-sidebar>.active>a:hover, .nav-sidebar>.active>a:focus
	{
	color: #fff;
	background-color: #428bca;
}

.title-center {
	text-align: center;
	padding-right: 100px;
	margin-bottom: 30px;
}
</style>
</head>
<body>
	<!--NavigationHead  -->
	<jsp:include page="/WEB-INF/html/navigation/NavigationHead.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar" style="margin-top: 60px;">
				<ul class="nav nav-sidebar">
					<li class="active"><a
						href="<%=basePath%>customer/customersOnlineList">在线用户</a></li>
					<li><a href="<%=basePath%>customer/customersList">用户列表</a></li>
				</ul>
			</div>
			<div class="col-sm-7 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h3 class="title-center">在线用户</h3>
				<div class="container table-responsive">
					<table class="table table-hover table-expandable">
						<thead>
							<tr>
								<th>序号</th>
								<th>用户名</th>
								<th>性别</th>
								<th>年龄</th>
								<th>手机号</th>
								<th>注册时间</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${customersview}" var="view">
								<tr>
									<td>${view.customer.IId}</td>
									<td>${view.customer.vcName}</td>
									<td><c:if test="${view.customer.tiGender==1}">男</c:if> <c:if
											test="${view.customer.tiGender==2}">女</c:if></td>
									<td>${utilKey.getAgeByBirthday(view.customer.TBirthday)}</td>
									<td>${view.customer.CPhoneNumber}</td>
									<td><fmt:formatDate value="${view.customer.TRegisteTime}"
											pattern="yyyy-MM-dd" /></td>
								</tr>
								<tr style="display: none">
									<td colspan="4">
										<h4>绑定设备</h4>
										<ul class="list-group">
											<c:choose>
												<c:when
													test="${view.deviceList!=null && fn:length(view.deviceList) > 0}">
													<c:forEach items="${view.deviceList}" var="device">
														<li class="list-group-item"><c:if
																test="${device.IStatus==1}">
																<span class="badge" style="background-color: green;">正常</span>
															</c:if> <c:if test="${device.IStatus==2}">
																<span class="badge" style="background-color: red;">报废</span>
															</c:if>设备号${device.IId}：${device.vcName}，绑定时间：<fmt:formatDate
																value="${device.TTime}" pattern="yyyy-MM-dd" /></li>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<li class="list-group-item">无</li>
												</c:otherwise>
											</c:choose>
										</ul>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<ul class="pager">
						<c:choose>
							<c:when test="${page.pageNo>1}">
								<li><a
									href="<%=basePath%>customer/customersOnlineList?pageNo=${page.pageNo-1}">&larr;
										上一页</a>
								<li>
							</c:when>
							<c:otherwise>
								<li class="disabled"><a>&larr; 上一页</a></li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${page.pageNo < page.totalPages}">
								<li><a
									href="<%=basePath%>customer/customersOnlineList?pageNo=${page.pageNo+1}">下一页
										&rarr;</a>
								<li>
							</c:when>
							<c:otherwise>
								<li class="disabled"><a>下一页 &rarr;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="../js/bootstrap.min.js"></script>
</html>