

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<link rel="icon" href="../images/ic_system.png">
<title>工作设备</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<!-- <link href="../css/dashboard.css" rel="stylesheet"> -->

<style type="text/css">
.nav-sidebar>.active>a, .nav-sidebar>.active>a:hover, .nav-sidebar>.active>a:focus
	{
	color: #fff;
	background-color: #428bca;
}
</style>
</head>
<body>
	<!--NavigationHead  -->
	<jsp:include page="/WEB-INF/html/navigation/NavigationHead.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row clearfix" style="margin-top: 90px">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">
				<div class="tabbable" id="tabs-device">
					<ul class="nav nav-tabs"
						style="font-size: 18px;">
						<li role="presentation"><a
							href="<%=basePath%>deviceManager/devicesList">设备列表</a></li>
						<li role="presentation"><a
							href="<%=basePath%>deviceManager/releaseDeviceList">出厂设备</a></li>
						<li role="presentation" class="active"><a
							href="<%=basePath%>deviceManager/validatedeviceList">工作设备</a></li>
						<li role="presentation"><a
							href="<%=basePath%>deviceManager/invalidatedeviceList">报废设备</a></li>
						<li role="presentation"><a
							href="<%=basePath%>deviceManager/enterdevice">录入设备</a></li>
					</ul>
					<div class="tab-content" style="margin: 30px 10px">
						<div class="tab-pane active" id="panel-validatedevicelist">
							<div class="table-responsive">
								<table class="table table-hover ">
									<thead style="font-size: 18px">
										<tr>
											<th>序号</th>
											<th>设备名</th>
											<th>设备状态</th>
											<th>出厂日期</th>
											<th>切换状态</th>
									</thead>
									<tbody style="font-size: 15px">
										<c:forEach items="${validatedeviceslist}"
											var="validatedevices">
											<tr>
												<td>${validatedevices.IId}</td>
												<td>${validatedevices.vcName}</td>
												<td><c:if test="${validatedevices.IStatus==1}">
														<span class="badge" style="background-color: green; font-size: 15px;">正常</span>
													</c:if>
												<td>${validatedevices.TTime}</td>
												<td><button type="button" 
												class="btn btn-warning round" 
												onclick="location.href='<%=basePath%>deviceManager/changevalidatedevice?IId=${validatedevices.IId}&IStatus=${validatedevices.IStatus}'">报废</button></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<ul class="pager">
									<c:choose>
										<c:when test="${page.pageNo>1}">
											<li><a
												href="<%=basePath%>deviceManager/validatedeviceList?pageNo=${page.pageNo-1}">&larr;
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
												href="<%=basePath%>deviceManager/validatedeviceList?pageNo=${page.pageNo+1}">下一页
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
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>

	<!-- 	Bootstrap core JavaScript
    ==================================================
	Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="/IntelligentMattress/WebContent/WEB-INF/js/jquery-3.1.1.min.js"></script>
	<script
		src="IntelligentMattress/WebContent/WEB-INF//js/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script
		src="/IntelligentMattress/WebContent/WEB-INF/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>













