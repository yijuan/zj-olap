<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<r:layoutResources />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>中国石油浙江销售分公司经营分析系统</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css?v=21')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<g:layoutHead/>
	</head>
	<body>
	    <div id="container">
	    	<div id="header">
	    		<div style="float:left">
		    		<a href="${createLink(uri: '/')}" title="业务分析系统"><img style="border: 0 none;" src="${resource(dir:'images',file:'title.png')}" width="900px" height="80px"/></a>
					<div style='float: right;font-size:12px;color:black;'>
					当前用户：<sec:username />&nbsp;&nbsp;<g:link controller="user" action="showChangePw" style="color:black;font-weight:normal;">更改密码</g:link>&nbsp;&nbsp;<g:link controller="logout" style="color:black;font-weight:normal;">退出</g:link>
					</div>
		    	</div>
	    	</div>
			<div id="leftcol">
			<h2 style="margin: 0 0 .5em 0;">台账</h2>
			<ul style="list-style:none;">
				<li><g:link controller="salesOrder" action="list">销售台账</g:link></li>
				<li><g:link controller="salesOrder" action="listApprove">台账审批</g:link></li>
				<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li><g:link controller="salesOrder" action="changeFMstatusClose">关闭账期</g:link></li>
				<li><g:link controller="salesOrder" action="changeFMstatusOpen">开启账期</g:link></li>
				</sec:ifAnyGranted>
				<%--<li><g:link controller="deliveryTaking" action="list">提货台账</g:link></li>
			--%></ul>
			<%--<h2>业务分析</h2>
			<ul style="list-style:none;">
				<li><g:link controller="reports" action="generalSearch">总体经营分析报表</g:link></li>
				<li><g:link controller="reports" action="searchWithCustomer">分客户类型经营分析报表</g:link></li>
			</ul>
			--%>
			<h2 style="margin: 0 0 .5em 0;">客存</h2>
			<ul style="list-style:none;">
				<li><g:link controller="customerStock" action="list">提货单</g:link></li>
				<li><g:link controller="customerStock" action="listApprove">提货单审批</g:link></li>
				<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li><g:link controller="customerStock" action="changeFMstatusClose">关闭账期</g:link></li>
				<li><g:link controller="customerStock" action="changeFMstatusOpen">开启账期</g:link></li>
				</sec:ifAnyGranted>
			</ul>
			
			<h2>数据上传</h2>
			<ul style="list-style:none;">
				<li><g:link controller="excelUpload" action="createSalesOrder">销售数据上传</g:link></li>
				<li><g:link controller="customerStock" action="initUploadExcel">提货数据上传</g:link></li>
				<%--<li><g:link controller="excelUpload" action="createDelivery">提货数据上传</g:link></li>
			--%></ul>
			
			<h2>警告</h2>
			<ul style="list-style:none;">
			<li><g:link controller="warningRule">警告规则</g:link></li>
			<li><g:link controller="warningRuleToBranchCustomer">设置客户警告</g:link></li>
			<li>---------------------</li>
			<li><g:link controller="warningRule" action="listBaseStockWarning">保底客存警告</g:link></li>
			<li><g:link controller="warningRule" action="listDaysStockWarning">超期未提油警告</g:link></li>
			<li><g:link controller="warningRule" action="listDaysSalesOrderWarning">超期无订单警告</g:link></li>
			</ul>
			
			
			<h2>客户</h2>
			<ul style="list-style:none;">
			<li><g:link controller="salesOrder" action="listNewCustomerCount">新客户数量</g:link></li>
			<li><g:link controller="customerVisting" action="create">客户回访记录</g:link></li>
			</ul>
			<h2>系统设置</h2>
			<ul style="float:center;list-style:none;">
				<sec:ifAnyGranted roles="ROLE_ADMIN">
					<li><g:link controller="thidrFactor">影响因素记录</g:link></li>
					<li><g:link controller="branch">分公司管理</g:link></li>
					<li><g:link controller="branchGroup">分公司组管理</g:link></li>
					<li><g:link controller="user">用户管理</g:link></li>
					<li><g:link controller="sales">客户经理管理</g:link></li>
				</sec:ifAnyGranted>
				
				<li><g:link controller="customer">客户管理</g:link></li>
				<li><g:link controller="customer" action="listApprove">客户管理审批</g:link></li>
				
				<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li><g:link controller="category">油品品种管理</g:link></li>
				<li><g:link controller="gasType">油品品号管理</g:link></li>
				<li><g:link controller="customerType">销售类别管理</g:link></li>
				<li><g:link controller="customerTypeLevel2">机构类型管理</g:link></li>
				<li><g:link controller="customerTypeLevel3">工业分类管理</g:link></li>
				<li><g:link controller="salingType">销售环节</g:link></li>
				<li><g:link controller="price">到位价管理</g:link></li>
				<li><g:link controller="customerVistingType">回访方式设置</g:link></li>
				<li><g:link controller="thidrFactorType">影响因素设置</g:link></li>
				<%--<li><g:link controller="industry">客户行业管理</g:link></li>
				--%></sec:ifAnyGranted>
			</ul>
			</div>

			<div id="main_content"><g:layoutBody /></div>
			<div id="footer">
			<p>
			_________________________________________________________________
			</p>
				&copy;<a href="http://www.sh-hansi.com" target="_blank">上海悍思企业管理咨询有限公司</a>技术支持
			</div>
	    </div>
	</body>
</html>
