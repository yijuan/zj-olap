<!DOCTYPE html>
<html>
<head>
<r:layoutResources />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>中国石油浙江销售分公司经营分析系统</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="${resource(dir:'jarvis/css',file:'AdminLTE.min.css')}">
<link rel="stylesheet" href="${resource(dir:'jarvis/css',file:'skin-blue.min.css')}">
<link rel="stylesheet" href="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap.css">
<link rel="stylesheet" href="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="http://cdn.staticfile.org/jquery/2.1.1-rc2/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${resource(dir:'jarvis/css',file:'all.css') }">
<script src="${resource(dir:'jarvis/js',file:'app.min.js') }"></script>
<link rel="stylesheet" href="${resource(dir:'jarvis/css',file:'bootstrap-datetimepicker.min.css') }">
<script src="${resource(dir:'jarvis/js',file:'bootstrap-datetimepicker.min.js') }"></script>
<script src="${resource(dir:'jarvis/js',file:'bootstrap-datetimepicker.zh-CN.js') }"></script>
<script src="${resource(dir:'jarvis/js',file:'jquery-ui.min.js') }"></script>
<script src="${resource(dir:'jarvis/js',file:'jquery.cookie.js') }"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <g:layoutHead/>
  
  <style type="text/css">
     .current{ font-weight:bold}
  </style>
</head>
<script type="text/javascript">
    $(document).ready(function(){
         $(".treeview-menu li a").click(function(){
            	   $.cookie("navstation", $(this).html(), { path: "/" });           	   
             })
        })
</script>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
            <!-- Logo -->
			<a href="" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><b>销售</b>分析</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>浙江</b>销售分析系统</span>
			</a>
			
			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				
				<div style="float: left; color: white; line-height: 100%; margin-left: 30%;"><h4>中国石油浙江销售分公司经营分析系统
				   </h4></div>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
				   
					<ul class="nav navbar-nav sidebar-menu">				
						<li class="dropdown user user-menu treeview" id="alink">
                     <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
              <span class="hidden-xs">当前用户：<sec:username /></span>
            </a>
            <ul class="dropdown-menu" id="dropdown-menu"> 
              <li class="user-header">
                <p>
                                                            中国石油浙江销售分公司
                  <small>经营分析系统</small>
                </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                 <g:link controller="user" action="showChangePw" class="btn btn-default btn-flat">修改密码</g:link>
                </div>
                
                <div class="pull-right">
                  <g:link controller="logout" class="btn btn-default btn-flat">退出登录</g:link>
                </div>
              </li>
            </ul>
          </li>
								
						<!-- Control Sidebar Toggle Button -->
						<%--<li><a href="#" data-toggle="control-sidebar"><i
								class="fa fa-gears"></i></a></li>
					--%></ul>
				</div>
			</nav>
		</header>
		
		<aside class="main-sidebar">			
			<section class="sidebar">				
				<ul class="sidebar-menu">
					<li class="header">HEADER</li>					
					<!-- 一个导航链接开始 -->
					<li class="treeview"><a href="#"><i class="fa fa-th-list"></i>
							<span>台账</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="salesOrder" action="list">销售台账</g:link></li>
							<li><a href="#" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="salesOrder" action="listApprove">台账审批</g:link></li>
							<sec:ifAnyGranted roles="ROLE_ADMIN">
							<li><a href="#" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="salesOrder" action="changeFMstatusClose">关闭台账账期</g:link></li>
				            <li><a href="#" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="salesOrder" action="changeFMstatusOpen">开启台账账期</g:link></li>
							</sec:ifAnyGranted>
						</ul></li>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<li class="treeview"><a href="#"><i class="fa  fa-users"></i>
							<span>客存</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerStock" action="list">提货单</g:link></li>
				            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerStock" action="listApprove">提货单审批</g:link></li>
				            <sec:ifAnyGranted roles="ROLE_ADMIN">
				            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerStock" action="changeFMstatusClose">关闭客存账期</g:link></li>
				            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerStock" action="changeFMstatusOpen">开启客存账期</g:link></li>
				            </sec:ifAnyGranted>
						</ul></li>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<li class="treeview"><a href="#"><i class="fa  fa-upload"></i>
							<span>数据上传</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="excelUpload" action="createSalesOrder">销售数据上传</g:link></li>
				            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerStock" action="initUploadExcel">提货数据上传</g:link></li>
						</ul></li>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<li class="treeview"><a href="#"><i class="fa  fa-warning (alias)"></i>
							<span>警告</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="warningRule">警告规则</g:link></li>
			                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="warningRuleToBranchCustomer">设置客户警告</g:link></li>
			                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="warningRule" action="listBaseStockWarning">保底客存警告</g:link></li>
			                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="warningRule" action="listDaysStockWarning">超期未提油警告</g:link></li>
			                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="warningRule" action="listDaysSalesOrderWarning">超期无订单警告</g:link></li>       
						</ul>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<li class="treeview"><a href="#"><i class="fa fa-user"></i>
							<span>客户</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="salesOrder" action="listNewCustomerCount">新客户数量</g:link></li>
			                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerVisting" action="list">客户回访记录</g:link></li>
			                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customer">客户管理</g:link></li>
				            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customer" action="listApprove">客户管理审批</g:link></li>
						</ul></li>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<sec:ifAnyGranted roles="ROLE_ADMIN">
					<li class="treeview"><a href="#"><i class="fa fa-share"></i>
							<span>分公司管理</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">												          
					            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="branch">分公司管理</g:link></li>
					            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="branchGroup">分公司组管理</g:link></li>
					            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="sales">客户经理管理</g:link></li>
					            <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="user">用户管理</g:link></li>				            
						</ul></li>
						</sec:ifAnyGranted>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<sec:ifAnyGranted roles="ROLE_ADMIN">
					<li class="treeview"><a href="#"><i class="fa fa-asterisk"></i>
							<span>油品管理</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">							
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="category">油品品种管理</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="gasType">油品品号管理</g:link></li>				            
						</ul></li>
						</sec:ifAnyGranted>
					<!-- 一个导航链接结束 -->
					
					<!-- 一个导航链接开始 -->
					<sec:ifAnyGranted roles="ROLE_ADMIN">
					<li class="treeview"><a href="#"><i class="fa fa-link"></i>
							<span>系统设置</span> <i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">	
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerType">销售类别管理</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerTypeLevel2">机构类型管理</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerTypeLevel3">工业分类管理</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="salingType">销售环节</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="price">到位价管理</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="customerVistingType">回访方式设置</g:link></li>
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="thidrFactor">影响因素记录</g:link></li>  
				                <li><a href="" style="float:left;"><i class="fa fa-circle-thin"></i></a><g:link controller="thidrFactorType">影响因素设置</g:link></li>  
						</ul></li>
						</sec:ifAnyGranted>
					<!-- 一个导航链接结束 -->
						
				</ul>
				<!-- /.sidebar-menu -->
			</section>
			<!-- /.sidebar -->
		</aside>
 <g:layoutBody/>
 
				
		<!-- Main Footer -->
		<footer class="main-footer">
			<!-- To the right -->
			<div class="pull-right hidden-xs"></div>
			<!-- Default to the left -->
			<strong>Copyright &copy; 2015 <a href="http://www.sh-hansi.com" target="_blank">上海悍思企业管理咨询有限公司</a>.
			</strong> All rights reserved.
		</footer>

		<!-- Control Sidebar -->
		<%--<aside class="control-sidebar control-sidebar-dark">
			<!-- Tab panes -->
			<div class="tab-content">
				<!-- Home tab content -->
				<div class="tab-pane active" id="control-sidebar-home-tab">
					这里显示的是设置按钮的内容
					
				</div>
		   </div>
		</aside>
		--%><!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->

	<!-- jQuery 2.1.4 -->
	<br>
	<script type="text/javascript">
         var navstation = $.cookie("navstation");
        if(navstation != null){
            $(".treeview-menu li a").each(function(){
            if($(this).html() == navstation){
               $(this).parent().parent().css("display","block");   
             //  $(".treeview-menu li a").removeClass("current")
               $(this).addClass("current")
               $(this).css("color","white")
        }
    });
}
</script>
</body>
</html>
