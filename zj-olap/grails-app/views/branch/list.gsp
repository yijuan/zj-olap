
<%@ page import="com.surelution.zjolap.Branch" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'branch.label', default: 'Branch')}" />
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
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:55%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
				     分公司管理<small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 分公司管理</a></li>
					<li class="active">分公司管理</li>
				</ol>
				</section>
	<section class="content">
			<h4><g:message code="default.list.label" args="[entityName]" /></h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'branch.name.label', default: 'Name')}" class="alink"/>
					
						<th><g:message code="branch.branchGroup.label" default="Branch Group" /></th>
					    <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${branchInstanceList}" status="i" var="branchInstance">
					<tr>
					
						<td>${fieldValue(bean: branchInstance, field: "name")}</td>
					
						<td>${branchInstance.branchGroup?.name}</td>
					<td>
					    <a href="${createLink(action:'delete',controller:'branch',id:branchInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除分公司"></span></a>		 
		&nbsp;&nbsp;		
		 <g:link rel="external" action="edit" id="${branchInstance?.id}" params="${params }" data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新分公司"></span></g:link>
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
			<div style="height: 50px;">
			<div class="list-page">
				<g:paginate total="${branchInstanceTotal}" class="pagination"/>
			</div>
			</div>
			</section>
		</div>
	</body>
</html>
