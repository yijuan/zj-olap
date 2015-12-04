
<%@ page import="com.surelution.zjolap.GasType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'gasType.label', default: 'GasType')}" />
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
					油品品号管理<small></small>
				</h1>
				<%--<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i> 油品管理</a></li>
					<li class="active">油品品号管理</li>
				</ol>
				--%></section>
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
			<h3><g:message code="default.list.label" args="[entityName]" /></h3>
		
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
						<%--<g:sortableColumn property="id" title="${message(code: 'gasType.id.label', default: 'Id')}" />
					
						--%><th><g:message code="gasType.category.label" default="Category" /></th>
					
						<g:sortableColumn property="name" title="${message(code: 'gasType.name.label', default: 'Name')}" class="alink"/>
					   <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${gasTypeInstanceList}" status="i" var="gasTypeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${gasTypeInstance.category.name}</td>
					
						<td>${fieldValue(bean: gasTypeInstance, field: "name")}</td>
					<td>
					    <a href="${createLink(action:'delete',controller:'gasType',id:gasTypeInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除油品品号"></span></a>		 
		&nbsp;&nbsp;		
		 <g:link rel="external" action="edit" id="${gasTypeInstance?.id}" params="${params }"  data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新油品品号"></span></g:link>
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
				<g:paginate total="${gasTypeInstanceTotal}" class="pagination"/>
			</div>
			</div>
		</section>
		</div>
	</body>
</html>
