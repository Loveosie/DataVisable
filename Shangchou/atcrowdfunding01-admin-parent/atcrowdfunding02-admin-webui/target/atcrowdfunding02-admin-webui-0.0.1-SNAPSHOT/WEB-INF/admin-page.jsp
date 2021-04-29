<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link href="css/pagination.css" rel="stylesheet"/>
<script type="text/javascript">
	//pagination分页处理
	$(function () {
		//初始化pagination
		var initPagination = initPagination();

		//初始化导航条
		function 	initPagination(){
			var totalRecord  = ${requestScope.pageInfo.total};
			var properties = {
				num_edge_entries: 3, //边缘页数
				num_display_entries: 5, //主体页数
				callback: pageselectCallback,
				items_per_page:${requestScope.pageInfo.pageSize},
				current_page: ${requestScope.pageInfo.pageNum-1}, //Page Index管理页面从零开始, PageInfo从1开始
				prev_text: "上一页",
				next_text: "下一页"
			};
			//生成导航条
			$("#Pagination").pagination(totalRecord,properties);
		}
		//点击页码回调, Pagination传递的页码由PageIndex给到从0开始
		function pageselectCallback(pageIndex, jquery){
			var pageNum = pageIndex + 1;

			// 跳转页面
			window.location.href = "admin/get/page.html?pageNum="+pageNum + "&keyword=${param.keyword}";
			//取消超链接的默认行为
			return false;
		}
	});
</script>
<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
					</div>
					<div class="panel-body">
						<form action="admin/get/page.html" method="post" class="form-inline" role="form" style="float:left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text" name="keyword" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
						</form>
						<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
						<%--<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>--%>
						<a class="btn btn-primary"  style="float:right;"  href="admin/to/add/page.html" ><i class="glyphicon glyphicon-plus"></i> 新增</a>
						<br>
						<hr style="clear:both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
								<tr>
									<th width="30">#</th>
									<th width="30"><input type="checkbox"></th>
									<th>账号</th>
									<th>名称</th>
									<th>邮箱地址</th>
									<th width="100">操作</th>
								</tr>
								</thead>
								<tbody>
								<c:if test="${empty requestScope.pageInfo.list}">
									<td colspan="6" align="center">nononono没有您想要的数据</td>
								</c:if>
								<c:if test="${!empty requestScope.pageInfo.list}">
									<c:forEach items="${requestScope.pageInfo.list}" var="admin">
										<tr>
											<td>1</td>
											<td><input type="checkbox"></td>
											<td>${admin.loginAcct}</td>
											<td>${admin.userName}</td>
											<td>${admin.email}</td>
											<td>
												<a href="assign/to/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
												<a href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
												<a href="admin/delete/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></a><br/>
											</td>
										</tr>
									</c:forEach>

								</c:if>

								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>