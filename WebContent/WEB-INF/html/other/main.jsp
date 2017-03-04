<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="../images/ic_system.png">

<title>智能床垫用户管理</title>
<link href="../css/signin.css" rel="stylesheet">
<link href="../css/base.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

	<table>
		<thead>
			<tr>
				<th>用户名</th>
				
				<th>密码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${manager}" var="manager">
				<tr>
					<td>${manager.vcName}</td>
					
					<td>${manager.vcPassword}
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form:form action = "../customer/show" modelAttribute="customersinfo" class="form-signin"
				role="form" method="post">
				<button class="btn btn-lg btn-primary btn-block" type="submit">用户管理</button>
			</form:form>
</body>
</html>