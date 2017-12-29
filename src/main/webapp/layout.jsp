<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>crud.html</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
	<style type="text/css">
		html{
			width:100%;
			height:100%;
		}
		body{
			font-size:14px;
			width:100%;
			height:100%;
		}
		a:hover{
			background-color: #F0E68C;
		}
	</style>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	
    <script type="text/javascript">
    	function urlClick(myTitle,myUrl){
    	
    		//判断title='学生管理' tab夜是否存在
    		var ifExist=$("#myTabs").tabs("exists",myTitle);
    		if(!ifExist){
    			// 添加一个选项卡面板  closable:true可关闭的   scrolling="no"去除滚动条
    			$("#myTabs").tabs('add',{
	    			title:myTitle,
	    			closable:true,
	    			//取消滚动条设置为:scrolling="no",表框:frameborder=0
	    			content:'<iframe frameborder=0 width="100%" height="100%" scrolling="no" src="'+myUrl+'"></iframe>',   
    			})  
    		}
    		// 选择一个选项卡面板
    		$("#myTabs").tabs("select",myTitle);
    	}
    </script>
  </head>
  
  <body style="margin:3px">
  	<div class="easyui-layout" style="width:100%;height:100%;margin:0px">
  		<!-- 北部只能设置高度 一般不会设置宽度 -->
		<div data-options="region:'north'" style="height:15%;background-image:url()">
			<div style="height:82%">
				<img alt="" src="js/tt.png" width="100%" height="990px">
			</div>
			<div style="text-align:right;width:85%"><a href="" style="text-decoration: none;">登录</a>
				<span>|&nbsp;</span>
				<a href="" style="text-decoration: none;">安全退出</a>
			</div>
		</div>
		<div data-options="region:'west',split:true" title="导航菜单" style="width:18%;">
			<div class="easyui-accordion" style="width:500px;height:300px;">
				<div title="权限管理" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
					<c:forEach var="menu" items="${requestScope.menuList}">
						<a href="javaScript:urlClick('${menu.menuName}','${pageContext.request.contextPath}${menu.menuUrl}')" style="text-decoration: none;"><img alt="" src="js/themes/icons/man.png" style="margin-top:4px">${menu.menuName}<br/></a>
					</c:forEach>
				</div>
				<div title="系统设置" data-options="iconCls:'icon-help'" style="padding:10px;"></div>
				<div title="TreeMenu" data-options="iconCls:'icon-search'" style="padding:10px;">
					<ul class="easyui-tree">
						<li>
							<span>Foods</span>
							<ul>
								<li>
									<span>Fruits</span>
									<ul>
										<li>apple</li>
										<li>orange</li>
									</ul>
								</li>
								<li>
									<span>Vegetables</span>
									<ul>
										<li>tomato</li>
										<li>carrot</li>
										<li>cabbage</li>
										<li>potato</li>
										<li>lettuce</li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>
				</div>
													
			</div>
		</div>
		<div data-options="region:'center',iconCls:'icon-ok'">
			<!-- 添加选项卡的空件 -->
			<div id="myTabs" class="easyui-tabs" style="width:100%;height:100%">
				<div title="欢迎使用" style="padding:10px">
					<img alt="" src="js/wec.png" width="100%" height="100%">
				</div>
			</div>
		</div>
	</div>
  </body>
</html>