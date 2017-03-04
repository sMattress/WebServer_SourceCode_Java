<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="../images/ic_system.png">

<title>智能床垫后台管理系统</title>

<link href="../css/signin.css" rel="stylesheet">
<link href="../css/base.css" rel="stylesheet">
<link href="../css/customize.css" rel="stylesheet">
<script type="text/javascript" src="../js/customize.js"></script>
<link rel="stylesheet" href="../css/bootstrap.css" />
<link rel="stylesheet" href="../css/bootstrapValidator.css" />
<script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/bootstrapValidator.js"></script>
</head>
<body>
	<div style="display: none">
		<input type="text" id="message" value="${message}" />
	</div>
	<div class="bg">
		<img src="../images/bg_login.jpg" class="bg-img" />
	</div>
	<div class="center">
		<div class="container">
			<form id="loginForm" method="post" class="form-signin"
				data-bv-message="This value is not valid"
				data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
				data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
				data-bv-feedbackicons-validating="glyphicon glyphicon-refresh">
				<h2 class="form-signin-heading">智能床垫后台管理系统</h2>
				<div class="form-group">
					<label class="control-label">账号</label> <input type="text"
						class="form-control" name="vcName" placeholder="账号" value="${vcName}"
						data-bv-trigger="keyup" required data-bv-notempty-message="账号不能为空" />
				</div>
				<div class="form-group">
					<label class="control-label">密码</label> <input type="password"
						class="form-control" name="vcPassword" placeholder="初始密码为123456" value="${vcPassword}"
						data-bv-trigger="blur" required data-bv-notempty-message="密码不能为空" />
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-lg btn-primary btn-block">登录</button>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#loginForm').bootstrapValidator();
		});
		var msg = document.getElementById("message").value;
		if (msg != undefined && msg != null && msg != "") {
			// alert(msg);
			notify({
				msg : msg,
				type : "info"
			});
		}
	</script>
</body>
</html>