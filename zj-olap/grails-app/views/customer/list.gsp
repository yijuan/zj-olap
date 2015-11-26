
<%@ page import="com.surelution.zjolap.Customer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<r:require module="jquery-ui"/>
<script type="text/javascript" src="${resource(file:'js/jquery.stickytableheaders.js') }"></script>
	<script type="text/javascript">
	        $(document).ready(function()
	        {
	          $("table").stickyTableHeaders({ scrollableArea: $(".scaffold-list")[0]});
	        })
	     </script>
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
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:40%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					客户管理 <small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 客户</a></li>
					<li class="active">客户管理</li>
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
          <g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
          <div class="box box-info">
                <div class="box-header with-border">
                 <h4><g:message code="default.list.label" args="[entityName]" /></h4>
                </div>
                <div class="box-body">	
			
			<form action="list">
			   <fieldset class="form">
			    <dl class="dl-horizontal" style="margin-left:-80px;">
	                           <dt><label for="branch">
							<g:message code="customer.name.label" default="Branch" />
					</label></dt>
	                           <dd>
	                               <div class="col-xs-3">
	                               <g:textField name="customerName" value="${params.customerName}" class="form-control"/>
	                               </div>
	                            </dd>
	                        </dl>
				</fieldset>
				<fieldset class="buttons">
						<g:submitButton name="search" class="search btn btn-primary" value="${message(code: 'default.button.search.label', default: 'Search')}" />
				<span class="menuButton"><g:link class="excel btn btn-primary" action="downloadExcel">下载excel</g:link></span>
				</fieldset>
			</form>
			</div>
			</div>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
						<%--<g:sortableColumn property="id" title="${message(code: 'customer.id.label', default: 'Id')}" params="${params}"/>
					
						--%><g:sortableColumn property="name" title="${message(code: 'customer.name.label', default: 'Name')}" params="${params}" class="alink"/>
						
						<g:sortableColumn property="tel" title="${message(code: 'customer.tel.label', default: 'tel')}" params="${params}" class="alink"/>
					   <g:sortableColumn property="customerType" title="${message(code: 'customer.customerType.label', default: 'customerType')}" params="${params}" class="alink"/>
					    <g:sortableColumn property="branch" title="提报分公司" params="${params}" class="alink"/>
					    
					   
					     <th>
					    	警告
					    </th>
					 
					   <%--<sec:ifAnyGranted roles="ROLE_ADMIN">
					      <th>操作</th>
					   </sec:ifAnyGranted>
					     
					--%></tr>
				</thead>
				<tbody>
				<g:each in="${customerInstanceList}" status="i" var="customerInstance">
					<tr>
						<td><g:link action="show" id="${customerInstance.customer.id}">${fieldValue(bean: customerInstance.customer, field: "name")}</g:link></td>
						
						<td>${fieldValue(bean: customerInstance.customer, field: "tel")}</td>
						
						<td>${customerInstance.customer?.customerType.name}</td>
						
						<td>${customerInstance.branch?.name}</td>
						
						
						<td>
							<g:link controller="warningRuleToBranchCustomer"  action="listByCustomer"  params='[customerId:"${customerInstance.customer.id}"]'>警告设置</g:link>
						</td>
						
						<%--<sec:ifAnyGranted roles="ROLE_ADMIN">
						    <td>
					      <a href="${createLink(action:'delete',controller:'customer',id:customerInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除销售台账"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${customerInstance?.id}" params="${params }" data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新销售台账"></span></g:link>
					    
					    
					    </td>
					    </sec:ifAnyGranted>
					--%></tr>
				</g:each>
				</tbody>
			</table>
			
			<div style="height: 50px;">
			<div class="list-page">
				<g:paginate total="${customerInstanceTotal}"  params="${params}" class="pagination"/>
			</div>
		</div>
			</section>
			</div>
	</body>
</html>
