<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="../images/ic_system.png">
<title>知识管理</title>

<!-- Bootstrap core CSS -->
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/bootstrap.css" />
<script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<style type="text/css">
.row {
	margin: 20px 0px;
}

.blog {
	border-bottom: 1px double #8ac007;
}

.text {
	margin-bottom: 20px;
	ext-overflow: -o-ellipsis-lastline;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
	overflow: hidden;
}

.view {
	color: #777;
}

.view:HOVER {
	color: #8ac007;
}
</style>
</head>
<body>

	<!--NavigationHead  -->
	<jsp:include page="/WEB-INF/html/navigation/NavigationHead.jsp"></jsp:include>
	<div class="container">
		<div class="tab-content">
			<div class="tab-pane active" id="viewList">
				<div style="text-align: center; font-size: 30px; color: #666;">
					<a onclick="toAdd()"><span class="glyphicon glyphicon-plus"
						style="margin-right: 10px;"></span>添 加 知 识</a>
				</div>
				<ul class="nav nav-tabs" role="tablist">
					<c:choose>
						<c:when test="${0==labelId}">
							<li role="presentation" class="active"><a
								href="<%=basePath%>knowledge/knowledgeList">全部</a></li>
						</c:when>
						<c:otherwise>
							<li role="presentation"><a
								href="<%=basePath%>knowledge/knowledgeList">全部</a></li>
						</c:otherwise>
					</c:choose>
					<c:if test="${lablelist!=null && fn:length(lablelist) > 0}">
						<c:forEach items="${lablelist}" var="lable">
							<c:choose>
								<c:when test="${lable.IId==labelId}">
									<li role="presentation" class="active"><a
										href="<%=basePath%>knowledge/knowledgeList?labelId=${lable.IId}">${lable.vcName}</a></li>
								</c:when>
								<c:otherwise>
									<li role="presentation"><a
										href="<%=basePath%>knowledge/knowledgeList?labelId=${lable.IId}">${lable.vcName}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
				</ul>
				<c:choose>
					<c:when
						test="${knowledgelist!=null && fn:length(knowledgelist) > 0}">
						<c:forEach items="${knowledgelist}" var="view">
							<div class="row blog">
								<h3>
									<a class="view" id="${view.knowledge.IId}">${view.knowledge.vcTitle}</a>
								</h3>
								<div style="margin-bottom: 15px;">
									<c:if
										test="${view.labelList!=null && fn:length(view.labelList) > 0}">
										<c:forEach items="${view.labelList}" var="label">
											<span id="${label.IId}" class="badge"
												style="background-color: #8ac007;">${label.vcName}</span>&nbsp;&nbsp;
										</c:forEach>
									</c:if>
								</div>
								<div class="text">${view.knowledge.txContent}</div>
							</div>
						</c:forEach>

						<ul class="pager">
							<c:choose>
								<c:when test="${page.pageNo>1}">
									<li><a
										href="<%=basePath%>knowledge/knowledgeList?labelId=${labelId}&pageNo=${page.pageNo-1}">&larr;
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
										href="<%=basePath%>knowledge/knowledgeList?labelId=${labelId}&pageNo=${page.pageNo+1}">下一页
											&rarr;</a>
									<li>
								</c:when>
								<c:otherwise>
									<li class="disabled"><a>下一页 &rarr;</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</c:when>
					<c:otherwise>
						<p class="row">这个标签还没有相关知识哦！</p>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- 编辑知识 -->
			<div class="tab-pane" id="addKnowledge">
				<ol class="breadcrumb">
					<li><a onclick="viewList()">知识列表</a></li>
					<li class="active">编辑知识</li>
				</ol>
				<div class="row">
					<div class="col-md-9">
						<form action="saveKnowledge" method="post" id="editForm"
							enctype="multipart/form-data">
							<div class="form-group">
								<label class="control-label">标题</label><input type="text"
									class="form-control" id="title" name="vcTitle" />
							</div>
							<div class="form-group">
								<input type="text" class="form-control" id="resLabel"
									name="vcLabelId" style="display: none;" /> <span
									class="glyphicon glyphicon-tag">标签</span>
								<div>
									<c:if test="${lablelist!=null && fn:length(lablelist) > 0}">
										<c:forEach items="${lablelist}" var="lable">
											<label class="checkbox-inline"><input
												name="checkLabel" value="${lable.IId}" type="checkbox">${lable.vcName}</label>
										</c:forEach>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">内容</label>
								<textarea name="txContent" id="editHtml" rows="10"
									style="width: 100%"></textarea>
							</div>
							<input type="number" id="IId" name="IId" style="display: none;" />		
							<a class="btn btn-success" type="button" onclick="save()">保存</a>
							<a class="btn btn-primary" type="button" onclick="preview()">预览</a>
						</form>
					</div>
					<div class="col-md-3">
						<div style='position: relative; z-index: 1;'>
							<img src="../images/iphone.jpg" width="100%" height="100%"
								style="margin-right: 50px" />
						</div>
						<div id="preview"
							style='word-wrap: break-word; overflow: auto; overflow-x: hidden; position: absolute; height: 73%; width: 77%; background-color: white; top: 15%; left: 11.5%; z-index: 2; padding-left: 10px'></div>
					</div>
				</div>
			</div>
			<!-- 查看知识 -->
			<div class="tab-pane" id="viewKnowledge">
				<ol class="breadcrumb">
					<li><a onclick="viewList()">知识列表</a></li>
					<li class="active">知识详情</li>
				</ol>
				<input type="number" id="knowledgeId" style="display: none;" /> <input
					type="text" id="labelIds" style="display: none;" />
				<div style="float: right;">
					<a class="btn btn-primary" type="button" onclick="toEdit()">编辑</a>
					<a class="btn btn-danger" type="button" onclick="toDelete()">删除</a>
				</div>
				<h3 id="knowledgeTitle"></h3>
				<div class="cantainer">
					<div id="knowlegeLabels"></div>
					<div id="knowledgeContent" style="margin-top: 20px;"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function addKnowledge() {
			$("#viewList").fadeOut();
			$("#viewList").removeClass("active");
			$("#viewKnowledge").fadeOut();
			$("#viewKnowledge").removeClass("active");
			$("#addKnowledge").fadeIn();
			$("#addKnowledge").addClass("active");
		}
		function viewList() {
			$("#addKnowledge").fadeOut();
			$("#addKnowledge").removeClass("active");
			$("#viewKnowledge").fadeOut();
			$("#viewKnowledge").removeClass("active");
			$("#viewList").fadeIn();
			$("#viewList").addClass("active");
		}
		function viewKnowledge() {
			$("#addKnowledge").fadeOut();
			$("#addKnowledge").removeClass("active");
			$("#viewList").fadeOut();
			$("#viewList").removeClass("active");
			$("#viewKnowledge").fadeIn();
			$("#viewKnowledge").addClass("active");
		}
		function toAdd() {
			$("#title").val("");
			$("#editHtml").val("");
			$("#preview").html("");
			var obj = document.getElementsByName('checkLabel');
			for (var i = 0; i < obj.length; i++) {
				obj[i].checked = false;
			}
			addKnowledge();
		}
		function save() {
			var title = document.getElementById("title").value;
			if (title.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("请输入知识标题!");
				$("#title").focus();
				return false;
			}
			var content = document.getElementById("editHtml").value;
			if (content.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("请输入知识内容!");
				$("#editHtml").focus();
				return false;
			}
			var obj = document.getElementsByName('checkLabel');
			//取到对象数组后，循环检测它是不是被选中 
			var s = '';
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked) {
					s += obj[i].value + ','; //如果选中，将value添加到变量s中 
				}
			}
			$("#resLabel").val(s.substring(0, s.length - 1));
			//提交到后台
			document.forms["editForm"].submit();
		}
		function preview() {
			var article = document.getElementById("editHtml").value;
			document.getElementById("preview").innerHTML = article;
		}
		$(".view")
				.map(
						function(index, domElement) {
							domElement.onclick = function(e) {
								$("#knowledgeId").val(e.target.id);
								$("#knowledgeTitle").html(e.target.innerText);
								$("#knowledgeContent").html(
										e.path[2].lastElementChild.innerHTML);
								var count = e.path[2].children[1].childElementCount;
								var labels = "";
								var labelIds = "";
								if (count != 0) {
									for (var i = 0; i < count; i++) {
										labelIds += e.path[2].children[1].children[i].id
												+ " ";
										labels += "<span id="+e.path[2].children[1].children[i].id+" class='badge' style='background-color: #8ac007;'>"
												+ e.path[2].children[1].children[i].innerText
												+ "</span>&nbsp;&nbsp;";
									}
								}
								$("#labelIds").val(
										labelIds.substring(0,
												labelIds.length - 1));
								$("#knowlegeLabels").html(labels);
								viewKnowledge();
							};
						});
		function toEdit() {
			$("#IId").val($("#knowledgeId").val());
			$("#title").val($("#knowledgeTitle").html());
			$("#editHtml").val($("#knowledgeContent").html());
			$("#preview").html($("#knowledgeContent").html());
			var labels = ($("#labelIds").val()).split(" ");
			var obj = document.getElementsByName('checkLabel');
			for (var i = 0; i < labels.length; i++) {
				for (var j = 0; j < obj.length; j++) {
					if (labels[i] == obj[j].value) {
						obj[j].checked = true;
					}
				}
			}
			addKnowledge();
		}
		function toDelete() {
			if (confirm("你确定要删除这条知识吗？ ")) {
				$.post("deleteKnowledge", {
					'knowledgeId' : $("#knowledgeId").val()
				}, function() {
					alert("删除成功！");
					window.location.href = "knowledgeList";
				}).fail(function() {
					alert("删除失败！");
				});
			}
		}
	</script>
</body>
<script src="../js/bootstrap.min.js"></script>
</html>