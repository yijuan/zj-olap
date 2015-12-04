<%@ page import="com.surelution.zjolap.WarningRuleToBranchCustomer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<script type="text/javascript">
		 $(document).ready(function() {
			   $("a[data-target=#customerModal]").click(function(event) {
							$(this).data('customerModal',null)
						    event.preventDefault();
						    var target = $(this).attr("href"); 
							    $("#customerModal").load(target, function() { 
							       $("#customerModal").addClass("modalstyle")      
				                   $("#customerModal").modal('show');  			    	
						    });
		    
		});
		   })

		</script>
		
		<style type="text/css">
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:50%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	    <div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					设置客户警告 <small></small>
				</h1>
				<%--<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 警告</a></li>
					<li class="active">设置客户警告</li>
				</ol>
				--%></section>
	<section class="content">
	    <div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
			<g:link class="create" action="create" data-toggle="modal" data-target="#myModal">新增客户警告</g:link>
			<!-- 新增台账的dialog -->	
			 <div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>		
       </div>
	    
		<div id="list-warningRuleToBranchCustomer" class="content scaffold-list">
			<h4>客户警告列表</h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<th><g:message code="warningRuleToBranchCustomer.branch.label" default="Branch" /></th>
						<th><g:message code="warningRuleToBranchCustomer.customer.label" default="Customer" /></th>
						  <th>
					      	 油品类型
					    </th>
					     <th>
					       	油品品号
					    </th>
						<th><g:message code="warningRuleToBranchCustomer.rule.label" default="Rule" /></th>
						<th><g:message code="warningRuleToBranchCustomer.value.label" default="Rule" /></th>
					   <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${warningRuleToBranchCustomerInstanceList}" status="i" var="warningRuleToBranchCustomerInstance">
					<tr>
						<td>${warningRuleToBranchCustomerInstance?.customerBranch?.branch?.name}</td>
						<td>${warningRuleToBranchCustomerInstance?.customerBranch?.customer?.name}</td>
						<td>${warningRuleToBranchCustomerInstance?.rule?.gasType?.category?.name}</td>
						<td>${warningRuleToBranchCustomerInstance?.rule?.gasType?.name}</td>
						<td>${warningRuleToBranchCustomerInstance?.rule?.typeName}</td>
						<td>${warningRuleToBranchCustomerInstance?.rule?.value}</td>
						<td>
					        <a href="${createLink(action:'delete',controller:'warningRuleToBranchCustomer',id:warningRuleToBranchCustomerInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除客户警告"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${warningRuleToBranchCustomerInstance?.id}"  data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新客户警告"></span></g:link>
					    
					    </td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="modal" id="customerModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>
			<div class="list-page" id="paginateButtons">
				<g:paginate total="${warningRuleToBranchCustomerInstanceTotal}" class="pagination"/>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>