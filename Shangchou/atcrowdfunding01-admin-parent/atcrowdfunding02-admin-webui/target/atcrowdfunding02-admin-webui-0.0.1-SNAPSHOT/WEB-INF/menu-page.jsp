<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="application/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="application/javascript" src="crowd/my-menu.js"></script>
<script type="application/javascript">
    $(function () {
        //调用封装好的函数初始化树形结构
        generateTree();

        //给添加子节点按钮绑定单击响应函数: 弹出模态框
        $("#treeDemo").on("click",".addBtn",function () {
            window.pid = this.id;
            $("#menuAddModal").modal("show");
            return false;
        })
        // 给编辑按钮绑定单击响应函数, 弹出模态框并回显
        $("#treeDemo").on("click",".editBtn",function () {
            window.id = this.id;
            $("#menuEditModal").modal("show");
            //获取zTreeObj对象(tree)
           var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            //根据节点id查询节点对象(node)
           var treeNode = zTreeObj.getNodeByParam("id",window.id);
            //回显表单
            $("#menuEditModal [name=name]").val(treeNode.name);
            $("#menuEditModal [name=url]").val(treeNode.url);
            $("#menuEditModal [name=icon]").val([treeNode.icon]);
            return false;
        })

        //给删除子节点按钮绑定单击响应函数: 弹出模态框
        $("#treeDemo").on("click",".removeBtn",function () {
            window.id = this.id;
            $("#menuConfirmModal").modal("show");
            //获取zTreeObj对象(tree)
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            //根据节点id查询节点对象(node)
            var treeNode = zTreeObj.getNodeByParam("id",window.id);
            //回显表单: 删除名称
            $("#removeNodeSpan").html("<i class='" + treeNode.icon + "'></i>"+treeNode.name);
            return false;
        })

        // 点击保存,新增子节点信息保存到数据库
        $("#menuSaveBtn").click(function (){
            //获取数据后发送ajax请求
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
            $.ajax({
                "url":"menu/save.json",
                "type":"post",
                "data": {
                    "pid":window.pid,
                    "name":name,
                    "url": url,
                    "icon": icon
                },
                "dataType":"json",
                "success":function (response){
                    var result = response.result;

                    if (result == "SUCCESS"){
                        layer.msg("操作成功!");
                        generateTree();
                    }
                    if (result == "FAILED"){
                        layer.msg("操作失败!");
                    }
                },
                "error":function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuAddModal").modal("hide");

            $("#menuResetBtn").click();
        });

        //点击编辑页面保存编辑后的信息,
        $("#menuEditBtn").click(function (){
            //获取数据后发送ajax请求
            var name = $.trim($("#menuEditModal [name=name]").val());
            var url = $.trim($("#menuEditModal [name=url]").val());
            var icon = $.trim($("#menuEditModal [name=icon]:checked").val());
            $.ajax({
                "url":"menu/update.json",
                "type":"post",
                "data": {
                    "id":window.id,
                    "name":name,
                    "url": url,
                    "icon": icon
                },
                "dataType":"json",
                "success":function (response){
                    var result = response.result;

                    if (result == "SUCCESS"){
                        layer.msg("操作成功!");
                        generateTree();
                    }
                    if (result == "FAILED"){
                        layer.msg("操作失败!");
                    }
                },
                "error":function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuEditModal").modal("hide");
        });

        //确认删除, 异步删除
        $("#confirmBtn").click(function (){
            $.ajax({
                "url":"menu/delete.json",
                "type":"post",
                "data":{
                   "id":  window.id
                },
                "dataType":"json",
                "success":function (response){
                    var result = response.result;

                    if (result == "SUCCESS"){
                        layer.msg("操作成功!");
                        generateTree();
                    }
                    if (result == "FAILED"){
                        layer.msg("操作失败!");
                    }
                },
                "error":function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuConfirmModal").modal("hide");
        });



    });
</script>
<body>

<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%-- 引入模态框, 新增,修改,调整--%>
<%@include file="/WEB-INF/modal-menu-add.jsp"%>
<%@include file="/WEB-INF/modal-menu-edit.jsp"%>
<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
</body>
</html>