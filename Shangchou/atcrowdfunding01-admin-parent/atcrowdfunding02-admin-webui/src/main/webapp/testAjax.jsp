<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="application/javascript">
    $(function () {

        $("#asynBtn").click(function () {
            console.log("ajax函数之前");
            //"async":false; 同步
            $.ajax({
                "url":"test/ajax/async.html",
                "type":"post",
                "dataType":"text",
                "success":function (response) {
                    console.log("ajax函数内部" + response);
                }
            });
            console.log("ajax函数之后");
        });

    });
</script>
<body>

<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <button id="asynBtn">异步请求测试</button><br/>
        </div>
    </div>
</div>
</body>
</html>