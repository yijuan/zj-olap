
<%@ page import="com.surelution.zjolap.Customer"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="jarvis">
<g:set var="entityName"
	value="${message(code: 'customer.label', default: 'Customer')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<r:require module="jquery-ui"/>
<script type="text/javascript" src="${resource(file:'js/jquery.stickytableheaders.js') }"></script>
	<script type="text/javascript">
	        $(document).ready(function()
	        {

	          $("table").stickyTableHeaders({ scrollableArea: $(".scaffold-list")[0]});
	        })
	     </script>
		
</head>
<body>
<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					客户管理审批 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客户</a></li>
					<li class="active">客户管理审批</li>
				</ol>
				</section>
	<section class="content">
	<div class="box box-info">
                <div class="box-header with-border">
                 <h4>客户查询</h4>
                </div>
                <div class="box-body">	
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<form action="listApprove">
			<fieldset class="form">
			<dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch"> <g:message code="customer.name.label"
						default="Branch" />
				</label></dt>
	                           <dd>
	                               <div class="col-xs-3">
	                               <g:textField name="customerName" value="${params.customerName}" class="form-control"/>
	                               </div>
	                            </dd>
	                        </dl>
				
				
			</fieldset>
			<fieldset class="buttons">
				<g:submitButton name="search" class="search btn btn-primary"
					value="${message(code: 'default.button.search.label', default: 'Search')}" />
			</fieldset>
		</form>
		</div>
		</div>
		<div id="list-salesOrder"  class="scaffold-list"
			style="height: 600px; overflow: scroll;">
		<table style="width: 1500px;" class="table table-bordered  table-striped">
			<thead>
				<tr>
					<g:sortableColumn property="name"
						title="${message(code: 'customer.name.label', default: 'Name')}"
						params="${params}" class="alink"/>

					<g:sortableColumn property="tel"
						title="${message(code: 'customer.tel.label', default: 'tel')}"
						params="${params}" class="alink"/>

					<th>
						${message(code: 'customer.customerType.label', default: 'customerType')}
					</th>
					<!-- 
					  <th>增加的公司</th>
					   -->
					<th>提交公司</th>
					<th>提交时间</th>
					<th>状态</th>
					<th>省公司操作原因</th>
					 <sec:ifAnyGranted roles="ROLE_ADMIN">
					<th style="width: 300px">审批</th>
					</sec:ifAnyGranted>
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${customerInstanceList}" status="i"
					var="customerInstance">
					<tr>
						<td>
						   <g:if test="${customerInstance?.status !='DISABLE'}">
						       ${fieldValue(bean: customerInstance, field: "name")}
						   </g:if>
						   <g:else>
						    <g:link action="show" target='_blank' id="${customerInstance.id}">
							${fieldValue(bean: customerInstance, field: "name")}
							</g:link>
							</g:else>
						</td>

						<td>
							${fieldValue(bean: customerInstance, field: "tel")}
						</td>

						<td>
							${customerInstance?.customerType?.name}
						</td>

						<!-- 
						<td>${customerInstance?.addByBranch?.name}</td>
						 -->
			
						<td>
							${customerInstance?.updateByBranch?.name}
						</td>
			
						<td>
						<g:formatDate date="${customerInstance?.dateCreated}" format="yyyy-MM-dd"/>
						</td>
						
						<td>
							${customerInstance?.statusName}
						</td>
                    
						<td>
							${customerInstance?.reason}
						</td>

						 <sec:ifAnyGranted roles="ROLE_ADMIN">
						 
						<td style="width:30%;">
						<g:if test="${customerInstance?.status !='DISABLE' && customerInstance?.status !='ADDD'}">
						    
						    <g:form action="saveApprove" method="post">
						       <g:hiddenField name="id" value="${customerInstance.id}"/>
						       <div class="col-md-2"> <g:actionSubmit action="saveApprove" value="同意" class="btn btn-default"/></div>
						      
						     <div class="col-md-2">   <g:actionSubmit action="saveDisApprove" value="驳回" class="btn btn-default"/></div>
						    <div class="col-xs-2">原因：</div>
						    <div class="col-md-6"><g:textField name="reason" value="${customerInstance.reason}" class="form-control"/></div>
						    </g:form>
							
							</g:if>
							</td>
							
							</sec:ifAnyGranted>
						     
						 
					</tr>
				</g:each>
			</tbody>
		</table>
		
		<div class="list-page">
			<g:paginate total="${customerInstanceTotal}" params="${params}" class="pagination"/>
		</div>
		</div>
</section>
</div>
</body>
</html>
