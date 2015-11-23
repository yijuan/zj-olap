
<%@ page import="com.surelution.zjolap.WarningRuleToBranchCustomer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					警告 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i>警告</a></li>
					<li class="active"></li>
				</ol>
				</section>
	<section class="content">
	
		<div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
			<g:link class="create" action="create" data-toggle="modal" data-target="#myModal"><g:message code="default.new.label" args="[entityName]" /></g:link>
			<!-- 新增台账的dialog -->	
			 <div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>		
       </div>
		<div id="list-warningRuleToBranchCustomer" >
			<h4><g:message code="default.list.label" args="[entityName]" /></h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<th><g:message code="warningRuleToBranchCustomer.branch.label" default="Branch" /></th>
						<th><g:message code="warningRuleToBranchCustomer.customer.label" default="Customer" /></th>
					
						<th><g:message code="warningRuleToBranchCustomer.rule.label" default="Rule" /></th>
						<th><g:message code="warningRuleToBranchCustomer.value.label" default="Rule" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${warningRuleToBranchCustomerInstanceList}" status="i" var="warningRuleToBranchCustomerInstance">
					<tr>
					
						<td><g:link action="show" id="${warningRuleToBranchCustomerInstance.id}">${warningRuleToBranchCustomerInstance?.customerBranch?.branch?.name}</g:link></td>
						<td>${warningRuleToBranchCustomerInstance?.customerBranch?.customer?.name}</td>
						<td>${warningRuleToBranchCustomerInstance?.rule?.typeName}</td>
						<td>${warningRuleToBranchCustomerInstance?.rule?.value}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="list-page" id="paginateButtons">
				<g:paginate total="${warningRuleToBranchCustomerInstanceTotal}" params="${['customerBranchId':customerBranch.id]}" class="pagination"/>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>
