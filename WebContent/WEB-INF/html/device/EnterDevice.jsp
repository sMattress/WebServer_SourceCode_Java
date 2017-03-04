

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

<title>录入设备</title>

<!-- Bootstrap core CSS -->

<link rel="stylesheet" href="../css/bootstrap.css" />
<link rel="stylesheet" href="../css/bootstrapValidator.css" />
<script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/bootstrapValidator.js"></script>




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
						<li role="presentation"><a
							href="<%=basePath%>deviceManager/validatedeviceList">工作设备</a></li>
						<li role="presentation"><a
							href="<%=basePath%>deviceManager/invalidatedeviceList">报废设备</a></li>
						<li class="active" role="presentation"><a
							href="<%=basePath%>deviceManager/enterdevice">录入设备</a></li>

					</ul>
					<div class="tab-content" style="margin: 30px 10px">
						<div class="tab-pane active" id="panel-enterdevicelist">
							<div class="col-md-6 col-md-offset-3">
								<div class="panel-body">
									<form id="enterForm" method="post"
										data-bv-message="This value is not valid"
										data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
										data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
										data-bv-feedbackicons-validating="glyphicon glyphicon-refresh">
										<div class="form-group">
											<label class="control-label">设备名称</label> <input type="text"
												class="form-control" name="vcName" placeholder="设备名称"
												value="${vcName}" data-bv-trigger="keyup" required
												data-bv-notempty-message="不能为空" />
										</div>
										<div class="form-group">
											<button type="submit"
												class="btn btn-lg btn-primary btn-block">确定</button>				
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#enterForm').bootstrapValidator();
		});
/* 		$("#releasetime").val();
		var releasetime = document.getElementById("TTime").value; */
	</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="../js/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>













