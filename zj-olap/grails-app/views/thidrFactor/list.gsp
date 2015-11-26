
<%@ page import="com.surelution.zjolap.ThidrFactor" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="jarvis">
		<g:set var="entityName" value="${message(code: 'thidrFactor.label', default: 'ThidrFactor')}" />
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
		    .modalstyle{ width: 40%; background-color:#F5F5F5;filter:alpha(opacity=50); margin-left:30%; height:65%; margin-top:5%; border-radius:5px; border: 1px solid #C5CBD7}
		</style>
	</head>
	<body>
	<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					影响因素记录<small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#" style="cursor: none;"><i class="fa fa-dashboard"></i>系统设置</a></li>
					<li class="active">影响因素记录</li>
				</ol>
				</section>
	<section class="content">
	<div class="c1" style=" height: 30px; border-bottom: 1px solid #e5e5e5;margin-bottom:10px;">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
			<g:link class="create" action="create" data-toggle="modal" data-target="#myModal">新增影响因素记录</g:link>
			<!-- 新增台账的dialog -->	
			 <div class="modal fade" id="myModal" role="dialog">
                     <div class="modal-dialog">
                           <!-- Modal content-->
                           <div class="modal-content"> 
                           </div>
                          </div>
                      </div>		
          </div>
		<div id="list-thidrFactor" class="content scaffold-list">
			<h4>影响因素记录列表</h4>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered  table-striped">
				<thead>
					<tr>
					
						<g:sortableColumn property="branch" title="${message(code: 'thidrFactor.branch.label', default: 'Branch')}" class="alink"/>
					
						<g:sortableColumn property="influncedAt" title="${message(code: 'thidrFactor.influncedAt.label', default: 'Influnced At')}" class="alink"/>
					
						<th><g:message code="thidrFactor.type.label" default="Type" /></th>
					
						<g:sortableColumn property="description" title="${message(code: 'thidrFactor.description.label', default: 'Description')}" class="alink"/>
					    <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${thidrFactorInstanceList}" status="i" var="thidrFactorInstance">
					<tr>
					
						<td>
						<g:if test="${thidrFactorInstance?.branch}">${thidrFactorInstance?.branch.name}</g:if>
							<g:else>全省</g:else></td>
					
						<td><g:formatDate date="${thidrFactorInstance.influncedAt}" format="yyyy-MM-dd"/></td>
					
						<td>${thidrFactorInstance.type?.name}</td>
					
						<td>${fieldValue(bean: thidrFactorInstance, field: "description")}</td>
					    <td>
					    <a href="${createLink(action:'delete',controller:'thidrFactor',id:thidrFactorInstance.id)}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="glyphicon glyphicon-trash" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="删除影响记录"></span></a>
		 
		&nbsp;&nbsp;
		
		 <g:link rel="external" action="edit" id="${thidrFactorInstance?.id}"  data-toggle="modal" data-target="#customerModal">
		 <span class="glyphicon glyphicon-pencil" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="更新影响记录"></span></g:link>
					    
					    
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
			<div class="list-page">
				<g:paginate total="${thidrFactorInstanceTotal}"  class="pagination"/>
			</div>
		</div>
		</section>
		</div>
	</body>
</html>
