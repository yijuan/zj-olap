
<%@ page import="com.surelution.zjolap.Customer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
	    <div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					保底客存警告 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 警告</a></li>
					<li class="active">客户警告</li>
					<li class="active">保底客存警告</li>
				</ol>
				</section>
	<section class="content">
	<div class="box box-info">
                <div class="box-header with-border">
                  <h4><g:message code="default.list.label" args="[entityName]" /></h4>
                </div>
                <div class="box-body">	
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<form action="listBaseStockWarning">
			   <fieldset class="form">
			   <sec:ifAnyGranted roles="ROLE_ADMIN">
						<div class="fieldcontain  ">
							<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
								<g:message code="salesOrder.branch.label" default="Branch" />
							</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                                 <g:select id="branch" name="branchId" from="${com.surelution.zjolap.Branch.list()}"  optionKey="id" optionValue="name" value="${params['branchId']}" class="many-to-one form-control"/>
	                               </div>
	                            </dd>
	                        </dl>						
						</div>
				</sec:ifAnyGranted>
				<div class="fieldcontain  ">
				          <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
								<g:message code="customer.name.label" default="Branch" />
							</label></dt>
	                           <dd>
	                               <div class="col-md-3">
	                               <g:textField name="customerName" value="${params.customerName}" class="form-control"/>
	                               </div>
	                            </dd>
	                        </dl>						
						</div>
				</fieldset>
				<fieldset class="buttons">
						<g:submitButton name="search" class="search btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />
				</fieldset>
			</form>
			</div>
			</div>
			
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'customer.name.label', default: 'Name')}" params="${params}" class="alink"/>
						
						<g:sortableColumn property="tel" title="${message(code: 'customer.tel.label', default: 'tel')}" params="${params}" class="alink"/>
					   
					    <th>
					    	${message(code: 'customer.customerType.label', default: 'customerType')}
					    </th>
					     <th>
					      	 油品类型
					    </th>
					     <th>
					       	油品品号
					    </th>
					    <th>
					    	警告客存
					    </th>
					    <th>
					    	实际客存
					    </th>
					     
					</tr>
				</thead>
				<tbody>
				<g:each in="${baseStockWarningInstanceList}" status="i" var="wrct">
					<tr>
						<%--<td>${fieldValue(bean: customerInstance, field: "id")}</td>
					
						--%><td>${wrct?.customerBranch?.customer?.name}</td>
						
						<td>${wrct?.customerBranch?.customer?.tel}</td>
						
						<td>${wrct?.customerBranch?.customer?.customerType?.name}</td>
						
						<td>${wrct?.rule?.gasType?.category?.name}</td>
						
						<td>${wrct?.rule?.gasType?.name}</td>
						
						<td>${wrct?.warningQty}</td>
						
						<td><g:formatNumber number="${wrct?.baseQty}" format=".###"/></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			
			<div class="list-page" style="margin-bottom: 25px;">
				<g:paginate total="${baseStockWarningInstanceTotal}"  class="pagination"/>		
		    </div>
			
		</section>
		</div>
	</body>
</html>
